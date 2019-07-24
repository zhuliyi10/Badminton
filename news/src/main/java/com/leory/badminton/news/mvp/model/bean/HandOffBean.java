package com.leory.badminton.news.mvp.model.bean;

import java.util.List;

/**
 * Describe : 交手bean
 * Author : leroy
 * Date : 2019-07-24
 */
public class HandOffBean {
    private String player1HeadUrl;
    private String player1Flag;
    private String player1Name;
    private String player12HeadUrl;
    private String player12Flag;
    private String player12Name;
    private String player1Ranking;
    private String player1Win;

    private String player2HeadUrl;
    private String player2Flag;
    private String player2Name;
    private String player22HeadUrl;
    private String player22Flag;
    private String player22Name;
    private String player2Ranking;
    private String player2Win;

    private List<Record>recordList;

    public static class Record{
        private String date;
        private String matchName;
        private String score;
        private boolean leftWin;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMatchName() {
            return matchName;
        }

        public void setMatchName(String matchName) {
            this.matchName = matchName;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public boolean isLeftWin() {
            return leftWin;
        }

        public void setLeftWin(boolean leftWin) {
            this.leftWin = leftWin;
        }
    }

    public String getPlayer1HeadUrl() {
        return player1HeadUrl;
    }

    public void setPlayer1HeadUrl(String player1HeadUrl) {
        this.player1HeadUrl = player1HeadUrl;
    }

    public String getPlayer1Flag() {
        return player1Flag;
    }

    public void setPlayer1Flag(String player1Flag) {
        this.player1Flag = player1Flag;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer1Ranking() {
        return player1Ranking;
    }

    public void setPlayer1Ranking(String player1Ranking) {
        this.player1Ranking = player1Ranking;
    }

    public String getPlayer1Win() {
        return player1Win;
    }

    public void setPlayer1Win(String player1Win) {
        this.player1Win = player1Win;
    }

    public String getPlayer2HeadUrl() {
        return player2HeadUrl;
    }

    public void setPlayer2HeadUrl(String player2HeadUrl) {
        this.player2HeadUrl = player2HeadUrl;
    }

    public String getPlayer2Flag() {
        return player2Flag;
    }

    public void setPlayer2Flag(String player2Flag) {
        this.player2Flag = player2Flag;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer2Ranking() {
        return player2Ranking;
    }

    public void setPlayer2Ranking(String player2Ranking) {
        this.player2Ranking = player2Ranking;
    }

    public String getPlayer2Win() {
        return player2Win;
    }

    public void setPlayer2Win(String player2Win) {
        this.player2Win = player2Win;
    }

    public String getPlayer12HeadUrl() {
        return player12HeadUrl;
    }

    public void setPlayer12HeadUrl(String player12HeadUrl) {
        this.player12HeadUrl = player12HeadUrl;
    }

    public String getPlayer12Flag() {
        return player12Flag;
    }

    public void setPlayer12Flag(String player12Flag) {
        this.player12Flag = player12Flag;
    }

    public String getPlayer12Name() {
        return player12Name;
    }

    public void setPlayer12Name(String player12Name) {
        this.player12Name = player12Name;
    }

    public String getPlayer22HeadUrl() {
        return player22HeadUrl;
    }

    public void setPlayer22HeadUrl(String player22HeadUrl) {
        this.player22HeadUrl = player22HeadUrl;
    }

    public String getPlayer22Flag() {
        return player22Flag;
    }

    public void setPlayer22Flag(String player22Flag) {
        this.player22Flag = player22Flag;
    }

    public String getPlayer22Name() {
        return player22Name;
    }

    public void setPlayer22Name(String player22Name) {
        this.player22Name = player22Name;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }
}
