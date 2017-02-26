package com.school.calculateaccountprofits;

import java.math.BigInteger;

/**
 * Created by Hamid on 2/19/2017.
 */
public class Qarz extends DepositType {
    public Qarz(Integer customerNumber, BigInteger depositBalance, Integer durationInDays) throws Exception {
        super(customerNumber, depositBalance, durationInDays);
    }
}
