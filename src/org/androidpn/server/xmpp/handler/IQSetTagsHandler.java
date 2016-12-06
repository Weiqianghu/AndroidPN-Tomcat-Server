package org.androidpn.server.xmpp.handler;

import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;

public class IQSetTagsHandler extends IQHandler {
	private static final String NAMESPACE = "androidpn:iq:settags";
	private SessionManager sessionManager;

	public IQSetTagsHandler() {
		sessionManager = SessionManager.getInstance();
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		IQ reply = null;

		ClientSession session = sessionManager.getSession(packet.getFrom());
		if (session == null) {
			log.error("Session not found for key " + packet.getFrom());
			reply = IQ.createResultIQ(packet);
			reply.setChildElement(packet.getChildElement().createCopy());
			reply.setError(PacketError.Condition.internal_server_error);
			return reply;
		}

		if (IQ.Type.set.equals(packet.getType()) && session.getStatus() == Session.STATUS_AUTHENTICATED) {
			Element element = packet.getChildElement();
			if (element != null) {
				String username = element.elementText("username");
				String tags = element.elementText("tags");
				if (username != null && !"".equals(username) && tags != null && !"".equals(tags)) {
					String[] tagArray = tags.split(",");
					if (tagArray != null) {
						for (String tag : tagArray) {
							sessionManager.setUserTag(username, tag);
						}
						System.out.println("set tags success");
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

}
