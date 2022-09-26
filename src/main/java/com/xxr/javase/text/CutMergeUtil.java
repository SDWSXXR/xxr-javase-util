package com.xxr.javase.text;

import java.io.*;

public class CutMergeUtil {
    public static void main(String[] args) throws Exception {
        cutWithKey("D:\\1.txt","D:\\1","");
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
}
