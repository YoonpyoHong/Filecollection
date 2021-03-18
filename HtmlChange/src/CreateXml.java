import java.io.File;
import java.io.FileOutputStream;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class CreateXml {
 
    public static void main (String[] args) {
 
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
            // book ������Ʈ
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true); //standalone="no" �� �����ش�.
 
            Element book = doc.createElement("book");
            doc.appendChild(book);
 
            // code ������Ʈ
            Element code = doc.createElement("doc");
            book.appendChild(code);
 
            // �Ӽ��� ���� (id:1)
            code.setAttribute("id", "1");
            
            // name ������Ʈ
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode("����� �������� ��°�?"));
            code.appendChild(name);
 
            // writer ������Ʈ
            Element writer = doc.createElement("writer");
            writer.appendChild(doc.createTextNode("�罺����"));
            code.appendChild(writer);
 
            // price ������Ʈ
            Element price = doc.createElement("price");
            price.appendChild(doc.createTextNode("100"));
            code.appendChild(price);
 
            code = doc.createElement("doc");
            book.appendChild(code);
 
            // �Ӽ��� ���� (id:2)
            code.setAttribute("id", "2");
 
            // name ������Ʈ
            name = doc.createElement("name");
            name.appendChild(doc.createTextNode("ȫ�浿 ��"));
            code.appendChild(name);
 
            // writer ������Ʈ
            writer = doc.createElement("writer");
            writer.appendChild(doc.createTextNode("���"));
            code.appendChild(writer);
 
            // price ������Ʈ
            price = doc.createElement("price");
            price.appendChild(doc.createTextNode("300"));
            code.appendChild(price);
 
            // XML ���Ϸ� ����
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
 
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //���� �����̽�4ĭ
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //�鿩����
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
 
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File("src/data/book.xml")));
 
            transformer.transform(source, result);
 
            System.out.println("=========END=========");
 
        }catch (Exception e){
            e.printStackTrace();
        }
 
    }
 
}