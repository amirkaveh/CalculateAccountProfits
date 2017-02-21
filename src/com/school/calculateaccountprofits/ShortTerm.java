package com.school.calculateaccountprofits;

import java.math.BigInteger;

/**
 * Created by Hamid on 2/19/2017.
 */
public class ShortTerm extends com.school.calculateaccountprofits.BasicAccount {
    public ShortTerm(Integer customerNumber, BigInteger depositBalance, Integer durationInDays) throws Exception {
        super(customerNumber, depositBalance, durationInDays);
        interestRate = 10;
        calculatePI();
    }
    private Integer interestRate;

    private void calculatePI() {
        payedInterest = depositBalance.multiply(BigInteger.valueOf(interestRate*durationInDays)).divide(BigInteger.valueOf(36500));
    }
}
