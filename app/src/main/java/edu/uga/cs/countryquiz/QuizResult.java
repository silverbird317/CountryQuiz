package edu.uga.cs.countryquiz;

import java.io.Serializable;

/**
 * This class (a POJO) represents a single job lead, including the company name,
 * phone number, URL, and brief comments.
 */
public class QuizResult implements Serializable {

    private long cid;
    private String date;
    private int score;
    public QuizResult() {
        this.date = null;
        this.score = 0;
    }

    public QuizResult(String date, int score) {
        this.date = date;
        this.score = score;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String toString() {
        return "Date: " + date + "\nScore:" + score + " / 6";
    }
    public long getCid() {
        return cid;
    }
    public void setCid(long cid) {
        this.cid = cid;
    }
}
