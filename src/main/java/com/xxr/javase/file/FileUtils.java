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
 *  �ļ�������
 * @author XXR
 */
public class FileUtils {

//    public  static  String path = "D:\\st";
//
    public static void main(String[] args) {
//        delFileByTxt("D://demo");
//        copyFileName("F :\\A�¼���֮ǰ��F���ļ�\\.untitled\\250");
//        creatUrlFile("");
//        List<String> urlList = creatUrlList(path);
//        openUrl(urlList,path.substring(path.lastIndexOf("/")+1));
//        findErrorFile(path);
//        getSameFile("I://eclipse_work//.metadata//.mylyn//.taskListIndex//new a//����","F://A�¼���֮ǰ��F���ļ�//.untitled//250//250");
//        copyFileByText("D://1.txt","F://A�¼���֮ǰ��F���ļ�//.untitled//250");
        moveAllFileToPath("\\",
                "\\");
//        rnameByKey("C:\\Users\\17279\\Desktop\\1\\����","{");
    }

    public static void rnameByKey(String path ,String key){
        File file = new File(path);
        for(File f : file.listFiles()){
            String name = f.getName();
            if(name.contains(key)){
                name = name.substring(0,name.lastIndexOf(key)) + ".txt";
            }
            f.renameTo(new File("D://txt//" + name));
        }
    }



    public static void copyFileName(String path){
        File dic = new File(path);
        try {
            FileWriter fw = new FileWriter(new File("D:/",dic.getName()+".txt"));
            File[] files = dic.listFiles();
            for(File f : files){
                fw.write(f.getName().substring(0,f.getName().lastIndexOf(".")));
                fw.write("\t\t\t");
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
                System.err.println("�ļ�·�����ԣ�");
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
                        if(lineText.contains("δ�ҵ�����Ҫ����ļ�")){
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

    /**
     * ת��pathĿ¼�����е��ļ������ļ��У� ��mainPathĿ¼
     * @param path
     */
    public static void moveAllFileToPath(String path,String mainPath){
        File mainFile = new File(path);
        if(!mainFile.exists()){
            throw new RuntimeException(path+"Ŀ¼�����ڣ�");
        }
        File[] files = mainFile.listFiles();
        if(files == null){
            throw new RuntimeException(path+"Ŀ¼��û�����ļ�����");
        }
        for(File f : files){
            if(f.isDirectory()){//������ļ��� �͵ݹ�һ�¼���ת��
                moveAllFileToPath(f.getAbsolutePath(),mainPath);
            }else{
                if(!path.equals(mainPath)){
                    String newPath = rename(mainPath + "/" + f.getName());
                    f.renameTo(new File(newPath));
                }
            }
        }
    }

    private static int nowKey = 0;
    private static synchronized int getKey(){
        //�˴�����˼����  i++  �� i+=1������
        //i++ ����i+1֮ǰ��ֵ   i+=1 ����i+1֮���ֵ
        return nowKey +=1 ;
    }

    /**
     * ��һ���ļ���Ŀ¼ �ж�����ļ��治���� ������ھ���������
     * @param path
     * @return
     */
    private static String rename(String path){
        String result = "";
        File temp = new File(path);
        String fileName = temp.getName();
        if(temp.exists()){
            fileName = getFileNameSuff(fileName) + "_" +getKey()+ getFileType(fileName);
        }else{
            return temp.getAbsolutePath();
        }
        File newFile = new File(temp.getParent() + "/" + fileName);
        if(newFile.exists()){
            result = rename(newFile.getParent() + "/" +  temp.getName());
        }else{
            result =  newFile.getAbsolutePath();
        }
        nowKey = 0;//�ҵ����������ļ� ��key����Ϊ0
        return result;
    }

    public static void moveFileToParent(String path){
        File parentFile = new File(path);
        File[] files = parentFile.listFiles();
        if(files!=null){
            for(File pFile : files){
                if(pFile.isDirectory()){
                    File[] files1 = pFile.listFiles();
                    for(File f : files1){
                        boolean b = f.renameTo(new File(path+f.getName()));
                        System.out.println(b ? f.getName()+"������ɹ���" : f.getName()+"������ʧ�ܣ�");
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
     * �����ļ�����ȡ�����ļ���·���а�������ͬ�ļ�(��targetΪ��)
     */
    public static void getSameFile(String resource,String target){
        File resourceFile = new File(resource);
        File targetFile = new File(target);
        //�ֱ��ȡ�ļ�
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
     * ͨ���ݹ��ȡ�ļ��������е��ļ�
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

    public static void copyFileByText(String txtPath,String filePath){
        try{
            InputStreamReader isr = new InputStreamReader(new FileInputStream(txtPath),"GBK");
            List<File> fileList = new ArrayList<File>();
            fileList = getFileList(fileList,new File(filePath));
            BufferedReader br = new BufferedReader(isr);
            String temp = null;
            while((temp = br.readLine()) != null){
                for(File f: fileList){
                    if(f.getName().contains(temp.replace("\t",""))){
                        Files.copy(f,new File("D://1//"+f.getName()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static  String getFileNameSuff(String name){
        return name.substring(0,name.lastIndexOf("."));
    }
    public static  String getFileType(String name){
        return name.substring(name.lastIndexOf("."));
    }

}
