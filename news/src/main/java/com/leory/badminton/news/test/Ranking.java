package com.leory.badminton.news.test;

import com.leory.badminton.news.mvp.model.bean.RankingBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Describe : todo
 * Author : zhuly
 * Date : 2019-07-04
 */
public class Ranking {
    private static HashMap<String, String> countryMap;
    private static HashMap<String, String> playerNameMap;
    private static BufferedWriter out;
    private static String type = "wd";

    public static void main(String[] args) {
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(type + ".csv"), "UTF-8"));
            out.write("name,type,value,date");
            out.newLine();
//            Date today = new Date();


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date today = df.parse("2019-07-05");
            Date setDate = df.parse("2010-01-05");
            long betweenDay = (today.getTime() - setDate.getTime()) / (1000 * 3600 * 24);
            int dataNum = (int) (betweenDay / 7) + 1;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.WEEK_OF_YEAR, -dataNum);
            while (dataNum > 0) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                System.out.print(dataNum);
                System.out.print(" ");
                System.out.print(df.format(calendar.getTime()));
                System.out.print(" ");
                System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
                getHistoryData(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR), df.format(calendar.getTime()));
                dataNum--;
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getHistoryData(int year, int week, String date) throws IOException {
        String urlType = "null";
        if (type.equals("ms")) {
            urlType = "6/men-s-singles/";
        } else if (type.equals("ws")) {
            urlType = "7/women-s-singles/";
        } else if (type.equals("md")) {
            urlType = "8/men-s-doubles/";
        } else if (type.equals("wd")) {
            urlType = "9/women-s-doubles/";
        } else if (type.equals("xd")) {
            urlType = "10/mixed-doubles/";
        }
        StringBuffer urlBuffer = new StringBuffer().append("https://bwfbadminton.cn/rankings/2/bwf-world-rankings/")
                .append(urlType).append(year).append("/").append(week).append("?rows=10&page_no=1");
        String result = sendGet(urlBuffer.toString());
        List<RankingBean> data = getRankingData(result);
        array2CSV(data, date);
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    private static List<RankingBean> getRankingData(String html) {
        List<RankingBean> rankingList = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements trList = doc.select("tr");
            for (int i = 1; i < trList.size(); i = i + 2) {
                Element tr = trList.get(i);
                Elements tdList = tr.select("td");
                if (tdList.size() == 8) {
                    RankingBean bean = new RankingBean();
                    bean.setRankingNum(tdList.get(0).text());
                    Elements countries = tdList.get(1).select("div.country");
                    if (countries.size() > 0) {
                        bean.setCountryName(translateCountry(countries.get(0).select("span").first().text()));
                        bean.setCountryFlagUrl(countries.get(0).select("img").first().attr("src"));
                    }
                    if (countries.size() > 1) {
                        bean.setCountry2Name(translateCountry(countries.get(1).select("span").first().text()));
                        bean.setCountryFlag2Url(countries.get(1).select("img").first().attr("src"));
                    }
                    Elements players = tdList.get(2).select("div.player span");
                    if (players.size() > 0) {
                        bean.setPlayerName(translatePlayerName(players.get(0).select("a").first().text()));
                        bean.setPlayerUrl(players.get(0).select("a").first().attr("href"));
                    }
                    if (players.size() > 1) {
                        bean.setPlayer2Name(translatePlayerName(players.get(1).select("a").first().text()));
                        bean.setPlayer2Url(players.get(1).select("a").first().attr("href"));
                    }
                    bean.setRiseOrDrop(Integer.parseInt(tdList.get(3).select("span").first().text()));
                    Element arrowElement=tdList.get(3).select("img").first();
                    if(arrowElement!=null){
                        if(arrowElement.attr("href").contains("arrow-down")){//为负
                            bean.setRiseOrDrop(0-bean.getRiseOrDrop());
                        }
                    }
                    bean.setWinAndLoss(tdList.get(4).text());
                    bean.setBonus(tdList.get(5).text());
                    String point = tdList.get(6).select("strong").first().text();
                    if (point != null) {
                        String[] points = point.split("/");
                        if (points.length > 0) {
                            bean.setPoints(points[0].trim());
                        }
                    }

                    bean.setPlayerId(tdList.get(7).select("div").first().attr("id"));
                    rankingList.add(bean);
                }

            }


        }
        return rankingList;
    }

    /**
     * 翻译国家
     *
     * @param key
     * @return
     */
    private static String translateCountry(String key) {
        if (countryMap == null) {
            countryMap = FileHashUtils.getCountry();
        }
        String value = countryMap.get(key);
        if (value == null) {
            return key;
        } else {
            return value;
        }
    }

    /**
     * 翻译运动员名字
     *
     * @param key
     * @return
     */
    private static String translatePlayerName(String key) {
        if (playerNameMap == null) {
            playerNameMap = FileHashUtils.getPlayerName();
        }
        String value = playerNameMap.get(key);
        if (value == null) {
            return key;
        } else {
            return value;
        }
    }

    public static void array2CSV(List<RankingBean> data, String date) throws IOException {
        for (RankingBean bean : data) {
            if (bean.getPlayer2Name() != null) {
                out.write(bean.getPlayerName() + "&" + bean.getPlayer2Name());
            } else {
                out.write(bean.getPlayerName());
            }
            out.write(",");
            out.write(bean.getCountryName());
            out.write(",");
            out.write(bean.getPoints().replace(",", ""));
            out.write(",");
            out.write(date);
            out.newLine();
        }

    }
}
