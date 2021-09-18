package com.zhj.event.utils;

import com.zhj.event.exception.SimHashException;

/**
 * @program: rghomework
 * @description: 根据SimilarHash哈希值计算海明距离的工具类
 * @author: 周华娟
 * @create: 2021-09-17 19:09
 **/
public class HammingDistanceUtil {
    /**
     * 计算两个simHash的海明距离
     * @param simHash1 文本1的simHash值
     * @param simHash2 文本2的simHash值
     * @return 返回两个simHash的海明距离
     */
    public static int getHammingDistance(String simHash1, String simHash2) {
        int distance = 0;
        try {
            if (simHash1.length() != simHash2.length()) {
                // 文本1和文本2的SimHash值长度不相等，不能计算汉明距离
                throw new SimHashException();
            } else {
                for (int i = 0; i < simHash1.length(); i++) {
                    if (simHash1.charAt(i) != simHash2.charAt(i)) {
                        distance++;
                    }
                }
                return distance;
            }
        } catch (SimHashException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 输入两个simHash值，输出相似度
     * @param simHash1 文本1的simHash值
     * @param simHash2 文本1的simHash值
     * @return 相似度
     */
    public static double getSimilarity(String simHash1, String simHash2) {
        int distance = getHammingDistance(simHash1, simHash2);
        return 0.01 * (100 - distance * 100 / 128);
    }


}
