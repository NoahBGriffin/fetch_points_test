package com.fetch.BackendCodingExercise.dao;

import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;

import java.util.Map;

public interface TransactionRecordDAO {

    //add transaction
    public TransactionRecord addTransaction(TransactionRecord transactionRecord) throws PointsOverdraftException;
    //spend points
    //return all payer point balances
    public Map<String, Integer> pointBalances();

}
