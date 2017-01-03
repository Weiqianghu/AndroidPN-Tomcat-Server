<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Admin Console</title>
	<meta name="menu" content="notification" />    
</head>

<body>

<h1>Send Notifications</h1>

<%--<div style="background:#eee; margin:20px 0px; padding:20px; width:500px; border:solid 1px #999;">--%>
<div style="margin:20px 0px;">
<form action="notification.do?action=send" method="post" style="margin: 0px;" enctype="multipart/form-data">
<table width="600" cellpadding="4" cellspacing="0" border="0">
<tr>
	<td width="20%">To:</td>
	<td width="80%">
		<input type="radio" name="broadcast" value="0" checked="checked" />  All (Broadcast) 
        <input type="radio" name="broadcast" value="1" /> By Username 
        <input type="radio" name="broadcast" value="2" /> By Alias
        <input type="radio" name="broadcast" value="3" /> By Tag
	</td>
</tr>
<tr id="trUsername" style="display:none;">
	<td>Username:</td>
	<td><input type="text" id="username" name="username" value="" style="width:380px;" /></td>
</tr>
<tr id="trAlias" style="display:none;">
	<td>Alias:</td>
	<td><input type="text" id="alias" name="alias" value="" style="width:380px;" /></td>
</tr>
<tr id="trTag" style="display:none;">
	<td>Tag:</td>
	<td><input type="text" id="tag" name="tag" value="" style="width:380px;" /></td>
</tr>
<tr>
	<td>Title:</td>
	<td><input type="text" id="title" name="title" value="Message Title" style="width:380px;" /></td>
</tr>
<tr>
	<td>Message:</td>
	<td><textarea id="message" name="message" style="width:380px; height:80px;" >Message Content!</textarea></td>
</tr>
<%--
<tr>
	<td>Ticker:</td>
	<td><input type="text" id="ticker" name="ticker" value="" style="width:380px;" /></td>
</tr>
--%>
<tr>
	<td>URI:</td>
	<td><input type="text" id="uri" name="uri" value="" style="width:380px;" />
	    <br/><span style="font-size:0.8em">ex) http://weiqianghu.github.io/</span>
	</td>
</tr>
<tr>
	<td>Image:</td>
	<td><input type="file" id="image" name="image" value="" style="width:380px;" />
	    <br/><span style="font-size:0.8em">Only Image !</span>
	</td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td><input type="submit" value="Submit" /></td>
</tr>
</table> 
</form>
</div>

<script type="text/javascript"> 
//<![CDATA[
 
$(function() {
	$('input[name=broadcast]').click(function() {
		if ($('input[name=broadcast]')[0].checked) {
			$('#trUsername').hide();
			$('#trAlias').hide();
			$('#trTag').hide();
		} else if($('input[name=broadcast]')[1].checked){
			$('#trUsername').show();
			$('#trAlias').hide();
			$('#trTag').hide();
		}else if($('input[name=broadcast]')[2].checked){
			$('#trAlias').show();
			$('#trUsername').hide();
			$('#trTag').hide();
		}else{
			$('#trAlias').hide();
			$('#trUsername').hide();
			$('#trTag').show();
		}
	});
	
	if ($('input[name=broadcast]')[0].checked) {
		$('#trUsername').hide();
		$('#trAlias').hide();
		$('#trTag').hide();
	} else if($('input[name=broadcast]')[1].checked){
		$('#trUsername').show();
		$('#trAlias').hide();
		$('#trTag').hide();
	}else if($('input[name=broadcast]')[2].checked){
		$('#trAlias').show();
		$('#trUsername').hide();
		$('#trTag').hide();
	}else{
		$('#trAlias').hide();
		$('#trUsername').hide();
		$('#trTag').show();
	}
});
 
//]]>
</script>

</body>
</html>
