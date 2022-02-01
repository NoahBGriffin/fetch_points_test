package com.fetch.BackendCodingExercise.dao;

import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;

import java.util.List;
import java.util.Map;

public interface TransactionRecordDAO {

    TransactionRecord addTransaction(TransactionRecord transactionRecord) throws PointsOverdraftException;

    Map<String, Integer> pointBalances();

    Map<String, Integer> spendPoints(int points) throws PointsOverdraftException;

    List<TransactionRecord> get();
}
