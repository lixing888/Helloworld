package com.fh.controller.userInfo.bjjduserinfo;

import org.apache.solr.client.solrj.beans.Field;  

/**
 * ˵����������sechma.xml�е�����Ҫ��ͬ.ע�⣺����ʹ��score������������ֶΣ���slor���ó�ͻ����exception��
 */
public class BookIndex {  
    @Field    
    private String zjid ;  
    @Field    
    private String title;  
    @Field    
    private String ssid;  
    @Field    
    private String dxid;  
    @Field   
    private String bookname;  
    @Field   
    private String author;  
    @Field   
    private String publisher;  
    @Field   
    private String pubdate;  
    @Field   
    private String year;  
    @Field   
    private String fenlei;  
    @Field   
    private String score1;  
    @Field   
    private String isbn;  
    @Field   
    private String fenleiurl;  
    @Field   
    private String mulu;  
    @Field   
    private String isp;  
    @Field   
    private String iep;
	public String getZjid() {
		return zjid;
	}
	public void setZjid(String zjid) {
		this.zjid = zjid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getDxid() {
		return dxid;
	}
	public void setDxid(String dxid) {
		this.dxid = dxid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getFenlei() {
		return fenlei;
	}
	public void setFenlei(String fenlei) {
		this.fenlei = fenlei;
	}
	public String getScore1() {
		return score1;
	}
	public void setScore1(String score1) {
		this.score1 = score1;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getFenleiurl() {
		return fenleiurl;
	}
	public void setFenleiurl(String fenleiurl) {
		this.fenleiurl = fenleiurl;
	}
	public String getMulu() {
		return mulu;
	}
	public void setMulu(String mulu) {
		this.mulu = mulu;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getIep() {
		return iep;
	}
	public void setIep(String iep) {
		this.iep = iep;
	}  
    
}
