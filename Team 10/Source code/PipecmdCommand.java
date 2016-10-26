package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import test.Command;
import test.PipecmdExecCommand;
import test.ProcessException;

public class PipecmdCommand extends Command {
	PipecmdExecCommand pipeExec1= new PipecmdExecCommand();
	PipecmdExecCommand pipeExec2= new PipecmdExecCommand();
	

	//method describes the pipe command
	public void describe() 
	{
		System.out.println("");
		System.out.println("Pipe command is being executed...");
	}
	//executing the pipe command
	public void execute(Map<String,Command> commandList ) throws ProcessException 

	{
		this.describe();
		try
		{
			List<String> exec1 = new ArrayList<String>();	
			exec1.add(pipeExec1.com_path);			
			//adding the commands arguments 
			for(int i=0; i<pipeExec1.com_args.size(); i++)
				exec1.add(pipeExec1.com_args.get(i));
			
			//building the first process builder 
			System.out.println("Executing the first exec of pipe command with ID-"+pipeExec1.com_id);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(exec1);
			final Process process1 = builder.start();
			OutputStream outputstream = process1.getOutputStream();
			
			try
            {
                if(!(pipeExec1.com_in.isEmpty()||pipeExec1.com_in==null))
                {
                	String inFile =  commandList.get(pipeExec1.com_in).com_path;
                	System.out.println("I/P file: " + inFile);            	
                	FileInputStream fis = new FileInputStream(new File(inFile));        	
                	exec1.add(inFile);
        	    	
                	streamsCopy(fis, outputstream);
                }               
            }
            catch(Exception e)
            {
                throw new ProcessException("Error executing the first exec command of pipe- Unable to find Input file with ID: " + pipeExec1.com_in);
            }
				
			
			List<String> exec2 = new ArrayList<String>();		
			exec2.add(pipeExec2.com_path);
			//adding the commands arguments
			for(int i=0;i<pipeExec2.com_args.size();i++)
						exec2.add(pipeExec2.com_args.get(i));

			//building the second process builder
			System.out.println("Executing the second exec of pipe command with ID-"+pipeExec2.com_id);
			ProcessBuilder builder2 = new ProcessBuilder(exec2);			
			final Process process2 = builder2.start();			
			InputStream inputstream = process1.getInputStream();
			OutputStream outputstream2 = process2.getOutputStream();
			
			streamsCopy(inputstream, outputstream2);  // Inputstream of the first process is copied to the outputstream of the second process
			
			InputStream inputstream2 = process2.getInputStream();
			try
            {
                if(!(pipeExec2.com_out.isEmpty()||pipeExec2.com_out==null))
                {
        			File outfile = new File(commandList.get(pipeExec2.com_out).com_path);
        			System.out.println("O/P file: " + outfile);
           			FileOutputStream fos = new FileOutputStream(outfile);
           			streamsCopy(inputstream2, fos);
                }
            }
			catch(NullPointerException e)
            {
                throw new ProcessException("Error executing the second command of pipe- Unable to find Output file with ID: " + pipeExec2.com_out);
            }
			
			
		}
		catch(IOException e)
        {
			throw new ProcessException("Pipe command IOException");
        }
		System.out.println("Pipe Command has completed execution");

	}

	//method to parse the pipe command
	public void parse(Element element) throws ProcessException 
	{
		
		String id = element.getAttribute("id");
		if (id == null || id.isEmpty()) 
			throw new ProcessException("Missing ID in Pipe Command");
		
		com_id=id;
		
		NodeList nodes = element.getElementsByTagName("exec");

		Node node1 = nodes.item(0);
		if (node1.getNodeType() == Node.ELEMENT_NODE) 
		{
			Element elem = (Element) node1;
			System.out.println("The first exec of the pipe command is being parsed...");
			pipeExec1.parse(elem);
		}
		
		Node node2 = nodes.item(1);
		if (node2.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) node2;
			System.out.println("The second exec of the pipe command is being parsed...");
			pipeExec2.parse(elem);
		}
	}
	

	// This method enables the copy of contents to the output stream from the input stream via separate thread.    

	static void streamsCopy(final InputStream inputstream, final OutputStream outputstream) throws ProcessException
	{
		Runnable copyThread = (new Runnable(){
			@Override
			public void run()
			{
				try 
				{
					int achar;
					while ((achar = inputstream.read()) != -1) 
						outputstream.write(achar);
					
					outputstream.close();
				}
				catch(IOException e) 
				{
					
				}
			}
		});
		new Thread(copyThread).start();
	}

}
