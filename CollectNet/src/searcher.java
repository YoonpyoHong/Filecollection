
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {
	public HashMap<Integer, Double> Innerproduct(String searchfile, String input) {
		try {
			//indexer에서 저장 해놓은 hashmap데이터 받아오기
			HashMap<String, String[]> IndexMap = new HashMap<String, String[]>();
			
			FileInputStream fileinputstream = new FileInputStream(searchfile);
			ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
			 
			IndexMap = (HashMap<String, String[]>)objectinputstream.readObject();
			
			String example = input;
			
			//weight_list 안에 가중치 값이 정리된 배열 저장
			KeywordExtractor ke = new KeywordExtractor();
        	KeywordList kl = ke.extractKeyword(example, true);
        	String[][] weight_list = new String[kl.size()][];
        	//글자 개수 저장하기
        	int word_count = kl.size();
        	for (int i = 0; i<kl.size(); i++) {
        		Keyword kwrd =  kl.get(i);
        		weight_list[i] =IndexMap.get(kwrd.getString());	
        	}
        	
        	Double save[][] = {{0.0,-1.0},{0.0,-1.0},{0.0,-1.0}};
        	
        	HashMap<Integer, Double> save_sum = new HashMap<Integer, Double>();
        	
        	
        	for(int id_save = 0; id_save*2<weight_list[0].length; id_save++) {
        		double weight = 0;
        		
        		for(int i = 0;i<word_count; i++) {
        			weight += Double.parseDouble(weight_list[i][id_save*2+1]);
        		}
        		
        		save_sum.put(id_save, weight);
        		
        		}
        		
        	return save_sum;

		
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void CalcSim(String searchfile, String input) {
		try {
			//indexer에서 저장 해놓은 hashmap데이터 받아오기
			HashMap<String, String[]> IndexMap = new HashMap<String, String[]>();
			
			FileInputStream fileinputstream = new FileInputStream(searchfile);
			ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
			 
			IndexMap = (HashMap<String, String[]>)objectinputstream.readObject();
			
			File file = new File("./collection.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			//document로 파싱
	        Document doc = docBuilder.parse(file);
	        //element 불러오기
	        Element docs = doc.getDocumentElement();
			
	        NodeList content = docs.getElementsByTagName("doc");
	        
			String example = input;
			
			//weight_list 안에 가중치 값이 정리된 배열 저장
			KeywordExtractor ke = new KeywordExtractor();
        	KeywordList kl = ke.extractKeyword(example, true);
        	String[][] weight_list = new String[kl.size()][];
        	//글자 개수 저장하기
        	int word_count = kl.size();
        	for (int i = 0; i<kl.size(); i++) {
        		Keyword kwrd =  kl.get(i);
        		weight_list[i] =IndexMap.get(kwrd.getString());	
        	}
        	
        	Double save[][] = {{0.0,-1.0},{0.0,-1.0},{0.0,-1.0}};
        	
        	
        	
        	
        	for(int id_save = 0; id_save*2<weight_list[0].length; id_save++) {
        		
        		double weight = Innerproduct(searchfile, input).get(id_save);
        		double weight_cos_a = 0;
        		double weight_cos_b = 0;
        		for(int i = 0;i<word_count; i++) {
        			Keyword kwrd =  kl.get(i);
        			weight_cos_a += Math.pow(Double.parseDouble(weight_list[i][id_save*2+1]), 2);
        			weight_cos_b += Math.pow(kwrd.getCnt(),2);
        		}
        		
        		if(Math.sqrt(weight_cos_a )*Math.sqrt(weight_cos_b) != 0) {
        			weight = weight/Math.sqrt(weight_cos_a )*Math.sqrt(weight_cos_b);
        		}
        		else {
        			weight =0;
        		}
        		
        		if(weight>save[2][1]) {
        			if(weight>save[1][1]) {
        				if(weight>save[0][1]) {
        					save[2][0] =save [1][0];
        					save[2][1] =save [1][1];
        					save[1][0] = save[0][0];
        					save[1][1] = save[0][1];
        					save[0][0] = (double) id_save;
        					save[0][1] = weight;
        				}
        				else {
        					save[2][0] =save [1][0];
        					save[2][1] =save [1][1];
        					save[1][0] = (double) id_save;
        					save[1][1] = weight;
        				}
        			}
        			else {
        				save[2][0] = (double) id_save;
    					save[2][1] = weight;
        			}
        		}
        		
        	}
        	for(int i =0; i<3; i++) {
        		for(Node node = content.item((int) Math.round(save[i][0])).getFirstChild(); node !=null; node = node.getNextSibling()) {
        			if (node.getNodeName().equals("title")) {
        				if(save[i][1]!= 0) {
        				System.out.print(node.getTextContent());
        				System.out.print(" ");
        				System.out.println((double)Math.round(save[i][1]*1000)/1000);
        				}
        			}
        		}
        	}

        	
		} catch (IOException | ClassNotFoundException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
