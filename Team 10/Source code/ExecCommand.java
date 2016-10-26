package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

public class ExecCommand extends Command
{
	//method describes the Exec command
	public void describe() 
	{
		System.out.println("");
		System.out.println("Exec command is being executed...");
	}
	
	//Parses each command
	public void parse(Element elem) throws ProcessException
	{
		Node_Name=elem.getNodeName();
		String id = elem.getAttribute("id");
		if(id == null || id.isEmpty())
			throw new ProcessException("The ID of the exec command is missing");
		com_id = id;
		
		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) 
			throw new ProcessException("The PATH of the exec command is missing");		
		com_path=path;
		
		String in = elem.getAttribute("in");
		if (!(in == null || in.isEmpty()))
			com_in=in;
		
		String out = elem.getAttribute("out");
		if (!(out == null || out.isEmpty())) 
			com_out=out;
		
		
		List<String> comArgs = new ArrayList<String>();		
		String args = elem.getAttribute("args");
		if (!(args == null || args.isEmpty())) 
		{
			StringTokenizer st = new StringTokenizer(args);
			while (st.hasMoreTokens())
			{
				String t = st.nextToken();
				comArgs.add(t);
			}
		}
		com_args=comArgs;	
	}

	//Executes each command	
	public void execute(Map<String,Command> commands) throws ProcessException 
	{
			this.describe();
			List<String> c = new ArrayList<String>();
			//Forms the command to be executed 
			c.add(com_path);
			//Adds the arguments to the command
			for(int i=0;i<com_args.size();i++)
				c.add(com_args.get(i));
			
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(c);
			System.out.println("Command being executed:"+c);

			//Read the 'In' tag
			if (getIn() != null)
			{	
				try
				{
					//Fetch and assign the physical I/P file name
					String inFile=commands.get(getIn()).getPath();
					System.out.println("I/P file:"+inFile);
					builder.redirectInput(new File(inFile));
				}
				catch(Exception e)
				{
					throw new ProcessException(getIn()+" not mapped");
				}
			}
			
			//Read the 'Out' tag
			if (getOut() != null)
			{	
				try
				{
					//Fetch and assign the physical O/P file name 
					String outFile=commands.get(getOut()).getPath();	
					System.out.println("O/P file:"+outFile);
					builder.redirectOutput(new File(outFile));
				}
				catch(Exception e)
				{
					throw new ProcessException(getOut() + " not mapped");
				}
			}
			builder.redirectError(new File("error.txt"));
			System.out.println("Starting process...");

			try
			{
				//Starts the process
				Process process = builder.start();
				//Wait for termination of process
				process.waitFor();
				System.out.println("The current command is executed Successfully!!");
				System.out.println("##########################################");
			}
			catch(Exception e)
			{
				throw new ProcessException(e.getMessage());
			}
	}
}
