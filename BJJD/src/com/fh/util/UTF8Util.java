package com.fh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

   public class UTF8Util {
		public static String sendGet(String url,String param) throws IOException{
		String result="";
		BufferedReader in=null;
		String urlName=url+param;
		URL realUrl=new URL(urlName);
		URLConnection conn=realUrl.openConnection();
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Window NT 5.1;SV1)");
		conn.connect();
		in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while((line=in.readLine())!=null){
		    result+="\n"+line;

		}

		   return result;
		}
	
   
   
   
   public static void main(String args[]) throws IOException{
	     
	    String s=UTF8Util.sendGet("http://dict-co.iciba.com/api/dictionary.php?w=", "word");
	   /* String s="李兴";
	    byte[] b=s.getBytes("UTF-8");
	    s =new String(b,"ISO-8859-1");
	    s=new String(s.getBytes("ISO-8859-1"),"UTF-8");*///中文乱码
	    System.out.print(s);
    			 

	}
   
   }
