package com.stingraytradingsolutions.tradesource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.text.DecimalFormat;
/**
*   This class will perform the following functions: <p>
*   1. Read a configuration file and store all of the name value pairs in the Properties class<p>
*   2. Read a comma separated text file<p>
*   3. while not eof<p>
*   	- parse the record into a string array<p>
*   	- convert the numeric fields from the string array to doubles<p>
*   4.	once 4 days of prices have been captured, pass the data to the PnLTools class to calc the PnL Tools<p>
*   5.  print out the PnLTools
*   
*/

public class ReadandParsePriceFileintoArrayList {
	public static void main(String[] args) {
		currentTime();  
		// The name of the file to open.
				String fileName = "OIL.CSV";
				String directoryName = "/Users/blynch63/Data/";
		        String fileDirName =  directoryName + fileName;
		        String csvSplitBy = ",";
		        
		        int row = 0;
		        
		        // The fields that are contained in the input price file 
		        String timePeriod = "";
		        String priceDate_T = "";
                String priceDate_T1 = "";
                String priceDate_T2 = "";
                String priceDate_T3 = "";
                double openPrice_T = 0.0;
                double openPrice_T1 = 0.0;
                double openPrice_T2 = 0.0;
                double openPrice_T3 = 0.0;
                double highPrice_T = 0.0;
                double highPrice_T1 = 0.0;
                double highPrice_T2 = 0.0;
                double highPrice_T3 = 0.0;
                double lowPrice_T = 0.0;
                double lowPrice_T1 = 0.0;
                double lowPrice_T2 = 0.0;
                double lowPrice_T3 = 0.0;
                double closePrice_T = 0.0;
                double closePrice_T1 = 0.0;
                double closePrice_T2 = 0.0;
                double closePrice_T3 = 0.0;

             
                //********************************************************************************************************************************
		        //* Call the method to read the configuration file and load the name value pairs into the Properties class.
                //********************************************************************************************************************************
                Properties p = new Properties();
                getAppConfigProperties(p);
                //System.out.println("" + p.getProperty("NumberofPricesBarsToAverage"));

                //*********************************************************************************
		        //* Create an Array List to store the price objects after the PnL Tools have been calculated
                //* Syntax:
                //*    ArrayList<Type> obj = new ArrayList<Type>()
                //*    'Type' is the PriceData object/collection
                //*    'al' is the name of the array list
                //*********************************************************************************
                //ArrayList<PriceData> al = new ArrayList<PriceData>();
                ArrayList<ZoneData> al = new ArrayList<ZoneData>();
                // Define iterator for looping through Array List
                //Iterator itr=al.iterator();
                // Loop through elements in the Array List and print each element
                //while (itr.hasNext())
                //	{
                //	row++;
                //	PriceData pd=(PriceData)itr.next();
                //	System.out.println("******************************************");
                //	System.out.println(row+";"+pd.priceDate + ";"+pd.openPrice + ";"+pd.highPrice+";"+pd.lowPrice+";"+pd.closePrice);

                
                
                //*********************************************************************************                
                //* This will loop thru the price file and perform the following functions:
                //* 1. Read one record at a time
                //* 2. While not eof
                //*		  parse the file base on the ',' delimiter
                //*		  set the price variables for the first 4 records
                //*		  create the price object for each record
                //*		  update the price objects with values for the pl tools
                //*		  update the array list with the price objects
                //*	3. Loop thru the array list and write a record for each day to compare to the P&L Pal
		        //* 
                //*********************************************************************************
		        try {
			        // FileReader reads text files in the default encoding.
		            FileReader fileReader = new FileReader(fileDirName);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = new BufferedReader(fileReader);

		            boolean eof = false;
		           
		            while (!eof)  {
		                String line = bufferedReader.readLine();
		                if (line == null) 
		                {
		                	eof = true;
		                } else 

		                	{
		                	// Parse record by defined delimiter into array of strings
		                	String[] prices = line.split(csvSplitBy);
		                	
		                	// Convert string values to double
		                	// Set time period for data being read in from file
		                	String fileDate = prices[0];
		                	double fileOpen = Double.parseDouble(prices[1]);
		                	double fileHigh = Double.parseDouble(prices[2]);
		                	double fileLow = Double.parseDouble(prices[3]);
		                	double fileClose = Double.parseDouble(prices[4]);
		                	timePeriod = "Daily";

		                	//*********************************************************************************                
		                    //* Print the date read from the file
		                    //*********************************************************************************
		             		System.out.println("Row:"+row+" Date:"+fileDate + " Open;"+fileOpen + " High;"+fileHigh+" Low;"+fileLow+" Close;"+fileClose);
		                	
		                	// Set the price values for 4 time periods (T thru T-3)
			             	if (row == 0) 
			                		{
			                		priceDate_T3 = fileDate;
			                		openPrice_T3 = fileOpen;
				                	highPrice_T3 = fileHigh;
				                	lowPrice_T3 = fileLow;
				                	closePrice_T3 = fileClose;
			                		} 
			                	else 
			                		{
			                		if (row == 1) 
			                			{
			                			priceDate_T2 = fileDate;
				                		openPrice_T2 = fileOpen;
					                	highPrice_T2 = fileHigh;
					                	lowPrice_T2 = fileLow;
					                	closePrice_T2 = fileClose;
			                			}
			                	 	else 
			                	 		{
			                	 		if (row == 2) 
			                	 			{
			                	 			priceDate_T1 = fileDate;
			                	 			openPrice_T1 = fileOpen;
			                	 			highPrice_T1 = fileHigh;
			                	 			lowPrice_T1 = fileLow;
			                	 			closePrice_T1 = fileClose;
			                	 			}
			                	 		else 
			                	 			{
			                	 			if (row == 3) 
			                	 			{
			                	 				priceDate_T = fileDate;
			                	 				openPrice_T = fileOpen;
			                	 				highPrice_T = fileHigh;
			                	 				lowPrice_T = fileLow;
			                	 				closePrice_T = fileClose;
			                	 		
			                	 				//********************************************************************************
			                	 				// Create new instance of the Price Data object and populate with record from file
			                	 				//********************************************************************************
			                	 				//PriceData PriceData_T = new PriceData(timePeriod, priceDate_T,openPrice_T,highPrice_T,lowPrice_T,closePrice_T);
			        		     		        //PriceData PriceData_T1 = new PriceData(timePeriod,priceDate_T1,openPrice_T1,highPrice_T1,lowPrice_T1,closePrice_T1);
			        		     		        //PriceData PriceData_T2 = new PriceData(timePeriod,priceDate_T2,openPrice_T2,highPrice_T2,lowPrice_T2,closePrice_T2);
			        		     		        //PriceData PriceData_T3 = new PriceData(timePeriod, priceDate_T3,openPrice_T3,highPrice_T3,lowPrice_T3,closePrice_T3);
			                	 				ZoneData PriceData_T = new ZoneData(timePeriod, priceDate_T,openPrice_T,highPrice_T,lowPrice_T,closePrice_T);
			                	 				ZoneData PriceData_T1 = new ZoneData(timePeriod,priceDate_T1,openPrice_T1,highPrice_T1,lowPrice_T1,closePrice_T1);
			                	 				ZoneData PriceData_T2 = new ZoneData(timePeriod,priceDate_T2,openPrice_T2,highPrice_T2,lowPrice_T2,closePrice_T2);
			                	 				ZoneData PriceData_T3 = new ZoneData(timePeriod, priceDate_T3,openPrice_T3,highPrice_T3,lowPrice_T3,closePrice_T3);
			        		     		        //********************************************************************************
			        		     		        // Calculate the average price
			        		     		        //********************************************************************************
			        		     		        PriceData_T.setPriceAvg(PriceData_T);
			        		     		        PriceData_T1.setPriceAvg(PriceData_T1);
			        		     		        PriceData_T2.setPriceAvg(PriceData_T2);
			        		     		        PriceData_T3.setPriceAvg(PriceData_T3);
			        		     		   
			        		     		        //********************************************************************************
			        		     		        // Calculate PL Dot for T and T-1
			        		     		        //********************************************************************************
			        		     		        PriceData_T1.setPLDot(PriceData_T1, PriceData_T2, PriceData_T3);
			        		     		        PriceData_T.setPLDot(PriceData_T, PriceData_T1, PriceData_T2);
			        		     		      
			        		     		        //********************************************************************************
			        		     		        // Calculate PL Tools for T and T-1
			        		     		        //********************************************************************************
			        		     		        PriceData_T.calcPLTools(PriceData_T, PriceData_T1, PriceData_T2, p);
			        		     		        PriceData_T1.calcPLTools(PriceData_T1, PriceData_T2, PriceData_T3, p);
			        		     		      
			        		     		        //********************************************************************************
			        		     		        // Determine if the price bar for T1 and T2 contain an isolated high or low
			        		     		        //*
			        		     		        //* Not calculating, need 4 days incase the isolated high or low is comprise of 2 days
			        		     		        //********************************************************************************
			        		     		        //PriceData_T1.setIsolatedHighandLow(PriceData_T, PriceData_T1, PriceData_T2);
			        		     		        //PriceData_T2.setIsolatedHighandLow(PriceData_T1, PriceData_T2, PriceData_T3);
			        		     		       
			        		     		        //********************************************************************************
			        		                    // Add instances T, T1, T2 and T3 of Price Data object to the array list
			        		     		        //********************************************************************************
			        		     		        al.add(PriceData_T3);
			        		     		        al.add(PriceData_T2);
			        		     		       	al.add(PriceData_T1);
			        		     		      	al.add(PriceData_T);
			                	 			}
			                	 			else
			                	 				{
			                	 				//********************************************************************************
			        		                    //* Create a new instance of Price data for T1, T2, T3, and T4 based on the values that were in
			                	 				//* the last 3 rows of array list 'al' (T, T1, T2, and T3) before setting T with the values from the file
			        		     		        //********************************************************************************
			                	 				
			                	 				//PriceData PriceData_T1 = new PriceData(al.get(row -1));
			                	 				//PriceData PriceData_T2 = new PriceData(al.get(row -2));
			                	 				//PriceData PriceData_T3 = new PriceData(al.get(row -3));
			                	 				//PriceData PriceData_T4 = new PriceData(al.get(row -4));
			                	 				ZoneData PriceData_T1 = new ZoneData(al.get(row -1));
			                	 				ZoneData PriceData_T2 = new ZoneData(al.get(row -2));
			                	 				ZoneData PriceData_T3 = new ZoneData(al.get(row -3));
			                	 				ZoneData PriceData_T4 = new ZoneData(al.get(row -4));
			                	 				
			                	 				//********************************************************************************
			        		                    //* Set price variables to values in the input file
			        		     		        //********************************************************************************
			                	 				priceDate_T = fileDate;
			                	 				openPrice_T = fileOpen;
			                	 				highPrice_T = fileHigh;
			                	 				lowPrice_T = fileLow;
			                	 				closePrice_T = fileClose;

			                	 				//********************************************************************************
			                	 				// Create new instance of the Price Data object T  and populate with record from file
			                	 				//********************************************************************************
			                	 				//PriceData PriceData_T = new PriceData(timePeriod,priceDate_T,openPrice_T,highPrice_T,lowPrice_T,closePrice_T);
			                	 				ZoneData PriceData_T = new ZoneData(timePeriod,priceDate_T,openPrice_T,highPrice_T,lowPrice_T,closePrice_T);
			        		     		        
			                	 				//********************************************************************************
			                	 				// Calculate the average price for T
			                	 				//********************************************************************************
			        		     		        PriceData_T.setPriceAvg(PriceData_T);

			                	 				//********************************************************************************
			        		     		        // Calculate PL Dot for T
			                	 				//********************************************************************************
			        		     		        PriceData_T.setPLDot(PriceData_T, PriceData_T1, PriceData_T2);
			        		     		        
			                	 				//********************************************************************************
			        		     		        // Calculate PL Tools for T
			                	 				//********************************************************************************
			        		     		        PriceData_T.calcPLTools(PriceData_T, PriceData_T1, PriceData_T2, p);
			        		     		   
			        		     		        
			        		     		        //********************************************************************************
			        		     		        // Determine if the price bar for T1 contains an isolated high or low and update the PriceData_T1 object
			        		     		        // Remove the last row in the array list that contains the T1 data
			        		     		        // Add the updated T1 object back into the array list
			        		     		        // 
			        		     		        //********************************************************************************
			        		     		        PriceData_T1.setIsolatedHighandLow(PriceData_T, PriceData_T1, PriceData_T2, PriceData_T3);
			        		     		        al.remove(row-1);
			        		     		        al.add(PriceData_T1);
			        		     		        System.out.println("Down Block Level:"+PriceData_T.downSideBlockLevel +" Date:"+PriceData_T.priceDate);
			        		     		        System.out.println("Top Block Level:"+PriceData_T.topSideBlockLevel +" Date:"+PriceData_T.priceDate);
			        		     		       
			        		     		        //********************************************************************************
			        		     		        // Determine dotted line
			        		     		        // Remove the last 4 rows in the array list that contains the T1 - T4 data
			        		     		        // Add the updated T1 - T4 objects back into the array list
			        		     		        // 
			        		     		        //********************************************************************************
			        		     		        PriceData_T.setDottedLine(PriceData_T, PriceData_T1, PriceData_T2, PriceData_T3, PriceData_T4);
			        		     		        
			        		     		        al.remove(row-1);
			        		     		        al.remove(row-2);
			        		     		        al.remove(row-3);
			        		     		        al.remove(row-4);
			        		     		        al.add(PriceData_T4);
			        		     		        al.add(PriceData_T3);
			        		     		        al.add(PriceData_T2);
			        		     		        al.add(PriceData_T1);
			        		     		        //System.out.println("Down Block Level:"+PriceData_T.downSideBlockLevel +" Date:"+PriceData_T.priceDate);
			        		     		        //System.out.println("Top Block Level:"+PriceData_T.topSideBlockLevel +" Date:"+PriceData_T.priceDate); 
			        		     		        //System.out.println(" Date: "+PriceData_T4.priceDate + "Dotted Line T4: "+PriceData_T4.dottedLine);
			        		     		        //System.out.println(" Date: "+PriceData_T3.priceDate + "Dotted Line T3: "+PriceData_T3.dottedLine);
			        		     		        //System.out.println(" Date: "+PriceData_T2.priceDate + "Dotted Line T2: "+PriceData_T2.dottedLine);
			        		     		        //System.out.println(" Date: "+PriceData_T1.priceDate + "Dotted Line T1: "+PriceData_T1.dottedLine);
			        		     		     
			        		     		        
			        		     		        //********************************************************************************
			        		     		        // Identify which of the 7 types of trading for the current price bar
			        		     		        //
			        		     		        //
			        		     		        // 
			        		     		        //********************************************************************************
			        		     		        PriceData_T.setTypeOfTrading(PriceData_T, PriceData_T1, PriceData_T2, PriceData_T3, PriceData_T4);
			        		     		        
			        		     		        //********************************************************************************
			        		     		        // Identify Zones for the current Price Bar
			        		     		        //
			        		     		        //
			        		     		        // 
			        		     		        //********************************************************************************
			        		     		        //PriceData_T.setZoneRange(PriceData_T); 
			        		     		        //PriceData_T.setAverageRangeandZoneStatistics(al, p);
			        		     		        PriceData_T.setZoneData(PriceData_T, al, p); 
			        		     		        //********************************************************************************
			        		                    // Add instances T of Price Data object to the array list
			        		     		        //********************************************************************************
			        		     		        al.add(PriceData_T);		        		     		    
			                	 				}
			                	 			}
			                	 		}
			                		}
		                }
		                row++;
		                }		            	
		            	bufferedReader.close();
		                
		            	//********************************************************************************
		                // Write a file
		     		    //********************************************************************************
		            	DecimalFormat dec = new DecimalFormat("#0.00000"); 
		            	int size=al.size();
		            	FileWriter f0 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TRIUP.CSV");
		            	FileWriter f1 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TRIDN.CSV");
		            	FileWriter f2 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TRAUP.CSV");
		            	FileWriter f3 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TRADN.CSV");
		            	FileWriter f4 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TRSUP.CSV");
		            	FileWriter f5 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TRSDN.CSV");
		            	FileWriter f6 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CACTDN.CSV");
		            	FileWriter f7 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CACTUP.CSV");
		            	FileWriter f8 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CACT2DN.CSV");
		            	FileWriter f9 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CACT2UP.CSV");
		            	FileWriter f10 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CENTDN.CSV");
		            	FileWriter f11 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CENTUP.CSV");
		            	FileWriter f12 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CENT2DN.CSV");
		            	FileWriter f13 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CENT2UP.CSV");
		            	FileWriter f14 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_TYPESOFTRADING.CSV");
		            	FileWriter f15 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CEXITDN.CSV");
		            	FileWriter f16 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CEXITUP.CSV");
		            	FileWriter f17 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CEXIT2DN.CSV");
		            	FileWriter f18 = new FileWriter("/Users/blynch63/Data/STS_OILDAILY_CEXIT2UP.CSV");
		            	


		     		    String newLine = System.getProperty("line.separator");

		     		    f14.write("Date" + "," + "Open" + "," + "High"  + "," + "Low" + "," + "Close" + "," + "PL Dot"  + "," + "Red Bird" + "," + "MCL" + "," + "Type of Trading" + "," + "Direction" + newLine);
		     		    
		     		    for(row=0;row<size;row++)
		     		       {
		     		    	//Get a row from the array list
		     		    	ZoneData PriceData_T1 = new ZoneData(al.get(row));   

		     		    	//Format date to match PLPal format of MM/DD/YYY
		     		    	String year1 = PriceData_T1.priceDate.substring(0,4); 
		     		    	String month1 = PriceData_T1.priceDate.substring(4,6); 
		     		    	String day1 = PriceData_T1.priceDate.substring(6,8); 
		     		    	String priceDatePLPal = (month1 + "/" + day1 + "/" + year1 + ":24:00");
		     		    	//System.out.println(" Trade Date: " + PriceData_T1.priceDate);
		     		    	//System.out.println("plPal Format: " + priceDatePLPal+ PriceData_T1.typeOfTrading);
		     		    	
		     		    	//Write file with select fields
		     		    	if ((PriceData_T1.typeOfTrading == "TRI") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    		//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + dec.format(PriceData_T1.zoneALow)  + "," + dec.format(PriceData_T1.zoneAHigh) +  newLine);
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.$6_1_Up) + "," + dec.format(PriceData_T1.$6_1_Down) + "," + dec.format(PriceData_T1.$6_5_Up) + "," + dec.format(PriceData_T1.$6_5_Down) + newLine);
		     		    		//f0.write(priceDatePLPal + ","  + dec.format(PriceData_T1.$6_7_Up) + "," + dec.format(PriceData_T1.$6_7_Down) +  newLine);	
		     		    		//f0.write(priceDatePLPal + ","  + dec.format(PriceData_T1.$6_6_Up) + "," + dec.format(PriceData_T1.$6_6_Down) + newLine);
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.$5_9_Up)  + "," + dec.format(PriceData_T1.$5_9_Down) + "," + dec.format(PriceData_T1.$5_9_Up_Ext)  + "," + dec.format(PriceData_T1.$5_9_Down_Ext) +  "," + dec.format(PriceData_T1.$5_9_Up_Closed) + "," + dec.format(PriceData_T1.$5_9_Down_Closed) + newLine);	
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.$5_3_Up) + "," + dec.format(PriceData_T1.$5_3_Down) + "," + dec.format(PriceData_T1.$5_3_Up_Closed) + "," + dec.format(PriceData_T1.$5_3_Down_Closed) + newLine);
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.$5_2_Up) + "," + dec.format(PriceData_T1.$5_2_Down) + "," + dec.format(PriceData_T1.$5_2_Up_Ext) + "," + dec.format(PriceData_T1.$5_2_Down_Ext) + newLine);
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.$5_1_Up) + "," + dec.format(PriceData_T1.$5_1_Down) + "," + dec.format(PriceData_T1.$5_1_Up_Closed) + "," + dec.format(PriceData_T1.$5_1_Down_Closed) + newLine);	
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.$1_1_Dot) + "," + dec.format(PriceData_T1.$1_1_Low) + "," + dec.format(PriceData_T1.$1_1_High) + newLine);	
		     		    		//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.plDot)  + "," + dec.format(PriceData_T1.redBirdDot) + "," + dec.format(PriceData_T1.mcLine) + newLine);
		     		    		//f0.write(priceDatePLPal + "," + PriceData_T1.openPrice + "," + PriceData_T1.highPrice  + "," + PriceData_T1.lowPrice + "," + PriceData_T1.closePrice + newLine);
		     		    		//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + dec.format(PriceData_T1.zoneBLow)  + "," + dec.format(PriceData_T1.zoneBHigh) +  newLine);
		     		    		//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + dec.format(PriceData_T1.zoneCLow)  + "," + dec.format(PriceData_T1.zoneCHigh) +  newLine);
		     		    		//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + dec.format(PriceData_T1.zoneDLow)  + "," + dec.format(PriceData_T1.zoneDHigh) +  newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "TRI") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f1.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "TRA") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f2.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "TRA") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f3.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "TRS") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f4.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "TRS") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f5.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CAct") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f6.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CAct") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f7.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CAct2") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f8.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CAct2") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f9.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CEnt") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f10.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CEnt") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f11.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CEnt2") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f12.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CEnt2") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f13.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	f14.write(priceDatePLPal + "," + dec.format(PriceData_T1.openPrice) + "," + dec.format(PriceData_T1.highPrice)  + "," + dec.format(PriceData_T1.lowPrice) + "," + dec.format(PriceData_T1.closePrice) + "," + dec.format(PriceData_T1.plDot)  + "," + dec.format(PriceData_T1.redBirdDot) + "," + dec.format(PriceData_T1.mcLine) + "," + PriceData_T1.typeOfTrading + "," + PriceData_T1.typeOfTradingDirection + newLine);
		    		    	
		    		    	if ((PriceData_T1.typeOfTrading == "CExit") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f15.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CExit") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f16.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CExit2") && (PriceData_T1.typeOfTradingDirection == "Down"))
		     		    	{
		     		    		f17.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	if ((PriceData_T1.typeOfTrading == "CExit2") && (PriceData_T1.typeOfTradingDirection == "Up"))
		     		    	{
		     		    		f18.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	}
		    		    	//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + PriceData_T1.zoneBLow  + "," + PriceData_T1.zoneBHigh +  newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + PriceData_T1.zoneCLow  + "," + PriceData_T1.zoneCHigh +  newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + "," + PriceData_T1.zoneDLow  + "," + PriceData_T1.zoneDHigh +  newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.openPrice + "," + PriceData_T1.highPrice  + "," + PriceData_T1.lowPrice + "," + PriceData_T1.closePrice + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.typeOfTrading + " " + PriceData_T1.typeOfTradingDirection + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.plDot  + "," + PriceData_T1.redBirdDot + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$1_1_Dot + "," + PriceData_T1.$1_1_High + "," + PriceData_T1.$1_1_Low + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_1_Up + "," + PriceData_T1.$5_1_Down + "," + PriceData_T1.$5_1_Up_Closed + "," + PriceData_T1.$5_1_Down_Closed + newLine);
		      		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_2_Up + "," + PriceData_T1.$5_2_Down + "," + PriceData_T1.$5_2_Up_Ext + "," + PriceData_T1.$5_2_Down_Ext + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_3_Up + "," + PriceData_T1.$5_3_Down + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$6_7_Up + "," + PriceData_T1.$6_7_Down + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_3_Up_Closed + "," + PriceData_T1.$5_3_Down_Closed + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_9_Up  + "," + PriceData_T1.$5_9_Down + "," + PriceData_T1.$5_9_Up_Ext  + "," + PriceData_T1.$5_9_Down_Ext + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_9_Up_Closed + "," + PriceData_T1.$5_9_Down_Closed + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.$5_1_Up + "," + PriceData_T1.$5_1_Down  + "," + PriceData_T1.$5_3_Up + "," + PriceData_T1.$5_3_Down + "," + PriceData_T1.$5_9_Up  + "," + PriceData_T1.$5_9_Down + "," + PriceData_T1.$5_2_Up + "," + PriceData_T1.$5_2_Down + "," + PriceData_T1.$5_2_Up_Ext + "," + PriceData_T1.$5_2_Down_Ext + newLine);
		     		    	//f0.write(priceDatePLPal + "," + PriceData_T1.mcLine + newLine);
		     		    	//f0.write(priceDatePLPal + "," + dec.format(PriceData_T1.openPrice) + "," + dec.format(PriceData_T1.highPrice)  + "," + dec.format(PriceData_T1.lowPrice) + "," + dec.format(PriceData_T1.closePrice) + "," + PriceData_T1.plDot  + "," + PriceData_T1.mcLine + "," + PriceData_T1.redBirdDot + "," + PriceData_T1.$1_1_Dot + "," + PriceData_T1.$1_1_Low + "," + PriceData_T1.$1_1_High + newLine);

		     		       }
		     		       f0.close();
		     		       f1.close();
		     		       f2.close();
		     		       f3.close();
		     		       f4.close();
		     		       f5.close();
		     		       f6.close();
		     		       f7.close();
		     		       f8.close();
		     		       f9.close();
		     		       f10.close();
		     		       f11.close();
		     		   	   f12.close();
		     		   	   f13.close();
		     		   	   f14.close();
		     		   	   f15.close();
		     		   	   f16.close();
		     		   	   f17.close();
		     		   	   f18.close();
		     		     
		     		    //********* end of file write  *****************
		   				currentTime();  
		        }
		        catch(FileNotFoundException ex) 
		        	{
		            System.out.println(
		                "Unable to find file '" + 
		                fileDirName + "'");
	            		ex.printStackTrace();
		        	}
		        catch(IOException ex) 
		        	{
		            System.out.println(
		                "Error reading file '" 
		                 + fileDirName + "'");                  
		            	ex.printStackTrace();
		        	}
		        //catch (ParseException e) {
				// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
			}
    //**********************************************************************************************************************************                
    //*                                                                                                                                *
	//* This section contains the methods that will be called from within this class                                                     *
    //*                                                                                                                                *
    //**********************************************************************************************************************************
	public static void currentTime () 
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println( sdf.format(cal.getTime()) );
	}


    /**                
    * This method will loop thru the configuration file referencing one record at a time
    * and store the name value pairs in the Properties class
    */
	
	public static Properties getAppConfigProperties(Properties prop)  /*Valid signature to return a object */
	{

	// The name and path of the configuration file to open.
	String configFileName = "StingrayConfig.txt";
	String configDirectoryName = "/Users/blynch63/Data/";
    String configFileDirName =  configDirectoryName + configFileName;
    String configCSVSplitBy = ":";
    
    //*********************************************************************************                
    //* This will loop thru the configuration file reading one record at a time
    //*********************************************************************************
    try {
        // FileReader reads text files in the default encoding.
        FileReader configFileReader = new FileReader(configFileDirName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader configBufferedReader = new BufferedReader(configFileReader);

        boolean eof = false;
                
        while (!eof)  
        	{
            String line = configBufferedReader.readLine();
            if (line == null) 
            	{
            	eof = true;
            	} 
            else 
            	{
            	// Skip the comment records
            	// Parse record by defined delimiter into array of name value pairs
            	// Add name value pair to Properties class
	            if (!line.startsWith("*"))
	            	{
	                String[] nameValuePair = line.split(configCSVSplitBy);
                	String name = nameValuePair[0];
                	String value = nameValuePair[1];
                	prop.put(name,value);
                	//prop.put(nameValuePair[0],nameValuePair[1]);
	            	}
    	        }
        	}	
            configBufferedReader.close();
    	}           
        catch(FileNotFoundException ex)
			{
            System.out.println(
                "Unable to find file '" + 
                configFileDirName + "'");
            	ex.printStackTrace();
			}
        catch(IOException ex) 
			{
        	System.out.println(
        		"Error reading file '" 
        		+ configFileDirName + "'");                  
        		ex.printStackTrace();

		     } 
    	//catch (ParseException e) {
		// TODO Auto-generated catch block
		//	e.printStackTrace();
    	//	}
    return prop;
	}
}
