package com.fh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeSub {

	public static void main(String args[]){  
        /*Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}");  
        Matcher matcher = pattern.matcher("关于召开知识管理典型设计评审工作会议的通知 2011-4-29 15:30:18公司各部门：兹定于2011年5月4日下午14：00在");  
           
        String dateStr = null;  
        if(matcher.find()){  
          dateStr = matcher.group(0);  
        }  
          
        String str =dateStr.toString();  
        System.out.println(str);  */
        
        
        String reg = "(?<=截至)[0-9]{4}[年][0-9]{1,2}[月][0-9]{1,2}[日]";
        String reg2 = "[0-9]{4}[年][0-9]{1,2}[月][0-9]{1,2}[日](?=到期)";
        String str1 = "1.2013年04月03日机构“ZC”发放的400,000元（人民币）个人住房贷款，业务号X，组合（不含保证）担保，按其他方式归还，2043年04月03日到期。截至2014年12月03日，";
        
        Pattern pattern1 = Pattern.compile (reg);
        Matcher matcher1 = pattern1.matcher (str1);
        Pattern pattern2 = Pattern.compile (reg2);
        Matcher matcher2 = pattern2.matcher (str1);
        while (matcher1.find ()){
            System.out.println ("截至日期:"+matcher1.group ());
        }
        while (matcher2.find ()){
            System.out.println ("到期日期:"+matcher2.group ());
        }
         
    }  
	
	
}
