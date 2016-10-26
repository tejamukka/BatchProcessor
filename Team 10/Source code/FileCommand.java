package test;

import java.util.Map;
import org.w3c.dom.Element;

public class FileCommand extends Command
{

	//method describes the File command
	public void describe() 
	{
		System.out.println("");
		System.out.println("File command is being executed...");
	}
	
	//Executes/displays the filename command ID being currently executed  
	public void execute (Map<String,Command> Commands)
	{
		this.describe();
		System.out.println("The filename command of ID " + com_id + " is being set...");
	}
	
	//Parses the filename command
	public void parse(Element element) throws ProcessException
	{
		this.describe();
		String id = element.getAttribute("id");
		if(id == null || id.isEmpty())
			throw new ProcessException("The ID of the file command is missing");
				
		com_id = id;		
		
		String path = element.getAttribute("path");
		if(path == null || path.isEmpty())
			throw new ProcessException("The PATH of the file command is missing");
		
		com_path = path;
	}
}