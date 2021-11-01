package jetop.recruitment.task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jetop.recruitment.task1.datatypes.JSONcomponent;
import jetop.recruitment.task1.models.JSONparser;

public class Program
{
	public static void main(String[] args) throws IOException
	{
		// 0. create JSON objects collection and JSON parser
		
		Map<String,JSONcomponent> globalJSONobjects = new HashMap<>();
		JSONparser jsonParser = new JSONparser();
		
		// 1. parse JSON files
		
		String inputDirectoryPath = "./inputFiles_task1";
		File directory = new File(inputDirectoryPath);
		
		for(File inputFile : directory.listFiles()) {
			
			//create one JSON object for each input file
			String fileName = inputFile.getName().split("\\.")[0]; //removing file extension
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String firstLine = br.readLine();
			
			JSONcomponent inputJSON = jsonParser.parse(firstLine, br);	//launch recursive algorithm (!)
			globalJSONobjects.put(fileName, inputJSON);
			
			br.close();
		}
		
		// 2. clean JSON objects (removing 'edges' and 'node' properties (and 'data'))
		//  -> I'm going to remove 'data' property too, as it is showed in the output files) 
		
		String propertiesToRemove[] = new String[]{"edges", "node", "data"};
		
		for(String jsonName : globalJSONobjects.keySet()) {
			
			JSONcomponent jsonObject = globalJSONobjects.get(jsonName);
			
			//removing properties from the current JSON object
			jsonObject = jsonObject.removeProperties(propertiesToRemove);
			
			//substitute the new (cleaned) JSON object with the previous one in the Map
			globalJSONobjects.put(jsonName, jsonObject);
		}
		
		// 3. print output (cleaned JSON objects)
		
		for(var jsonObjectPair : globalJSONobjects.entrySet()) {
			String outputFileName = jsonObjectPair.getKey() + "_parsed.txt";
			
			FileWriter fw = new FileWriter("./outputFiles_task1/" + outputFileName);
			JSONcomponent jsonObject = jsonObjectPair.getValue();
			
			int startingIndentation = 0;		// 0 -> starting indentation
			fw.write(jsonObject.print(startingIndentation));	
			
			fw.flush();
			fw.close();
		}
	}

}
