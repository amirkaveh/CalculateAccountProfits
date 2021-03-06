package com.school.calculateaccountprofits;

import com.school.exceptions.DepositBalanceException;
import com.school.exceptions.DurationInDaysException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by $Hamid on 2/19/2017.
 */
class Deposit implements Comparable<Deposit> {

    private Integer customerNumber;
    private BigDecimal depositBalance;
    private Integer durationInDays;
    private DepositType depositType;
    private BigDecimal payedInterest;

    public Deposit(Integer customerNumber, BigDecimal depositBalance, Integer durationInDays, DepositType depositType) throws Exception {
        this.customerNumber = customerNumber;
        if (depositBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new DepositBalanceException("The value can not be negative!");
        this.depositBalance = depositBalance;
        if (durationInDays <= 0)
            throw new DurationInDaysException("The value must be positive!");
        this.durationInDays = durationInDays;
        this.depositType = depositType;
        calculatePayedInterest();
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public BigDecimal getPayedInterest() {
        return payedInterest;
    }

    private void calculatePayedInterest() {
        payedInterest = depositBalance.multiply(BigDecimal.valueOf(depositType.getInterestRate() * durationInDays)).divide(BigDecimal.valueOf(36500), RoundingMode.FLOOR);
    }

    @Override
    public int compareTo(Deposit otherDeposit) {
        return -payedInterest.compareTo(otherDeposit.getPayedInterest());
    }
}
