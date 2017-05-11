package com.fh.util;
/*import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;

import java.io.*;
import java.sql.*;
import java.util.Date;

import com.test.db.*;
import com.test.utility.*;

*//**
 * Title: SearchIndexer
 * Description: 全文索引
 * Copyright:   Copyright (c) 2001
 * Company: test
 * @author Sean
 * @version 1.0
 *//*
public class SearchIndexer {
  private String indexPath = null;
  protected Analyzer analyzer = new ChineseAnalyzer();

  public SearchIndexer(String s) {
    this.indexPath = s;
  }
  *//**
   * 索引某日期以前的所有文档
   * @param fromdate
   * @return
   *//*
  public final void updateIndex(String fromdate) {
    Connection conn = DbUtil.getCon();
    IndexWriter indexWriter = null;
    try {
      indexWriter = getWriter(false);
     //索引发布系统内部文件
        PreparedStatement pstm = conn.prepareStatement(
            "select title,body,creationtime from document where creationtime > ‘" + fromdate +
            "‘ order by creationtime");
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
          String creationtime = rs.getString("creationtime");
          String title = rs.getString("title");
          String body = rs.getString("body");

          
          if (title == null || body == null) {
            continue;
          }
          try {
            addDocsToIndex(title,body, creationtime,indexWriter);
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
       }
      indexWriter.optimize();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      try {
        indexWriter.close();
        conn.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  *//**
   * 检查索引文件是否存在
   * @param s
   * @return 索引是否存在
   *//*
  private boolean indexExists(String s) {
    File file = new File(s + File.separator + "segments");
    return file.exists();
  }
  *//**
   * 增加一组索引
   * @param title
   * @param body
   * @param creationtime
   * @param indexwriter
   * @return
   *//*
  private final void addNewsToIndex(String docid, String url,String title, String body,
                                      String ptime, IndexWriter indexwriter) throws
      IOException {
    if (indexwriter == null) {
      return;
    }
    else {
      try {
        Document document = new Document();
        document.add(Field.Text("title", title));
        document.add(Field.Text("body", body));
        document.add(new Field("creationtime", creationtime, true, true, false));
        indexwriter.addDocument(document);
      }
      catch (Exception ex) {
    ex.printStackTrace();
      }
      return;
    }
  }
  *//**
   * 取得IndexWriter
   * @param flag 是否新建索引
   * @return IndexWriter
   *//*
  private IndexWriter getWriter(boolean flag) throws IOException {
    String s = indexPath;
    if (s == null) {
      throw new IOException("索引文件路径设置错误.");
    }
    indexPath = s + File.separator + "search";
    IndexWriter indexwriter = null;
    if (flag) {
      try {
        indexwriter = new IndexWriter(indexPath, analyzer, true);
      }
      catch (Exception exception) {
        System.err.println("ERROR: Failed to create a new index writer.");
        exception.printStackTrace();
      }
    }
    else {
      if (indexExists(indexPath)) {
        try {
          indexwriter = new IndexWriter(indexPath, analyzer, false);
        }
        catch (Exception exception1) {
          System.err.println("ERROR: Failed to open an index writer.");
          exception1.printStackTrace();
        }
      }
      else {
        try {
          indexwriter = new IndexWriter(indexPath, analyzer, true);
        }
        catch (Exception exception2) {
          System.err.println("ERROR: Failed to create a new index writer.");
          exception2.printStackTrace();
        }
      }
    }
    return indexwriter;
  }

  public static void main(String[] args) {
    String lastUpdate = "/home/lucenetest/lastUpdate.txt";
    SearchIndexer searchIndexer = new SearchIndexer("/home/lucenetest/index");
    //取出上次更新时间
    String str = Util.readTxtFile(lastUpdate);
    if(str==null || str.length()==0){
      str = new java.util.Date().toString();
    }
    searchIndexer.updateIndex(str);
    //写入当前时间
    Util.writeTxtFile(lastUpdate,new java.util.Date(),false);
  }
}

*/