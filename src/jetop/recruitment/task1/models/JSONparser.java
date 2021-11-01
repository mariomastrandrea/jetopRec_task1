package jetop.recruitment.task1.models;

import java.io.BufferedReader;
import java.io.IOException;

import jetop.recruitment.task1.datatypes.JSONarray;
import jetop.recruitment.task1.datatypes.JSONbool;
import jetop.recruitment.task1.datatypes.JSONcomponent;
import jetop.recruitment.task1.datatypes.JSONnull;
import jetop.recruitment.task1.datatypes.JSONnumber;
import jetop.recruitment.task1.datatypes.JSONobject;
import jetop.recruitment.task1.datatypes.JSONstring;

public class JSONparser
{
	public JSONparser() { }
	
	/**
	 * It parses a generic JSON object from an input buffered reader, using a recursive algorithm
	 * @param currentLine
	 * @param br buffered reader linked to the input JSON text
	 * @return the corresponding JSON object
	 * @throws IOException
	 */
	public JSONcomponent parse(String currentLine, BufferedReader br) throws IOException 
	{		
		if(currentLine == null) {
			currentLine = br.readLine();
			if(currentLine == null) return null;
		}
		
		while(currentLine.isBlank()) {
			currentLine = br.readLine();
			if(currentLine == null) return null;
		}
		
		currentLine = currentLine.trim();		
		char firstChar = currentLine.charAt(0);
		
		if(firstChar == '{')  	// there is a JSON object
			return this.parseObject(br);
		
		if(firstChar == '[') 	// there is a JSON array
			return this.parseArray(br);
		
		if(firstChar == '"') {	// there is a string
			
			String stringValue;
			int length = currentLine.length();
			
			if(currentLine.charAt(length - 1) == ',') {
				stringValue = currentLine.substring(1, length - 2);
			}
			else {
				stringValue = currentLine.substring(1, length - 1);
			}
				
			return new JSONstring(stringValue);
		}
		
		if(currentLine.length() >= 4 && 
				currentLine.substring(0, 4).equals("null"))	// there is a null value
			return new JSONnull();
		
		if(currentLine.length() >= 4 && 
				currentLine.toLowerCase().substring(0,4).equals("true")) // there is a 'true' boolean value
			return new JSONbool(true);
		
		if(currentLine.length() >= 4 && 
				currentLine.toLowerCase().substring(0,4).equals("false")) // there is a 'false' boolean value
			return new JSONbool(false);
		
		// it may be a number
			
		String line = currentLine;
			
		if(line.charAt(line.length() - 1) == ',')		//removing trailing comma
			line = line.substring(0, line.length() - 1);
			
		try
		{
			double number = Double.parseDouble(line);
			return new JSONnumber(number);
		}
		catch(NumberFormatException nfe)
		{
			System.out.println("Number format exception with line: \"" + currentLine + "\"");	//for debugging
		}
		
		return null;
	}

	/**
	 * It parses a JSON array of generic objects, delimited by '[' and ']'
	 * @param br buffered reader linked to the input JSON text
	 * @return the JSON array itself
	 * @throws IOException
	 */
	private JSONarray parseArray(BufferedReader br) throws IOException
	{
		String currentLine = br.readLine();
		while(currentLine != null && currentLine.isBlank())
			currentLine = br.readLine();
		
		JSONarray array = new JSONarray();
		
		while(currentLine != null && !currentLine.trim().startsWith("]")) {
					
			JSONcomponent nextComponent = this.parse(currentLine, br);
			array.add(nextComponent);
			
			currentLine = br.readLine();
			
			while(currentLine != null && currentLine.isBlank())
				currentLine = br.readLine();
		}
			
		return array;
	}


	/**
	 * It parses a JSON object (delimited by '{' and '}')
	 * @param br buffered reader linked to the input JSON text
	 * @return the JSON object itself
	 * @throws IOException
	 */
	private JSONobject parseObject(BufferedReader br) throws IOException
	{
		String currentLine = br.readLine();
		while(currentLine != null && currentLine.isBlank())
			currentLine = br.readLine();
		
		JSONobject object = new JSONobject();
		
		while(currentLine != null && !currentLine.trim().startsWith("}")) {
			
			String fields[] = currentLine.split(":", 2);
			String key = fields[0].trim();
			key = key.substring(1, key.length() - 1);
			
			JSONcomponent value = this.parse(fields[1], br);
			object.addProperty(key, value);
			
			currentLine = br.readLine();
			
			while(currentLine != null && currentLine.isBlank())
				currentLine = br.readLine();
		}
		
		return object;
	}
}
