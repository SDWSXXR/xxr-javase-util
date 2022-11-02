package com.xxr.javase.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Allen
 */
public class BtCatSelenium {
    public static void main(String[] args) {
        openUrl(creatUrlList("D://123.txt"),"2.txt");
    }

    public static List<String> creatUrlList(String path){
        List<String> urlList = new ArrayList<String>();
        File txtFile = new File(path);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(txtFile));
            BufferedReader br = new BufferedReader(isr);
            String lineText = null;
            while ((lineText = br.readLine()) != null){
                if(lineText.contains("-")){
                    String text = "http://clm2.net/search?word="+lineText.replace("\t","")+"&sort=hits";
                    urlList.add(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlList;
    }




    public static void openUrl (List<String> urlList, String outFileName) {
        List<String> errorList = new ArrayList<String>();
        System.setProperty("webdriver.chrome.driver",
                "D:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File("D://"+outFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("��ʼ��ȡ��Ϣ�������ݣ�"+urlList.size()+"��");
        int i = 0;
        int error = 0;
        int success = 0;
        int waring = 0;
        for(String url : urlList){
            try {
                String fanNum = url.substring(url.lastIndexOf("d=")+2,url.lastIndexOf("&"));
                driver.get(url);
                if(elementExists(driver,"/html/body/table/tbody/tr/td/div[2]/a")){
                    driver.findElement(By.xpath("/html/body/table/tbody/tr/td/div[2]/a")).click();
                }
                if(alertExists(driver)){
                    driver.switchTo().alert().accept();
                }
                if(elementExists(driver,"//*[@id=\"Search_list_wrapper\"]/li[1]/div[1]/div/a")){
                    List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"Search_list_wrapper\"]/li"));
                    boolean succ = false;
                    for(WebElement we :elements){
                        String text = we.findElement(By.xpath("div[2]")).getText();
                        text = text.replace(" ","").substring(text.indexOf("С��")+2);
                        text = text.substring(0,text.lastIndexOf("����"));
                        if(checkSize(text)){
                            //�������ס�˵��Ŀ���
                            WebElement ele = we.findElement(By.xpath("div[1]/div/a"));
                            driver.get(ele.getAttribute("href"));
                            if(alertExists(driver)){
                                driver.switchTo().alert().accept();
                            }
                            if(fanNum.length() > 7){
                                fw.write(fanNum+"\t"+driver.findElement(By.id("down-url")).getAttribute("href"));
                            }else{
                                fw.write(fanNum+"\t\t"+driver.findElement(By.id("down-url")).getAttribute("href"));
                            }
                            System.out.println(url+"\t��ȡ�ɹ���");
                            succ = true;
                            success++;
                            break;
                        }
                    }
                    if(!succ){
                        fw.write(fanNum+"\tδ�ҵ�����Ҫ����ļ������飡");
                        waring++;
                    }
                }else{
                    waring++;
                    fw.write(fanNum+"\tδ���ҵ����ݣ�");
                    System.out.println(url+"\tδ���ҵ����ݣ�");
                }
                fw.write("\r\n");
            }catch (Exception e){
                error++;
                errorList.add(url);
                System.err.println(url+"\t��ʧ�ܣ�:"+e);
            }
            i++;
        }
        System.out.println("ִ����ϣ���ִ��"+i+"�����ɹ�"+success+"��������"+waring+"����ʧ��"+error+"��");
        driver.quit();
        try {
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if(errorList.size()>0){
//            openUrl(errorList,path.substring(path.lastIndexOf("/")+1));
//        }
    }



    private static boolean elementExists(WebDriver driver,String xPath){
        try{
            driver.findElement(By.xpath(xPath));
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private static boolean alertExists(WebDriver driver){
        try {
            driver.switchTo().alert();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean checkSize(String sizeText){
        if(sizeText.contains("GB")){
            //����1g��
//            Double size = Double.valueOf(sizeText.replace(" ","").replace("GB",""));
//            if(size>4){
//                return false;
//            }
        }else if(sizeText.contains("MB")){
            //С��1g
            Double size = Double.valueOf(sizeText.replace(" ","").replace("MB",""));
            if(size<300){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

}
