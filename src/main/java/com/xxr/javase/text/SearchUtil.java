package com.xxr.javase.text;

import com.sun.org.glassfish.gmbal.Description;

import java.io.*;
import java.util.*;

/**
 * @author xxr
 * @Description 查找某一个txt文件或者目录下所有text文件中的某个字段 并显示其位置数量
 * @date 2021/1/15 13:36
 */
public class SearchUtil {

    public static void main(String[] args) {
        List<File> t = new ArrayList<File>();
        String text = "";
        String path = "F:\\music\\text";
        //String path = "D:\\SPXD\\text";
        getAllTextFile(new File (path),t);
        try {
            search(t,text,"D:\\y.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *@Description 根据关键词查找，path为结果输出目录，为空则只在控制台打印不输出
     *@author xxr
     *@date 2021-01-15 16:14:31
     */
    public static void search(List<File> textFiles , String key,String path) throws Exception{
        Map<Integer,String> sortMap = new TreeMap<Integer, String>(
                new Comparator<Integer>() {
                    public int compare(Integer o1 , Integer o2) {
                        return o2.compareTo(o1);
                    }
                }
        );
        File pathFile = new File(path);
        pathFile.delete();
        if(!pathFile.exists()){
            pathFile.createNewFile();
        }
        FileWriter fw = new FileWriter(path,true);
        BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (path,true),"GBK"));
//            fw.write(f.getPath()+""+"\r\n");
        for(File f : textFiles){
            InputStreamReader isr = new InputStreamReader(new FileInputStream(f),"GBK");
            BufferedReader br = new BufferedReader(isr);
            String temp = null;
            int line = 0;
            int count = 0;
            while((temp = br.readLine()) != null){
                line++;
                if(temp.contains(key)){
                    count ++;
//                    fw.write("行数("+line + ") >>>" +temp+"\r\n");
                }
            }
//            fw.write("===================================================================="+"\r\n");
            if(count > 0){
                sortMap.put(count,sortMap.get(count) == null ? f.getName() :sortMap.get(count) + ","+f.getName());
//                fw.write(f.getAbsolutePath()+"\t\t------"+count  + "\r\n");
            }
            br.close();
            isr.close();
        }
        Set<Integer> set = sortMap.keySet();
        for(int i : set){
//            fw.write(i+"\t\t------"+sortMap.get(i)  + "\r\n");
            writer.write(i+"\t\t------"+sortMap.get(i)  + "\r\n");
        }
        writer.close();
        fw.close();
    }


    public static List<File> getAllTextFile(File pathFile, List<File> textFiles){
        if(!pathFile.exists()) {
            return null;
        }
        File[] files = pathFile.listFiles();
        if(files.length == 0) {
            return textFiles;
        }
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
