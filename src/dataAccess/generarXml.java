package dataAccess;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class generarXml {

	public static void generarArchivoXML(String pNombre, 
			ArrayList<String> pDescripcion, ArrayList<Integer> pId,ArrayList<Integer> pEntradas, 
			ArrayList<Integer> pSalidas, ArrayList<String> pCompuertas) throws Exception{
		
        if(pId.isEmpty() || pDescripcion.isEmpty() || pEntradas.isEmpty() || pSalidas.isEmpty()){ 
            System.out.println("Error: inserte datos al archivo XML");
           
        }
        else
        {
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document documento = implementation.createDocument(null, "Archivo",null);
            documento.setXmlVersion("1.0");
 
            //nodo principal o root
            Element raiz = documento.getDocumentElement();
            //Por cada descripcion, carpeta e ID que se crea, una compuerta contendrá el ID, la carpeta y la descripcion respectiva
            for(int indice=0; indice < pId.size();indice++){
            	Element NodoCompuerta = documento.createElement("CIRCUITO"); 
                //nodo descripcion
                Element NodoDescripcion = documento.createElement("DESCRIPCION"); 
                Text NodoTextoDescripcion = documento.createTextNode(pDescripcion.get(indice));
                NodoDescripcion.appendChild(NodoTextoDescripcion);
                //nodo descripcion real
                Element NodoDesc = documento.createElement("DESCRIPCION");
                Text NodoTextoDesc = documento.createTextNode(pDescripcion.get(indice));
                NodoDesc.appendChild(NodoTextoDesc);
                NodoDescripcion.appendChild(NodoDesc);
                //nodo ID
                Element NodoID = documento.createElement("ID"); 
                Text NodoTextoID = documento.createTextNode(pId.get(indice)+"");                
                NodoID.appendChild(NodoTextoID);
                NodoCompuerta.appendChild(NodoDescripcion);
                NodoDescripcion.appendChild(NodoID);
                //Nodo entradas
                Element NodoEntradas = documento.createElement("ENTRADAS");
                Text NodoTextoEntradas = documento.createTextNode(pEntradas.get(indice)+"");
                NodoEntradas.appendChild(NodoTextoEntradas);
                NodoDescripcion.appendChild(NodoEntradas);
                //Nodo salidas
                Element NodoSalidas = documento.createElement("SALIDAS");
                Text NodoTextoSalidas = documento.createTextNode(pSalidas.get(indice)+"");
                NodoSalidas.appendChild(NodoTextoSalidas);
                NodoDescripcion.appendChild(NodoSalidas);
                //Nodo Compuertas
                
                for(int k = 0; k < pCompuertas.size(); k++){
                	Element NodoCompuertas = documento.createElement("COMPUERTA");
                	Text NodoTextoCompuertas = documento.createTextNode(pCompuertas.get(k));
                    NodoCompuertas.appendChild(NodoTextoCompuertas);
                    NodoDescripcion.appendChild(NodoCompuertas);
                }
                
                
                
                raiz.appendChild(NodoCompuerta);//se une el elemento a la raiz "Documento"
                
            }                	
            //cuando genera todas las compuertas, finalmente genera el XML
            Source fuente = new DOMSource(documento);
            //almacenamiento
            
            Result resultado = new StreamResult(new java.io.File(pNombre + ".xml")); //nombre del archivo
            Transformer transf = TransformerFactory.newInstance().newTransformer();
            transf.transform(fuente, resultado);
            
            
            System.out.println(pNombre+" "+"generado");
        }
    }

}
