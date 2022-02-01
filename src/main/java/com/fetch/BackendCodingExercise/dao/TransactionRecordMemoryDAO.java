package com.fetch.BackendCodingExercise.dao;

import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Memory data access object for a user's transaction record information
 * Allows REST controller to access user transaction data from system memory
 */
public class TransactionRecordMemoryDAO implements TransactionRecordDAO {

    private List<TransactionRecord> transactions;

    /**
     * Constructor for TransactionRecordMemoryDAO
     * If desired, the transactions list can be pre-loaded with test data using
     * the setTransactions method so that memory will contain data before any client calls are made
     */
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


    /**
     * Takes in amount of points to be spend and spends them across payer balances, making sure
     * no payer balance goes negative. Spends points in chronological order from oldest to newest.
     * Updates transaction records with points spent, timestamp, and payer information
     *
     * @param points amount of points to be spent from this account
     * @return a map representation of payers and the amount of points spent from each
     * @throws PointsOverdraftException thrown in the case that points exceed available points balance
     */
    public Map<String, Integer> spendPoints(int points) throws PointsOverdraftException {
        if (getPointTotal() < points) throw new PointsOverdraftException();

        Map<String, Integer> result = new HashMap<>();

        Collections.sort(transactions);
        List<TransactionRecord> pointsLeft = subtractNegativePointsInList();

        int index = 0;
        while (points > 0) {

            TransactionRecord current = pointsLeft.get(index);
            String payer = current.getPayer();
            int pointsSubtracted = current.getPoints();

            if (points >= pointsSubtracted) {
                points -= pointsSubtracted;
            } else {
                pointsSubtracted = points;
                points = 0;
            }
            if (pointsSubtracted != 0) {
                TransactionRecord record = new TransactionRecord(payer, -pointsSubtracted, LocalDateTime.now());
                addTransaction(record);
                addToMap(result, record);
            }
            index++;
        }

        return result;
    }

    /**
     * Calculates and returns point balances for each payer
     *
     * @return Point balance information for each payer on this account
     */
    public Map<String, Integer> pointBalances() {
        Map<String, Integer> balances = new HashMap<>();
        for (TransactionRecord transaction : transactions) {
            addToMap(balances, transaction);
        }
        return balances;
    }

    /**
     * Takes a given Map and adds given TransactionRecord payer and points information to it
     *
     * @param map the Map to add a TransactionRecord to
     * @param transaction the TransactionRecord whose data is to be added to the map
     */
    private void addToMap(Map<String, Integer> map, TransactionRecord transaction) {
        if(map.containsKey(transaction.getPayer())) {
            int sum = map.get(transaction.getPayer()) + transaction.getPoints();
            map.put(transaction.getPayer(), sum);
        } else {
            map.put(transaction.getPayer(), transaction.getPoints());
        }
    }

    /**
     * Takes the list of transactions from memory and creates a second list which contains updated points information.
     * This list contains only positive points values, subtracting any negative points values from transactions memory data
     * based on timestamp (from oldest to newest). This allows for accurate point and timestamp information
     *
     * @return a list of TransactionRecords representing positive balances only according to timestamp information, with
     * negative points transactions in transaction memory data appropriately subtracted
     * @throws PointsOverdraftException if a given payer does not have enough points
     */
    private List<TransactionRecord> subtractNegativePointsInList() throws PointsOverdraftException {
        List<TransactionRecord> result = new ArrayList<>();

        for (TransactionRecord transactionRecord : transactions) {
            if (transactionRecord.getPoints() < 0) {
                subtractPointsByPayer(result, transactionRecord);
            } else {
                try {
                    result.add((TransactionRecord) transactionRecord.clone());
                } catch (CloneNotSupportedException e) {
                    System.out.println(e);
                }
            }
        }

        return result;
    }

    /**
     * Subtracts points based on the given TransactionRecord's payer, going in chronological order from oldest timestamp
     *
     * @param list list of transaction records to subtract points from
     * @param record the record containing information about points to subtract and which payer to subtract from
     * @throws PointsOverdraftException if the payer in the list does not have enough points
     */
    private void subtractPointsByPayer(List<TransactionRecord> list, TransactionRecord record) throws PointsOverdraftException {
        int negativePoints = record.getPoints();

        for (TransactionRecord current : list) {

            if (current.getPayer().equals(record.getPayer())) {
                int currentPoints = current.getPoints();
                if (currentPoints > -negativePoints) {
                    current.setPoints(currentPoints + negativePoints);
                    negativePoints = 0;
                } else { //negative points greater than current transaction
                    current.setPoints(0);
                    negativePoints += currentPoints;
                }
            }

            if (negativePoints == 0) break;
        }

        if (negativePoints != 0) throw new PointsOverdraftException();
    }


    /**
     * Private method for calculating total points in account
     * @return total points available in account across all payers
     */
    private int getPointTotal() {
        int sum = 0;
        for (TransactionRecord transaction : transactions) {
            sum += transaction.getPoints();
        }
        return sum;
    }

    /**
     * Private method for creating test transaction data
     * feel free to add or delete transactions for testing purposes
     * a sample transaction has been left and commented out as an example
     */
    private void setTransactions() {
//        transactions.add(new TransactionRecord(
//                "DANNON",
//                1000,
//                LocalDateTime.of(2015, Month.APRIL, 29, 19, 30, 20))
//        );
    }
}
