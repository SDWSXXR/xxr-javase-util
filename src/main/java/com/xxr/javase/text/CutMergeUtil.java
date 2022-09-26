package com.xxr.javase.text;

import java.io.*;

public class CutMergeUtil {
    public static void main(String[] args) throws Exception {
        cutWithKey("D:\\1.txt","D:\\1","");
    }




    /**
     * 根据key分割文件
     * @param sourcePath    源txt文件
     * @param targetPath    分割后的子文件存放目录
     * @param key           分割关键字
     */
    public static void cutWithKey(String sourcePath, String targetPath, String key) throws Exception {
        File sourceFile = new File(sourcePath);
        if(!sourceFile.exists()){
            throw new RuntimeException("源文件不存在");
        }
        File targetFile = new File(targetPath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        if(!targetFile.isDirectory()){
            throw new RuntimeException("输出路径需为文件夹");
        }
        if(key == null || "".equals(key)){
            throw new RuntimeException("key值不能为空");
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(sourceFile),"GBK");
        BufferedReader br = new BufferedReader(isr);
        String temp = null;
        String text = "";
        int count = 0;
        while((temp = br.readLine()) != null){
            text += temp +"\r\n";
            if(temp.contains(key)){
                count ++;
                File txt = new File(targetPath+"//"+count+".txt");
                txt.createNewFile();
                BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (txt,true),"GBK"));
                writer.write(text);
                writer.close();
                text=temp +"\r\n";
            }
        }
        br.close();
        isr.close();
    }
}
