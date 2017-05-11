package com.fh.controller.userInfo.bjjduserinfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.workguanli.WorkGuanliService;
import com.fh.util.AppUtil;
import com.fh.util.FileUtil;
import com.fh.util.GetDirectory;
import com.fh.util.GetPinyin;
import com.fh.util.PageData;


/** 
 * 类名称：BjjdUserInfoController
 * 创建人：FH 
 * 创建时间：2016-07-12
 */
@Controller
@RequestMapping(value="/bjjduserinfo")
public class BjjdUserInfoController extends BaseController {
	
	static int count = 0;
	static String ip=PropertiesUtil.getStringByKey("ip");
	String menuUrl = "bjjduserinfo/list.do"; //菜单地址(权限用)
	@Resource(name="workguanliService")
	private WorkGuanliService workguanliService;
	
	/**
	 * 专门建立索引的 全文扫描
	 * @return 
	 * @Description: 建立附件索引
	 * 需要定时执行的方法
	 */
	@RequestMapping(value="/createIndex")
	@ResponseBody
	public static Object createIndex() throws Exception{
		//solr服务配置
		String urlString = "http://"+ip+"/solr";  
		String result="扫描完毕";
        SolrServer server = new CommonsHttpSolrServer(urlString);
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        Collection<SolrInputDocument> docChilds = new ArrayList<SolrInputDocument>();
		List<PageData> listInfo = new ArrayList<PageData>();
        //系统时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String huanCunDate = formatter.format(currentTime);
        
          //附件所在位置 要遍历文件夹的
	      File file=new File("D:/附件");//搜索附件的位置(此处为查找方便写死了)
	      
		  File flist[] = file.listFiles();
		  if (flist == null || flist.length == 0) {
		      return null;
		  }
		  
		  //遍历某个文件夹下的附件
		  for (File f : flist) {
		      if (f.isDirectory()) {
		          //这里将列出所有的子文件夹
		          System.out.println("子文件夹名称:" + f.getAbsolutePath()); 
		     //   getDirectory(f);
		          GetDirectory.GetDirectory( f.getAbsolutePath());
		          System.out.println("子文件夹下的附件"+GetDirectory.GetDirectory(f.getAbsolutePath()).get(0));
		         
		          File fileChild=new File(f.getPath());
              	  File[] fileChilds = fileChild.listFiles();
              	  for(File fs : fileChilds){
              		  System.out.println("附件路径：" + fs.getAbsolutePath()+"===附件名："+fs.getName());
	              	  //取得附件内容
	  		          String content=  parseFile(new File(fs.getAbsolutePath()));
	  		          //将读取的内容放到solr缓存中
	  		            
	  		          SolrInputDocument doc1 = new SolrInputDocument();
	  		          doc1.addField("id",fs.getName());//以附件名称为ID 确保了唯一性(这样下次缓存时就会更新了)
	  		          doc1.addField("mulu",fs.getAbsolutePath());//附件的路径
	  		          doc1.addField("msg_title",fs.getName());//附件名称 (可根据msg_title进行查询)
	  		          doc1.addField("content", content); //附件里的内容
	  		          doc1.addField("ssid", huanCunDate); //缓存时间 
	  		          doc1.addField("dxid", "ganwu");
	  		          
	  		          docChilds.add(doc1);  
              	    
              	 } 
		      }else{
		    	  //这里将列出所有的文件
		          System.out.println("附件路径：" + f.getAbsolutePath()+"===附件名："+f.getName());
		          
		          //取得附件内容
		          String content=  parseFile(new File(f.getAbsolutePath()));
		          //将读取的内容放到solr缓存中
		            
		          SolrInputDocument doc = new SolrInputDocument();
		          doc.addField("id",f.getName());//以附件名称为ID 确保了唯一性(这样下次缓存时就会更新了)
		          doc.addField("mulu",f.getAbsolutePath());//附件的路径
		          doc.addField("msg_title",f.getName());//附件名称 (可根据msg_title进行查询)
		          doc.addField("content", content); //附件里的内容
		          doc.addField("ssid", huanCunDate); //缓存时间 
		          doc.addField("dxid", "ganwu");
		          
		          docs.add(doc);  
		      }
		  }
		if(docChilds!=null&&docChilds.size()>0){
			server.add(docChilds); 
			server.commit();
		}
		 
		server.add(docs);
        //根据ID删除索引
        //server.deleteById("e94a55f3-031c-4988-ab4d-3d1db99b8af5");
		//删除全部的索引缓存
      	//server.deleteByQuery("*:*");
        //提交服务
        server.commit();
        byte[] b=result.getBytes("UTF-8");
        result =new String(b,"ISO-8859-1");
        result=new String(result.getBytes("ISO-8859-1"),"UTF-8");//中文乱码
        System.out.println("----索引创建完毕!!!----"+result);
		return result;   
	 }
	
	/**
	* @Title: BjjdUserInfoController.java 
	* @Description: 附件全文检索
	 */
	@RequestMapping(value="/delSolr")
	@ResponseBody
	public void delSolr() throws Exception{
		//solr服务配置
		String urlString = "http://"+ip+"/solr"; 
        SolrServer server = new CommonsHttpSolrServer(urlString);
        //删除全部的索引缓存
      	server.deleteByQuery("*:*");
	 }
	
	

