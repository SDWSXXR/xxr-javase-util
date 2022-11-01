package com.xxr.javase.file;

import com.google.common.io.Files;
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
    public static void main(String[] args) {
//        delFileByTxt("D://demo");
//        copyFileName("");
//        creatUrlFile("");
//        List<String> urlList = creatUrlList(path);
//        openUrl(urlList,path.substring(path.lastIndexOf("/")+1));
//        findErrorFile(path);
        getSameFile("I://eclipse_work//.metadata//.mylyn//.taskListIndex//new a//分类","F://A陈加旭之前的F盘文件//.untitled//250//250");
    }



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

    /**
     * 根据文件名获取两个文件夹路径中包含的相同文件(以target为辅)
     */
    public static void getSameFile(String resource,String target){
        File resourceFile = new File(resource);
        File targetFile = new File(target);
        //分别获取文件
        List<File> resourceFiles = new ArrayList<File>();
        resourceFiles = getFileList(resourceFiles,resourceFile);
        List<File> targetFiles = new ArrayList<File>();
        targetFiles = getFileList(targetFiles,targetFile);
        for(File f : resourceFiles){
            for(File f2 : targetFiles) {
                if(getFileNameSuff(f.getName()).contains(getFileNameSuff(f2.getName()))){
                    System.out.println(f2.getName());
                    try {
//                        File toFile = new File("D://1//"+f2.getName());
//                        if(!toFile.getParentFile().exists()){
//                            toFile.mkdirs();
//                        }
//                        Files.copy(f2,toFile);
                        System.out.println(f2.delete());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 通过递归获取文件夹内所有的文件
     * @param path
     */
    public static List<File> getFileList(List<File> files ,File path){
        for(File f: path.listFiles()){
            if(f.isDirectory()){
                getFileList(files,f);
            }else{
                files.add(f);
            }
        }
        return  files;
    }

    public static  String getFileNameSuff(String name){
        return name.substring(0,name.lastIndexOf("."));
    }

}
