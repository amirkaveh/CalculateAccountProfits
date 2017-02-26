package com.school.calculateaccountprofits;

import java.math.BigInteger;

/**
 * Created by Hamid on 2/19/2017.
 */
public class LongTerm extends DepositType {
    public LongTerm(Integer customerNumber, BigInteger depositBalance, Integer durationInDays) throws Exception {
        super(customerNumber, depositBalance, durationInDays);
        interestRate = 20;
        calculatePayedInterest();
    }
    private Integer interestRate;

    private void calculatePayedInterest() {
        payedInterest = depositBalance.multiply(BigInteger.valueOf(interestRate*durationInDays)).divide(BigInteger.valueOf(36500));
    }
}
