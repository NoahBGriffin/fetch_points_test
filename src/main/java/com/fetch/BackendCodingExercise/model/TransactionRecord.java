package com.fetch.BackendCodingExercise.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Override
    public int compareTo(TransactionRecord other) {
        return this.getTimestamp().compareTo(other.getTimestamp());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }
}
