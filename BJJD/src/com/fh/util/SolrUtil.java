package com.fh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.common.SolrInputDocument;

public class SolrUtil {
public static String downloadPage(String path) throws IOException{
		//根据传入的路径构造 URL
		URL pageURL = new URL(path);
		//创建网络流
		BufferedReader reader = new BufferedReader(new InputStreamReader(
		pageURL.openStream()));
		String line;
		//读取网页内容
		StringBuilder pageBuffer = new StringBuilder();
		while ((line = reader.readLine()) != null) {
		   pageBuffer.append(line);
		}
		//返回网页内容
		return pageBuffer.toString();
    }
	/**
	* 测试代码
	 * @throws IOException 
	 * @throws SolrServerException 
	*/
	public static void main(String[] args) throws IOException, SolrServerException {
	//抓取 lietu 首页然后输出
		//System.out.println(SolrUtil.downloadPage("https://www.taobao.com/"));
		 //solr3.5与tomcat6的配置地址
        String urlString = "http://127.0.0.1:8080/solr"; 
        SolrServer server = new CommonsHttpSolrServer(urlString);  
        SolrInputDocument doc1 = new SolrInputDocument();  
        
        //随机ID
        String Id=UUID.randomUUID().toString();
        //其中 content字段需要在D:\lucene\solr\home\conf\schema.xml配置
        doc1.addField("id","6b677ae7-1b3c-44a3-9e31-1519eb206008");
        doc1.addField("mulu","D:\\tikatest\\人生感悟.doc");
        doc1.addField("msg_title", "人生感悟");
        doc1.addField("content", "不要抱着过去不放，拒绝新的观念和挑战。");  
        doc1.addField("ssid", "rensheng");  
        doc1.addField("dxid", "ganwu"); 
        SolrInputDocument doc2 = new SolrInputDocument();  
        doc2.addField("id","5a7b1a92-b804-4a56-9a0a-fdd4d0f45786"); 
        doc2.addField("mulu","D:\\tikatest\\励志语录.doc");
        doc2.addField("msg_title", "励志语录");
        doc2.addField("content", "穷人缺什么：表面缺资金，本质缺野心，脑子缺观念，机会缺了解，骨子缺勇气，改变缺行动，事业缺毅力。");
        doc2.addField("ssid", "lizhi");  
        doc2.addField("dxid", "yulu"); 
        SolrInputDocument doc3 = new SolrInputDocument();
        doc3.addField("id", "54c1c75b-4553-441e-9f21-c6ff1b426e1e");
        doc3.addField("mulu","D:\\tikatest\\心灵鸡汤.doc");
        doc3.addField("msg_title", "心灵鸡汤");
        doc3.addField("content", "生命不在于活得长与短，而在于顿悟的早与晚。");
        doc3.addField("ssid", "xinling");  
        doc3.addField("dxid", "jitang"); 
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();  
        docs.add(doc1);  
        docs.add(doc2);
        docs.add(doc3);
        server.add(docs);
        
        //提交服务
        server.commit();
        System.out.println("----索引创建完毕!!!----"); 
      //根据ID删除索引
        server.deleteByQuery("*:*");
        System.out.println("----索引删除完毕!!!----");
        UpdateRequest req = new UpdateRequest();  
        req.setAction(AbstractUpdateRequest.ACTION.COMMIT, false, false);  
        //添加索引
   //   req.add(docs);  
        req.process(server);  
        
        SolrQuery query = new SolrQuery();  
  
        query.setQuery("test");  
        query.setHighlight(true).setHighlightSnippets(1);                                                     
        query.setParam("hl.fl", "content");  
  
        QueryResponse ret = server.query(query);  
  
        System.out.println("返回的相应:"+ret);  
	}
}
