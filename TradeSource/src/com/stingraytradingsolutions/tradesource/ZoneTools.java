package com.stingraytradingsolutions.tradesource;

/**
 * This class is used to identify the PL tools that are used to create a zone.  An instance of this class will be created for each PL tool and
 * loaded into an array list.  The array list will be processed against the PL Zone Tools Rules to determine which tools are actually 
 * eligible to create the zone
 *
 */
public class ZoneTools
{
	public String zoneToolLabel;
	public double zoneToolValue;
	public boolean zoneToolUsed = true;
	public String zoneToolRule = "Rule 1";																		// 11052018 Added zone tool rule to closs						

//***************************************************************************************************************************************
//* 
//*  Constructor to create new instance of ZoneTool when passed a tool label and tool price value  
//*
//***************************************************************************************************************************************
public ZoneTools(String zoneToolLabel, Double zoneToolValue)
{
	this.zoneToolLabel = zoneToolLabel;
	this.zoneToolValue = zoneToolValue;
	this.zoneToolUsed = true;
	this.zoneToolRule = zoneToolRule;
}

//***************************************************************************************************************************************
//* 
//*  Constructor to create new instance of ZoneTool when passed a tool label, tool price value, and the tool used flag  
//*
//***************************************************************************************************************************************
public ZoneTools(String zoneToolLabel, Double zoneToolValue, Boolean zoneToolUsed)
{
	this.zoneToolLabel = zoneToolLabel;
	this.zoneToolValue = zoneToolValue;
	this.zoneToolUsed = zoneToolUsed;
	this.zoneToolRule = zoneToolRule;
}
//***************************************************************************************************************************************
//* 
//*  Constructor to create new instance of ZoneTool when passed an Instance of the ZoneTools class 
//*
//*  Note: This method was created to receive the values from an object from the  Array List
//*
//***************************************************************************************************************************************
public ZoneTools(ZoneTools ZT)
{
	this.zoneToolLabel = ZT.zoneToolLabel;
	this.zoneToolValue = ZT.zoneToolValue;
	this.zoneToolUsed = ZT.zoneToolUsed;
	this.zoneToolRule = ZT.zoneToolRule;
}

public double getZoneToolValue() {
	return zoneToolValue;
}

public void setZoneToolValue(double zoneToolValue) {
	this.zoneToolValue = zoneToolValue;
}

public boolean isZoneToolUsed() {
	return zoneToolUsed;
}

public void setZoneToolUsed(boolean zoneToolUsed) {
	this.zoneToolUsed = zoneToolUsed;
}	
public void setZoneToolRule(String zoneToolRule) {													// 20181105 Added Zone Rule to Zone Tools Array
	if (this.zoneToolRule == "Rule 1")																// 20181105 Added Zone Rule to Zone Tools Array
	{																								// 20181105 Added Zone Rule to Zone Tools Array
		this.zoneToolRule = zoneToolRule;															// 20181105 Added Zone Rule to Zone Tools Array
	}																								// 20181105 Added Zone Rule to Zone Tools Array
}	
}
