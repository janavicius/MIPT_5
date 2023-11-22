package com.example.mipt_5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<String> parseXml(String xmlData) {
        List<String> currencyList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.xml.sax.InputSource is = new org.xml.sax.InputSource(new StringReader(xmlData));
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("currency");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String currencyCode = element.getElementsByTagName("code").item(0).getTextContent();
                String rate = element.getElementsByTagName("rate").item(0).getTextContent();

                String currencyInfo = currencyCode + " - " + rate;
                currencyList.add(currencyInfo);
            }

            System.out.println("error");
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            System.err.println("Smth wrong with parseXML");
        }

        return currencyList;
    }
}
