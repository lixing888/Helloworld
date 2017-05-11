package com.fh.util;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Base64 {
	// 将 s 进行 BASE64 编码 
	public static String getBASE64(String s) { 
	if (s == null) return null; 
	   return (new BASE64Encoder()).encode( s.getBytes() ); 
	} 
	 
	// 将 BASE64 编码的字符串 s 进行解码 
	public static String getFromBASE64(String s) { 
	if (s == null)
		return null; 
	    BASE64Decoder decoder = new BASE64Decoder(); 
	try { 
		byte[] b = decoder.decodeBuffer(s); 
		return new String(b); 
	} catch (Exception e) { 
	    return null; 
	} 
 }
	
	
	public static void main(String[] args) {
		String s="lixing";
		String s1=getBASE64(s);
		System.out.println("base64加密："+s1);
		System.out.println("base64解密："+getFromBASE64(s1)); 
	}
}