	/**
	* @Title: BjjdUserInfoController.java 
	* @Description: 附件全文检索
	 */
	@RequestMapping(value="/searchByKeyWord")
	@ResponseBody
	public List<PageData> searchByKeyWord(String keyword) throws Exception{
		//solr服务配置
		String urlString = "http://"+ip+"/solr"; 
		System.out.println(urlString); 
        SolrServer server = new CommonsHttpSolrServer(urlString);
       /* Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        
        ModelAndView mv = this.getModelAndView();
		List<PageData> listInfo = new ArrayList<PageData>();
        //系统时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String huanCunDate = formatter.format(currentTime);
        //附件所在位置
	      File file=new File("D:/附件");//搜索附件的位置
		  File flist[] = file.listFiles();
		  if (flist == null || flist.length == 0) {
		      return null;
		  }
		  //删除全部的索引缓存
		  //server.deleteByQuery("*:*");
		  //遍历某个文件夹下的附件
		  for (File f : flist) {
		      if (f.isDirectory()) {
		          //这里将列出所有的文件夹
		          System.out.println("Dir==>" + f.getAbsolutePath()); 
		       // getDirectory(f);
		      } else {
		    	  //这里将列出所有的文件
		          System.out.println("附件路径：" + f.getAbsolutePath()+"===附件名："+f.getName());
		          
		          //取得附件内容
		          String content=  parseFile(new File(f.getAbsolutePath()));
		          //将读取的内容放到solr缓存中
		           
		          SolrInputDocument doc = new SolrInputDocument();
		          doc.addField("id",f.getName());//以附件名称为ID 确保了唯一性(这样下次缓存时就会更新了)
		          doc.addField("mulu",f.getAbsolutePath());//附件的路径
		          doc.addField("msg_title",f.getName());//附件名称 (可根据msg_title进行查询)
		          doc.addField("content", content); //附件里的内容
		          doc.addField("ssid", huanCunDate); //缓存时间 
		          doc.addField("dxid", "ganwu");
		          
		          docs.add(doc);  
		      }
		  }
		  
		  server.add(docs);
        //根据ID删除索引
        //server.deleteById("e94a55f3-031c-4988-ab4d-3d1db99b8af5");
        //提交服务
        server.commit();
        System.out.println("----索引创建完毕!!!----");*/
       //   ModelAndView mv = this.getModelAndView();
      		List<PageData> listInfo = new ArrayList<PageData>();
    	    List<BookIndex> list = new ArrayList<BookIndex>();
            //通过关键字检索附件信息
            ModifiableSolrParams params = new ModifiableSolrParams(); 
			/*Tika tika = new Tika();*/
     //     keyword=new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
			params.set("qt","/spell");//qt 检查拼写
			params.set("q", "content:"+keyword);//q 查询关键词  *:* 全部
			//分页>>start=0就是从0开始，，rows=5当前返回5条记录，，，第二页就是变化start这个值为5就可以了。 
			params.set("start", 0); 
			params.set("rows", 5); 
			//sort排序，对查询结果进行排序>>如果按照id 排序，，那么将score desc 改成 id desc(or asc) 
			params.set("sort", "score desc"); 
			//fl返回字段内容信息 *为全部   这里是全部加上score，如果不加下面就不能使用score 
			params.set("fl", "*,score"); 
			QueryResponse response = server.query(params); 
			//搜索得到的结果数 
			System.out.println("找到文件个数:"+ response.getResults().getNumFound()+"\n\n"); 
			//输出结果 
			for(SolrDocument doc:response.getResults()) { 
				/* BookIndex bookIndex=new BookIndex();
				 System.out.println("ID:" + doc.getFieldValue("id").toString()); 
				 System.out.println("路径："+doc.getFieldValue("mulu").toString());
				 System.out.println("标题:" + doc.getFieldValue("msg_title").toString());*/
				 PageData pdBanWord = new PageData();
				 pdBanWord.put("MULU", doc.getFieldValue("mulu").toString());
				 pdBanWord.put("TITLE", doc.getFieldValue("msg_title").toString());
				
			/*	bookIndex.setMulu(doc.getFieldValue("mulu").toString());
				bookIndex.setTitle(doc.getFieldValue("msg_title").toString());
				list.add(bookIndex);*/
				listInfo.add(pdBanWord);
			} 
			
/*
		    JSONArray json = new JSONArray();
          for(BookIndex a : list){
              JSONObject jo = new JSONObject();
              jo.put("title", a.getTitle());
              jo.put("mulu", a.getMulu());
              json.add(jo);
          }*/
      //  mv.addObject("data", listInfo);
      //  mv.setViewName("system/admin/aftsearch");//跳转到的页面全文检索的页面
          System.out.println("json格式："+listInfo);  
          return listInfo;
         // return mv;
	 }
	
