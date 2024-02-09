package br.com.marinha.xml;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.com.marinha.modelo.OrganizacaoMilitar;

public class XML {

	private static Document doc;
	
	public static void insertNodeXML(Element elementoPai, String nomeElementoFilho, String valor){
		Element elemento = doc.createElement(nomeElementoFilho);
		elemento.appendChild(doc.createTextNode(valor));
		elementoPai.appendChild(elemento);
	}
	
	public static void insertAttributeXML(Element elemento, String nomeAtributo, String valorAtributo){
		Attr attrId 	= doc.createAttribute(nomeAtributo);
		attrId.setValue(valorAtributo);
		elemento.setAttributeNode(attrId);
	}
	
	public static String geraListagemXML(List<OrganizacaoMilitar> listaOM){
		
		String retorno = "";
		
		try{
			//cria xml
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("listagem");
			doc.appendChild(rootElement);
						
			for(int i = 0; i < 30; i++){  
				
				try{
					
					OrganizacaoMilitar om = listaOM.get(i);
														
					Element documentoXML = doc.createElement("codigo");
					rootElement.appendChild(documentoXML);
								
					insertAttributeXML(documentoXML, "id", Integer.toString(i+1));
					insertAttributeXML(documentoXML, "name", om.getSigla());
										
					insertNodeXML(documentoXML, "descricao", om.getDescricao());
					insertNodeXML(documentoXML, "sigla", om.getSigla());
					insertNodeXML(documentoXML, "indicativo", om.getIndicativo());
					insertNodeXML(documentoXML, "posto", om.getPosto());
					insertNodeXML(documentoXML, "sequencia", om.getSequencia());
					insertNodeXML(documentoXML, "genero", om.getGenero());

										
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
	
			StringWriter stringWriter = new StringWriter();
			StreamResult result = new StreamResult(stringWriter );
			transformer.transform(source, result);
			retorno = stringWriter.toString();
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return retorno;
	}	
}
