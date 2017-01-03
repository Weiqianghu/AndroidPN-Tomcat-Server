/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.console.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * A controller class to process the notification related requests.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationController extends MultiActionController {

	private NotificationManager notificationManager;

	public NotificationController() {
		notificationManager = new NotificationManager();
	}

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// mav.addObject("list", null);
		mav.setViewName("notification/form");
		return mav;
	}

	public ModelAndView send(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String broadcast = "";
		String username = "";
		String alias = "";
		String tag = "";
		String title = "";
		String message ="";
		String uri = "";
		String imgUrl="";

		String apiKey = Config.getString("apiKey", "");
		logger.debug("apiKey=" + apiKey);

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		List<FileItem> fileItmes = servletFileUpload.parseRequest(request);
		for (FileItem fileItem : fileItmes) {
			switch (fileItem.getFieldName()) {
			case "broadcast":
				broadcast = fileItem.getString("utf-8");
				break;
			case "username":
				username = fileItem.getString("utf-8");
				break;
			case "alias":
				alias = fileItem.getString("utf-8");
				break;
			case "tag":
				tag = fileItem.getString("utf-8");
				break;
			case "title":
				title = fileItem.getString("utf-8");
				break;
			case "message":
				message = fileItem.getString("utf-8");
				break;
			case "uri":
				uri = fileItem.getString("utf-8");
				break;
			case "image":
				imgUrl=uploadImg(request, fileItem);
				break;
			}
		}
		
		if (broadcast.equalsIgnoreCase("0")) {
			notificationManager.sendBroadcast(apiKey, title, message, uri,imgUrl);
		} else if (broadcast.equalsIgnoreCase("1")) {
			notificationManager.sendNotifcationToUser(apiKey, username, title, message, uri, imgUrl);
		} else if (broadcast.equalsIgnoreCase("2")) {
			notificationManager.sendNotifcationToAlias(apiKey, alias, title, message, uri, imgUrl);
		} else {
			notificationManager.sendNotifcationToTag(apiKey, tag, title, message, uri, imgUrl);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:notification.do");
		return mav;
	}
	
	private String uploadImg(HttpServletRequest request,FileItem fileItem) throws IOException {
		String uploadPath=getServletContext().getRealPath("/upload");
		logger.debug(uploadPath);
		File uploadDir=new File(uploadPath);
		if(!uploadDir.exists()){
			uploadDir.mkdir();
		}
		if(fileItem!=null && fileItem.getContentType().startsWith("image")){
			String suffix=fileItem.getName().substring(fileItem.getName().lastIndexOf("."));
			String fileName=System.currentTimeMillis()+suffix;
			InputStream inputStream=fileItem.getInputStream();
			FileOutputStream fileOutputStream=new FileOutputStream(uploadPath+"/"+fileName);
			byte[] bs=new byte[1024];
			int len=0;
			while((len=inputStream.read(bs))>0){
				fileOutputStream.write(bs,0,len);
				fileOutputStream.flush();
			}
			fileOutputStream.close();
			inputStream.close();
			
//			String serverName=request.getServerName();
			String serverName="192.168.250.106";
			String serverPort=String.valueOf(request.getServerPort());
			String imgUrl="http://"+serverName+":"+serverPort+"/upload/"+fileName;
			logger.debug(imgUrl);
			return imgUrl;
		}
		return "";
	}

}
