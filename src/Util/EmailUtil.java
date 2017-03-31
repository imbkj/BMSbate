package Util;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.xbill.DNS.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

	/**
	 * 邮箱验证 优点：验证出的结果，完全符合真实情况，如果一个邮箱被验证存在，那么它就一定存在，反之亦然。
	 * 缺点：验证时比较耗时，需要5秒左右的时耗；实际情况视你自己写的发件方而定。
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if (email == null) {
			return false;
		} else {

			String host = "";
			String hostName = email.split("@")[1];
			Record[] result = null;
			SMTPClient client = new SMTPClient();

			try {
				// 查找MX记录
				Lookup lookup = new Lookup(hostName, Type.MX);
				lookup.run();
				if (lookup.getResult() != Lookup.SUCCESSFUL) {
					return false;
				} else {
					result = lookup.getAnswers();
				}

				// 连接到邮箱服务器
				for (int i = 0; i < result.length; i++) {
					host = result[i].getAdditionalName().toString();
					client.connect(host);
					if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
						client.disconnect();
						continue;
					} else {
						break;
					}
				}

				// 以下2项自己填写快速的，有效的邮箱
				client.login("163.com");
				client.setSender("13421374673@163.com");
				client.addRecipient(email);
				if (250 == client.getReplyCode()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					client.disconnect();
				} catch (IOException e) {
				}
			}
			return false;
		}
	}

	/**
	 * 简单验证邮箱格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmailSimple(String email) {
		if (email == null) {
			return false;
		}

		String checkPattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(checkPattern);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}
}
