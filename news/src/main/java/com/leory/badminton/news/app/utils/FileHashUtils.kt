package com.leory.badminton.news.app.utils

import android.content.Context
import com.leory.commonlib.utils.AppUtils
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 * Describe : 翻译工具
 * Author : leory
 * Date : 2019-06-17
 */
object FileHashUtils {
    /**
     * 获取月份
     * @return
     */
    val month: HashMap<String, String>
        get() = readKeyValueTxtToMap(AppUtils.getApplication(), "tran_month.txt")
    /**
     * 获取球员名称
     * @return
     */
    val playerName: HashMap<String, String>
        get() = readKeyValueTxtToMap(AppUtils.getApplication(), "tran_player_name.txt")
    /**
     * 获取比赛名称
     * @return
     */
    val matchName: HashMap<String, String>
        get() = readKeyValueTxtToMap(AppUtils.getApplication(), "tran_match_name.txt")
    /**
     * 获取比赛分类
     * @return
     */
    val matchCategory: HashMap<String, String>
        get() = readKeyValueTxtToMap(AppUtils.getApplication(), "tran_match_category.txt")
    /**
     * 获取国家
     * @return
     */
    val country: HashMap<String, String>
        get() = readKeyValueTxtToMap(AppUtils.getApplication(), "tran_country.txt")

    /**
     * 获取与中国北京时差
     * @return
     */
    val timeDiffer: HashMap<String, String>
        get() = readKeyValueTxtToMap(AppUtils.getApplication(), "tran_time_differ.txt")

    /**
     * 从文件中获取key - value值
     * @param context
     * @param fileName
     * @return
     */
    private fun readKeyValueTxtToMap(context: Context, fileName: String): HashMap<String, String> {
        //循环直至返回map
        while (true) {
            val keyValueMap = HashMap<String, String>()//保存读取数据keyValueMap
            //每一个循环读取一组key=value
            while (true) {
                try {
                    val open = context.assets.open(
                            fileName)
                    val readArray = ByteArray(open.available())
                    open.read(readArray)
                    open.close()
                    val allLine = StringTokenizer(readArray.toString(Charset.forName("UTF-8")), "\r\n")//以"\r\n"作为key=value的分解标志
                    while (allLine.hasMoreTokens()) {
                        val oneLine = StringTokenizer(allLine.nextToken(), "=")//以"="作为分解标志
                        val leftKey = oneLine.nextToken()//读取第一个字符串key
                        if (!oneLine.hasMoreTokens()) {
                            break
                        }
                        val rightValue = oneLine.nextToken()//读取第二个字符串value
                        keyValueMap.put(leftKey, rightValue)
                    }
                    return keyValueMap
                } catch (e: IOException) {
                    e.printStackTrace()
                    return keyValueMap
                }

            }
        }
    }
}
