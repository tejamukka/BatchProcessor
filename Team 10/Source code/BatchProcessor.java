package test;

	import java.io.*;
	import java.util.Map;

	import test.Batch;
	import test.Command;

	public class BatchProcessor 
	{
		public static void main (String args[]) throws ProcessException
		{
		 //	String fileName = args[0];
		 	
		 	System.out.println("Enter the xml file name:");
		 	java.util.Scanner sc=new java.util.Scanner(System.in);
		 	String fileName=sc.nextLine();
			sc.close();	
		
			File f = new File(fileName);
			System.out.println("Absolute path:"+f.getAbsolutePath());
			
			BatchParser xp=new BatchParser();
			//Passes the file and returns the entire Batch
			Batch b=xp.buildBatch(f);
			if(b.getStatus())
				runBatch(b);
			System.out.println("COMPLETED");
		}			
			
		//Start running the batch
		public static void runBatch(Batch batch) throws ProcessException
		{
			Map<String, Command> commandList;
			//Gets the entire list of commands of the batch
			commandList=batch.getCommands();
	        for(String key : commandList.keySet())
	        {
	        	System.out.println("The id of the current tag being executed : " + key.toString());
	        	//Executes each command corresponding to a key 
	            commandList.get(key).execute(commandList);
	        }
		}
	}