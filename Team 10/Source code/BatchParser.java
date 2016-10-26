package test;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import test.Batch;
import test.Command;

public class BatchParser 
{
		//Method to parse and build the batch
		public Batch buildBatch(File batchFile) throws ProcessException 
		{
			
			Batch batch = new Batch();
			try {
				System.out.println("Opening " + batchFile);
				FileInputStream fis = new FileInputStream(batchFile);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fis);
				Element pnode = doc.getDocumentElement();
				NodeList nodes = pnode.getChildNodes();
				for (int idx = 0; idx < nodes.getLength(); idx++) 
				{
					Node node = nodes.item(idx);
					if (node.getNodeType() == Node.ELEMENT_NODE) 
					{
						// Find errors in batch commands
						NamedNodeMap attrs = node.getAttributes();
					      for (int i = 0; i < attrs.getLength(); i++) 
					      {
					        Attr attribute = (Attr) attrs.item(i);
					        if (attribute.getName() != "id" &&
					        	attribute.getName() != "path" &&
					        	attribute.getName() != "in" &&
					        	attribute.getName() != "out" &&
					        	attribute.getName() != "args"){
					        		throw new ProcessException("The tag '"+attribute.getName()+"' is WRONG in line "+idx);
					        		
					        }
					      }
						Element elem = (Element) node;
						Command command = buildCommand(elem);
						batch.addCommand(command);
					}	
				}
				batch.setStatus(true);
				System.out.println("Parsing completed and batch is built");
				
			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				System.out.println("Parsing complete with errors");		
			}	
			return batch;
		}
	
		// Method to parse each line of the input XML file and return the command to be added to the batch
		private Command buildCommand(Element elem) throws ProcessException 		
		{
			String commandName = elem.getNodeName();
			Command command = null;
			//No command
			if (commandName == null) 
			{
				throw new ProcessException("Can not  parse command from "+ elem.getTextContent());
			} 
		
			//Pipe command
			else if ("pipecmd".equalsIgnoreCase(commandName)) 
			{
				System.out.println("");
				System.out.println("Parsing the pipe tag..");
				command = new PipecmdCommand();
				command.parse(elem);
			} 
		
			//Exec command
			else if ("exec".equalsIgnoreCase(commandName)) 
			{
				System.out.println("");
				System.out.println("Parsing exec tag...");
				command = new ExecCommand();
				command.parse(elem);
			} 
		
			//Filename command
			else if ("filename".equalsIgnoreCase(commandName)) 
			{
				System.out.println("");
				System.out.println("Parsing filename tag... "  );
				command = new FileCommand();
				command.parse(elem);
			} 
		
			else 
			//Unknown command
			{
				throw new ProcessException("Unknown command " + commandName + " from: "	+ elem.getBaseURI());
			}
			
			return command;

		}
			
			
			
			
			
		
		




}
