package com.teymurakh.iwblr.core;

import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import com.teymurakh.iwblr.util.XmlHelper;

public class GameState {

	private final HashMap<String, String> stateMap;
	private final String path;
	
	public GameState() {
		stateMap = new HashMap<String, String>();
		path = "saves/";
	}
	
	public void put(String key, String value) {
		stateMap.put(key, value);
	}
	
	public String get(String key) {
		return stateMap.get(key);
	}
	
	public Set<String> keySet() {
		return stateMap.keySet();
	}
	
	
	public void loadState(String levelName) {
		String filePath = path + levelName + ".xml";
		try {
			Document doc = XmlHelper.loadDoc(filePath);
			Element level = (Element) doc.getElementsByTagName("gameState").item(0);
			NodeList parameterList = level.getElementsByTagName("parameter");

			for (int i = 0; i < parameterList.getLength(); i++) {
				Node node = parameterList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String name = element.getAttribute("name");
					String value = element.getAttribute("value");
					stateMap.put(name, value);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/// SAVING
	public void saveState(String levelName) {
		String filePath = path + levelName + ".xml";
		
		try {
			Document doc = XmlHelper.createDoc();
			
			Element rootElement = doc.createElement("gameState");
			rootElement.setAttribute("version", Game.VERSION);
			doc.appendChild(rootElement);
			
			Element parameters = doc.createElement("parameters");
			rootElement.appendChild(parameters);
		
			for (String key : stateMap.keySet()) {
				Element parameter = doc.createElement("parameter");
				String value = stateMap.get(key);
				
				parameter.setAttribute("name", key);
				parameter.setAttribute("value", value);
					
				parameters.appendChild(parameter);
			}
			
			// write the content into xml file
			XmlHelper.saveDoc(doc, filePath);
		
		} catch (ParserConfigurationException pce) {
			  pce.printStackTrace();
		} catch (TransformerException tfe) {
			  tfe.printStackTrace();
		}
	}
}