	/**
	* @Title: BjjdUserInfoController.java 
	* @Description: 附件名称检索
	 */
	@RequestMapping(value="/searchByFileName")
	@ResponseBody
	public List<PageData> searchByFileName(String fileName) throws Exception{
		//solr服务配置
		/*String urlString = "http://"+ip+"/solr"; 
        SolrServer server = new CommonsHttpSolrServer(urlString);
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        
        ModelAndView mv = this.getModelAndView();
		List<PageData> listInfo = new ArrayList<PageData>();
        //系统时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String huanCunDate = formatter.format(currentTime);
        //附件所在位置
	      File file=new File("D:/附件");//搜索附件的位置
		  File flist[] = file.listFiles();
		  if (flist == null || flist.length == 0) {
		      return null;
		  }
		  //删除全部的索引缓存
		  //server.deleteByQuery("*:*");
		  //遍历某个文件夹下的附件
		  for (File f : flist) {
		      if (f.isDirectory()) {
		          //这里将列出所有的文件夹
		          System.out.println("Dir==>" + f.getAbsolutePath()); 
		       // getDirectory(f);
		      } else {
		    	  //这里将列出所有的文件
		          System.out.println("附件路径：" + f.getAbsolutePath()+"===附件名："+f.getName());
		          
		          //取得附件内容
		          String content=  parseFile(new File(f.getAbsolutePath()));
		          //将读取的内容放到solr缓存中
		           
		          SolrInputDocument doc = new SolrInputDocument();
		          doc.addField("id",f.getName());//以附件名称为ID 确保了唯一性(这样下次缓存时就会更新了)
		          doc.addField("mulu",f.getAbsolutePath());//附件的路径
		          doc.addField("msg_title",f.getName());//附件名称 (可根据msg_title进行查询)
		          doc.addField("content", content); //附件里的内容
		          doc.addField("ssid", huanCunDate); //缓存时间 
		          doc.addField("dxid", "ganwu");
		          
		          docs.add(doc);  
		      }
		  }
		  
		  server.add(docs);
        //根据ID删除索引
        //server.deleteById("e94a55f3-031c-4988-ab4d-3d1db99b8af5");
        //提交服务
        server.commit();
        System.out.println("----索引创建完毕!!!----");*/
        String urlString = "http://"+ip+"/solr"; 
        SolrServer server = new CommonsHttpSolrServer(urlString);
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        
     // ModelAndView mv = this.getModelAndView();
		List<PageData> listInfo = new ArrayList<PageData>();
        
    	    List<BookIndex> list = new ArrayList<BookIndex>();
            //通过关键字检索附件信息
            ModifiableSolrParams params = new ModifiableSolrParams(); 
			/*Tika tika = new Tika();*/
         // fileName=new String(fileName.getBytes("ISO-8859-1"),"UTF-8");//中文乱码
			params.set("qt","/spell");//qt 检查拼写
			params.set("q", "msg_title:"+fileName);//q 查询关键词  *:* 全部  根据附件名称查询
			//分页>>start=0就是从0开始，，rows=5当前返回5条记录，，，第二页就是变化start这个值为5就可以了。 
			params.set("start", 0); 
			params.set("rows", 5); 
			//sort排序，对查询结果进行排序>>如果按照id 排序，，那么将score desc 改成 id desc(or asc) 
			params.set("sort", "score desc"); 
			//fl返回字段内容信息 *为全部   这里是全部加上score，如果不加下面就不能使用score 
			params.set("fl", "*,score"); 
			QueryResponse response = server.query(params); 
			//搜索得到的结果数 
			System.out.println("找到文件个数:"+ response.getResults().getNumFound()+"\n\n"); 
			//输出结果 
			for(SolrDocument doc:response.getResults()) { 
				/* BookIndex bookIndex=new BookIndex();
				 System.out.println("ID:" + doc.getFieldValue("id").toString()); 
				 System.out.println("路径："+doc.getFieldValue("mulu").toString());
				 System.out.println("标题:" + doc.getFieldValue("msg_title").toString());*/
				 PageData pdBanWord = new PageData();
				 pdBanWord.put("MULU", doc.getFieldValue("mulu").toString());
				 pdBanWord.put("TITLE", doc.getFieldValue("msg_title").toString());
				
			/*	bookIndex.setMulu(doc.getFieldValue("mulu").toString());
				bookIndex.setTitle(doc.getFieldValue("msg_title").toString());
				list.add(bookIndex);*/
				listInfo.add(pdBanWord);
			} 
			

      //  mv.addObject("data", listInfo);
      //  mv.setViewName("system/admin/aftsearch");//跳转到的页面全文检索的页面
          System.out.println("json格式："+listInfo);  
          return listInfo;
         // return mv;
	 }
	
	
	
