package com.xxr.javase.text.search;

import com.sun.org.glassfish.gmbal.Description;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xxr
 * @Description 查找某一个txt文件或者目录下所有text文件中的某个字段 并显示其位置数量
 * @date 2021/1/15 13:36
 */
public class SearchUtil {

    public static void main(String[] args) {
        List<File> t = new ArrayList<File>();
        getAllTextFile(new File ("D:\\新建文件夹"),t);

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("D:\\1.txt"),"GBK");
            BufferedReader br = new BufferedReader(isr);
            String temp = null;
            while((temp = br.readLine()) != null){
                if(temp.contains("")){
                    System.out.println(temp);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *@Description 根据关键词查找，path为结果输出目录，为空则只在控制台打印不输出
     *@author xxr
     *@date 2021-01-15 16:14:31
     */
    public static void search(List<File> textFiles , String key,String path){

        for(File f : textFiles){
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(f),"GBK");
                BufferedReader br = new BufferedReader(isr);
                String temp = null;
                while((temp = br.readLine()) != null){
                    if(temp.contains(key)){
                        System.out.println(temp);
                        
                    }
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static List<File> getAllTextFile(File pathFile, List<File> textFiles){
        if(!pathFile.exists())return null;
        File[] files = pathFile.listFiles();
        if(files.length == 0)return textFiles;
        for(File f : files){
            if(f.isDirectory()){
                getAllTextFile(f,textFiles);
            }else{
                if("txt".equals(getFileLastName(f))){
                    textFiles.add(f);
                }
            }
        }
        return textFiles;
    }

    static String getFileLastName(File file){
        return file.getName().substring(file.getName().lastIndexOf(".")+1);
    }

}
