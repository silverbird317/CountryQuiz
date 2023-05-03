package edu.uga.cs.countryquiz;

import java.io.Serializable;

public class QuizResult implements Serializable {

    private long cid;
    private String date;
    private int score;

    /*
     * public constructor to instantiate date and score
     */
    public QuizResult(String date, int score) {
        this.date = date;
        this.score = score;
    }

    /*
     * date getter
     */
    public String getDate()
    {
        return date;
    }

    /*
     * date setter
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /*
     * score getter
     */
    public int getScore()
    {
        return score;
    }

    /*
     * score setter
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    /*
     * toString override
     */
    public String toString() {
        return "Date: " + date + "\nScore:" + score + " / 6";
    }

    /*
     * cid getter
     */
    public long getCid() {
        return cid;
    }

    /*
     * cid setter
     */
    public void setCid(long cid) {
        this.cid = cid;
    }
}