	//首先遍历D:/tikatest盘下的附件如pdf，word，execl，zip，ppt等文件
	public void getDirectory() throws SolrServerException, IOException {
		  //solr服务配置
		  String urlString ="http://"+ip+"/solr"; 
          SolrServer server = new CommonsHttpSolrServer(urlString);
          Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
          //系统时间
          Date currentTime = new Date();
          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String huanCunDate = formatter.format(currentTime);
          //附件所在位置
  	      File file=new File("D:/附件");
		  File flist[] = file.listFiles();
		  if (flist == null || flist.length == 0) {
		      return;
		  }
		  //删除全部的索引缓存
		  //server.deleteByQuery("*:*");
		  //遍历某个文件夹下的附件
		  for (File f : flist) {
		      if (f.isDirectory()) {
		          //这里将列出所有的文件夹
		          System.out.println("Dir==>" + f.getAbsolutePath()); 
		       // getDirectory(f);
		      } else {
		    	  //这里将列出所有的文件
		          System.out.println("附件路径：" + f.getAbsolutePath()+"===附件名："+f.getName());
		          
		          //取得附件内容
		          String content=  parseFile(new File(f.getAbsolutePath()));
		          //将读取的内容放到solr缓存中
		           
		          SolrInputDocument doc = new SolrInputDocument();
		          doc.addField("id",f.getName());//以附件名称为ID 确保了唯一性(这样下次缓存时就会更新了)
		          doc.addField("mulu",f.getAbsolutePath());//附件的路径
		          doc.addField("msg_title",f.getName());//附件名称
		          doc.addField("content", content); //内容
		          doc.addField("ssid", huanCunDate); //缓存时间 
		          doc.addField("dxid", "ganwu");
		         
		          docs.add(doc);  
		      }
		  }
		  
		  server.add(docs);
          //根据ID删除索引
          //server.deleteById("e94a55f3-031c-4988-ab4d-3d1db99b8af5");
		  
          //提交服务
          server.commit();
          System.out.println("----索引创建完毕!!!----");
          
          //通过关键字检索附件信息
          ModifiableSolrParams params = new ModifiableSolrParams(); 
			/*Tika tika = new Tika();*/
			params.set("qt","/spell");//qt 检查拼写
			params.set("q", "content:功能模块 功能点 详细描述 总体指标 完整解决方案 提供完善的解决方案，实现包括用户管理、组管理、应用管理、认证方式管理、管理员权限管理、配置参数管理、认证集成接口等功能的一体化平台。");//q 查询关键词  *:* 全部
			//分页>>start=0就是从0开始，，rows=5当前返回5条记录，，，第二页就是变化start这个值为5就可以了。 
			params.set("start", 0); 
			params.set("rows", 2); 
			//sort排序，对查询结果进行排序>>如果按照id 排序，，那么将score desc 改成 id desc(or asc) 
			params.set("sort", "score desc");//按照相识度进行排序
			//fl返回字段内容信息 *为全部   这里是全部加上score，如果不加下面就不能使用score 
			params.set("fl", "*,score"); 
			QueryResponse response = server.query(params); 
			//搜索得到的结果数 
			System.out.println("找到文件个数:"+ response.getResults().getNumFound()+"\n\n"); 
			//输出结果 
			for(SolrDocument doc:response.getResults()) { 
				
				System.out.println("ID:" + doc.getFieldValue("id").toString()); 
				System.out.println("路径："+doc.getFieldValue("mulu").toString());
				System.out.println("标题:" + doc.getFieldValue("msg_title").toString());
			
				
			} 
		
	 }
	
