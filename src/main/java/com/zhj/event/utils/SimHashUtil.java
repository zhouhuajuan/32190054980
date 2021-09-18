package com.zhj.event.utils;

import com.hankcs.hanlp.HanLP;
import com.zhj.event.exception.FileIsNullException;
import com.zhj.event.exception.FileIsShortException;
import com.zhj.event.exception.WeightIsWrongException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @program: rghomework
 * @description: 传入文本计算SimHash值
 * @author: 周华娟
 * @create: 2021-09-17 19:20
 **/
public class SimHashUtil {

    /**
     * 计算字符串的hash值并输出
     * @param str 字符串
     * @return 返回str的hash值
     */
    public static String getHash(String str) {
        //捕获异常 NoSuchAlgorithmException 和 UnsupportedEncodingException
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return new BigInteger(1, messageDigest.digest(str.getBytes("UTF-8"))).toString(2);
        }catch(Exception e){
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 传入String,计算出它的simHash值，并以字符串形式输出
     * @param str 传入的Srting类型字符串
     * @return 返回str的simHash值
     */
    public static String getSimHash(String str){
        //文本内容为空，捕获异常
        try {
            if (str.length() == 0){
                throw new FileIsNullException();
            }
        } catch (FileIsNullException e) {
            e.printStackTrace();
        }

        //定义一个128位的int型数组，存放所有关键字的添加权重后的哈希码
        int[] index = new int[128];

        //1、分词
        List<String> list = HanLP.extractKeyword(str, str.length());
        //文本长度小于15时会造成分权异常
        try {
            if (list.size() < 15) throw new WeightIsWrongException();
        } catch (WeightIsWrongException e) {
            e.printStackTrace();
        }

        //文本长度小于150，过短取不到关键字
        try {
            if (list.size() > 15 && list.size() <150){
                throw new FileIsShortException();
            }
        } catch (FileIsShortException e) {
            e.printStackTrace();
        }


        //遍历分词后的数组
        for(int i=0;i<list.size();i++){
            //2.哈希：计算每个分词的哈希值，得到一个128位定长的二进制序列
            StringBuilder hash = new StringBuilder(getHash(list.get(i)));
            if (hash.length() < 128) {
                // hash值可能少于128位，在低位以0补齐
                int dif = 128 - hash.length();
                for (int j = 0; j < dif; j++) {
                    hash.append("0");
                }
            }

            // 3、加权、合并
            for (int j = 0; j < index.length; j++) {
                //判断hash的每一位是否==1
                if (hash.charAt(j) == '1') {
                    //权重分15级，由词频从高到低，取权重15~0
                    index[j] += (15 - (i / (list.size() / 15)));
                } else {
                    index[j] -= (15 - (i / (list.size() / 15)));
                }
            }
        }

        // 4、降维
        StringBuilder simHash = new StringBuilder();
        for (int i : index) {
            if (i <= 0) {
                simHash.append("0");
            } else {
                simHash.append("1");
            }
        }
        return simHash.toString();
    }

}
