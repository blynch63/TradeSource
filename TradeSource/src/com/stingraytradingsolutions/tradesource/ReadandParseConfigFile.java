package com.stingraytradingsolutions.tradesource;
import java.io.*;
import java.util.*;
//import Prices.*; 		 //custom package
//****************************************************************************************************
// This class will perform the following functions:
//	read a pipe separated text file
//	while not eof
//		parse the record into a string array
//		convert the numeric fields from the string array to doubles
//
//****************************************************************************************************

public class ReadandParseConfigFile
{
	public String inputDirectory;
	public String outputDirectory;
	public double numberofPricesBarsToAverage;
	public double minimumZoneSizePercentage;
	public double oversizedZonePercentage;
	public double toolNearClosePercentage;
	public double toolNearZonePercentage;
	public double oversizedZoneReductionPercentage;
	public double expandedZonePercentage;

public void setConfigVariables ()
{
	// New instance of the Properties class
	Properties prop = new Properties();
	
	// The name of the file to open.
	String fileName = "StringRayConfig.txt";
	String directoryName = "/Users/blynch63/Data/";
    String fileDirName =  directoryName + fileName;
    String csvSplitBy = ":";

    
    //*********************************************************************************                
    //* This will loop thru the file referencing one record at a time
    //* String line = null;
    //*********************************************************************************
    try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(fileDirName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        boolean eof = false;
       
        while (!eof)  
        	{
            String line = bufferedReader.readLine();
            if (line == null) 
            	{
            	eof = true;
            	} 
            else 
            	{
            	// Parse record by defined delimiter into array of strings
	            if (!line.startsWith("*"))
	            	{
	                String[] nameValuePair = line.split(csvSplitBy);
      		        prop.put(nameValuePair[0],nameValuePair[1]);
	            	}
    	        }
            bufferedReader.close();
        	}	
    	}           
	                
		        catch(FileNotFoundException ex)
    				{
		            System.out.println(
		                "Unable to find file '" + 
		                fileDirName + "'");                
    				}
		        catch(IOException ex) 
    				{
		            System.out.println(
		                "Error reading file '" 
		                 + fileDirName + "'");                  
		            // Or we could just do this: 
		            // ex.printStackTrace();

		        //} catch (ParseException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
    				}
}
}