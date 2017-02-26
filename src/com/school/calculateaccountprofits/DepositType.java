package com.school.calculateaccountprofits;

import java.math.BigInteger;

/**
 * Created by Hamid on 2/19/2017.
 */
public class DepositType implements Comparable<DepositType> {
    protected Integer customerNumber;
    protected BigInteger depositBalance;
    protected Integer durationInDays;
    protected BigInteger payedInterest = BigInteger.ZERO;

    public DepositType(Integer customerNumber, BigInteger depositBalance, Integer durationInDays) throws Exception {
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
    public BigInteger getPayedInterest(){
        return payedInterest;
    }

    @Override
    public int compareTo(DepositType o) {
        return -payedInterest.compareTo(o.getPayedInterest());
    }
}
