import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import org.snu.ids.kkma.index.*;



public class makeKeyword {
	 public void keyword(String xmlFile){
		File file = new File(xmlFile);
		String body_text  = null;
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		//document로 파싱
        Document doc = docBuilder.parse(file);
        //element 불러오기
        Element docs = doc.getDocumentElement();
        
        NodeList content = docs.getElementsByTagName("doc");
        for (int k = 0 ; content.item(k) != null;k++ ) {
        	Node body = null;
        	for(Node node = content.item(k).getFirstChild(); node !=null; node = node.getNextSibling()) {
        		
        		if (node.getNodeName().equals("body")) {
        			body_text = node.getTextContent();
        			body = node;
        		}
        	}
       
        	KeywordExtractor ke = new KeywordExtractor();
        	KeywordList kl = ke.extractKeyword(body_text, true);
        	String first_test = "" ;
        	for (int i = 0; i<kl.size(); i++) {
        		Keyword kwrd = kl.get(i);
        		first_test += kwrd.getString()+":"+ kwrd.getCnt()+"#";
        		}
        		body.getFirstChild().setNodeValue(first_test);
        	}
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		 
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); 
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult compelete = new StreamResult(new FileOutputStream(new File("./index.xml")));

        transformer.transform(source, compelete);
		

		}
		
		catch(Exception e){
			  e.printStackTrace();
		}
	
	}
}
