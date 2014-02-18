package com.teymurakh.iwblr.core;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.teymurakh.iwblr.entities.Entity;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.geom.Vec;
import com.teymurakh.iwblr.util.XmlHelper;

public class WorldIO {
	
	private final String levelDirectoryPath;
	
	public WorldIO(String levelsPath) {
		this.levelDirectoryPath = levelsPath;
	}

	public void loadLevel(String levelName, World world) {
		String filePath = levelDirectoryPath + levelName + ".xml";
		try {
			Document doc = XmlHelper.loadDoc(filePath);
			Element level = (Element) doc.getElementsByTagName("level").item(0);
			NodeList entityList = level.getElementsByTagName("entity");

			for (int i = 0; i < entityList.getLength(); i++) {
				Node node = entityList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					float groupX = Float.parseFloat(node.getParentNode().getAttributes().getNamedItem("x").getNodeValue());
					float groupY = Float.parseFloat(node.getParentNode().getAttributes().getNamedItem("y").getNodeValue());
					float x = Float.parseFloat(element.getAttribute("x"));
					float y = Float.parseFloat(element.getAttribute("y"));
					String id = element.getAttribute("name");

					Vec newPosition = new Vec(groupX + x, groupY + y);

					Entity newEntity = new Entity(id);
					world.placeGlobal(newEntity, newPosition);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
		//Game.saveHandler.load(levelName);
		//Game.gameState.loadState(levelName);
		//float guy_x = Float.parseFloat(Game.gameState.get("guy_x"));
		//float guy_y = Float.parseFloat(Game.gameState.get("guy_y"));
		
		//guy.setPosX(guy_x);
		//guy.setPosY(guy_y);
		
		world.refreshActiveList(new Rectangle(0f, 20f, 30f, 30f));
		
		
		Game.console.printLine("FIX me" + " entities successfully loaded from " + levelName + ".xml");
	}
	
	/// SAVING
	public void saveLevel(String levelName, World world) {
		
		String filePath = levelDirectoryPath + levelName + ".xml";
		
		try {
			Document doc = XmlHelper.createDoc();
			
			Element rootElement = doc.createElement("level");
			rootElement.setAttribute("version", "1.4");
			doc.appendChild(rootElement);
			
			Element entities = doc.createElement("entities");
			rootElement.appendChild(entities);
			
			Element group = doc.createElement("group");
			entities.appendChild(group);
			
			group.setAttribute("x", "0");
			group.setAttribute("y", "0");
		
			for (Entity item : world.getGlobal()) {
				Element block = doc.createElement("entity");
				
				block.setAttribute("name", item.getScriptName());
				block.setAttribute("x", item.getPos().getX()+"");
				block.setAttribute("y", item.getPos().getY()+"");
					
				group.appendChild(block);
			}
			
			// write the content into xml file
			XmlHelper.saveDoc(doc, filePath);
			Game.console.printLine("Level saved to " + filePath);
		
		} catch (ParserConfigurationException pce) {
			  pce.printStackTrace();
		} catch (TransformerException tfe) {
			  tfe.printStackTrace();
		}
	}
	
}
