package com.school.calculateaccountprofits;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.school.exceptions.DepositBalanceException;
import com.school.exceptions.DurationInDaysException;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.Collections;

/**
 * Created by $Hamid on 2/19/2017.
 */

public class CalculateProfits {
    public static void main(String[] args) throws Exception {

        NodeList depositNodeList = xmlToNodeList("input.xml", "deposit");
        List<Deposit> depositList = nodeListToDepositList(depositNodeList);
        Collections.sort(depositList);
        logDepositList(depositList);
        writeDepositListToFile(depositList, "output.txt");

    }

    private static NodeList xmlToNodeList(String inputFileName, String elementsTagName) throws Exception {
        File inputFile = new File(inputFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(elementsTagName);
    }

    private static List<Deposit> nodeListToDepositList(NodeList nodeList) throws Exception {
        List<Deposit> depositList = new ArrayList<Deposit>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node deposit = nodeList.item(i);
            if (deposit.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) deposit;
                try {
                    Class depositClass = null;
                    depositClass = Class.forName("com.school.calculateaccountprofits." + depositElement
                            .getElementsByTagName("depositType")
                            .item(0).getTextContent());

                    Integer customerNumber = Integer.parseInt(depositElement
                            .getElementsByTagName("customerNumber").item(0).getTextContent());
                    BigDecimal depositBalance = new BigDecimal(depositElement
                            .getElementsByTagName("depositBalance").item(0).getTextContent());
                    Integer durationInDays = Integer.parseInt(depositElement
                            .getElementsByTagName("durationInDays").item(0).getTextContent());
                    Constructor depConstructor = depositClass.getConstructor();

                    Object depositTypeObject = null;
                    depositTypeObject = depConstructor.newInstance();

                    depositList.add(new Deposit(customerNumber, depositBalance, durationInDays, (DepositType) depositTypeObject));
                } catch (DepositBalanceException | DurationInDaysException e) {
                    System.out.println(e);
                } catch (ClassNotFoundException e) {
                    System.out.println(e + " is not found!");
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

    private static void writeDepositListToFile(List<Deposit> depositList, String outputFileName) throws Exception {

        PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");
        for (Deposit temp : depositList) {
            writer.println(temp.getCustomerNumber().toString() + "#" + temp.getPayedInterest().toString());
        }
        writer.close();
    }
}
