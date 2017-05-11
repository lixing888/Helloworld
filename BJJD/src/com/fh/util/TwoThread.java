package com.fh.util;
/**
 * 
 * <table border="1">
 * <tr><td>功能</td><td colspan="4">多线程测试</td></tr>
 * <tr><td>注意事项</td><td colspan="4">无</td></tr>
 * <tr><td rowspan="2">版本历史</td>
 * <td>修改人</td><td>日期</td><td>版本</td><td>描述</td></tr>
 * <tr><td>Administrator</td><td>2016年12月14日 上午10:08:57
 * </td><td>1.0.1</td><td>创建</td></tr>
 * </table>
 */
	public class TwoThread extends Thread {     
		public void run() { 
	             
	    for ( int i = 0; i < 10; i++ ) { 
	        System.out.println("新的线程=="+i);        
	    }     
	    
	} 
		
	public static void main(String[] args) {      
		TwoThread tt = new TwoThread();
		tt.start(); 
        for ( int i = 0; i < 10; i++ ) { 
            System.out.println("Main thread++"+i); 
        }    
	}
}