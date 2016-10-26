package test;

public class ProcessException extends java.lang.Exception
{
	ProcessException(String message)
	{
		super("EXCEPTION MESSAGE TO USER: " +message);
	}	
}
