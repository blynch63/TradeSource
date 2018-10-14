package com.stingraytradingsolutions.tradesource;

import java.util.*;

public class ZoneData extends PriceData{
	//***********************************************************************************************************************************************
	// A class that extends another does not inherit the constructors from the super class
	// therefore the subclass has to call a constructor in the super class
	// The following fields are added to the PriceData class.  Unfortunately they reset to the default values when the zonedata class is called and 
	//   the data from the array list.  In order to keep values set in this class they have to be defined in the PriceData class and just updated in this class.
	//   See the zoneDpurpose variable.  It is defined in the PriceData class and just updated in this class.  The value is retained in the array list.
	//***********************************************************************************************************************************************
	//public String newField = "This is a new field added to the class";
	//public String newFieldEmptyConstructor = "This is a new field added to the class from Empty Constructor";
	//public String newFieldPriceFileConstructor = "This is a new field added to the class from Price File Constructor";
	//public String newFieldClassConstructor = "This is a new field added to the class from Class Constructor";
	
	//***********************************************************************************************************************************************
	//Call to the constructor in the super class to create an instance of the class when no values are passed when creating the new object
	//***********************************************************************************************************************************************
	public ZoneData(){
		super();
		//this.newField = newFieldEmptyConstructor;
	}
	
	//***************************************************************************************************************************************************** 
	//Call to the constructor in the super class to create an instance of the class when the values from the price file are passed to create the new object
	//*****************************************************************************************************************************************************
	public ZoneData(String timePeriod, String priceDate, double openPrice, double highPrice, double lowPrice, double closePrice){
		super( timePeriod,  priceDate,  openPrice,  highPrice,  lowPrice,  closePrice);
		//this.newField = newFieldPriceFileConstructor;
	}
	
