package com.xxr.javase.file;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  文件工具类
 * @author XXR
 */
public class FileUtils {

//    public  static  String path = "D:\\st";
//
//    public static void main(String[] args) {
////        delFileByTxt("D://demo");
////        copyFileName("");
////        creatUrlFile("");
////        List<String> urlList = creatUrlList(path);
////        openUrl(urlList,path.substring(path.lastIndexOf("/")+1));
//        findErrorFile(path);
//    }



    public static void copyFileName(String path){
        File dic = new File(path);
        try {
            FileWriter fw = new FileWriter(new File("D:/",dic.getName()+".txt"));
            File[] files = dic.listFiles();
            for(File f : files){
                fw.write(f.getName().substring(0,f.getName().lastIndexOf(".")));
                fw.write("\t\t");
                fw.write("\r\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void findErrorFile(String path){
        try{
            File file = new File(path);
            if(!file.exists()){
                System.err.println("文件路径不对！");
                return;
            }
            File errorFileTxt = new File("D://ErrorFile.txt");
            FileWriter errorFileTxtWriter = new FileWriter(errorFileTxt);
            File[] files = file.listFiles();
            if (files != null) {
                for(File f : files){
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
                    BufferedReader br = new BufferedReader(isr);
                    String lineText = null;
                    while ((lineText = br.readLine()) != null){
                        if(lineText.contains("未找到符合要求的文件")){
                            errorFileTxtWriter.write(lineText);
                            errorFileTxtWriter.write("\r\n");
                        }
                    }
                    br.close();
                    isr.close();
                }
            }
            errorFileTxtWriter.flush();
            errorFileTxtWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public static void delFileByTxt(String path){
        File txtFile = new File("D://1.txt");
        File file = new File(path);
        File mkdir = new File(path + "/error");
        mkdir.mkdir();
        File[] files = file.listFiles();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(txtFile));
            BufferedReader br = new BufferedReader(isr);
            String lineText = null;
            while ((lineText = br.readLine()) != null){
                for(File f : files){
                    if(f.getName().equals("error")){
                        continue;
                    }
                    if(lineText.trim().equals(f.getName().substring(0,f.getName().lastIndexOf(".")))){
                        f.renameTo(new File(mkdir.getAbsolutePath()+"/"+lineText.trim()+".jpg"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void moveFileToParent(String path){
        File parentFile = new File(path);
        File[] files = parentFile.listFiles();
        if(files!=null){
            for(File pFile : files){
                if(pFile.isDirectory()){
                    File[] files1 = pFile.listFiles();
                    for(File f : files1){
                        boolean b = f.renameTo(new File("path+\"/\"+f.getName()"));
                        System.out.println(b ? f.getName()+"：传输成功！" : f.getName()+"：传输失败！");
                    }
                }
            }
        }
    }

    public static void getWordNum(String path ,String word){
        File file = new File(path);
        for(File f : file.listFiles()){
            int num = 0;
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
                BufferedReader br = new BufferedReader(isr);
                String temp = null;
                while((temp = br.readLine()) != null){
                    if(temp.contains(word)){
                        num++;
                    }
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(num!=0){
                System.out.println(f.getName());
                System.out.println(num);
            }

        }

    }


}
