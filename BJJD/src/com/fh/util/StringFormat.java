package com.fh.util;

import java.text.DecimalFormat;

public class StringFormat {   
	  public static void main(String[] args) {   
	    int youNumber = 1;   
	    // 0 代表前面补充0   
	    // 4 代表长度为4   
	    // d 代表参数为正数型   
	    String str = String.format("%04d", youNumber);   
	    System.out.println(str); // 0001   
	    String num= haoAddOne_2("99");
	    System.out.println(num);
	  }
	  
	  
	     private static final String STR_FORMAT = "0000"; 

		public static String haoAddOne_2(String liuShuiHao){
		    Integer intHao = Integer.parseInt(liuShuiHao);
		    intHao++;
		    DecimalFormat df = new DecimalFormat(STR_FORMAT);
		    return df.format(intHao);
		}
	}  
