package com.fh.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMsg_webchinese {
public static void main(String[] args)throws Exception{

	HttpClient client = new HttpClient();
	PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
	post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//��ͷ�ļ�������ת��
	NameValuePair[] data ={ new NameValuePair("Uid", "lixing85210279"),
	new NameValuePair("Key", "d12d8a97cfb2b5c61498"),
	new NameValuePair("smsMob","13126512477"),
	new NameValuePair("smsText","�������ݣ��𾴵Ŀͻ����� ����̳�ֵ���2017��04��29�տ�չ�ذ����˻�����ʱ�μӡ�")};
	post.setRequestBody(data);

	client.executeMethod(post);
	Header[] headers = post.getResponseHeaders();
	int statusCode = post.getStatusCode();
	System.out.println("statusCode:"+statusCode);
	for(Header h : headers){
	   System.out.println(h.toString());
	}
	 String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
	 System.out.println(result); //��ӡ������Ϣ״̬


     post.releaseConnection();

 }

}

