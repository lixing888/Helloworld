package com.fh.util;

import java.util.Calendar;
import java.util.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Days {
	
	public static void main(String[] args) {
		
		int num1 = 7;  		  
        int num2 = 9;  
        // 创建一个数值格式化对象  
        NumberFormat numberFormat = NumberFormat.getInstance();  
        // 设置精确到小数点后2位  
        numberFormat.setMaximumFractionDigits(2);  
        String result = numberFormat.format((float) num1 / (float) num2 * 100);  
        System.out.println("num1和num2的百分比为:" + result + "%");  

		Date d1 = new Date();
		Date d2 = new Date(2013,10,23,14,15,26);

		int days1 = (int)((d1.getTime() - d2.getTime())/86400000);

	//	System.out.println("间隔天数："  + days1);
		
		try {
			//时间转换类
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse("2016-11-01");
			Date date2 = sdf.parse("2013-09-30");
			//将转换的两个时间对象转换成Calendard对象
			Calendar can1 = Calendar.getInstance();
			can1.setTime(date1);
			Calendar can2 = Calendar.getInstance();
			can2.setTime(date2);
			//拿出两个年份
			int year1 = can1.get(Calendar.YEAR);
			int year2 = can2.get(Calendar.YEAR);
			//天数
			int days = 0;
			Calendar can = null;
			//如果can1 < can2
			//减去小的时间在这一年已经过了的天数
			//加上大的时间已过的天数
			if(can1.before(can2)){
				days -= can1.get(Calendar.DAY_OF_YEAR);
				days += can2.get(Calendar.DAY_OF_YEAR);
				can = can1;
			}else{
				days -= can2.get(Calendar.DAY_OF_YEAR);
				days += can1.get(Calendar.DAY_OF_YEAR);
				can = can2;
			}
			for (int i = 0; i < Math.abs(year2-year1); i++) {
				//获取小的时间当前年的总天数
				days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
				//再计算下一年。
				can.add(Calendar.YEAR, 1);
			}
			System.out.println("天数差："+days);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
			
}


