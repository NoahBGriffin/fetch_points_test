package com.fetch.BackendCodingExercise.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Transacion Record class for storing data on a specific transaction.
 * Keeps who awarded points (payer), the number of points, and the timestamp for when points were awarded
 *
 */
public class TransactionRecord implements Comparable<TransactionRecord>, Cloneable {

    private String payer;
    private int points;
    private LocalDateTime timestamp;

    public TransactionRecord(String payer, int points, LocalDateTime timestamp) {
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
    }

    public TransactionRecord() {
        this.payer = "";
        this.points = 0;
        this.timestamp = null;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    @Override
    public String toString() {
        return "payer: " + this.payer + ", points: " + this.points;
    }

    /**
     * Compares two TransactionRecords based on their timestamps
     * @param other the other TransactionRecord to be compared against
     * @return int > 0 if this object has a chronologically later timestamp than other,
     *          int == 0 if this object and other have the same timestamp,
     *          int < - if this object has a chronologically earlier timestamp than other
     */
    @Override
    public int compareTo(TransactionRecord other) {
        return this.getTimestamp().compareTo(other.getTimestamp());
    }

    /**
     * creates exact clone of this instance of TransactionRecord
     * @return Object clone of this TransactionRecord
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
