package com.fh.util;

import java.io.File;
import java.util.LinkedList;
import java.util.TreeMap;
 
 
public class GetDirectory
{
    public static TreeMap<File, LinkedList<File>> dirFiles = new TreeMap<File, LinkedList<File>>();
     
    static void getDirectoryFiles(File dir)
    {
        if (!dir.isDirectory())
        {
            return;
        }
        LinkedList<File> files= new LinkedList<File>();
        File[] filesinDir = dir.listFiles();
        if(filesinDir.length > 0)
        {
            for (int i = 0; i < filesinDir.length; i++)
            {
                files.add(filesinDir[i]);
            }
        }
        else 
        {
            dirFiles.put(dir, null);
            return;
        }
        dirFiles.put(dir, files);
        for(int i = 0; i < filesinDir.length; i++)
        {
            if (filesinDir[i].isDirectory())
            {
                getDirectoryFiles(filesinDir[i]);
            }
        }
         
    }
 
    /**
     * @param args
     */
    public static void main(String[] args){
        /*GetDirectory.getDirectoryFiles(new File("src"));
        //Iterator<Entry<File, LinkedList<File>>> iterator = GetDirectory.dirFiles.entrySet().iterator();
        Iterator<File> iterator = GetDirectory.dirFiles.keySet().iterator();
        while (iterator.hasNext()){
            File dir = iterator.next();
            System.out.println(dir.getAbsolutePath());
            LinkedList<File> fileInDir = GetDirectory.dirFiles.get(dir);
            if (fileInDir != null){
                Iterator<File> it = fileInDir.iterator();
                while (it.hasNext()){
                    System.out.println(it.next().getAbsolutePath());
                }
            }
        }*/
    	File file=new File("d:/附件");

        File[] files = file.listFiles();

        if (files != null) {

              for (File f : files) {
                    if(f.isDirectory()){//判断是否为文件夹
                    	
                    	File fileChild=new File(f.getPath());
                    	File[] fileChilds = fileChild.listFiles();
                    	for(File fs : fileChilds){
                    		System.out.println("附件路径：" + fs.getAbsolutePath()+"===》》》附件名："+fs.getName());
                    	}
                    	/*System.out.println(f.getPath());
                    	System.out.println(GetDirectory(f.getPath()).get(0));*/
                    }
              }

        }
    }
    
    
    public static LinkedList<File> GetDirectory(String path) {  
    	      File file = new File(path);  
    	      LinkedList<File> Dirlist = new LinkedList<File>(); // 保存待遍历文件夹的列表  
    	     LinkedList<File> fileList = new LinkedList<File>();  
    	        GetOneDir(file, Dirlist, fileList);// 调用遍历文件夹根目录文件的方法  
    	       File tmp;  
    	        while (!Dirlist.isEmpty()) {  
    	           tmp = (File) Dirlist.removeFirst();// 从文件夹列表中删除第一个文件夹，并返回该文件夹赋给tmp变量  
    	           // 遍历这个文件夹下的所有文件，并把  
    	          GetOneDir(tmp, Dirlist, fileList);  
    	
    	        }  
    	       return fileList;  
    	    }  

    private static void GetOneDir(File file, LinkedList<File> Dirlist,  
            LinkedList<File> fileList) {  
        // 姣忎釜鏂囦欢澶归亶鍘嗛兘浼氳皟鐢ㄨ鏂规硶  
        File[] files = file.listFiles();  
  
        if (files == null || files.length == 0) {  
            return;  
        }  
        for (File f : files) {  
            if (f.isDirectory()) {  
                Dirlist.add(f);  
            } else {  
                // 杩欓噷鍒楀嚭褰撳墠鏂囦欢澶规牴鐩綍涓嬬殑鎵€鏈夋枃浠?骞舵坊鍔犲埌fileList鍒楄〃涓? 
                fileList.add(f);  
                // System.out.println("file==>" + f);  
  
            }  
        }  
    }  
}
