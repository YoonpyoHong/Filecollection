import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	public void  index(String Xmlfile) {
		
		try {
			//텍스트를 넣어줄 리스트 선언
			ArrayList<HashMap<String, String>> body_text_list = new ArrayList<HashMap<String, String>>() ;
			
			
			FileOutputStream filestream = new FileOutputStream("./index.post");
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(filestream);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			//document로 파싱
	        Document doc = docBuilder.parse(Xmlfile);
	       
	        Element docs = doc.getDocumentElement();
			
	        NodeList content = docs.getElementsByTagName("doc");
	        for (int k = 0 ; content.item(k) != null;k++ ) {
	        	//문자와 사용된 횟수를 저장할 hashmap 파일 만들기
	        	HashMap <String, String> map = new HashMap<String, String>();
	        	
	        	for(Node node = content.item(k).getFirstChild(); node !=null; node = node.getNextSibling()) {
	        		if (node.getNodeName().equals("body")) {
	        			//문서의 내용을 저장할 body_text와 문자와 사용 횟수 탐색을 위한 변수 초기화
	        			String body_text = "";
	        			body_text = node.getTextContent();
	        			int key = 0;
	        			String word = "";
	        			String count = "";
	        			while(key<body_text.length()) {
	        				//문자를 key 횟수를 value로 저장하기
	        				word = body_text.substring(key,body_text.indexOf(":",key));
	        				key = body_text.indexOf( ":", key);
	        				count = body_text.substring(key+1,body_text.indexOf("#", key));
	        				key = body_text.indexOf("#",key) + 1; 
	        				map.put(word, count);
	        				
	        			}
	        			body_text_list.add(map);
	        		}
	        	}
	        }
	        //index.post에 넣을 hashmap 생성
	        HashMap<String, String[]> IndexMap = new HashMap<String, String[]>();
	        
	        //Arraylist 돌기위한 Iterator#1
	        Iterator<HashMap<String, String>> it = body_text_list.iterator();
	        
	        while(it.hasNext()){
	        	//임시로 받을  hashmap 생성
		        HashMap<String, String> tmp1 = new HashMap<String, String>();
	        	tmp1 = it.next();
	        	for(String key: tmp1.keySet()) {
	        		Iterator<HashMap<String, String>> inside = body_text_list.iterator();
	        		if(IndexMap.get(key) == null ) {
	        			double id = 0;
	        			double count_id = 0;
	        			ArrayList<Integer> count_word = new ArrayList<Integer>();
	        			while(inside.hasNext()) {
	        				HashMap<String, String> tmp2 = new HashMap<String, String>();
	        				tmp2 = inside.next();
	        				if(tmp2.get(key)!=null) {
	        					count_word.add(Integer.parseInt(tmp2.get(key)));
	        					count_id++;
	        				}
	        				else {
	        					count_word.add(0);
	        				}
	        				id++;
	        			}
	        			int id_save = 0;
	        		
	        			String[] arr= new String[(int) id*2];
	        			for (int value: count_word) {
	        				 arr[id_save*2] =Integer.toString(id_save) ;
	        				 arr[id_save*2+1] = Double.toString((double)Math.round(value*Math.log(id/count_id)*1000)/1000);
	        				 
	        				 
	        				 id_save++;
	        			}
	        			
	        			IndexMap.put(key, arr);
	        				        	        		
	        		}	        			        		
	        	}
	        }
			
			objectoutputstream.writeObject(IndexMap);
			
			objectoutputstream.close();
	        
		}
		 catch ( IOException | ParserConfigurationException | SAXException e) {
			
			e.printStackTrace();
		}       
	}
}