package org.androidpn.server.dao.hibernate;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sun.nio.sctp.Notification;

public class NotificationDaoHibernate extends HibernateDaoSupport implements NotificationDao {

	public void saveNotification(Notification notification) {
		getHibernateTemplate().saveOrUpdate(notification);
		getHibernateTemplate().flush();
	}

	public List<Notification> findNotificationsByUsername(String username) {
		@SuppressWarnings("unchecked")
		List<Notification> notifications = getHibernateTemplate().find("from Notification where username=?", username);
		if (notifications == null || notifications.isEmpty()) {
			return null;
		} else {
			return notifications;
		}
	}

	public void deleteNotification(Notification notification) {
		getHibernateTemplate().delete(notification);
	}

}
