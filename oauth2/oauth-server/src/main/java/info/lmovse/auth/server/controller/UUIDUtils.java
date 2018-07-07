package info.lmovse.auth.server.controller;

import java.util.UUID;

public class UUIDUtils {
	
	public static String getID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	public static String getCode() {
		return getID();
	}
	
	public static String getUUIDName(String filename){
		// 先查找
		int index = filename.lastIndexOf(".");
		// 截取
		String lastname = filename.substring(index, filename.length());
		// 唯一 字符串  fsd-sfsdf-sfsd-sdfsd
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid+lastname;
	}
	
}
