package com.school.calculateaccountprofits;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.Collections;

/**
 * Created by Hamid on 2/19/2017.
 */

public class CalculateProfits {
    public static void main(String[] args) {
        java.util.List<DepositType> depositObjects = new java.util.ArrayList<DepositType>();
        try {
            File inputFile = new File("input.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList deposits = doc.getElementsByTagName("deposit");
            for (int i=0; i<deposits.getLength(); i++) {
                Node deposit = deposits.item(i);
                if(deposit.getNodeType()== Node.ELEMENT_NODE){
                    Element depositElement = (Element) deposit;
                    try {
                        Class depositClass = null;
                        try {
                            depositClass = Class.forName("com.school.calculateaccountprofits." + depositElement
                                    .getElementsByTagName("depositType")
                                    .item(0).getTextContent());
                        } catch (Exception e) {
                            throw new Exception(e.toString() +" class is not defined!");
                        }
                        Integer customerNumber = Integer.parseInt(depositElement
                                .getElementsByTagName("customerNumber").item(0).getTextContent());
                        BigInteger depositBalance = new BigInteger(depositElement
                                .getElementsByTagName("depositBalance").item(0).getTextContent());
                        Integer durationInDays = Integer.parseInt(depositElement
                                .getElementsByTagName("durationInDays").item(0).getTextContent());
                        Constructor depConstructor = depositClass.getConstructor(Integer.class,BigInteger.class,Integer.class);

                        Object depositObject = null;
                        try {
                            depositObject = depConstructor.newInstance(customerNumber, depositBalance, durationInDays);
                        } catch(Exception e) {
                            throw new Exception(e.getCause());
                        }
                        depositObjects.add((DepositType)depositObject);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Collections.sort(depositObjects);

        for (DepositType temp : depositObjects){
            System.out.println(temp.getCustomerNumber().toString()+ "#"+ temp.getPayedInterest().toString());
        }

        try{
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            for (DepositType temp : depositObjects){
                writer.println(temp.getCustomerNumber().toString()+ "#"+ temp.getPayedInterest().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
