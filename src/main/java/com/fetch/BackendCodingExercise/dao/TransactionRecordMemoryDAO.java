package com.fetch.BackendCodingExercise.dao;

import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


public class TransactionRecordMemoryDAO implements TransactionRecordDAO {

    private List<TransactionRecord> transactions;

    public TransactionRecordMemoryDAO() {
        transactions = new ArrayList<>();
        setTransactions();
    }

    /** Stores a new transaction record in our current data of all transaction records
     *
     * @param transaction a TransactionRecord to be added to our data
     * @return the TransactionRecord object that was added to our data
     * @throws PointsOverdraftException if the points balance of the given transaction record makes the payer's total points balance negative
     */
    public TransactionRecord addTransaction(TransactionRecord transaction) throws PointsOverdraftException {

        if (transaction.getPoints() < 0) {
            Map<String, Integer> balances = pointBalances();
            //if it's a new payer and therefore negative, or if it puts a payer balance to negative it is a no-no
            if (!balances.containsKey(transaction.getPayer()) ||
                    balances.get(transaction.getPayer()) - transaction.getPoints() < 0) {
                throw new PointsOverdraftException();
            }
        }

        transactions.add(transaction);
        return transaction;
    }

    //spend points
        //points spent from oldest timestamp to most recent
        //no negative points
        //returns payer and points spent
        //adds record to transactions
    public List<String> spendPoints(int points) throws PointsOverdraftException {
        if (getPointTotal() < points) throw new PointsOverdraftException();

        List<String> result = new ArrayList<>();

        Collections.sort(transactions);
        List<TransactionRecord> pointsLeft = subtractNegativePointsInList();

        

        return result;
    }

    /**
     * Calculates and returns point balances for each payer
     * @return Point balance information for each payer on this account
     */
    public Map<String, Integer> pointBalances() {
        Map<String, Integer> balances = new HashMap<>();
        for (TransactionRecord transaction : transactions) {
            if(balances.containsKey(transaction.getPayer())) {
                int sum = balances.get(transaction.getPayer()) + transaction.getPoints();
                balances.put(transaction.getPayer(), sum);
            } else {
                balances.put(transaction.getPayer(), transaction.getPoints());
            }
        }
        return balances;
    }

    private List<TransactionRecord> subtractNegativePointsInList() throws PointsOverdraftException {
        List<TransactionRecord> result = new ArrayList<>();

        for (TransactionRecord transactionRecord : transactions) {
            if (transactionRecord.getPoints() < 0) {
                subtractPoints(result, transactionRecord);
            } else {
                result.add(transactionRecord);
            }
        }

        return result;
    }

    private void subtractPoints(List<TransactionRecord> list, TransactionRecord record) throws PointsOverdraftException {
        int negativePoints = record.getPoints();

        for (TransactionRecord current : list) {
            if (current.getPayer().equals(record.getPayer())) {
                int currentPoints = current.getPoints();
                if (currentPoints > negativePoints) {
                    current.setPoints(currentPoints - negativePoints);
                    break;
                } else { //negative points greater than current transaction
                    current.setPoints(0);
                    negativePoints -= currentPoints;
                }
            }
        }

        if (negativePoints != 0) throw new PointsOverdraftException();
    }


    /** Private method for calculating total points in account
     *
     * @return total points available in account across all payers
     */
    private int getPointTotal() {
        int sum = 0;
        for (TransactionRecord transaction : transactions) {
            sum += transaction.getPoints();
        }
        return sum;
    }

    /** Private method for creating test transaction data
     *
     */
    private void setTransactions() {
        transactions.add(new TransactionRecord(
                "DANNON",
                1000,
                LocalDateTime.of(2015, Month.APRIL, 29, 19, 30, 20))
        );
    }
}
