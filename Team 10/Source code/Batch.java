	package test;
	import java.util.Map;
	import java.util.LinkedHashMap;


	public class Batch 
	{
		private boolean parse_status=false;
		
		//commands stores a list of all commands to be executed
		private Map<String,Command> commands;

		public Batch()
		{
	 		commands = new LinkedHashMap<String,Command>();
		}	
		
		//method to add command to the command list of the batch
		public void addCommand(Command command)
		{
			commands.put(command.getId(), command);
		}
		
		//gets all the commands from the batch
		public Map<String,Command> getCommands()
		{
			return commands;
		}	
		
		public boolean getStatus()
		{
			return parse_status;
		}
		public void setStatus(boolean parse_status)
		{
			this.parse_status=parse_status;
		}
	}