	 //利用tika取得文件内容
	 public static String parseFile(File file){  
	        Parser parser = new AutoDetectParser();  
	        InputStream input = null;  
            try{  	
	            Metadata metadata = new Metadata();  
	            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");  
	            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());  
	            input = new FileInputStream(file);  
	            BodyContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);   
	            ParseContext context = new ParseContext();  
	            context.set(Parser.class,parser);  
	            parser.parse(input,handler, metadata,context);  
	            for(String name:metadata.names()) {  
	                System.out.println(name+":"+metadata.get(name));  
	            }  
	            System.out.println(handler.toString());  
	            return handler.toString();  
	        }catch (Exception e){  
	            e.printStackTrace();  
	        }finally {  
	            try {  
	                if(input!=null)input.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return null;  
	  
	    }  
	
	    /**
	     * 全文检索页面 quanwenSerach
		 * 全文检索结果页面(旧版)
		 * 后台
		 */
	    @RequestMapping(value="/quanwenSerach")
		public ModelAndView groupMessageList(Page page){//分页查询
			logBefore(logger, "列表quanwenSerach");
		//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			List<PageData> listInfo = new ArrayList<PageData>();
			
			try{
				pd = this.getPageData();
				
				String state = pd.getString("state");      //关键词检索条件
				
				String lastLoginStart=pd.getString("lastLoginStart");
				String lastLoginEnd=pd.getString("lastLoginEnd");
				
				if(null != state && !"".equals(state)){
					pd.put("state", state.trim());
				}
				
				if(null != lastLoginStart && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart.trim());
				}
				if(null != lastLoginEnd && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd.trim());
				}
				page.setPd(pd);
				
				//page.setTotalResult(varList.size());//totalResult判断总数
				mv.setViewName("system/admin/aftsearch");//跳转到的页面全文检索的页面
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
			return mv;
		}
	    
	    /**
	     * 全文检索页面 quanwenSerach
		 * 全文检索结果页面(新版)
		 * 后台
		 */
	    @RequestMapping(value="/quanwenSerach1")
		public ModelAndView quanwenSerach1(Page page){//分页查询
			logBefore(logger, "列表quanwenSerach1");
		//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			List<PageData> listInfo = new ArrayList<PageData>();
			
			try{
				pd = this.getPageData();
				
				String state = pd.getString("state");      //关键词检索条件
				
				String lastLoginStart=pd.getString("lastLoginStart");
				String lastLoginEnd=pd.getString("lastLoginEnd");
				
				if(null != state && !"".equals(state)){
					pd.put("state", state.trim());
				}
				
				if(null != lastLoginStart && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart.trim());
				}
				if(null != lastLoginEnd && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd.trim());
				}
				page.setPd(pd);
				
				//page.setTotalResult(varList.size());//totalResult判断总数
				mv.setViewName("system/admin/aftsearch1");//跳转到的页面全文检索的页面
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
			return mv;
		}
	    
	    /**
	     * 全文检索页面 quanwenSerach
		 * 全文检索结果页面(新版)
		 * 后台
		 */
	    @RequestMapping(value="/serach")
		public ModelAndView serach(Page page){//分页查询
			logBefore(logger, "列表serach");
		//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			List<PageData> listInfo = new ArrayList<PageData>();
			
			try{
				pd = this.getPageData();
				
				String state = pd.getString("state");      //关键词检索条件
				
				String lastLoginStart=pd.getString("lastLoginStart");
				String lastLoginEnd=pd.getString("lastLoginEnd");
				
				if(null != state && !"".equals(state)){
					pd.put("state", state.trim());
				}
				
				if(null != lastLoginStart && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart.trim());
				}
				if(null != lastLoginEnd && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd.trim());
				}
				page.setPd(pd);
				
				//page.setTotalResult(varList.size());//totalResult判断总数
				mv.setViewName("system/admin/search");//跳转到的页面全文检索的页面
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
			return mv;
		}
	    
		/** 
		* @Description: 上传附件页面
		* @author lixing 设定文件 
		* @throws 
		*/ 
	    @RequestMapping(value="/upload")
		public ModelAndView upload(Page page){//分页查询
			logBefore(logger, "列表upload");
		//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			List<PageData> listInfo = new ArrayList<PageData>();
			
			try{
				pd = this.getPageData();
				
				String state = pd.getString("state");      //关键词检索条件
				
				String lastLoginStart=pd.getString("lastLoginStart");
				String lastLoginEnd=pd.getString("lastLoginEnd");
				
				if(null != state && !"".equals(state)){
					pd.put("state", state.trim());
				}
				
				if(null != lastLoginStart && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart.trim());
				}
				if(null != lastLoginEnd && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd.trim());
				}
				page.setPd(pd);
				
				//page.setTotalResult(varList.size());//totalResult判断总数
				mv.setViewName("system/admin/upload");//跳转到的页面全文检索的页面
			} catch(Exception e){
				logger.error(e.toString(), e);
			}
			return mv;
		}
		
	   
			
			 /**
		     * 支持在线打开文件的一种方式
		     */
			@RequestMapping(value = "/downLoad1")  
		    public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
		        File f = new File(filePath);
		        if (!f.exists()) {
		            response.sendError(404, "File not found!");
		            return;
		        }
		        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		        byte[] buf = new byte[1024];
		        int len = 0;
		        response.reset(); // 非常重要
		        if (isOnLine) { // 在线打开方式
		            URL u = new URL("file:///" + filePath);
		            response.setContentType(u.openConnection().getContentType());
		            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
		            // 文件名应该编码成UTF-8
		        } else { // 纯下载方式
		            response.setContentType("application/x-msdownload");
		            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		        }
		        OutputStream out = response.getOutputStream();
		        while ((len = br.read(buf)) > 0)
		            out.write(buf, 0, len);
		        br.close();
		        out.close();
		    }
			
		    /**
		     * 文件下载
		     */
			@RequestMapping(value = "/download")  
		    public HttpServletResponse download(String path, HttpServletResponse response) {
		        try {
		        //	path=new String(path.getBytes("ISO-8859-1"), "UTF-8");
		            //path是指欲下载的文件的路径。
		            File file = new File(path);
		            //取得文件名。
		            String filename = file.getName();
		      //    filename=new String(filename.getBytes("ISO-8859-1"), "UTF-8");
		            System.out.println("附件名称："+filename); 
		            // 取得文件的后缀名。
		            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

		            // 以流的形式下载文件。
		            InputStream fis = new BufferedInputStream(new FileInputStream(path));
		            byte[] buffer = new byte[fis.available()];
		            fis.read(buffer);
		            fis.close();
		            // 清空response
		            response.reset();
		            // 设置response的Header
		            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
		            response.addHeader("Content-Length", "" + file.length());
		            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		            
		            //表头(注意中文乱码的问题)
		            response.setContentType("application/octet-stream;charset=utf-8");  
		            response.setHeader("Content-Disposition", "attachment;filename="  
		                    + new String(filename.getBytes(),"iso-8859-1")); 
		            
		            toClient.write(buffer);
		            toClient.flush();
		            toClient.close();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		        return response;
		    }
			
			
			/**
			 * 定时器扫描附件
			 */
			public static void showTimer() {
		        TimerTask task = new TimerTask() {
		            @Override
		            public void run() {
		                ++count;
		                try {
							createIndex();
							System.out.println("时间=" + new Date() + " 扫描附件了" + count + "次"); // 1次
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                
		            }
		        };

		        //设置执行时间
		        Calendar calendar = Calendar.getInstance();
		        int year = calendar.get(Calendar.YEAR);
		        int month = calendar.get(Calendar.MONTH);
		        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
		        //定制每天的21:09:00执行，
		        calendar.set(year, month, day, 17, 30, 00);
		        Date date = calendar.getTime();
		        Timer timer = new Timer();
		        System.out.println(date);
		        
		        int period = 86400 * 1000;
		        //每天的date时刻执行task，每隔1天重复执行
		        timer.schedule(task, date, period);
		        //每天的date时刻执行task, 仅执行一次
		        //timer.schedule(task, date);
		    }
			
			 /**
		     * 百度地图 map
			 * 后台
			 */
		    @RequestMapping(value="/baiduMap")
			public ModelAndView baiduMap(Page page){//分页查询
				logBefore(logger, "列表baiduMap");
			//	if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				List<PageData> listInfo = new ArrayList<PageData>();
				
				try{
					
					mv.setViewName("system/admin/baiduMap");//跳转到的页面全文检索的页面
				} catch(Exception e){
					logger.error(e.toString(), e);
				}
				return mv;
			}
			
		    
		    /**
			 * 新增附件列表
			 */
			@RequestMapping(value="/fileList")
			public ModelAndView fileList(Page page,String userName,String buMenId,String buMenName){
				logBefore(logger, "列表WorkGuanli");
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				try{
					pd = this.getPageData();
					
		            String NAME=pd.getString("NAME");     //关键词检索条件
					
					if(null != NAME && !"".equals(NAME)){
						pd.put("NAME", NAME.trim());
					}
					
					
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date =new Date();
					String time=format.format(date);
					if(null != userName && !"".equals(userName)){
						userName=new String(userName.getBytes("ISO-8859-1"), "UTF-8");
					}
					if(null != buMenName && !"".equals(buMenName)){
						buMenName=new String(buMenName.getBytes("ISO-8859-1"), "UTF-8");
					}
					
					pd.put("ASSIGNER",userName);//
					pd.put("ASSIGNDATE",time);	//主键
					pd.put("BUMENID", buMenId); //部门ID 利于创建文件夹
					pd.put("BUMENNAME", buMenName); //部门名称 
					/*String Id=pd.get("Id").toString();
					if(Id!=null&&!"".equals(Id)){
						pd = workguanliService.findById(pd);	//根据ID读取
					}*/
					page.setPd(pd);
					List<PageData>	varList = workguanliService.list(page);	//列出WorkGuanli列表
					mv.setViewName("workguanli/workguanli/workguanli_list");
					mv.addObject("varList", varList);
					mv.addObject("pd", pd);
				} catch(Exception e){
					logger.error(e.toString(), e);
				}
				return mv;
			}
			
			 /**
			    * 上传照片附件
			    * 判断是否已经上传图片了 orUrl是否为空
			    * @param request
			    * @param servletRequest
			    * @param id
			    * @return
			     * @throws Exception 
			    */
				@RequestMapping(value = "/uploadFile")  
				public ModelAndView handleFormUpload(MultipartHttpServletRequest request,Page page,HttpServletRequest servletRequest) throws Exception{ 
				  String title=request.getParameter("WORKNAME");
				  String createDate=request.getParameter("ASSIGNDATE");
				  String content=request.getParameter("CONTENT");
				  String type=request.getParameter("TYPE");
				  String creater=request.getParameter("ASSIGNER");
				  String buMenId=request.getParameter("buMenId"); 
				  String buMenName=request.getParameter("buMenName");
			//	  buMenName=new String(buMenName.getBytes("ISO-8859-1"), "UTF-8");
				  String fileName="";
				  System.out.println("上传附件名称:"+title+"部门ID:"+buMenId);
				  //同时将数据传到数据库中

				  StringBuffer orUrl1 = new StringBuffer("");
				  long date=new Date().getTime(); //唯一标识作用
				  List<MultipartFile> file = request.getFiles("file");  
				  ModelAndView mv = this.getModelAndView();
		          //上传附件路径
				  String path = "D:\\附件\\"+buMenId;//写死的路径(建立子文件夹)
				  //partyproductService.insertUrl(partyproduct.getId());
				  //partyproductService.insertUrl(id,file);
				  System.out.println("路径1："+path); 
				  //创建文件夹
				  FileUtil.createDir(path);
				  FileOutputStream fileOutputStream = null;  
				  for (int i = 0; i < file.size(); i++) {  
				   if (!file.get(i).isEmpty()) {  
				    fileName =  file.get(i).getOriginalFilename();//附件名称	
				    System.out.println("附件名称："+fileName);
				   /* String name =  GetPinyin.getPinYinHeadChar(fileName.substring(0, fileName.indexOf(".")));
				    fileName=name+fileName.substring(fileName.indexOf("."));*/
				    String tuPianUrl=path +"\\"+fileName;//注意要加上\\
				    File files = new File(tuPianUrl);
				    System.out.println("附件路径："+tuPianUrl);

				    try {  
				     fileOutputStream = new FileOutputStream(files);  
				     fileOutputStream.write(file.get(i).getBytes());  
				     fileOutputStream.flush();  
				    } catch (Exception e) {  
				     e.printStackTrace();  
				    }  
				    if (fileOutputStream != null) { // 关闭流  
				     try {  
				      fileOutputStream.close();  
				     } catch (IOException ie) {  
				      ie.printStackTrace();  
				     }  
				    }  
				   }  
				  }  

				   String orUrl=orUrl1.toString();
				   //在此判断type=2存到party表中;type=2存到chest上

				   System.out.println("图片URL地址："+orUrl);
				   Map<String,Object> map = new HashMap<String,Object>();
				   
			        PageData pd=new PageData();
				    String id=UUID.randomUUID().toString();
				  
				    pd.put("WORKGUANLI_ID", id);
				    pd.put("WORKNAME", title);
				    pd.put("ASSIGNER", creater);
				    pd.put("ASSIGNDATE", createDate);
				    pd.put("CONTENT", content);
				    pd.put("TYPE", type);
				    pd.put("FILENAME", fileName);
				    pd.put("FANKUI", buMenId);//作为部门ID使用
				    pd.put("BUMENNAME", buMenName);
				    
				    workguanliService.save(pd);
					map.put("result", "1");
					map.put("msg", "操作成功");
					map.put("data", "");
					pd.put("BUMENID", buMenId);//作为部门ID
				//	mv.setViewName("system/admin/aftsearch");//跳转到的页面全文检索的页面
					List<PageData>	varList = workguanliService.list(page);	//列出WorkGuanli列表
					mv.setViewName("workguanli/workguanli/workguanli_list");
					mv.addObject("varList", varList);
					mv.addObject("pd", pd);
					System.out.println("返回的json字符串："+AppUtil.returnObject(new PageData(), map));	
				//	return AppUtil.returnObject(new PageData(), map);  
					return mv; 
				 }  
			
			/**
			 * 去新增页面
			 */
			@RequestMapping(value="/goFileAdd")
			public ModelAndView goFileAdd(Page page){
				logBefore(logger, "去新增goFileAdd页面");
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				try {
					page.setPd(pd);
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date =new Date();
					String time=format.format(date);

					pd.put("ASSIGNER","曹芹");//默认为月坛街道
					pd.put("ASSIGNDATE",time);	//主键
					mv.setViewName("workguanli/workguanli/workguanli_edit");
					mv.addObject("msg", "uploadFile");
					//作为接收人的选择下拉框数据
					mv.addObject("pd", pd);
				} catch (Exception e) {
					logger.error(e.toString(), e);
				}						
				return mv;
			}	
			
			/**
			 * 去修改页面
			 */
			@RequestMapping(value="/gofileEdit")
			public ModelAndView gofileEdit(){
				logBefore(logger, "去修改gofileEdit页面");
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				try {

					pd = workguanliService.findById(pd);	//根据ID读取
					mv.setViewName("workguanli/workguanli/workguanli_edit");
					mv.addObject("msg", "fileEdit");
					mv.addObject("pd", pd);
				} catch (Exception e) {
					logger.error(e.toString(), e);
				}						
				return mv;
			}	
			
			/**
			 * 去修改页面
			 */
			@RequestMapping(value="/goXiangqing")
			public ModelAndView goXiangqing(HttpServletRequest request){
				logBefore(logger, "去goXiangqing详情页面");
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				PageData pd1 = new PageData();
				pd = this.getPageData();
				try {
                    String buMenId=request.getParameter("buMenId");
                    String buMenName=request.getParameter("buMenName");
                    String userName=request.getParameter("userName");
					pd = workguanliService.findById(pd);//根据ID读取
					mv.setViewName("workguanli/workguanli/workguanli_xiangqing");
					mv.addObject("msg", "fileEdit");
					pd1.put("buMenId", buMenId);
					pd1.put("buMenName", buMenName);
					pd1.put("userName", userName);
					mv.addObject("pd1", pd1);
					mv.addObject("pd", pd);
				} catch (Exception e) {
					logger.error(e.toString(), e);
				}						
				return mv;
			}
			
			/**
			 * 修改编辑 同时将附件也替换  solr缓存中也删除下相应的内容
			 */
			@RequestMapping(value="/fileEdit")
			public ModelAndView fileEdit(MultipartHttpServletRequest request,Page page) throws Exception{
				logBefore(logger, "修改附件内容");
				String urlString = "http://"+ip+"/solr"; 
		        SolrServer server = new CommonsHttpSolrServer(urlString);
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				pd = this.getPageData();
				 String fileName="";
				 String OldFileName=request.getParameter("OldFileName");
				 String id=request.getParameter("WORKGUANLI_ID");
				 String title=request.getParameter("WORKNAME");
				 String createDate=request.getParameter("ASSIGNDATE");
				 String content=request.getParameter("CONTENT");
				 String type=request.getParameter("TYPE");
				 String creater=request.getParameter("ASSIGNER");
				 String buMenName=request.getParameter("BUMENNAME");
				 String buMenId=request.getParameter("FANKUI");
				 //原来附件的路径	
				 String OldfilePath="D:\\附件\\"+buMenId+"\\"+OldFileName;
				 System.out.println("原来附件的路径	:"+OldfilePath);
				 StringBuffer orUrl1 = new StringBuffer(""); 
				//附件的改变
				 List<MultipartFile> file = request.getFiles("file");  
		          //上传附件路径
				  String path = "D:\\附件\\"+buMenId;//没有写死的路径(建立子文件夹)
				  //partyproductService.insertUrl(partyproduct.getId());
				  //partyproductService.insertUrl(id,file);
				  System.out.println("路径1："+path); 
				  //创建文件夹
				  FileUtil.createDir(path);
				  FileOutputStream fileOutputStream = null;  
				  for (int i = 0; i < file.size(); i++) {  
				   if (!file.get(i).isEmpty()) {  
				    fileName =  file.get(i).getOriginalFilename();//附件名称	
				    System.out.println("附件名称："+fileName);
				   /* String name =  GetPinyin.getPinYinHeadChar(fileName.substring(0, fileName.indexOf(".")));
				    fileName=name+fileName.substring(fileName.indexOf("."));*/
				    String tuPianUrl=path +"\\"+fileName;//注意要加上\\
				    File files = new File(tuPianUrl);
				    System.out.println("附件路径："+tuPianUrl);
				    pd.put("FILENAME", fileName);
				    //物理删除原来的附件
				    FileUtil.delFile(OldfilePath);
				    //根据ID(即原附件名称)在solr缓存中删除此附件
				    server.deleteById(OldFileName);
				    //提交服务
				    server.commit();
				    try {  
				     fileOutputStream = new FileOutputStream(files);  
				     fileOutputStream.write(file.get(i).getBytes());  
				     fileOutputStream.flush();  
				    } catch (Exception e) {  
				     e.printStackTrace();  
				    }  
				    if (fileOutputStream != null) { // 关闭流  
				     try {  
				      fileOutputStream.close();  
				     } catch (IOException ie) {  
				      ie.printStackTrace();  
				     }  
				    }  
				   }  
				  }  

			    String orUrl=orUrl1.toString();
			    //在此判断type=2存到party表中;type=2存到chest上

			    System.out.println("图片URL地址："+orUrl); 
				
			    pd.put("WORKGUANLI_ID", id);
			    pd.put("WORKNAME", title);
			    pd.put("ASSIGNER", creater);
			    pd.put("ASSIGNDATE", createDate);
			    pd.put("CONTENT", content);
			    pd.put("TYPE", type);

			    pd.put("BUMENNAME", buMenName);
				
				workguanliService.edit(pd);
				   
				List<PageData>	varList = workguanliService.list(page);	//列出WorkGuanli列表
				mv.setViewName("workguanli/workguanli/workguanli_list");
				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
				return mv;
			}

			
			/**
			 * 附件删除(顺便将solr缓存数据删除)
			 */
			@RequestMapping(value="/deleteFile")
			public void deleteFile(PrintWriter out){
				logBefore(logger, "删除WorkGuanli");
				PageData pd = new PageData();
				PageData pd1 = new PageData();
				try{
					//solr服务配置
					String urlString = "http://"+ip+"/solr"; 
			        SolrServer server = new CommonsHttpSolrServer(urlString);
					
					pd = this.getPageData();
					
					pd1 = workguanliService.findById(pd);	//根据ID读取
					
					String fileName=pd1.getString("FILENAME");
					server.deleteById(fileName);
				//	server.deleteByQuery("msg_title:"+fileName);;//根据ID删除缓存
					 //提交服务
			        server.commit();
					workguanliService.delete(pd);
					
					out.write("success");
					out.close();
				} catch(Exception e){
					logger.error(e.toString(), e);
				}
				
			}
			
			
			
			/**
			 * 附件批量删除
			 */
			@RequestMapping(value="/deleteFileAll")
			@ResponseBody
			public Object deleteFileAll() {
				logBefore(logger, "批量删除WorkGuanli");
				PageData pd = new PageData();		
				Map<String,Object> map = new HashMap<String,Object>();
				try {
					pd = this.getPageData();
					List<PageData> pdList = new ArrayList<PageData>();
					String DATA_IDS = pd.getString("DATA_IDS");
					if(null != DATA_IDS && !"".equals(DATA_IDS)){
						String ArrayDATA_IDS[] = DATA_IDS.split(",");
						workguanliService.deleteAll(ArrayDATA_IDS);
						pd.put("msg", "ok");
					}else{
						pd.put("msg", "no");   
					}
					pdList.add(pd);
					map.put("list", pdList);
				} catch (Exception e) {
					logger.error(e.toString(), e);
				} finally {
					logAfter(logger);
				}
				return AppUtil.returnObject(pd, map);
			}
			
			/**
			 * 去修改页面
			 */
			@RequestMapping(value="/clock")
			public ModelAndView clock(HttpServletRequest request){
				logBefore(logger, "去clock详情页面");
				ModelAndView mv = this.getModelAndView();
  
				try {
                  
					mv.setViewName("system/admin/clock");
					
				} catch (Exception e) {
					logger.error(e.toString(), e);
				}						
				return mv;
			}
			
			
			
}
