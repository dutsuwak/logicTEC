package dataAccess;

import gui.facade.Facade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import logic.lista.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Readxml{
	
	public Readxml(String archivo, Facade facade){
		try {

		    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		    Document doc = docBuilder.parse (new File(archivo));

		    // normalize text representation
		    doc.getDocumentElement().normalize();

		    NodeList listaDeCompuertasINPUT = doc.getElementsByTagName("COMPUERTA");
		    NodeList listaEntradasINPUT = doc.getElementsByTagName("ENTRADAS");
		    NodeList listaSalidasINPUT = doc.getElementsByTagName("SALIDAS");
		    NodeList listaDescripcion = doc.getElementsByTagName("DESCRIPCION");
		    
		    List<String> listaDeCompuertasOUTPUT = new List<>();
		    List<String> listaEntradasOUTPUT = new List<>();
		    List<String> listaSalidasOUTPUT = new List<>();
		    List<String> listaDescripcionOUTPUT = new List<>();
		    
		    listaEntradasOUTPUT.insertPointer(listaEntradasINPUT.item(0).getTextContent());
		    listaSalidasOUTPUT.insertPointer(listaSalidasINPUT.item(0).getTextContent());
		    listaDescripcionOUTPUT.insertPointer(listaDescripcion.item(1).getTextContent());
		    //System.out.println("Entradas " + listaEntradasINPUT.item(0).getTextContent());
		    //System.out.println("Salidas " + listaSalidasINPUT.item(0).getTextContent());
		    //System.out.println("Descripcion " + listaDescripcion.item(1).getTextContent());
		    
		    for(int i=0; i<listaDeCompuertasINPUT.getLength() ; i++) {
		        Node temporal = listaDeCompuertasINPUT.item(i);
		        if(temporal.getNodeType() == Node.ELEMENT_NODE) {
		        	listaDeCompuertasOUTPUT.insertPointer(temporal.getTextContent());
		        }
		    }
		    //listaDeCompuertasOUTPUT.showData();
		    JOptionPane message = new JOptionPane();
			message.showMessageDialog(null,"Circuito Cargado Correctamente", "",  JOptionPane.INFORMATION_MESSAGE);
		    facade.cargarCircuito(listaDescripcionOUTPUT,listaEntradasOUTPUT,listaSalidasOUTPUT,listaDeCompuertasOUTPUT);
		    
		} catch (SAXParseException err) {
		    System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
		    System.out.println(" " + err.getMessage ());
		} catch (SAXException e) {
		    Exception x = e.getException ();
		    ((x == null) ? e : x).printStackTrace ();
		}
		catch (FileNotFoundException err){
			JOptionPane message = new JOptionPane();
			message.showMessageDialog(null,"Archivo No Encontrado", "",  JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
