package com.zhj.event.utils;

import java.io.*;

/**
 * 读写txt文件的工具类
 */
public class FileUtil {

    /**
     * 根据文件路径path读取文件的内容
     * @param path 文件路径
     * @return 返回文件内容
     */
    public static String readFile(String path) {
        String str = "";
        String line;
        File file = new File(path);
        FileInputStream fs ;
        try {
            fs = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fs, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            //拼接字符串
            while ((line = br.readLine()) != null) {
                str += line;
            }

            isr.close();
            br.close();
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 将计算得到的相似度similarity写入路径为path的文件中
     * @param similarity 相似度,double型
     * @param path 要写入的文件的路径
     */
    public static void writeFile(Double similarity,String path){
        String str = "相似度为：" + similarity;
        File file = new File(path);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(str ,0, (str.length() > 8 ? 9 : str.length()));
            writer.write("\r\n");
            writer.close();
        } catch (IOException e) {
            //捕获异常
            e.printStackTrace();
        }
    }

}
