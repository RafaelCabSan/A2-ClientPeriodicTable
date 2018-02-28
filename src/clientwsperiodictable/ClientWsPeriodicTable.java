package clientwsperiodictable;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ClientWsPeriodicTable {
    
    public static void main(String[] args) throws Exception {
        menu();
        dameNumeroServicio();
    }
    
    private static void menu () {
        System.out.println("1. Atomic Weight");
        System.out.println("2. Atomic Number");
        System.out.println("3. Get element Symbols");
        System.out.println("4. Get atoms");
    }
    
    private static void dameNumeroServicio() throws SAXException, ParserConfigurationException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a number to use any service");
        int numero = scanner.nextInt();
        
        String xml;
        NodeList node;
        System.out.println("Write an element");
        switch(numero) {
            case 1: xml = getAtomicNumber(scanner.next());
                    node = nodeL(xml);
                    show(node,"AtomicNumber");
                break;
            case 2: xml = getAtomicNumber(scanner.next());
                    node = nodeL(xml);
                    show(node,"AtomicWeight");
                break;
            case 3: xml = getElementSymbol(scanner.next());
                    node = nodeL(xml);
                    show(node,"Symbol");
                break;
            case 4: xml = getAtoms();
                    node = nodeL(xml);
                    show(node, "ElementName");
                break;
            default: System.out.println("Numero invalido");
        }
    }
    
    private static void show(NodeList nodes, String name) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList number = element.getElementsByTagName(name);
            Element line = (Element) number.item(0);
            System.out.println(name + ": " + getCharacterDataFromElement(line));
        }
    }
    
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";

    }

    private static NodeList nodeL (String xml) throws SAXException, ParserConfigurationException, IOException {
        
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        Document doc = db.parse(is);
        NodeList nodes = doc.getElementsByTagName("Table");
        return nodes;
    }
    
    private static String getAtomicNumber(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtomicNumber(elementName);
    }

    private static String getAtomicWeight(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtomicWeight(elementName);
    }

    private static String getAtoms() {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getAtoms();
    }

    private static String getElementSymbol(java.lang.String elementName) {
        net.webservicex.Periodictable service = new net.webservicex.Periodictable();
        net.webservicex.PeriodictableSoap port = service.getPeriodictableSoap();
        return port.getElementSymbol(elementName);
    }
}
