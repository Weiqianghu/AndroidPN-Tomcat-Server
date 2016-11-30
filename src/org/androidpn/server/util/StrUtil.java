package org.androidpn.server.util;

import java.util.Random;

public class StrUtil {

	public static String generateId() {
		Random random = new Random();
		return Integer.toHexString(random.nextInt());
	}
}
