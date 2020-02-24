package ar.edu.unlu.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
/**
 *
 * @author feder
 */
public class XMLGenerator {
        
    public void crearXML(ArrayList<String> colum1,String nameC1,ArrayList<String> colum2,String nameC2,ArrayList<String> colum3,String nameC3,ArrayList<String> colum4,String nameC4){
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "query", null);
            document.setXmlVersion("1.0");
           
            // Creo los elementos
            Element columnas = document.createElement("cols");
            if(!(colum1.isEmpty())) {
            Element columna1 = document.createElement("colname1");
            Text textColumna = document.createTextNode(nameC1);
            columna1.appendChild(textColumna);
            columnas.appendChild(columna1);
            }
            if(!(colum2.isEmpty())) {
            Element columna2 = document.createElement("colname2");
            Text textColumna2 = document.createTextNode(nameC2);
            columna2.appendChild(textColumna2);
            columnas.appendChild(columna2);
            }
            if(!(colum3.isEmpty())) {
            Element columna3 = document.createElement("colname3");
            Text textColumna3 = document.createTextNode(nameC3);
            columna3.appendChild(textColumna3);
            columnas.appendChild(columna3);
            }
            if(!(colum4.isEmpty())) {
            
            Element columna4 = document.createElement("colname4");
            Text textColumna4 = document.createTextNode(nameC4);
            columna4.appendChild(textColumna4);
            columnas.appendChild(columna4);
            }
                   
            
            
            
            document.getDocumentElement().appendChild(columnas);
            
            Element filas = document.createElement("rows");
           
            
            
            //---------------CICLO------------------------------------
            //-----------VALIDAR CADA COLUMNA
            //fila1.appendChild(fila1)
            //  
            for(int i=0;i<colum1.size();i++){
            
	            Element fila = document.createElement ("row");//+String.valueOf(i));
	            if(!(colum1.isEmpty())) {
		            Element valorColumna1= document.createElement("col1");            
		            Text textColumna1 = document.createTextNode(colum1.get(i));
		            valorColumna1.appendChild(textColumna1);
		            fila.appendChild(valorColumna1);
	            
	            }
	            if(!(colum2.isEmpty())) {
		            Element valorColumna2= document.createElement("col2");
		            Text textColumna2 = document.createTextNode(colum2.get(i));
		            valorColumna2.appendChild(textColumna2);
		            fila.appendChild(valorColumna2);
	            }
	            if(!(colum3.isEmpty())) {
		            Element valorColumna3= document.createElement("col3");
		            Text textColumna3 = document.createTextNode(colum3.get(i));
		            valorColumna3.appendChild(textColumna3);
		            fila.appendChild(valorColumna3);
	            }
	            if(!(colum4.isEmpty())) {
		            Element valorColumna4= document.createElement("col4"); 
		            Text textColumna4 = document.createTextNode(colum4.get(i));
		            valorColumna4.appendChild(textColumna4);
		            fila.appendChild(valorColumna4);
		           }
	            filas.appendChild(fila);
	            document.getDocumentElement().appendChild(filas);
            }
            // Asocio el source con el Document
            Source source = new DOMSource(document);
            // Creo el Result, indicado que fichero se va a crear
            Result result = new StreamResult(new File("query.xml"));
 
            // Creo un transformer, se crea el fichero XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            //return result;
 
        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());       
            
            
        
            
        }
        
    }

	
}
