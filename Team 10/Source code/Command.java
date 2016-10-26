package test;

import java.util.List;
import java.util.Map;


import org.w3c.dom.Element;

public class Command 
{
	protected String Node_Name;
	protected String com_id;
	protected String com_path;
	protected String com_in;
	protected String com_out;
	protected List <String> com_args;
	
	public String getName()
	{
		return Node_Name;
	}
	public String getId()
	{
		return com_id;
	}
	public String getPath()
	{
		return com_path;
	}
	public String getIn()
	{
		return com_in;
	}
	public String getOut()
	{
		return com_out;
	}
	
	public List<String> getArgs()
	{
		return com_args;
	}
	
	public void parse(Element elem) throws ProcessException
	{	
		
	}
	public void execute(Map<String,Command> commands) throws ProcessException 
	{	
		
	}	
	
	public void describe() 
	{
		
	} 

}	
