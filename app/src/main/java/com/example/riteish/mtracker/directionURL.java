package com.example.riteish.mtracker;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Riteish on 5/19/2015.
 */
public class directionURL extends AsyncTask {

    @Override
    protected List<MyDirections> doInBackground(Object[] params) {

        // getting the url value
        String myUrl = params[0].toString();
        String encodedUrl = null;

        encodedUrl = myUrl.replaceAll(" ", "%20");

        // creating a list of direction instructions
        List<MyDirections> dirList = new ArrayList<MyDirections>();

        // connecting to the URL, which is send from the direction file
        URL link = null;
        try {
            link = new URL(encodedUrl);
            URI myUri = link.toURI();
            String Uri = myUri.toString();

            // if successfully connected to the url file, we will have access to the xml file that we can parse

            // Using DOMParser to parse the xml files
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuiler = dbfactory.newDocumentBuilder();
            Document doc = dBuiler.parse(link.openStream());
            doc.getDocumentElement().normalize();

            // now with the parsing and going inside nodes and getting required values that equals "route"
            NodeList nlist = doc.getDocumentElement().getElementsByTagName("route");
            Node n = nlist.item(0);

            // getting the leg node from the overall childnode of "route" node
            Node routeInfo = n.getChildNodes().item(3);

            // getting the list of nodes from the legNodes, just get the ones that are tagged steps
            NodeList infoList = routeInfo.getChildNodes();


            for (int i = 0; i < infoList.getLength(); i++) {
                Node n1 = infoList.item(i);
                MyDirections dir = new MyDirections();

                // if the node name is step, then only process further
                if (n1.getNodeName().equals("step")){
                    // Nodelist to process
                    //System.out.println(n1.getNodeName());
                    NodeList pathList = n1.getChildNodes();
                    for (int j = 0; j < pathList.getLength(); j++){
                        Node myNodes = pathList.item(j);
                        if (!myNodes.getNodeName().contains("text")){
                            //System.out.println(myNodes.getTextContent());
                            // getting each line and adding them to a list
                            String name = myNodes.getNodeName();
                            String textValue;

                            // switch case for each node name
                            switch (name){
                                case "travel_mode":
                                    textValue = myNodes.getTextContent();
                                    dir.setMethod(textValue);
                                    break;

                                case "duration":
                                    textValue = myNodes.getTextContent().trim().split("\n")[1].trim();
                                    dir.setDuration(textValue);
                                    break;

                                case "html_instructions":
                                    textValue = myNodes.getTextContent();
                                    dir.setInformation(textValue);
                                    break;

                                case "distance":
                                    textValue = myNodes.getTextContent().trim().split("\n")[1].trim();
                                    dir.setDistance(textValue);
                                    break;

                                case "transit_details":
                                    NodeList tdetails = myNodes.getChildNodes();
                                    for (int a = 0; a < tdetails.getLength(); a++){
                                        Node na = tdetails.item(a);
                                        String nameNode = na.getNodeName();
                                        if (nameNode.equals("departure_stop")){
                                            dir.setStartStation(na.getTextContent().trim().split("\n ")[0]);
                                        }
                                        else if(nameNode.equals("arrival_stop")){
                                            dir.setEndStation((na.getTextContent().trim().split("\n ")[0]));
                                        }
                                        else if (nameNode.equals("line")){
                                            dir.setTrainName(na.getTextContent().trim().split("\n")[0]+ " " + na.getTextContent().trim().split("\n")[1].trim());
                                        }
                                        else if (nameNode.equals("departure_time")){
                                            dir.setDepatureTime(na.getTextContent().trim().split("\n")[1].trim());
                                        }
                                        else if (nameNode.equals("arrival_time")){
                                            dir.setArrivaltime(na.getTextContent().trim().split("\n")[1].trim());
                                        }

                                    }
                                    break;
                            }

                        }
                    }
                }
                dirList.add(dir);
            }





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return dirList;
    }
}
