package ar.edu.unlu.sockets;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DefaultListModel model= new DefaultListModel();
		File file= new File("C:\\Users\\feder\\query.xml");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
		try {
			documentBuilder = dbf.newDocumentBuilder();   
			Document document = documentBuilder.parse(file);
			System.out.println("raiz: " + document.getDocumentElement().getNodeName());
			NodeList columnas = document.getElementsByTagName("cols");
			  for (int temp = 0; temp<columnas.getLength(); temp++) {
	                Node nodo = columnas.item(temp);
	                System.out.println("Elemento:" + nodo.getNodeName());
	                
	                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
	                    Element element = (Element) nodo;
	                    System.out.println("columna1 " +element.getElementsByTagName("colname1").item(0).getTextContent());
	                    System.out.println("columna2 " +element.getElementsByTagName("colname2").item(0).getTextContent());
	                    System.out.println("columna3 " +element.getElementsByTagName("colname3").item(0).getTextContent());
	                    System.out.println("columna4 " +element.getElementsByTagName("colname4").item(0).getTextContent());  
	                }
			  }
			  NodeList filas = document.getElementsByTagName("row");
			  for (int i = 0; i<filas.getLength(); i++) {
				  Node nodo2 = filas.item(i);
	              System.out.println("Elemento:" + nodo2.getNodeName());
	              if (nodo2.getNodeType() == Node.ELEMENT_NODE) {  
	            	  Element elemento = (Element) nodo2;
	            	  
	            	  for (int x=1;x<=4;x++) {
	            	  System.out.println("fila1 " +elemento.getElementsByTagName("col"+String.valueOf(x)).item(0).getTextContent());
	            	  }
	            	  /*System.out.println("fila1 " +elemento.getElementsByTagName("col2").item(0).getTextContent());
	            	  System.out.println("fila1 " +elemento.getElementsByTagName("col3").item(0).getTextContent());
	            	  System.out.println("fila1 " +elemento.getElementsByTagName("col4").item(0).getTextContent());
	            	  */
	              }
			  }
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
