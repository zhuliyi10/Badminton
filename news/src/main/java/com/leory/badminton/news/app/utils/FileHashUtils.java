package com.leory.badminton.news.app.utils;

import android.content.Context;

import com.leory.commonlib.utils.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Describe : 翻译工具
 * Author : leory
 * Date : 2019-06-17
 */
public class FileHashUtils {
    /**
     * 获取月份
     * @return
     */
    public static HashMap getMonth(){
        return readKeyValueTxtToMap(AppUtils.getApplication(),"tran_month.txt");
    }
    /**
     * 获取球员名称
     * @return
     */
    public static HashMap getPlayerName(){
        return readKeyValueTxtToMap(AppUtils.getApplication(),"tran_player_name.txt");
    }
    /**
     * 获取比赛名称
     * @return
     */
    public static HashMap getMatchName(){
        return readKeyValueTxtToMap(AppUtils.getApplication(),"tran_match_name.txt");
    }
    /**
     * 获取比赛分类
     * @return
     */
    public static HashMap getMatchCategory(){
        return readKeyValueTxtToMap(AppUtils.getApplication(),"tran_match_category.txt");
    }
    /**
     * 获取国家
     * @return
     */
    public static HashMap getCountry(){
        return readKeyValueTxtToMap(AppUtils.getApplication(),"tran_country.txt");
    }

    /**
     * 获取与中国北京时差
     * @return
     */
    public static HashMap getTimeDiffer(){
        return readKeyValueTxtToMap(AppUtils.getApplication(),"tran_time_differ.txt");
    }
    /**
     * 从文件中获取key - value值
     * @param context
     * @param fileName
     * @return
     */
    public static HashMap readKeyValueTxtToMap(Context context,String fileName) {
        //循环直至返回map
        while (true) {
            final HashMap keyValueMap = new HashMap();//保存读取数据keyValueMap
            //每一个循环读取一组key=value
            while (true) {
                try {
                    final InputStream open = context.getAssets().open(
                            fileName);
                    final byte[] readArray = new byte[open.available()];
                    open.read(readArray);
                    open.close();
                    final StringTokenizer allLine = new StringTokenizer(new String(readArray, "UTF-8"), "\r\n");//以"\r\n"作为key=value的分解标志
                    while (allLine.hasMoreTokens()) {
                        final StringTokenizer oneLine = new StringTokenizer(allLine.nextToken(), "=");//以"="作为分解标志
                        final String leftKey = oneLine.nextToken();//读取第一个字符串key
                        if (!oneLine.hasMoreTokens()) {
                            break;
                        }
                        final String rightValue = oneLine.nextToken();//读取第二个字符串value
                        keyValueMap.put(leftKey, rightValue);
                    }
                    return keyValueMap;
                } catch (IOException e) {
                    e.printStackTrace();
                    return keyValueMap;
                }
            }
        }
    }
}
