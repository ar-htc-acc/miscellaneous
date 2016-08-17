package com.ar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class URLChecking {

    // Tuckey's URL rewrite configuration
    private static final String FILE_LOCATION = "/your/Tuckey/configuration/XML/file/urlrewrite.xml";

    public static void main(String[] args) {

        String userInput = "/put/target/URL/here";

        List<String> regexList = new ArrayList<>();

        File fXmlFile = new File(FILE_LOCATION);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("rule");

            for (int idx = 0; idx < nList.getLength(); idx++) {

                Node nNode = nList.item(idx);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    NodeList list = eElement.getElementsByTagName("from");

                    if (list != null && list.getLength() > 0) {
                        //System.out.println("Regex : " + eElement.getElementsByTagName("from").item(0).getTextContent());
                        regexList.add(eElement.getElementsByTagName("from").item(0).getTextContent());
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("Tuckey's regular expression list: " + regexList);
        System.out.println("User input: " + userInput);
        for (String regex: regexList) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(userInput);
            boolean matches = m.matches();
            if (matches) {
            	System.out.println("User input URL matches pattern: " + regex);
            	for (int i = 1; i <= m.groupCount(); i++) {
            		System.out.println(i + ": " + m.group(i));
            	}
            	// Found a match, break. Should check if it contains "<to last="true">"
            	break;
            }
        }
    }
}
