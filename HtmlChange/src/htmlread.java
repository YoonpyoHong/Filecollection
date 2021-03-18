import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class htmlread{
	public static void main(String[] args)  {
		File dir = new File("htmllist");
		File filelist[] = dir.listFiles();
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
        Document result = docBuilder.newDocument();
        result.setXmlStandalone(true);
        
        Element docs = result.createElement("docs"); 
        result.appendChild(docs);
        
      
        
       
        
       int code_num = 0;
        
		for(File file: filelist) {
			String code_num_String = Integer.toString(code_num);
			org.jsoup.nodes.Document input = Jsoup.parse(file, "UTF-8");
			
			Element doc = result.createElement("doc");
			docs.appendChild(doc);
			
			doc.setAttribute("id",code_num_String);
			
			Elements title_html = input.select("title");
			Elements content_html = input.select("#content");
			String title_text = title_html.text();
			String content_text = content_html.text();
			
			
			Element title = result.createElement("title");
			title.appendChild(result.createTextNode(title_text));
			doc.appendChild(title);
			
			Element body = result.createElement("body");
			body.appendChild(result.createTextNode(content_text));
			doc.appendChild(body);
			
			code_num++;
	
			}	
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
		 
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); 
         transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
         transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");

         DOMSource source = new DOMSource(result);
         StreamResult compelete = new StreamResult(new FileOutputStream(new File("src/data/book.xml")));

         transformer.transform(source, compelete);
		

		}

		catch (Exception e){
            e.printStackTrace();
        }
		
	
		 
		
	}
}






