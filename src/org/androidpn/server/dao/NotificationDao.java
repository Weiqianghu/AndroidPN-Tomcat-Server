package org.androidpn.server.dao;

import java.util.List;

import com.sun.nio.sctp.Notification;


public interface NotificationDao {

	void saveNotification(Notification notification);

	List<Notification> findNotificationsByUsername(String username);

	void deleteNotification(Notification notification);
}
