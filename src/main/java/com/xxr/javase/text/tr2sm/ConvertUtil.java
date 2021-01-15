package com.xxr.javase.text.tr2sm;

import com.github.stuxuhai.jpinyin.ChineseHelper;

import java.io.*;

/**
 * @author xxr
 * @Description 将中文文本中的繁体转换城简体或者简体转繁体
 * @date 2021/1/4 11:27
 */
public class ConvertUtil {


    public static void main(String[] args) {
        ConvertUtil.readTxtFile("D:\\1.txt");
    }



    /**
     * 简体转换为繁体
     * @param pinYinStr 要转换的字符串
     * @return
     */
    public static String convertToTraditionalChinese(String pinYinStr) {
        String tempStr = null;
        try {
            tempStr = ChineseHelper.convertToTraditionalChinese(pinYinStr);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        return tempStr;
    }


    /**
     * 繁体转换为简体
     * @param pinYinSt 要转换的字符串
     * @return
     */
    public static String convertToSimplifiedChinese(String pinYinSt) {
        String tempStr = null;
        try {
            tempStr = ChineseHelper.convertToSimplifiedChinese(pinYinSt);
        } catch (Exception e) {
            tempStr = pinYinSt;
            e.printStackTrace();
        }

        return tempStr;
    }

    public static void readTxtFile(String filePath){
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String linSim = convertToSimplifiedChinese(lineTxt);
                    WriteText(linSim,"D://"+file.getName()+"(sim)");
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    public static void WriteText(String text, String path){
        try {
            File newText = new File(path + ".txt");
            if(!newText.isFile()){
                newText.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newText,true),"utf-8"));
            bw.write(text+"\r\n");
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}
