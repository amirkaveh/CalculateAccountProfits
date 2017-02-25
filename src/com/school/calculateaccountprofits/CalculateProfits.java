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
        java.util.List<BasicAccount> depObjects = new java.util.ArrayList<BasicAccount>();
        try {
            File inputFile = new File("input.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList deposits = doc.getElementsByTagName("deposit");
            for (int i=0; i<deposits.getLength(); i++) {
                Node dep = deposits.item(i);
                if(dep.getNodeType()== Node.ELEMENT_NODE){
                    Element depElement = (Element) dep;
                    try {
                        Class depClass = null;
                        try {
                            depClass = Class.forName("com.school.calculateaccountprofits." + depElement
                                    .getElementsByTagName("depositType")
                                    .item(0).getTextContent());
                        } catch (Exception e) {
                            throw new Exception(e.toString() +" class is not defined!");
                        }
                        Integer customerNumber = Integer.parseInt(depElement
                                .getElementsByTagName("customerNumber").item(0).getTextContent());
                        BigInteger depositBalance = new BigInteger(depElement
                                .getElementsByTagName("depositBalance").item(0).getTextContent());
                        Integer durationInDays = Integer.parseInt(depElement
                                .getElementsByTagName("durationInDays").item(0).getTextContent());
                        Constructor depConstructor = depClass.getConstructor(Integer.class,BigInteger.class,Integer.class);

                        Object depObject = null;
                        try {
                            depObject = depConstructor.newInstance(customerNumber, depositBalance, durationInDays);
                        } catch(Exception e) {
                            throw new Exception(e.getCause());
                        }
                        depObjects.add((BasicAccount)depObject);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Collections.sort(depObjects);

        for (BasicAccount temp : depObjects){
            System.out.println(temp.getCustomerNumber().toString()+ "#"+ temp.getPI().toString());
        }

        try{
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            for (BasicAccount temp : depObjects){
                writer.println(temp.getCustomerNumber().toString()+ "#"+ temp.getPI().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
