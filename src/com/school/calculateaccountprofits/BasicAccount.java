package com.school.calculateaccountprofits;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * Created by Hamid on 2/19/2017.
 */
public class BasicAccount implements Comparable<BasicAccount> {
    protected Integer customerNumber;
    protected BigInteger depositBalance;
    protected Integer durationInDays;
    protected BigInteger payedInterest = BigInteger.ZERO;

    public BasicAccount(Integer customerNumber, BigInteger depositBalance, Integer durationInDays) throws Exception {
        this.customerNumber = customerNumber;
        if (depositBalance.compareTo(BigInteger.ZERO)<0)
            throw new IllegalArgumentException("DepositBalance can not be negative!");
        this.depositBalance = depositBalance;
        if (durationInDays<=0)
            throw new IllegalArgumentException("DurationInDays must be positive!");
        this.durationInDays = durationInDays;
    }

    public Integer getCustomerNumber(){
        return customerNumber;
    }
    public BigInteger getPI(){
        return payedInterest;
    }

    @Override
    public int compareTo(BasicAccount o) {
        return -payedInterest.compareTo(o.getPI());
    }
}
