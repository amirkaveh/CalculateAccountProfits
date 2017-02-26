package com.school.calculateaccountprofits;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.Collections;

/**
 * Created by Hamid on 2/19/2017.
 */

public class CalculateProfits {
    public static void main(String[] args) throws Exception {

        NodeList depositNodeList = xmlToNodeList("input.xml", "deposit");
        List<Deposit> depositList = nodeListToDepositList(depositNodeList);
        Collections.sort(depositList);
        logDepositList(depositList);
        logDepositListToFile(depositList, "output.txt");

    }

    private static NodeList xmlToNodeList(String inputFileName, String elementsTagName) throws Exception {
        File inputFile = new File(inputFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(elementsTagName);
    }

    private static List<Deposit> nodeListToDepositList(NodeList nodeList) {
        List<Deposit> depositList = new ArrayList<Deposit>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node deposit = nodeList.item(i);
            if (deposit.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) deposit;
                try {
                    Class depositClass = null;
                    try {
                        depositClass = Class.forName("com.school.calculateaccountprofits." + depositElement
                                .getElementsByTagName("depositType")
                                .item(0).getTextContent());
                    } catch (Exception e) {
                        throw new Exception(e.toString() + " class is not defined!");
                    }
                    Integer customerNumber = Integer.parseInt(depositElement
                            .getElementsByTagName("customerNumber").item(0).getTextContent());
                    BigDecimal depositBalance = new BigDecimal(depositElement
                            .getElementsByTagName("depositBalance").item(0).getTextContent());
                    Integer durationInDays = Integer.parseInt(depositElement
                            .getElementsByTagName("durationInDays").item(0).getTextContent());
                    Constructor depConstructor = depositClass.getConstructor();

                    Object depositTypeObject = null;
                    try {
                        depositTypeObject = depConstructor.newInstance();
                    } catch (Exception e) {
                        throw new Exception(e.getCause());
                    }
                    depositList.add(new Deposit(customerNumber, depositBalance, durationInDays, (DepositType) depositTypeObject));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return depositList;
    }

    private static void logDepositList(List<Deposit> depositList) {
        for (Deposit temp : depositList) {
            System.out.println(temp.getCustomerNumber().toString() + "#" + temp.getPayedInterest().toString());
        }
    }

    private static void logDepositListToFile(List<Deposit> depositList, String outputFileName) throws Exception {

        PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");
        for (Deposit temp : depositList) {
            writer.println(temp.getCustomerNumber().toString() + "#" + temp.getPayedInterest().toString());
        }
        writer.close();

    }


}
