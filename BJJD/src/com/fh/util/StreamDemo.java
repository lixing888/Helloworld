package com.fh.util;

import java.io.*;

public class StreamDemo{
 //将数据写入到txt文件中
 public static void main(String args[]){
  File f = new File("c:\\temp1.txt") ;
  OutputStream out = null ;
  try {
     out = new FileOutputStream(f) ;
  } 
  catch (FileNotFoundException e) {
     e.printStackTrace();
  }

  // 将字符串转成字节数组
  byte b[] = "从系统开放性、灵活性、可扩展性考虑，系统及管理平台需能同时支持主流操作系统平台和数据库，包括Windows、Solaris、AIX、Linux平台。数据库支持：SQL、Oracle、DB2等业界常用的数据库。".getBytes() ;

  try{
   // 将byte数组写入到文件之中
   out.write(b) ;

  } 

  catch (IOException e1) {

   e1.printStackTrace();

  }

  try {

   out.close() ;

  } 

  catch (IOException e2) {

   e2.printStackTrace();

  }
  // 以下为读文件操作
  InputStream in = null ;
  try  {
     in = new FileInputStream(f) ;
  } 
  catch (FileNotFoundException e3) {
     e3.printStackTrace();
  }

  // 开辟一个空间用于接收文件读进来的数据

  byte b1[] = new byte[1024] ;
  int i = 0 ;
  try {
     // 将b1的引用传递到read()方法之中，同时此方法返回读入数据的个数
     i = in.read(b1) ;
  } 
  catch (IOException e4) {
     e4.printStackTrace();
  }
  try{
     in.close() ;
  } 

  catch (IOException e5) {
     e5.printStackTrace();
  }
  //将byte数组转换为字符串输出
  System.out.println(new String(b1,0,i)) ;
 }

}
