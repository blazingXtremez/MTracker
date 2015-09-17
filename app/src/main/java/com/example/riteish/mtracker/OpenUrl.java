package com.example.riteish.mtracker;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by Riteish on 4/27/2015.
 */
public class OpenUrl extends AsyncTask {
    @Override
    protected List<Trains> doInBackground(Object... params) {

        // source and destination
        File source;
        File dest;
        // Url pointer
        URL link = null;
        try {
            link = new URL("http://web.mta.info/status/serviceStatus.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // creating a list that will store the train information
        List<Trains> trainLst = new ArrayList<Trains>();



        // main method will open the file, run the xml parser with the document and will print the data
        // DocumentBuilder Factory sertup
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();



        // DOM builder
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // loading and parsing document
            try {
                Document doc = builder.parse(link.openStream());


                // iterating through the file
                NodeList nodeList = doc.getDocumentElement().getChildNodes();
                //System.out.println(nodeList.toString());            // this gives me services


                // loop to go through all the child nodes
                for (int i=0; i<nodeList.getLength(); i++){
                    Node node = nodeList.item(i);

                    //System.out.println(node.toString());            // prints all the nodes of the service element

                    // create a nodelist that captures childnodes of those nodes
                    NodeList cnlist = node.getChildNodes();
                    for (int j= 0; j<cnlist.getLength(); j++){
                        Node cNode = cnlist.item(j);
                        Trains trains = new Trains();

                        //System.out.println(cNode.toString());
                        // get the nodelist of the node again
                        NodeList lineList = cNode.getChildNodes();
                        for (int k = 0; k < lineList.getLength(); k++){
                            Node lNode = lineList.item(k);
                            //String a = lNode.getNodeName();
                            //if (a != "#text"){
                            //System.out.println(a);} // gives me name status text date and time

                            String catl = lNode.getNodeName();
                            //System.out.println(catl);
                            String value;


                            switch (catl) {
                                case "name":
                                    value = lNode.getFirstChild().getNodeValue();
                                    //System.out.println(value);
                                    trains.trainName = value;
                                    break;

                                case "status":
                                    value = lNode.getFirstChild().getNodeValue();
                                    //System.out.println(value);
                                    trains.status = value;
                                    break;

                                case"text":
                                    if (trains.status.equals("GOOD SERVICE")){
                                        break;
                                    }
                                    else {
                                        value = lNode.getFirstChild().getNodeValue();
                                        value = value.replaceAll("\\<[^>]*>","");
                                        //System.out.println(value);
                                        trains.information = value;
                                        break;
                                    }

                                case "Date":
                                    if (trains.status.equals("GOOD SERVICE")){
                                        break;
                                    }
                                    else {
                                        value = lNode.getFirstChild().getNodeValue();
                                        //System.out.println(value);
                                        trains.date = value;
                                        break;
                                    }

                                case "Time":
                                    if (trains.status.equals("GOOD SERVICE")){
                                        break;
                                    }
                                    else {
                                        value = lNode.getFirstChild().getNodeValue();
                                        //System.out.println(value);
                                        trains.time = value;
                                    }
                                    break;

                            }

                        }
                        // add the information to trains
                        trainLst.add(trains);

                    }

                }




            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        // mandatory return statement
        return trainLst;
    }


}
