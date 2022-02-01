package com.fetch.BackendCodingExercise.controllers;

import com.fetch.BackendCodingExercise.dao.TransactionRecordDAO;
import com.fetch.BackendCodingExercise.dao.TransactionRecordMemoryDAO;
import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;
import com.fetch.BackendCodingExercise.model.WebPointsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for transaction records, allows client to add a transaction, access balance information, and spend points
 */
@RestController
public class TransactionController {

    private TransactionRecordDAO transactionRecordDAO;

    public TransactionController() {
        this.transactionRecordDAO = new TransactionRecordMemoryDAO();
    }

    /**
     * Allows client to add a new transaction to data
     * @param transaction a JSON web token representing transaction information, must include payer, points, and timestamp
     *                    example: { "payer": "ME", "points": 2000, "timestamp": "2011-05-29T19:30:20" }
     * @return the transaction token that was passed in
     * @throws PointsOverdraftException
     */
    @RequestMapping(path = "/add-transaction", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public TransactionRecord add(@RequestBody TransactionRecord transaction) throws PointsOverdraftException {
        return transactionRecordDAO.addTransaction(transaction);
    }

    /**
     * Allows a client to spend points from the specific user account
     * Points are spent in chronological order from when they were awarded, with earliest received points being spent first
     * @param points JSON token representing points to be spent.
     *               example: { "points": 200 }
     * @return a JSON token with information about how points were spent between payer balances
     *          example: { "DANNON": -200 }
     * @throws PointsOverdraftException
     */
    @RequestMapping(path = "/spend-points", method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Map<String, Integer> spend(@RequestBody WebPointsRequest points) throws PointsOverdraftException {
        if (points.getPoints() < 0) throw new IllegalArgumentException("Points cannot be negative");
        return transactionRecordDAO.spendPoints(points.getPoints());
    }

    /**
     * Retrieves point balance data for each payer who has paid points on this users account
     * @return a JSON token representing information about payers and their point balances for this account
     *          example: { "DANNON": 300, "ME": 20, "SOMECOMPANY": 5000}
     */
    @RequestMapping(path = "/point-balance", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Integer> getPointBalances() {
        return transactionRecordDAO.pointBalances();
    }

}
