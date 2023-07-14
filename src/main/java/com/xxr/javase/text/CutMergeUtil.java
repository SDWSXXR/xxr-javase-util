package com.xxr.javase.text;

import com.xxr.javase.file.FileUtils;

import java.io.*;
import java.util.*;

public class CutMergeUtil {
    public static void main(String[] args) throws Exception {
//        cutWithKey("D:\\1.txt","D:\\1","");
        mergeTxt("D:\\t",".txt");

    }




    /**
     * ����key�ָ��ļ�
     * @param sourcePath    Դtxt�ļ�
     * @param targetPath    �ָ������ļ����Ŀ¼
     * @param key           �ָ�ؼ���
     */
    public static void cutWithKey(String sourcePath, String targetPath, String key) throws Exception {
        File sourceFile = new File(sourcePath);
        if(!sourceFile.exists()){
            throw new RuntimeException("Դ�ļ�������");
        }
        File targetFile = new File(targetPath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        if(!targetFile.isDirectory()){
            throw new RuntimeException("���·����Ϊ�ļ���");
        }
        if(key == null || "".equals(key)){
            throw new RuntimeException("keyֵ����Ϊ��");
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

    public static void mergeTxt(String path,String fileName){
        try {
            File souce = new File(path + "//merge");
            File dest = new File(path + "//" + fileName);
            dest.createNewFile();
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (dest,true),"UTF-8"));
            String temp = null;
            List<File> files = getTxtFiles(souce);
            files.sort(Comparator.comparing(f -> Integer.parseInt(FileUtils.getFileNameSuff(f.getName()))));
            for(File f : files){
                InputStreamReader isr = new InputStreamReader(new FileInputStream(f),"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                while((temp = br.readLine()) != null){
                    writer.write(temp);
                    writer.write("\r\n");
                }
                br.close();
                isr.close();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<File> getTxtFiles(File souce) {
        List<File> l = new ArrayList<>();
        for(File f : souce.listFiles()){
            if(!f.isDirectory() && f.getName().contains("txt")){
                l.add(f);
            }
        }
        return l;
    }
}
