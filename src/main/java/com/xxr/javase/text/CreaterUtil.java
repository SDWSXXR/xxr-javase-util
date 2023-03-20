package com.xxr.javase.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CreaterUtil {

    public static void main(String[] args) {
        try {
            createHtml("D://1.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createHtml(String filePath) throws Exception{
        File txtFile = new File(filePath);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(txtFile),"GBK");
        BufferedReader br = new BufferedReader(isr);
        String temp = null;
        while((temp = br.readLine()) != null){
            temp = temp.trim();
            if("-".equals(temp)){
                System.out.println("\r\n\n\n\n");
                continue;
            }
            //读取一行
//            <td><el-input v-model="zhszpjVo.g1sxfBxYw" :disabled="disable" maxlength="2" class="input-center"></el-input></td>
//            <td>{{ zhszpjVo.g1sxfXx1Yw }}</td>
            String html = "<td colspan=\"2\"><el-input v-model=\"";
            String model = temp.substring(temp.indexOf("{") + 2,temp.lastIndexOf("}") -1).replace(" ","");
            html = html + model + "\" :disabled=\"disable\" maxlength=\"2\" class=\"input-center\"></el-input></td>";
            System.out.println(html);
        }
    }

}
