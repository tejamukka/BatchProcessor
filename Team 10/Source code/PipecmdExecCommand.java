package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

import test.Command;
import test.ProcessException;

public class PipecmdExecCommand extends Command 
{
		// This keeps the count of the number of the exec commands given in the pipecmd
		static int count=0;
		PipecmdExecCommand()
		{
			count++;
		}
				

		//parsing the each exec command
		public void parse(Element elem) throws ProcessException  
		{						
			String id = elem.getAttribute("id");
			if (id == null || id.isEmpty()) 
				throw new ProcessException("The ID of pipe exec command"+count+" is missing");
			com_id=id;
			

			String path = elem.getAttribute("path");
			if (path == null || path.isEmpty()) 
				throw new ProcessException("The PATH of pipe exec command"+count+" is missing");		
			com_path=path;

			// Add arguments
			List<String> comArgs = new ArrayList<String>();
			String arg = elem.getAttribute("args");
			if (!(arg == null || arg.isEmpty())) 
			{
				StringTokenizer st = new StringTokenizer(arg);
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					comArgs.add(t);
				}
			}
			com_args=comArgs;

			String in = elem.getAttribute("in");
			if (!(in == null || in.isEmpty())) 
				com_in=in;


			String out = elem.getAttribute("out");
			if (!(out == null || out.isEmpty())) 
				com_out=out;
			
		}
		
		
		
		//Execute/display pipe exec command
		public void execute( Map<String, Command> batch_CommandList ) 
		{
			this.describe();
			System.out.println("Executing the pipe exec command  with id: "+ com_id);	
		}
		
		
		//describing the PipecmdCommand
				public void describe() 
				{
					System.out.println("");
					System.out.println("Pipe exec command"+count+" execution has started");
				}					
				
		

}