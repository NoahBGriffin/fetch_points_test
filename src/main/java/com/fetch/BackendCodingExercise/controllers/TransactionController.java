package com.fetch.BackendCodingExercise.controllers;

import com.fetch.BackendCodingExercise.dao.TransactionRecordDAO;
import com.fetch.BackendCodingExercise.dao.TransactionRecordMemoryDAO;
import com.fetch.BackendCodingExercise.exception.PointsOverdraftException;
import com.fetch.BackendCodingExercise.model.TransactionRecord;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    private TransactionRecordDAO transactionRecordDAO;

    public TransactionController() {
        this.transactionRecordDAO = new TransactionRecordMemoryDAO();
    }

    @RequestMapping(path = "/add-transaction", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public TransactionRecord add(@RequestBody TransactionRecord transaction) throws PointsOverdraftException {
        return transactionRecordDAO.addTransaction(transaction);
    }

    @RequestMapping(path = "/spend-points", method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Map<String, Integer> spend(@RequestBody int points) throws PointsOverdraftException {
        if (points < 0) throw new IllegalArgumentException("Points cannot be negative");
        return transactionRecordDAO.spendPoints(points);
    }

    @RequestMapping(path = "/point-balance", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Integer> getPointBalances() {
        return transactionRecordDAO.pointBalances();
    }


    //TODO: delete later
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public List<TransactionRecord> get() {
        return transactionRecordDAO.get();
    }

}
