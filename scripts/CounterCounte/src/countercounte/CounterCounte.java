/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package countercounte;

import java.io.BufferedReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author s129778
 */
public class CounterCounte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Provide the output of the counterexample and the xml of the lts as parameters");
            return;
        }
        ArrayList<String> states = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(args[0]));
            try {
                String line = br.readLine();

                while (line != null) {
                    if (line.indexOf("@") + line.indexOf("'") > 0) {
                        int begin = Math.max(line.indexOf("@") + 1, line.indexOf("'") + 1);
                        int end = begin;
                        boolean b = true;
                        while (b) {
                            try {
                                Integer.parseInt(line.charAt(end) + "");
                                end++;
                            } catch (Exception e) {
                                b = false;
                            }
                        }
                        if (!line.endsWith("*")) {
                            states.add(line.substring(begin, end));
                        }
                    }
                    line = br.readLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            HashMap<String, String> transitionLabels = new HashMap();
            HashMap<String, HashMap<String, ArrayList<String>>> transitions = new HashMap();
            HashMap<String, String> valueToTransition = new HashMap();
            try {
                File fXmlFile = new File(args[1]);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                NodeList nList = doc.getElementsByTagName("TransitionLabel");
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        transitionLabels.put(eElement.getAttribute("value"), eElement.getAttribute("label"));
                    }
                }
                nList = doc.getElementsByTagName("TransitionLabelNode");
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        valueToTransition.put(eElement.getAttribute("value"), eElement.getAttribute("labelindex"));
                    }
                }
                nList = doc.getElementsByTagName("Transition");
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        String label = transitionLabels.get(valueToTransition.get(eElement.getAttribute("value")));
                        if (transitions.get(eElement.getAttribute("from")) == null) {
                            HashMap<String, ArrayList<String>> from = new HashMap();
                            ArrayList<String> list = new ArrayList();
                            list.add(label);
                            from.put(eElement.getAttribute("to"), list);
                            transitions.put(eElement.getAttribute("from"), from);
                        } else {
                            if (transitions.get(eElement.getAttribute("from")).get(eElement.getAttribute("to")) == null) {
                                ArrayList<String> list = new ArrayList();
                                list.add(label);
                                transitions.get(eElement.getAttribute("from")).put(eElement.getAttribute("to"), list);
                            } else {
                                transitions.get(eElement.getAttribute("from")).get(eElement.getAttribute("to")).add(label);
                            }
                        }
                    }
                }
                for (int i = 0; i < states.size() - 1; i++) {
                    System.out.println("State label: " + states.get(i));
                    System.out.println(transitions.get(states.get(i)).get(states.get(i + 1)));
                }
                System.out.println("State label: " + states.get(states.size() - 1));
            } catch (SAXException ex) {
                Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(CounterCounte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