	//***********************************************************************************************************************************************
	//Call to the constructor in the super class to create an instance of the class when an instance of the class is passed to create the new object
	//***********************************************************************************************************************************************
	public ZoneData(PriceData T){
		super(T);
		//this.newField = newFieldClassConstructor;
	}
	/**
	*  This method will calculate the Average Range and Zone Statistics found in Appendix G.6, for each market and time period (Monthly, Weekly, Daily).
	*  The statistics are calculated based the minimum number of price bars to use as defined in the configuration file.  These statistics include: Average Range,
	*  Size of a Single Tool Zone, Size of an Oversized Zone, and the Maximum Focus Risk.
	*/
	public ZoneStatistics setAverageRangeandZoneStatistics(ArrayList <ZoneData> alPriceData, Properties p, ZoneStatistics zs)
	{

		//* get the zone size variables from the configuration file via the Properties class
		double minimumZoneSizePercent = Double.parseDouble(p.getProperty("MinimumZoneSizePercentage"))/100;
		double oversizedZonePercent = Double.parseDouble(p.getProperty("OversizedZonePercentage"))/100;
		double toolNearClosePercentage = Double.parseDouble(p.getProperty("ToolNearClosePercentage"))/100;
		double toolNearZonePercentage = Double.parseDouble(p.getProperty("ToolNearZonePercentage"))/100;
		double oversizedZoneReductionPercentage = Double.parseDouble(p.getProperty("OversizedZoneReductionPercentage"))/100;
		double expandedZonePercentage = Double.parseDouble(p.getProperty("ExpandedZonePercentage"))/100;
		
		double averageRange = 0;
		double sumOfPriceBarRange = 0;
		int row = Integer.parseInt(p.getProperty("NumberofPricesBarsToAverage"));
		double numberOfPricesBarsToAverage = row;
		int size=alPriceData.size();    
		if (size < row);		//* set row to 0 if the number of rows in the arraylist is less than the minimum price bars to average 
		{
			row = size;
		}
		//* Loop through the array list of prices to use the last NumberofPricesBarsToAverage (as defined in the config file)
		//* to determine the average range of the price bar.  This will be used to calculate the zone sizes
		for(row = size - row;row<size;row++)
		{
			ZoneData PriceDataValues = new ZoneData(alPriceData.get(row));
			sumOfPriceBarRange = sumOfPriceBarRange + PriceDataValues.priceBarRange;
		}
		if (numberOfPricesBarsToAverage > size);
		{	
			numberOfPricesBarsToAverage = size;
		}
		averageRange = (sumOfPriceBarRange/numberOfPricesBarsToAverage);
		double minimumZoneSize = averageRange * minimumZoneSizePercent;
		double sizeOfSingleToolZone = minimumZoneSize * 2;
		double sizeOfOversizedZone = averageRange * oversizedZonePercent;
		
		//* Set values in the zone statistics class
		zs.setTicker ("TBD");
		zs.setTimePeriod ("TBD");
		zs.setAverageRange (averageRange);
		zs.setMinimumZoneSize (minimumZoneSize);
		zs.setSizeOfSingleToolZone (sizeOfSingleToolZone);
		zs.setSizeOfOversizedZone (sizeOfOversizedZone);
		
		return zs;
	}

	
	/**
	 *  This method will apply the zone tool rule 1- 6 against the tools that are passed in on the array list
	 *  
	 *  Input:
	 *    alZoneRules - Array list of the ZoneTools class the identifies the each tool that can be used to create a zone, the value of the tool, the tool name, and an flag to indicate if the tool is used 
	 *    PriceData - The PriceData class for the current time period being processed
	 *    ZoneStatistics - The ZoneStatistics class that defines the zone attributes defined in the configuration file
	 *    alValues - An array list the contains just the value of each tool in the ZoneTools class
	 *    zoneName - The name of the zone being processed (A, B, C, or D)
	 *  
	 *  Output:
	 *    Update alZoneRules array list, identifying the tools that were used to create the zone and any updates to the tool values as a result of applying the Zone Rules
	 *    Update alValues array list, updates to the tool values as a result of applying the Zone Rules (matching the results in the alZoneRules array list)
	 */
	//*public void applyZoneRules(ArrayList <ZoneTools> alZoneRules, PriceData T, Properties p, ZoneStatistics zs, ArrayList <Double> alValues, String zoneName)
	public void applyZoneRules(ArrayList <ZoneTools> alZoneRules, PriceData T, Properties p, ZoneStatistics zs, String zoneName)
	{

		//System.out.println("" + p.getProperty("NumberofPricesBarsToAverage"));
		//***************************************************************************************************************************************
		//* Define common variables that will be used in the Rule evaluation
		//*
		//***************************************************************************************************************************************		
		int row = 0;
		int size=alZoneRules.size();  
		int toolsUsedinZone = size;


		//***************************************************************************************************************************************
		//*  Apply Zone Rule 1:
		//*
		//*	 1.	Negate all tools that have a null (NaN) or 0 value
		//*
		//*  Logic:
		//*  	Loop through the array list that contains the tools for the given zone 
		//*		Determine if the tool value is NaN
		//*		Update the tool used flag
		//*		Update the tool in the array list
		//*		Update the counter that indicates how many tools are used in this zone
		//*
		//***************************************************************************************************************************************
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
			if ((Double.isNaN(ZoneRulesTools.zoneToolValue) == true) || ((ZoneRulesTools.zoneToolValue) == 0)) 
			{
				ZoneRulesTools.zoneToolUsed = false;
				alZoneRules.set(row, ZoneRulesTools);
				toolsUsedinZone--;	
			}
		}
		setCurrentZoneBoundaries(alZoneRules, T);
		
		
		//***************************************************************************************************************************************
		//*  Apply Zone Rule 2:
		//*
		//*	 	1.  Determine if all of the tools in the zone are near the close.  If they are, do not negate any of the tools.
		//*		2.	Negate all tools that form 'near' the close of the current price bar
		//*
		//*  Logic:
		//*  	Loop through the array list that contains the tools used for the given zone 
		//*		Count the number of the tools that are 'near' the close		
		//*  	If ALL of the tools in the zone are NOT near to the close
		//*			Loop through the array list that contains the tools used for the given zone 
		//*			Determine if the tool is 'near' the close
		//*			Update the tool used flag
		//*			Update the tool in the array list
		//*			Update the counter that indicates how many tools are used in this zone
		//*
		//*  Near  = within 10% +/- of the close
		//***************************************************************************************************************************************
    	int nbrZoneToolsNearClose = 0;
    	int nbrZoneToolsUsed = 0;
		
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
			if (ZoneRulesTools.zoneToolUsed == true)
			{
				nbrZoneToolsUsed++;
				if ((ZoneRulesTools.zoneToolValue > T.priceBarNearCloseLow) && (ZoneRulesTools.zoneToolValue < T.priceBarNearCloseHigh))
				{	
					nbrZoneToolsNearClose++;
				}
			}
		}
		
		if (nbrZoneToolsNearClose != nbrZoneToolsUsed)
		{
			for(row=0;row<size;row++)
				{
					ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
					if (ZoneRulesTools.zoneToolUsed == true)
					{
						if ((ZoneRulesTools.zoneToolValue > T.priceBarNearCloseLow) && (ZoneRulesTools.zoneToolValue < T.priceBarNearCloseHigh))
						{
							ZoneRulesTools.zoneToolUsed = false;
							alZoneRules.set(row, ZoneRulesTools);
							toolsUsedinZone--;	
						}
					}
				}
		}
		setCurrentZoneBoundaries(alZoneRules, T);
		
		//***************************************************************************************************************************************
		//*  Apply Zone Rule 3
		//*
		//*  If the zone only contains only a single tool, expand the tool on both sides to create a zone 
		//*  that has the minimum width
		//*
		//*	 Logic:
		//*		Determine if only one tool was used to create the zone
		//*		Loop through the array list that contains the tools for the given zone to find the tool that is used
		//*			Expand the tool to define the zone high
		//*			Expand the tool to define the zone low
		//*			Update the tool in the array list with the new zone high
		//*			Add a new row to the tool array list with the zone low
		//*			Add a new row to the values array list with the zone low
		//*		Loop through the zone tool array list to find the values used to create the zone and add the value to the Values array list
		//***************************************************************************************************************************************
		if (toolsUsedinZone == 1)
		{
			for(row=0;row<size;row++)
			{
				ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
				if (ZoneRulesTools.zoneToolUsed == true)
				{

					//* Expand a single tool zone
					SingleToolZone SingleToolZone = new SingleToolZone();
					SingleToolZone.setOriginalToolValue (ZoneRulesTools.zoneToolValue);
					SingleToolZone.setExpandedZoneValues(zs);
					
					//* Update the tools and values array lists with the expanded zone values (tools and values
					String name = ZoneRulesTools.zoneToolLabel;
					ZoneRulesTools.setZoneToolValue(SingleToolZone.getExpandedHighValue());						//* update expanded tool value(high) in object
					alZoneRules.set(row, ZoneRulesTools);														//* update expanded tool value(high) in tool array list
					//*alValues.set(row, SingleToolZone.getExpandedHighValue());									//* update expanded tool value(high) in values array list
					ZoneTools ZoneRulesTools2 = new ZoneTools(name, SingleToolZone.getExpandedLowValue(), true);//* create new object for expanded tool value(low)
					alZoneRules.add(ZoneRulesTools2);															//* create new entry for expanded tool value(low) in tool array list			
					//*alValues.add(SingleToolZone.getExpandedLowValue());											//* create new entry for expanded tool value(low) in values array list
					size ++;
					break;			
				}
			}
			setCurrentZoneBoundaries(alZoneRules, T);
		}
		//***************************************************************************************************************************************
		//*  Apply Zone Rule 4
		//*
		//* If 2 or more tools make up the zone and the zone width is less than the minimum size for the time period, expand the tools on both
		//* sides to create the minimum sized zone
		//***************************************************************************************************************************************
		if (toolsUsedinZone > 1)
		{		
			if (T.zoneWidth < zs.minimumZoneSize)
			{	
				double zoneVariance = (zs.minimumZoneSize - T.zoneWidth)/2;
				for(row=0;row<size;row++)
				{
					ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
					if (ZoneRulesTools.zoneToolUsed == true)
					{
						if (ZoneRulesTools.zoneToolValue == T.zoneHigh)
						{
							double highToolValue = ZoneRulesTools.zoneToolValue + zoneVariance;
							ZoneRulesTools.setZoneToolValue(highToolValue);
							alZoneRules.set(row, ZoneRulesTools);
						}
						else
						{
							if (ZoneRulesTools.zoneToolValue == T.zoneLow)
							{
								double lowToolValue = ZoneRulesTools.zoneToolValue - zoneVariance;
								ZoneRulesTools.setZoneToolValue(lowToolValue);
								alZoneRules.set(row, ZoneRulesTools);
							}
						}
					}
				}
			}
			setCurrentZoneBoundaries(alZoneRules, T);
		}

		//***************************************************************************************************************************************
		//*  Apply Zone Rule 5
		//*
		//*  For A and D zones determine if the zone is over sized and constructed from just two tools
		//*  negate the tool furthest away from prices and expand the remaining tool.
		//*
		//*	 Logic:
		//*		Determine if the zone if over sized
		//*     Determine if the zone is constructed from 2 tools
		//*     Loop through the ZoneRules array list
		//*			Add tool value to tools used array list
		//*     Loop through tools array list
		//*			To determine the tool value that is furthest from the prices
		//*     Loop through the ZoneRules array list 
		//*			To identify that tool that is not used
		//*			Update the tools array entry to indicate that the tool is not used
		//***************************************************************************************************************************************
		ArrayList<Double> alToolsUsedValues = new ArrayList<Double>();
		double highestValue = 0;
		double lowestValue = 999999;
		
		if ((zoneName == "A") || (zoneName == "D"))  
		{
			if (T.zoneWidth >= zs.sizeOfOversizedZone)
			{	
				if (toolsUsedinZone == 2)
				{	
					for(row=0;row<size;row++)
					{
						ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
						if (ZoneRulesTools.zoneToolUsed == true)
						{
							alToolsUsedValues.add(ZoneRulesTools.zoneToolValue);
						}
					
					}
					int size2=alToolsUsedValues.size();
					for(row=0;row<size2;row++)
					{
						if (zoneName == "A")
						{
							if (alToolsUsedValues.get(row) > highestValue)
							{
								highestValue = alToolsUsedValues.get(row);
							}
						}
						else
						{
							if (zoneName == "D")
							{
								if (alToolsUsedValues.get(row) < lowestValue)
								{
									lowestValue = alToolsUsedValues.get(row);
								}
							}
						}
					}
					if (zoneName == "A")
					{
						for(row=0;row<size;row++)
						{
							ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
							if (ZoneRulesTools.zoneToolValue != highestValue)
							{
								ZoneRulesTools.setZoneToolUsed(false);
								alZoneRules.set(row, ZoneRulesTools);
							}
						
						}
					}
					if (zoneName == "D")
					{
						for(row=0;row<size;row++)
						{
							ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
							if (ZoneRulesTools.zoneToolValue != lowestValue)
							{
								ZoneRulesTools.setZoneToolUsed(false);
								alZoneRules.set(row, ZoneRulesTools);
							}
						
						}
					}
					//* Expand the single tool to define the zone
					for(row=0;row<size;row++)
					{
						ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
						if (ZoneRulesTools.zoneToolUsed == true)
						{

							//* Expand a single tool zone
							SingleToolZone SingleToolZone = new SingleToolZone();
							SingleToolZone.setOriginalToolValue (ZoneRulesTools.zoneToolValue);
							SingleToolZone.setExpandedZoneValues(zs);
							
							//* Update the tools and values array lists with the expanded zone values (tools and values
							String name = ZoneRulesTools.zoneToolLabel;
							ZoneRulesTools.setZoneToolValue(SingleToolZone.getExpandedHighValue());						//* update expanded tool value(high) in object
							alZoneRules.set(row, ZoneRulesTools);														//* update expanded tool value(high) in tool array list
							//*alValues.set(row, SingleToolZone.getExpandedHighValue());									//* update expanded tool value(high) in values array list
							ZoneTools ZoneRulesTools2 = new ZoneTools(name, SingleToolZone.getExpandedLowValue(), true);//* create new object for expanded tool value(low)
							alZoneRules.add(ZoneRulesTools2);															//* create new entry for expanded tool value(low) in tool array list			
							//*alValues.add(SingleToolZone.getExpandedLowValue());											//* create new entry for expanded tool value(low) in values array list
							size ++;
							break;
						}
					}
					setCurrentZoneBoundaries(alZoneRules, T);
				}
			}
		}		

		//***************************************************************************************************************************************
		//*  Apply Zone Rule 5A
		//*  For B and C zones determine if the zone is over sized and constructed from just two tools
		//*  negate the tool closest to prices and expand the remaining tool.
		//*
		//*	 Logic:
		//*		Determine if the zone if over sized
		//*     Determine if the zone is constructed from 2 tools
		//*     Loop through the ZoneRules array list
		//*			Add tool value to tools used array list
		//*     Loop through tools array list
		//*			To determine the tool value that is closest the prices
		//*     Loop through the ZoneRules array list 
		//*			To identify that tool that is not used
		//*			Update the tools array entry to indicate that the tool is not used
		//***************************************************************************************************************************************
		alToolsUsedValues.clear();
		double zoneCToolValueClosestToPrices = 0;
		double zoneBToolValueClosestToPrices = 0;
		
		if ((zoneName == "B") || (zoneName == "C"))  
		{
			if (T.zoneWidth >= zs.sizeOfOversizedZone)
			{	
				if (toolsUsedinZone == 2)
				{	
					for(row=0;row<size;row++)
					{
						ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
						if (ZoneRulesTools.zoneToolUsed == true)
						{
							alToolsUsedValues.add(ZoneRulesTools.zoneToolValue);
						}
					
					}
					if (zoneName == "C")
					{
						if (Math.abs(T.lowPrice - alToolsUsedValues.get(0)) < Math.abs(T.lowPrice - alToolsUsedValues.get(1)))
						{
							zoneCToolValueClosestToPrices = alToolsUsedValues.get(0);
						}
						else
						{
							zoneCToolValueClosestToPrices = alToolsUsedValues.get(1);
						}
					}
					else
					{
						if (zoneName == "B")
						{
							if (Math.abs(T.highPrice - alToolsUsedValues.get(0)) < Math.abs(T.highPrice - alToolsUsedValues.get(1)))
							{
								zoneBToolValueClosestToPrices = alToolsUsedValues.get(0);
							}
							else
							{
								zoneBToolValueClosestToPrices = alToolsUsedValues.get(1);
							}
						}
					}
						
//					int size2=alToolsUsedValues.size();
//					for(row=0;row<size2;row++)
//					{
//						if (zoneName == "C")
//						{
//							if (alToolsUsedValues.get(row) > highestValue)
//							{
//								highestValue = alToolsUsedValues.get(row);
//							}
//						}
//						else
//						{
//							if (zoneName == "B")
//							{
//								if (alToolsUsedValues.get(row) < lowestValue)
//								{
//									lowestValue = alToolsUsedValues.get(row);
//								}
//							}
//						}
//					}
					if (zoneName == "C")
					{
						for(row=0;row<size;row++)
						{
							ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
							if (ZoneRulesTools.zoneToolValue == zoneCToolValueClosestToPrices)
							{
								ZoneRulesTools.setZoneToolUsed(false);
								alZoneRules.set(row, ZoneRulesTools);
							}
						
						}
					}
					if (zoneName == "B")
					{
						for(row=0;row<size;row++)
						{
							ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
							if (ZoneRulesTools.zoneToolValue == zoneBToolValueClosestToPrices)
							{
								ZoneRulesTools.setZoneToolUsed(false);
								alZoneRules.set(row, ZoneRulesTools);
							}
						
						}
					}
					if ((alToolsUsedValues.get(0)) == (alToolsUsedValues.get(1)))
					{

					}
					//* Expand the single tool to define the zone
					for(row=0;row<size;row++)
					{
						ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
						if (ZoneRulesTools.zoneToolUsed == true)
						{

							//* Expand a single tool zone
							SingleToolZone SingleToolZone = new SingleToolZone();
							SingleToolZone.setOriginalToolValue (ZoneRulesTools.zoneToolValue);
							SingleToolZone.setExpandedZoneValues(zs);
							
							//* Update the tools and values array lists with the expanded zone values (tools and values
							String name = ZoneRulesTools.zoneToolLabel;
							ZoneRulesTools.setZoneToolValue(SingleToolZone.getExpandedHighValue());						//* update expanded tool value(high) in object
							alZoneRules.set(row, ZoneRulesTools);														//* update expanded tool value(high) in tool array list
							//*alValues.set(row, SingleToolZone.getExpandedHighValue());									//* update expanded tool value(high) in values array list
							ZoneTools ZoneRulesTools2 = new ZoneTools(name, SingleToolZone.getExpandedLowValue(), true);//* create new object for expanded tool value(low)
							alZoneRules.add(ZoneRulesTools2);															//* create new entry for expanded tool value(low) in tool array list			
							//*alValues.add(SingleToolZone.getExpandedLowValue());											//* create new entry for expanded tool value(low) in values array list
							size ++;
							break;
						}
					}
					setCurrentZoneBoundaries(alZoneRules, T);
				}
			}
		}
	}
	
	/**
	 *  This method will apply zone rule 6
	 *  
	 *  Input:
	 *  
	 *  Output:
	 */
	public void applyZoneRule6(PriceData T, Properties p)
	{
		//***************************************************************************************************************************************
		//Apply Rule 6 for A and B zones 
    	//***************************************************************************************************************************************    

		ArrayList<ZoneTools> alZoneRule6Tools = new ArrayList<ZoneTools>();
		String zoneName = "A";
		boolean zoneAUpdated = false;
		boolean zoneBUpdated = false;

		//***************************************************************************************************************************************
		//Calculate range of zone and the NEAR value 
    	//***************************************************************************************************************************************    
    	double zoneBNear  = (T.zoneBHigh - T.zoneBLow) / Double.parseDouble(p.getProperty("ToolNearZonePercentage"));
    	double zoneNearHigh = T.zoneBHigh + zoneBNear;
    	double zoneNearLow = T.zoneBLow - zoneBNear;

		//***************************************************************************************************************************************
		//Determine if all tools in zone A would be 'NEAR', which would result in zone A having no tools.
    	//***************************************************************************************************************************************    
    	int row = 0;
    	int nbrZoneAToolsNear = 0;
    	int nbrZoneAToolsUsed = 0;
		int size=T.alZoneATools.size();
		
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRule6Tools = new ZoneTools(T.alZoneATools.get(row));
			if (ZoneRule6Tools.zoneToolUsed == true)
			{
				nbrZoneAToolsUsed++;
				if ((ZoneRule6Tools.zoneToolValue >= zoneNearLow) && (ZoneRule6Tools.zoneToolValue <= zoneNearHigh))
				{	
					nbrZoneAToolsNear++;
				}
			}
		}
		//***************************************************************************************************************************************
		//Determine of any tools in zone A 'NEAR' one B.
    	//***************************************************************************************************************************************    
		if (nbrZoneAToolsNear != nbrZoneAToolsUsed)
		{		
			for(row=0;row<size;row++)
			{
				ZoneTools ZoneRule6Tools = new ZoneTools(T.alZoneATools.get(row));
				alZoneRule6Tools.add(ZoneRule6Tools);
				if (ZoneRule6Tools.zoneToolUsed == true)
				{
					if ((ZoneRule6Tools.zoneToolValue >= zoneNearLow) && (ZoneRule6Tools.zoneToolValue <= zoneNearHigh))
					{	
						T.alZoneBTools.add(ZoneRule6Tools);
						ZoneRule6Tools.setZoneToolUsed(false);
						alZoneRule6Tools.set(row, ZoneRule6Tools);
						T.alZoneATools.set(row, ZoneRule6Tools);
						zoneAUpdated = true;
						zoneBUpdated = true;
					}
				}
			}
		}
		//***************************************************************************************************************************************
		//Redefine the boundaries for Zone A if it's tools have been updated 
    	//***************************************************************************************************************************************    		
		if (zoneAUpdated)
		{
			setZoneBoundaries(alZoneRule6Tools, T, zoneName);	
		}
		//***************************************************************************************************************************************
		//Redefine the boundaries for Zone B if it's tools have been updated 
    	//***************************************************************************************************************************************    		
		if (zoneBUpdated)
		{
			alZoneRule6Tools.clear();
			zoneName = "B";
			size=T.alZoneBTools.size();
			for(row=0;row<size;row++)
			{
				ZoneTools ZoneRule6Tools = new ZoneTools(T.alZoneBTools.get(row));
				alZoneRule6Tools.add(ZoneRule6Tools);
			}
			setZoneBoundaries(alZoneRule6Tools, T, zoneName);
		}
		//***************************************************************************************************************************************
		//Apply Rule 6 for C and D zones 
    	//***************************************************************************************************************************************    

		alZoneRule6Tools.clear();
		zoneName = "D";
		boolean zoneCUpdated = false;
		boolean zoneDUpdated = false;
		
		//***************************************************************************************************************************************
		//Calculate range of zone and the NEAR value 
    	//***************************************************************************************************************************************    
    	double zoneCNear  = (T.zoneCHigh - T.zoneCLow) / Double.parseDouble(p.getProperty("ToolNearZonePercentage"));
    	zoneNearHigh = T.zoneCHigh + zoneCNear;
    	zoneNearLow = T.zoneCLow - zoneCNear;

    	//***************************************************************************************************************************************
		//Determine if all tools in zone D would be 'NEAR', which would result in zone D having no tools.
    	//***************************************************************************************************************************************    
    	int nbrZoneDToolsNear = 0;
    	int nbrZoneDToolsUsed = 0;
		size=T.alZoneDTools.size();
		
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRule6Tools = new ZoneTools(T.alZoneDTools.get(row));
			if (ZoneRule6Tools.zoneToolUsed == true)
			{
				nbrZoneDToolsUsed++;
				if ((ZoneRule6Tools.zoneToolValue >= zoneNearLow) && (ZoneRule6Tools.zoneToolValue <= zoneNearHigh))
				{	
					nbrZoneDToolsNear++;
				}
			}
		}		
    	
		//***************************************************************************************************************************************
		//Determine of any tools in zone D 'NEAR' one C.
    	//***************************************************************************************************************************************    
		if (nbrZoneDToolsNear != nbrZoneDToolsUsed)
		{		
			for(row=0;row<size;row++)
			{
				ZoneTools ZoneRule6Tools = new ZoneTools(T.alZoneDTools.get(row));
				alZoneRule6Tools.add(ZoneRule6Tools);
				if (ZoneRule6Tools.zoneToolUsed == true)
				{
					if ((ZoneRule6Tools.zoneToolValue >= zoneNearLow) && (ZoneRule6Tools.zoneToolValue <= zoneNearHigh))
					{	
						T.alZoneCTools.add(ZoneRule6Tools);
						ZoneRule6Tools.setZoneToolUsed(false);
						alZoneRule6Tools.set(row, ZoneRule6Tools);
						T.alZoneDTools.set(row, ZoneRule6Tools);
						zoneDUpdated = true;
						zoneCUpdated = true;
					}
				}
			}
		}
		//***************************************************************************************************************************************
		//Redefine the boundaries for Zone D if it's tools have been updated 
    	//***************************************************************************************************************************************    	
		if (zoneDUpdated)
		{
			setZoneBoundaries(alZoneRule6Tools, T, zoneName);	
		}		

		//***************************************************************************************************************************************
		//Redefine the boundaries for Zone C if it's tools have been updated 
    	//***************************************************************************************************************************************    		
		if (zoneCUpdated)
		{
			alZoneRule6Tools.clear();
			zoneName = "C";
			size=T.alZoneCTools.size();
			for(row=0;row<size;row++)
			{
				ZoneTools ZoneRule6Tools = new ZoneTools(T.alZoneCTools.get(row));
				alZoneRule6Tools.add(ZoneRule6Tools);
			}
			setZoneBoundaries(alZoneRule6Tools, T, zoneName);
		}
	}
	/**
	 *  This method will load the values in the alZoneRules array list into the appropriate Zone array list in the Prices class
	 *  
	 *  Input:
	 *  
	 *  Output:
	 */
	public void loadZoneToolsArray(ArrayList <ZoneTools> alZoneRules, PriceData T, String zoneName)
	{
		int row = 0;
		int size=alZoneRules.size();
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
			switch (zoneName) {
				case "A":
					T.alZoneATools.add(ZoneRulesTools);
					break;
				case "B":
					T.alZoneBTools.add(ZoneRulesTools);
					break;
				case "C":
					T.alZoneCTools.add(ZoneRulesTools);
					break;
				case "D":
					T.alZoneDTools.add(ZoneRulesTools);
					break;
			}
		}
	}
	
	/**
	 *  This method will find the boundaries of the zone by loading the values of the tools used to create the zone into an array to and then finding the max and min values for the appropriate zoen
	 *  The method will also define a generic zone high low and range which will be used in Zone Rules 4, 5, and 5A
	 *  
	 *  Input:
	 *  
	 *  Output:
	 */
	public void setZoneBoundaries(ArrayList <ZoneTools> alZoneRules, PriceData T, String zoneName)
	{
		int row = 0;
		int size=alZoneRules.size();
		ArrayList<Double> alZoneToolsValue = new ArrayList<Double>();
		
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
			if (ZoneRulesTools.zoneToolUsed == true )
			{
				alZoneToolsValue.add(ZoneRulesTools.zoneToolValue);
			}
		}	
		switch (zoneName) {
			case "A":
				T.zoneAHigh = Collections.max(alZoneToolsValue);
				T.zoneALow = Collections.min(alZoneToolsValue);
				break;
			case "B":
				T.zoneBHigh = Collections.max(alZoneToolsValue);
				T.zoneBLow = Collections.min(alZoneToolsValue);
				break;
			case "C":
				T.zoneCHigh = Collections.max(alZoneToolsValue);
				T.zoneCLow = Collections.min(alZoneToolsValue);
				break;
			case "D":
				T.zoneDHigh = Collections.max(alZoneToolsValue);
				T.zoneDLow = Collections.min(alZoneToolsValue);
				break;
		}
		//T.zoneHigh = Collections.max(alZoneToolsValue);
		//T.zoneLow = Collections.min(alZoneToolsValue);
		//T.zoneWidth = T.zoneHigh - T.zoneLow;
	}
	/**
	 *  This method will find the boundaries and width of the current zone
	 *  The method will define the zone high low and range for the currnet zone being created, these values will be used in Zone Rules 4, 5, and 5A
	 *  
	 *  Input:
	 *  
	 *  Output:
	 */
	public void setCurrentZoneBoundaries(ArrayList <ZoneTools> alZoneRules, PriceData T)
	{
		int row = 0;
		int size=alZoneRules.size();
		ArrayList<Double> alZoneToolsValue = new ArrayList<Double>();		
		for(row=0;row<size;row++)
		{
			ZoneTools ZoneRulesTools = new ZoneTools(alZoneRules.get(row));
			if (ZoneRulesTools.zoneToolUsed == true )
			{
				alZoneToolsValue.add(ZoneRulesTools.zoneToolValue);
			}
		}	
		T.zoneHigh = Collections.max(alZoneToolsValue);
		T.zoneLow = Collections.min(alZoneToolsValue);
		T.zoneWidth = T.zoneHigh - T.zoneLow;
	}

	/**
	 *  This method will define the zones for the 4 zones for each type of trading.
	 *  
	 *  Input:  Current Price Data object
	 *  		Array list of the PriceData object for all prices the have been read 
	 *  		Properties file that contains the zone tool parameters to build the Zone Statistics in Appendix G.6
	 *  
	 *   Output: Current Price Data object updated with the details for each zone including:
	 *   			- an array list that that contains
	 *   				-the tools available to define the zone
	 *   				- the tools that were actually used to define the zone after the Zone Rules were applied
	 *   			- the zone high and low
	 *   			- the purpose of the zone 
	 *    
	 *   Logic:
	 *   	1.  Calculate the zone statistics based on price bars read so far
	 *   	2.  For each type of trading:
	 *   	3.		For each zone
	 *   	4.			Load the array list with each tool that can be used to create a zone
	 *   	5.			Apply the Zone Rules 2, 3, 4, 5, 5a, and 6
	 *   	6.			Load the Price Data object with the tools the define each zone
	 *   	7.			Determine the boundaries (high and low) of the zone and update the Price Data object
	 *   	8.		Apply Rule 6      
	 */
	public void setZoneData(PriceData T, ArrayList <ZoneData> alPriceData, Properties p)
	{
		ZoneStatistics ZoneStats = new ZoneStatistics();
		setAverageRangeandZoneStatistics(alPriceData, p, ZoneStats); 
		
		//*ArrayList<Double> alist = new ArrayList<Double>();
		ArrayList<ZoneTools> alZoneTools = new ArrayList<ZoneTools>();
		String zoneName = "";
		
		//ZoneRules ZoneRulesValues = new ZoneRules("",0.00);
		//***************************************************************************************************************************************
		//*  Define Zones for Trend Run Up Intact trading
		//***************************************************************************************************************************************
		if ((T.trendRunIntact) && (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues); 			
			ZoneTools ZoneToolsValues1 = new ZoneTools("6/5 Up",T.$6_5_Up);
			alZoneTools.add(ZoneToolsValues1);						
			ZoneTools ZoneToolsValues2 = new ZoneTools("5/3 Up",T.$5_3_Up);
			alZoneTools.add(ZoneToolsValues2);			
			ZoneTools ZoneToolsValues3 = new ZoneTools("MCL",T.mcLine);
			alZoneTools.add(ZoneToolsValues3);			
			ZoneTools ZoneToolsValues4 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues4);			
			if (T.plDotInRange)
			{
				ZoneTools ZoneToolsValues5 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues5);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to continue Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();						
			if (T.$5_2_Down_Regular)
			{
				ZoneTools ZoneToolsValues7 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues7);
			}
			if (T.$5_2_Down_Deep)
			{
				ZoneTools ZoneToolsValues14 = new ZoneTools("5/2 Down Deep",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues14);
			}			
			ZoneTools ZoneToolsValues6 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues6);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneAPurpose = "Where high forms to continue Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues8 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues8);			
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues9);
			}
			if (T.$5_2_Down_Short)
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues10);
			}			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);		
			T.zoneBPurpose = "Where high forms to enter Congestion Entrance Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues11);				
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues12);			
			if (T.plDotOutsideRange)
			{
				ZoneTools ZoneToolsValues13 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues13);
			}			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);			
			T.zoneDPurpose = "Where low forms to enter Congestion Entrance Down";			
		}

		//***************************************************************************************************************************************
		//*  Define Zones for Trend Run Up Slowing trading
		//***************************************************************************************************************************************
		if ((T.trendRunSlowing) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues1 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues1);
			ZoneTools ZoneToolsValues2 = new ZoneTools("PLDot",T.plDot);
			alZoneTools.add(ZoneToolsValues2);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to continue Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			if (T.$5_2_Down_Short)
			{
			ZoneTools ZoneToolsValues3 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
			alZoneTools.add(ZoneToolsValues3);
			}			
			if (T.$5_2_Down_Regular)
			{
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
			alZoneTools.add(ZoneToolsValues4);
			}			
			ZoneTools ZoneToolsValues5 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues5);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneAPurpose = "Where high forms to continue Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			if (T.mcLine >= T.closePrice) 
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("MCLine",T.mcLine);
				alZoneTools.add(ZoneToolsValues6);
			}
			ZoneTools ZoneToolsValues7 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues7);			
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues8);
			}			
			if (T.redBirdDot > T.closePrice)
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues9);
			}
			ZoneTools ZoneToolsValues10 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues10);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to enter Congestion Entrance Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues11);			
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues12);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneDPurpose = "Where low forms to enter Congestion Entrance Down";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Trend Run Up Aborting trading
		//***************************************************************************************************************************************
		if ((T.trendRunAborting) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();					
			ZoneTools ZoneToolsValues1 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues1);			
			ZoneTools ZoneToolsValues2 = new ZoneTools("5/3 Up",T.$5_3_Up);
			alZoneTools.add(ZoneToolsValues2);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to continue Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			if (T.$5_2_Down_Short)
			{
			ZoneTools ZoneToolsValues3 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
			alZoneTools.add(ZoneToolsValues3);
			}		
			if (T.$5_2_Down_Regular)
			{
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
			alZoneTools.add(ZoneToolsValues4);
			}			
			ZoneTools ZoneToolsValues5 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues5);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneAPurpose = "Where high forms to continue Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues6 = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues6);			
			ZoneTools ZoneToolsValues7 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues7);			
			ZoneTools ZoneToolsValues8 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues8);			
			if (T.redBirdDot > T.closePrice) 
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues9);
			}
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues10);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to enter Congestion Entrance Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues11);			
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues12);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneDPurpose = "Where low forms to enter Congestion Entrance Down";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Entrance Down trading
		//***************************************************************************************************************************************
		if ((T.congestionEntranceDown) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			//* NEED TO ADD CURRENT BLOCK LEVEL AND 5/1 AND 6/1 TO SEE IF THEY ARE IN THE CONGESTION PARMS
			//ZoneTools ZoneToolsValues = new ZoneTools("Currnet Block Level",T.Currnet Block Level);
			//alZoneTools.add(ZoneToolsValues);
			ZoneTools ZoneToolsValues1 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues1);			
			ZoneTools ZoneToolsValues2 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues2);			
			ZoneTools ZoneToolsValues3 = new ZoneTools("5/1 Up",T.$5_1_Up);
			alZoneTools.add(ZoneToolsValues3);			
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues4 = new ZoneTools("5/2 Up Short",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues4);		
			}			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Action Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues5 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues5);			
			ZoneTools ZoneToolsValues6 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues6);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneAPurpose = "Where high forms to go to Congestion Action Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues7 = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues7); 			
			ZoneTools ZoneToolsValues8 = new ZoneTools("6/5 Down",T.$6_5_Down);
			alZoneTools.add(ZoneToolsValues8);						
			ZoneTools ZoneToolsValues9 = new ZoneTools("5/3 Down",T.$5_3_Down);
			alZoneTools.add(ZoneToolsValues9);			
			ZoneTools ZoneToolsValues10 = new ZoneTools("MCL",T.mcLine);
			alZoneTools.add(ZoneToolsValues10);			
			ZoneTools ZoneToolsValues11 = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues11);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to go to Congestion Exit Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/2 Up Ext",T.$5_2_Up_Ext);
			alZoneTools.add(ZoneToolsValues12);			
			ZoneTools ZoneToolsValues13 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues13);			
			if (T.$5_2_Up_Deep)
			{
				ZoneTools ZoneToolsValues14 = new ZoneTools("5/2 Up Deep",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues14);				
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneDPurpose = "Where low forms to go to Congestion Exit Down";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for 2nd Bar of Congestion Entrance Down trading
		//***************************************************************************************************************************************
		if ((T.congestionEntranceDownDay2) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues);			
			ZoneTools ZoneToolsValues1 = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues1);			
			if (T.mcLine > T.closePrice) 
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues2);
			}
			if (T.plDot > T.closePrice) 
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues3);
			}				
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneBPurpose = "Where high forms to go to Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//* Need deep 5/2 up
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues4);			
			if (T.$5_2_Up_Deep)
			{
				ZoneTools ZoneToolsValues5 = new ZoneTools("5/2 Up Deep",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues5);				
			}
			if (T.$5_2_Up_Regular)
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("5/2 Up Regular",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues6);				
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneDPurpose = "Where low forms to go to Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//* Need short 5/2 up
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues7 = new ZoneTools("1/1 Up",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues7);			
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/2 Up Short",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues8);				
			}			
			ZoneTools ZoneToolsValues9 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues9);			
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues10);
			}		
			if (T.plDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues11);
			}		
			if (T.mcLine < T.closePrice)
			{
				ZoneTools ZoneToolsValues12 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues12);
			}			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Action Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues13 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues13);			
			ZoneTools ZoneToolsValues14 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues14);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);		
			T.zoneAPurpose = "Where high forms to go to Congestion Action Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Exit Down trading
		//***************************************************************************************************************************************
		if ((T.congestionExit) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//* Need Last block to form
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("6/5 Down",T.$6_5_Down);
			alZoneTools.add(ZoneToolsValues);			
			ZoneTools ZoneToolsValues1 = new ZoneTools("5/3 Down",T.$5_3_Down);
			alZoneTools.add(ZoneToolsValues1);			
			//ZoneTools ZoneToolsValues2 = new ZoneTools("Last Block to Form",T.lastBlockToForm);
			//alZoneTools.add(ZoneToolsValues2);			
			ZoneTools ZoneToolsValues3 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues3);			
			ZoneTools ZoneToolsValues4 = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues4);		
			ZoneTools ZoneToolsValues5 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues5);			
			if (T.plDotInRange)
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues6);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneBPurpose = "Where high forms to go to Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			if (T.$5_2_Up_Regular)
			{
				ZoneTools ZoneToolsValues7 = new ZoneTools("5/2 Up Regular",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues7);				
			}
			if (T.$5_2_Up_Deep)
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/2 Up Deep",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues8);	
			}			
			ZoneTools ZoneToolsValues9 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues9);				
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneDPurpose = "Where low forms to go to Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues10);
			}			
			ZoneTools ZoneToolsValues11 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues11);			
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues12 = new ZoneTools("5/2 Up Short",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues12);					
			}			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			
			T.zoneCPurpose = "Where low forms to go to Congestion Action Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues13 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues13);			
			ZoneTools ZoneToolsValues14 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues14);			
			if (T.plDotOutsideRange)
			{
				ZoneTools ZoneToolsValues15 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues15);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);		
			T.zoneAPurpose = "Where high forms to go to Congestion Action Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Action Down trading
		//***************************************************************************************************************************************
		if ((T.congestionAction) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues);			
			if ((T.$6_1_Down >= T.lowPrice) && (T.$6_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues1 = new ZoneTools("6/1 Down",T.$6_1_Down);
				alZoneTools.add(ZoneToolsValues1);
			}			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneBPurpose = "Where high forms to go to Congestion Exit Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			if (T.$5_2_Up_Regular)
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("5/2 Up Regular",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues2);			
			}			
			if (T.$5_2_Up_Deep)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("5/2 Up Deep",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues3);			
			}								
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues4);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneDPurpose = "Where low forms to go to Congestion Exit Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//* Need Last Block to Form
			//* Need short 5/2 up
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			//ZoneTools ZoneToolsValues5 = new ZoneTools("Last Block to Form",T.$1_1_Low);
			//alZoneTools.add(ZoneToolsValues5);			
			ZoneTools ZoneToolsValues6 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues6);			
			ZoneTools ZoneToolsValues7 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues7);			
			if (T.$5_2_Up_Short)
			{					
			ZoneTools ZoneToolsValues8 = new ZoneTools("5/2 Up Short",T.$5_2_Up);
			alZoneTools.add(ZoneToolsValues8);
			}			
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues9);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);						
			T.zoneCPurpose = "Where low forms to go to Congestion Action Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues10 = new ZoneTools("5/2 Down",T.$5_2_Down);
			alZoneTools.add(ZoneToolsValues10);
			if ((T.$6_1_Down > T.highPrice) || (T.$6_1_Down < T.lowPrice))
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Down",T.$6_1_Down);
				alZoneTools.add(ZoneToolsValues11);
				//*alist.add(T.$6_1_Down);
			}			
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues12);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);			
			T.zoneAPurpose = "Where high forms to go to Congestion Action Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for 2nd Bar of Congestion Action Down trading
		//***************************************************************************************************************************************
		if ((T.congestionActionContinued) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues);		
			ZoneTools ZoneToolsValues1 = new ZoneTools("6/5 Down",T.$6_5_Down);
			alZoneTools.add(ZoneToolsValues1);			
			ZoneTools ZoneToolsValues2 = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues2);			
			if (T.mcLine > T.closePrice)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues3);
			}
			if (T.redBirdDot > T.closePrice)
			{
				ZoneTools ZoneToolsValues4 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues4);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);		
			T.zoneBPurpose = "Where high forms to go to Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			if (T.$5_2_Up_Deep)
			{
				ZoneTools ZoneToolsValues5 = new ZoneTools("5/2 Up Deep",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues5);			
			}			
			ZoneTools ZoneToolsValues6 = new ZoneTools("5/2 Up Ext",T.$5_2_Up_Ext);
			alZoneTools.add(ZoneToolsValues6);						
			ZoneTools ZoneToolsValues7 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues7);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneDPurpose = "Where low forms to go to Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues8 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues8);			
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("5/2 Up",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues9);					
			}			
			ZoneTools ZoneToolsValues10 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues10);			
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues11);
			}
			if (T.plDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues12 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues12);
			}			
			if (T.mcLine < T.closePrice)
			{
				ZoneTools ZoneToolsValues13 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues13);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Action Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues14 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues14);			
			ZoneTools ZoneToolsValues15 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues15);			
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);			
			T.zoneAPurpose = "Where high forms to go to Congestion Action Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Trend Run Down Intact trading
		//***************************************************************************************************************************************
		if ((T.trendRunIntact) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues);			
			ZoneTools ZoneToolsValues1 = new ZoneTools("6/5 Down",T.$6_5_Down);
			alZoneTools.add(ZoneToolsValues1);			
			ZoneTools ZoneToolsValues2 = new ZoneTools("5/3 Down",T.$5_3_Down);
			alZoneTools.add(ZoneToolsValues2);			
			ZoneTools ZoneToolsValues3 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues3);			
			ZoneTools ZoneToolsValues4 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues4);
			if (T.plDotInRange)
			{
				ZoneTools ZoneToolsValues5 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues5);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to continue Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();			
			if (T.$5_2_Up_Regular)
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("5/2 Up",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues6);					
			}
			if (T.$5_2_Up_Deep)
			{
				ZoneTools ZoneToolsValues7 = new ZoneTools("5/2 Up",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues7);					
			}
			ZoneTools ZoneToolsValues8 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues8);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneDPurpose = "Where low forms to xontinue Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues9 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues9);					
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/2 Up",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues10);					
			}
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues11);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Entrance Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues12 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues12);	
			ZoneTools ZoneToolsValues13 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues13);//*alist.clear();
			if (T.plDotOutsideRange)
			{
				ZoneTools ZoneToolsValues14 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues14);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneAPurpose = "Where high forms to go to Congestion Entrance Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Trend Run Down Slowing trading
		//***************************************************************************************************************************************
		if ((T.trendRunSlowing) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues);
			ZoneTools ZoneToolsValues1 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues1);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to continue Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//* Need short 5/2 up
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("5 2 Up Short",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues2);					
			}
			if (T.$5_2_Up_Regular)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("5 2 Up Regular",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues3);					
			}
			
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues4);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneDPurpose = "Where low forms to continue Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			if (T.mcLine < T.closePrice)
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues6);
			}
			ZoneTools ZoneToolsValues7 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues7);
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues8);
			}
			if (T.redBirdDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues9);
			}
			ZoneTools ZoneToolsValues10 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues10);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Entrance Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues11);
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues12);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneAPurpose = "Where high forms to go to Congestion Entrance Up";
		}		
		//***************************************************************************************************************************************
		//*  Define Zones for Trend Run Down Aborting trading
		//***************************************************************************************************************************************
		if ((T.trendRunAborting) & (T.typeOfTradingDirection == "Down")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues);
			ZoneTools ZoneToolsValues1 = new ZoneTools("5/3 Down",T.$5_3_Down);
			alZoneTools.add(ZoneToolsValues1);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to continue Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//* Need short 5/2 up
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			if (T.$5_2_Up_Short)
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("5 2 Up Short",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues2);					
			}
			if (T.$5_2_Up_Regular)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("5 2 Up Regular",T.$5_2_Up);
				alZoneTools.add(ZoneToolsValues3);					
			}
			
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues4);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneDPurpose = "Where low forms to continue Trend Run Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues5 = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues5);
			ZoneTools ZoneToolsValues6 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues6);
			if (T.redBirdDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues7 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues7);
			}
			ZoneTools ZoneToolsValues8 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues8);
			if ((T.$5_1_Up >= T.lowPrice) && (T.$5_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("5/1 Up",T.$5_1_Up);
				alZoneTools.add(ZoneToolsValues9);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Entrance Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues11);
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues12);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneAPurpose = "Where high forms to go to Congestion Entrance Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Entrance Up trading
		//***************************************************************************************************************************************
		if ((T.congestionEntranceUp) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the B Zone
			// need current blocklevel
			// need 5/1 down in congestion
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();
			//ZoneTools ZoneToolsValues = new ZoneTools("Current Block Level",T.Current Block Level);
			//alZoneTools.add(ZoneToolsValues);
			ZoneTools ZoneToolsValues1 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues1);			
			if (T.$5_2_Down_Short)
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues2);					
			}
			ZoneTools ZoneToolsValues3 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues3);
			// Needs to be in congestion range rather than price range
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues4 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues4);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where high forms to go to Congestion Action Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues5 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues5);
			ZoneTools ZoneToolsValues14 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues14);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneDPurpose = "Where low forms to go to COngestion Action Down";

			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues6 = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues6);
			ZoneTools ZoneToolsValues7 = new ZoneTools("6/5 Up",T.$6_5_Up);
			alZoneTools.add(ZoneToolsValues7);
			ZoneTools ZoneToolsValues8 = new ZoneTools("5/3 Up",T.$5_3_Up);
			alZoneTools.add(ZoneToolsValues8);
			ZoneTools ZoneToolsValues9 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues9);
			ZoneTools ZoneToolsValues10 = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues10);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);;
			T.zoneCPurpose = "Where low forms to go to Congestion Exit Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues11 = new ZoneTools("5/2 Down Ext",T.$5_2_Down_Ext);
			alZoneTools.add(ZoneToolsValues11);					
			if (T.$5_2_Down_Deep)
			{
				ZoneTools ZoneToolsValues12 = new ZoneTools("5/2 Down Deep",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues12);					
			}
			ZoneTools ZoneToolsValues13 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues13);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneAPurpose = "Where high forms to go to Congestion Exit Up";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Entrance 2nd Day  Up trading
		//***************************************************************************************************************************************
		if ((T.congestionActionContinued) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			// need current blocklevel
			// need short 5/2
			// need 5/1 down in congestion
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues);
			ZoneTools ZoneToolsValues1 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues1);
			if (T.plDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues2);
			}
			if (T.redBirdDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues3);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();
			if (T.$5_2_Down_Regular)
			{
				ZoneTools ZoneToolsValues4 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues4);					
			}
			if (T.$5_2_Down_Deep)
			{
				ZoneTools ZoneToolsValues5 = new ZoneTools("5/2 Down Deep",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues5);					
			}			
			ZoneTools ZoneToolsValues6 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues6);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);			
			T.zoneAPurpose = "Where low forms to go to Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues7 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues7);
			if (T.$5_2_Down_Short)
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues8);					
			}
			ZoneTools ZoneToolsValues9 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues9);
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues10);
			}
			if (T.plDot > T.closePrice)
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues11);
			}
			if (T.mcLine > T.closePrice)
			{
				ZoneTools ZoneToolsValues12 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues12);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where low forms to go to Congestion Action Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues13 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues13);alZoneTools.clear();
			ZoneTools ZoneToolsValues14 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues14);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);
			T.zoneDPurpose = "Where high forms to go to Congestion Action Down";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Exit Up trading
		//***************************************************************************************************************************************
		if ((T.congestionExit) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			// last block
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();			
			ZoneTools ZoneToolsValues = new ZoneTools("6/5 Up",T.$6_5_Up);
			alZoneTools.add(ZoneToolsValues);			
			ZoneTools ZoneToolsValues1 = new ZoneTools("5/3 Up",T.$5_3_Up);
			alZoneTools.add(ZoneToolsValues1);			
			//ZoneTools ZoneToolsValues2 = new ZoneTools("Last Block to Form",T.lastBlockToForm);
			//alZoneTools.add(ZoneToolsValues2);			
			ZoneTools ZoneToolsValues3 = new ZoneTools("MC Line",T.mcLine);
			alZoneTools.add(ZoneToolsValues3);			
			ZoneTools ZoneToolsValues4 = new ZoneTools("Red Bird Dot",T.redBirdDot);
			alZoneTools.add(ZoneToolsValues4);			
			ZoneTools ZoneToolsValues5 = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues5);			
			if (T.plDotInRange)
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues6);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			// need deep 5/2
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();
			if (T.$5_2_Down_Regular)
			{
				ZoneTools ZoneToolsValues7 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues7);					
			}
			if (T.$5_2_Down_Deep)
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/2 Down Deep",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues8);					
			}			
			ZoneTools ZoneToolsValues9 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues9);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneAPurpose = "Where low forms to go to Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			// need short 5/2 down
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();			
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues10 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues10);
			}
			ZoneTools ZoneToolsValues10 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues10);					
			if (T.$5_2_Down_Short)
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("5/2 Down",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues11);					
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where low forms to go to Congestion Action Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues12 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues12);alZoneTools.clear();
			ZoneTools ZoneToolsValues13 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues13);
			if ((T.plDot < T.lowPrice) || (T.plDot > T.highPrice))
			{
				ZoneTools ZoneToolsValues14 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues14);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);			
			T.zoneDPurpose = "Where high forms to go to Congestion Action Down";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for Congestion Action Up trading
		//***************************************************************************************************************************************
		if ((T.congestionAction) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues);
			if ((T.$6_1_Up >= T.lowPrice) || (T.$6_1_Up <= T.highPrice))
			{
				ZoneTools ZoneToolsValues1 = new ZoneTools("6/1 Up",T.$6_1_Up);
				alZoneTools.add(ZoneToolsValues1);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Congestion Exit Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();
			if (T.$5_2_Down_Regular)
			{
				ZoneTools ZoneToolsValues2 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues2);					
			}
			if (T.$5_2_Down_Deep)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("5/2 Down Deep",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues3);					
			}			
			ZoneTools ZoneToolsValues4 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues4);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneAPurpose = "Where low forms to go to Congestion Exit Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			// need last block
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();
			//ZoneTools ZoneToolsValues5 = new ZoneTools("Last Block to From",T.LastBlocktoForm);
			//alZoneTools.add(ZoneToolsValues5);
			ZoneTools ZoneToolsValues6 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues6);			
			ZoneTools ZoneToolsValues7 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues7);
			if (T.$5_2_Down_Short)
			{
				ZoneTools ZoneToolsValues8 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues8);					
			}
			if ((T.$5_1_Down >= T.lowPrice) && (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues9);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where low forms to go to Congestion Action Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues10 = new ZoneTools("5/2Up",T.$5_2_Up);
			alZoneTools.add(ZoneToolsValues10);alZoneTools.clear();
			if ((T.$6_1_Up < T.lowPrice) || (T.$6_1_Up > T.highPrice))
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("6/1 Up",T.$6_1_Up);
				alZoneTools.add(ZoneToolsValues11);
			}
			ZoneTools ZoneToolsValues12 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues12);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);		
			T.zoneDPurpose = "Where high forms to go to Congestion Action Down";
		}
		//***************************************************************************************************************************************
		//*  Define Zones for 2nd Bar of Congestion Action Up trading
		//***************************************************************************************************************************************
		if ((T.congestionActionContinued) & (T.typeOfTradingDirection == "Up")) 
		{
			//***************************************************************************************************************************************
			//*  Define the C Zone
			//***************************************************************************************************************************************
			zoneName = "C";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues = new ZoneTools("1/1 Low",T.$1_1_Low);
			alZoneTools.add(ZoneToolsValues);
			ZoneTools ZoneToolsValues1 = new ZoneTools("6/5 Up",T.$6_5_Up);
			alZoneTools.add(ZoneToolsValues1);
			ZoneTools ZoneToolsValues2 = new ZoneTools("PL Dot",T.plDot);
			alZoneTools.add(ZoneToolsValues2);
			if (T.mcLine < T.closePrice)
			{
				ZoneTools ZoneToolsValues3 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues3);
			}
			if (T.redBirdDot < T.closePrice)
			{
				ZoneTools ZoneToolsValues4 = new ZoneTools("Red Bird Dot",T.redBirdDot);
				alZoneTools.add(ZoneToolsValues4);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneCPurpose = "Where low forms to go to Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the A Zone
			//***************************************************************************************************************************************
			zoneName = "A";
			alZoneTools.clear();
			if (T.$5_2_Down_Regular)
			{
				ZoneTools ZoneToolsValues5 = new ZoneTools("5/2 Down Regular",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues5);					
			}
			if (T.$5_2_Down_Deep)
			{
				ZoneTools ZoneToolsValues6 = new ZoneTools("5/2 Down Deep",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues6);					
			}			
			ZoneTools ZoneToolsValues7 = new ZoneTools("5/9 Down",T.$5_9_Down);
			alZoneTools.add(ZoneToolsValues7);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneAPurpose = "Where low forms to go to Trend Run Up";

			//***************************************************************************************************************************************
			//*  Define the B Zone
			// need last block
			//***************************************************************************************************************************************
			zoneName = "B";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues8 = new ZoneTools("1/1 High",T.$1_1_High);
			alZoneTools.add(ZoneToolsValues8);
			if (T.$5_2_Down_Short)
			{
				ZoneTools ZoneToolsValues9 = new ZoneTools("5/2 Down Short",T.$5_2_Down);
				alZoneTools.add(ZoneToolsValues9);					
			}
			ZoneTools ZoneToolsValues10 = new ZoneTools("6/1 Down",T.$6_1_Down);
			alZoneTools.add(ZoneToolsValues10);
			if ((T.$5_1_Down >= T.lowPrice) || (T.$5_1_Down <= T.highPrice))
			{
				ZoneTools ZoneToolsValues11 = new ZoneTools("5/1 Down",T.$5_1_Down);
				alZoneTools.add(ZoneToolsValues11);
			}
			if (T.mcLine > T.closePrice)
			{
				ZoneTools ZoneToolsValues12 = new ZoneTools("MC Line",T.mcLine);
				alZoneTools.add(ZoneToolsValues12);
			}
			if (T.plDot > T.closePrice)
			{
				ZoneTools ZoneToolsValues13 = new ZoneTools("PL Dot",T.plDot);
				alZoneTools.add(ZoneToolsValues13);
			}
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			T.zoneBPurpose = "Where low forms to go to Congestion Action Down";

			//***************************************************************************************************************************************
			//*  Define the D Zone
			//***************************************************************************************************************************************
			zoneName = "D";
			alZoneTools.clear();
			ZoneTools ZoneToolsValues14 = new ZoneTools("5/9 Up",T.$5_9_Up);
			alZoneTools.add(ZoneToolsValues14);alZoneTools.clear();
			ZoneTools ZoneToolsValues15 = new ZoneTools("6/1 Up",T.$6_1_Up);
			alZoneTools.add(ZoneToolsValues15);
			applyZoneRules(alZoneTools, T, p, ZoneStats, zoneName);
			loadZoneToolsArray(alZoneTools, T, zoneName);
			setZoneBoundaries(alZoneTools, T, zoneName);
			applyZoneRule6(T, p);		
			T.zoneDPurpose = "Where high forms to go to Congestion Action Down";
		}
	}	
}
