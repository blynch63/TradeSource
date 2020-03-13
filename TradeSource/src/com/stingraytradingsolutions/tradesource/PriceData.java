package com.stingraytradingsolutions.tradesource;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
/** PriceData class details
 * information created for Java Doc test
 */
public class PriceData
{
		static double LASTCONGESTIONPARAMETERHIGH;
		static double LASTCONGESTIONPARAMETERLOW;
		static double HIGHTHRUPLDOT;
		static double LOWTHRUPLDOT;
		static double HIGHTHRU1_1DOT;
		static double LOWTHRU1_1DOT;
		// Set in Price Class
		public String priceDate;
		public String timePeriod;
		public double openPrice;
		public double highPrice;
		public double lowPrice;
		public double closePrice;
		// Set PL Energy Indicators
		public double plDot;
		public double redBirdDot;
		public double priceAvg;
		public double distanceBetweenPlDots;
		public double mcLine;
		// Set in PL Geometry
		public double $1_1_Dot = Double.NaN;
		public double $1_1_High = Double.NaN;
		public double $1_1_Low = Double.NaN;
		public double $5_1_Up = Double.NaN;
		public double $5_1_Down = Double.NaN;
		public double $5_1_Up_Closed = Double.NaN;
		public double $5_1_Down_Closed = Double.NaN;
		public double $5_2_Up;
		public double $5_2_Down;
		public double $5_2_Up_Ext;
		public double $5_2_Down_Ext;
		public boolean $5_2_Down_Deep;
		public boolean $5_2_Down_Regular;
		public boolean $5_2_Down_Short;
		public boolean $5_2_Up_Deep;
		public boolean $5_2_Up_Regular;
		public boolean $5_2_Up_Short;
		public double $5_3_Up;
		public double $5_3_Down;
		public double $5_3_Up_Closed;
		public double $5_3_Down_Closed;
		public double $5_9_Up;
		public double $5_9_Down;
		public double $5_9_Up_Ext;
		public double $5_9_Down_Ext;
		public double $5_9_Up_Closed;
		public double $5_9_Down_Closed;
		public double $6_1_Up;
		public double $6_1_Down;
		public double $6_2_Up;
		public double $6_2_Down;
		public double $6_4_Up;
		public double $6_4_Down;
		public double $6_5_Up;
		public double $6_5_Down;
		public double $6_6_Up;
		public double $6_6_Down;
		public double $6_7_Up;
		public double $6_7_Down;
		// More PL Energy Indicator
		public boolean isolatedHigh;
		public boolean isolatedLow;
		public boolean dottedLine;
		public boolean dottedLineHigh;
		public boolean dottedLineLow;
		public double dottedLineValue;
		public boolean plDotMomentumIncreasing;
		public boolean plDotMomentumDecreasing;
		public boolean plDotMomentumEquidistant;
		public boolean plDotExhausting;
		public boolean plDotAccelerating;
		public boolean plDotDecelerating;
		public boolean plDotSwitchedSides;
		public boolean plDotInRange;
		public boolean plDotInLowEndOfRange;
		public boolean plDotInHighEndOfRange;
		public boolean plDotInMiddleOfRange;
		public boolean plDotOutsideRange;
		public boolean plDotAboveRange;
		public boolean plDotBelowRange;
		public boolean closeInHighEndOfRange;
		public boolean closeInLowEndOfRange;
		public boolean closeInMiddleOfRange;
		public boolean closeAbovePLDot;
		public boolean closeBelowPLDot;
		public boolean closeAboveCurrentPLDot;
		public boolean closeBelowCurrentPLDot;
		public boolean closeNearCurrentPLDot;
		public boolean closeWellAboveCurrentPLDot;
		public boolean closeWellBelowCurrentPLDot;
		public boolean closeWellAboveMCL;
		public boolean closeWellBelowMCL;
		public boolean downSideBlockLevel;
		public double downSideBlockLevelValue;
		public boolean topSideBlockLevel;
		public double topSideBlockLevelValue;
		public double congestionParameterHigh;
		public double congestionParameterLow;
		public double lastBlockToForm;							//20181104  Added block level and congestion parameter functionality
		// Type of Trading
		public String typeOfTrading;
		public String typeOfTradingDirection;
		public boolean trendRunUp;
		public boolean trendRunDown;
		public boolean trendRunIntact;
		public boolean trendRunSlowing;
		public boolean trendRunAborting;
		public boolean trendRunBlobbing;
		public boolean congestionEntranceUp;
		public boolean congestionEntranceUpDay1;
		public boolean congestionEntranceUpDay2;
		public boolean congestionEntranceDown;
		public boolean congestionEntranceDownDay1;
		public boolean congestionEntranceDownDay2;
		public boolean congestionEntranceContinued;
		public boolean congestionAction;
		public boolean congestionActionUp;
		public boolean congestionActionDown;
		public boolean congestionActionContinued;
		public boolean congestionActionContinuedUp;
		public boolean congestionActionContinuedDown;
		public boolean congestionExit;
		public boolean congestionExitUp;
		public boolean congestionExitDown;
		public boolean congestionExitDay2;
		public boolean congestionExitDay2Up;
		public boolean congestionExitDay2Down;
		public double priceBarRange;
		public double priceBarNear;
		public double priceBarNearCloseHigh;
		public double priceBarNearCloseLow;
		// Zone data
		public double zoneHigh;
		public double zoneLow;
		public double zoneWidth;
		public double zoneAHigh;
		public double zoneALow;
		public String zoneAPurpose;
		public double zoneBHigh;
		public double zoneBLow;
		public String zoneBPurpose;
		public double zoneCHigh;
		public double zoneCLow;
		public String zoneCPurpose;
		public double zoneDHigh;
		public double zoneDLow;
		public String zoneDPurpose;
		List<ZoneTools> alZoneATools = new ArrayList<ZoneTools>();
		List<ZoneTools> alZoneBTools = new ArrayList<ZoneTools>();
		List<ZoneTools> alZoneCTools = new ArrayList<ZoneTools>();
		List<ZoneTools> alZoneDTools = new ArrayList<ZoneTools>();

    	//***************************************************************************************************************************************
		//* 
		//*  Constructor to create a new instance of Price Data object with default values 
		//*
		//*  Note: This is required because there is another constructor to build an instance of Price Data 
		//*        with the values passed in from an instance of the array list.
		//*
    	//***************************************************************************************************************************************
		public PriceData()
		{
			this.priceDate = null;
			this.timePeriod = null;
			this.openPrice =0;
			this.highPrice =0;
			this.lowPrice =0;
			this.closePrice =0;
			this.plDot =0;
			this.redBirdDot =0;
			this.priceAvg =0;
			this.distanceBetweenPlDots =0;
			this.mcLine =0;
			this.$1_1_Dot = Double.NaN;
			this.$1_1_High = Double.NaN;
			this.$1_1_Low = Double.NaN;
			this.$5_1_Up = Double.NaN;
			this.$5_1_Down = Double.NaN;
			this.$5_1_Up_Closed = Double.NaN;
			this.$5_1_Down_Closed = Double.NaN;
			this.$5_2_Up = 0;
			this.$5_2_Down = 0;
			this.$5_2_Up_Ext = 0;
			this.$5_2_Down_Ext = 0;
			this.$5_2_Down_Deep = false;
			this.$5_2_Down_Regular = false;
			this.$5_2_Down_Short = false;
			this.$5_2_Up_Deep = false;
			this.$5_2_Up_Regular = false;
			this.$5_2_Up_Short = false;
			this.$5_3_Up = 0;
			this.$5_3_Down = 0;
			this.$5_3_Up_Closed = 0;
			this.$5_3_Down_Closed = 0;
			this.$5_9_Up = 0;
			this.$5_9_Down = 0;
			this.$5_9_Up_Ext = 0;
			this.$5_9_Down_Ext = 0;
			this.$5_9_Up_Closed = 0;
			this.$5_9_Down_Closed = 0;
			this.$6_1_Up = 0;
			this.$6_1_Down = 0;
			this.$6_2_Up = 0;
			this.$6_2_Down = 0;
			this.$6_4_Up = 0;
			this.$6_4_Down = 0;
			this.$6_5_Up = 0;
			this.$6_5_Down = 0;
			this.$6_6_Up = 0;
			this.$6_6_Down = 0;
			this.$6_7_Up = 0;
			this.$6_7_Down = 0;
			this.isolatedHigh = false;
			this.isolatedLow = false;
			this.dottedLine= false;
			this.dottedLineHigh = false;
			this.dottedLineLow = false;
			this.dottedLineValue =0;
			this.plDotMomentumIncreasing = false;
			this.plDotMomentumDecreasing = false;
			this.plDotMomentumEquidistant = false;
			this.plDotExhausting = false;
			this.plDotAccelerating = false;
			this.plDotDecelerating = false;
			this.plDotSwitchedSides = false;
			this.plDotInRange = false;
			this.plDotInLowEndOfRange = false;
			this.plDotInHighEndOfRange = false;
			this.plDotInMiddleOfRange = false;
			this.plDotOutsideRange = false;
			this.plDotAboveRange = false;
			this.plDotBelowRange = false;
			this.closeInHighEndOfRange = false;
			this.closeInLowEndOfRange = false;
			this.closeInMiddleOfRange = false;
			this.closeAbovePLDot = false;
			this.closeBelowPLDot = false;
			this.closeAboveCurrentPLDot = false;
			this.closeBelowCurrentPLDot = false;
			this.closeNearCurrentPLDot = false;
			this.closeWellAboveCurrentPLDot = false;
			this.closeWellBelowCurrentPLDot = false;
			this.closeWellAboveMCL = false;
			this.closeWellBelowMCL = false;
			this.topSideBlockLevel= false;
			this.topSideBlockLevelValue=0;
			this.downSideBlockLevel= false;
			this.downSideBlockLevelValue=0;
			this.congestionParameterHigh =0;
			this.congestionParameterLow =0;
			this.lastBlockToForm = 0;								//20181104  Added block level and congestion parameter functionality
			this.typeOfTrading = null;
			this.typeOfTradingDirection = null;
			this.trendRunUp = false;
			this.trendRunDown = false;
			this.trendRunIntact  = false;
			this.trendRunSlowing = false;
			this.trendRunAborting  = false;
			this.trendRunBlobbing  = false;
			this.congestionEntranceUp  = false;
			this.congestionEntranceUpDay1  = false;
			this.congestionEntranceUpDay2  = false;
			this.congestionEntranceDown  = false;
			this.congestionEntranceDownDay1  = false;
			this.congestionEntranceDownDay2  = false;
			this.congestionEntranceContinued  = false;
			this.congestionAction  = false;
			this.congestionActionUp  = false;
			this.congestionActionDown  = false;
			this.congestionActionContinued  = false;
			this.congestionActionContinuedUp  = false;
			this.congestionActionContinuedDown  = false;
			this.congestionExit  = false;
			this.congestionExitUp  = false;
			this.congestionExitDown  = false;
			this.congestionExitDay2  = false;
			this.congestionExitDay2Up  = false;
			this.congestionExitDay2Down  = false;
			this.priceBarRange =0;
			this.priceBarNear =0;
			this.priceBarNearCloseHigh =0;
			this.priceBarNearCloseLow =0;
			this.zoneHigh =0;
			this.zoneLow =0;
			this.zoneWidth =0;
			this.zoneAHigh =0;
			this.zoneALow =0;
			this.zoneAPurpose = null;
			this.zoneBHigh =0;
			this.zoneBLow =0;
			this.zoneBPurpose = null;
			this.zoneCHigh =0;
			this.zoneCLow =0;
			this.zoneCPurpose = null;
			this.zoneDHigh =0;
			this.zoneDLow =0;
			this.zoneDPurpose = null;
			this.alZoneATools = null; 
			this.alZoneBTools = null;
			this.alZoneCTools = null;
			this.alZoneDTools = null;
		}
    	//***************************************************************************************************************************************
		//* 
		//*  Constructor to create new instance of Price data with the values of the PriceData
		//*  object that was passed in.
		//*
		//*  Note: This method was created to receive the values from an object from the  Array List
		//*
    	//***************************************************************************************************************************************
		public PriceData(PriceData T)
		{
			this.priceDate = T.priceDate;
			this.timePeriod = T.timePeriod;
			this.openPrice = T.openPrice;
			this.highPrice = T.highPrice;
			this.lowPrice = T.lowPrice;
			this.closePrice = T.closePrice;
			this.plDot = T.plDot;
			this.redBirdDot = T.redBirdDot;
			this.priceAvg = T.priceAvg;
			this.distanceBetweenPlDots = T.distanceBetweenPlDots;
			this.mcLine = T.mcLine;
			this.$1_1_Dot = T.$1_1_Dot;
			this.$1_1_High = T.$1_1_High;
			this.$1_1_Low = T.$1_1_Low;
			this.$5_1_Up = T.$5_1_Up;
			this.$5_1_Down = T.$5_1_Down;
			this.$5_1_Up_Closed = T.$5_1_Up_Closed;
			this.$5_1_Down_Closed = T.$5_1_Down_Closed;
			this.$5_2_Up = T.$5_2_Up;
			this.$5_2_Down = T.$5_2_Down;
			this.$5_2_Up_Ext = T.$5_2_Up_Ext;
			this.$5_2_Down_Ext = T.$5_2_Down_Ext;
			this.$5_2_Down_Deep = T.$5_2_Down_Deep;
			this.$5_2_Down_Regular = T.$5_2_Down_Regular;
			this.$5_2_Down_Short = T.$5_2_Down_Short;
			this.$5_2_Up_Deep = T.$5_2_Up_Deep;
			this.$5_2_Up_Regular = T.$5_2_Up_Regular;
			this.$5_2_Up_Short = T.$5_2_Up_Short;
			this.$5_3_Up = T.$5_3_Up;
			this.$5_3_Down = T.$5_3_Down;
			this.$5_3_Up_Closed = T.$5_3_Up_Closed;
			this.$5_3_Down_Closed = T.$5_3_Down_Closed;
			this.$5_9_Up = T.$5_9_Up;
			this.$5_9_Down = T.$5_9_Down;
			this.$5_9_Up_Ext = T.$5_9_Up_Ext;
			this.$5_9_Down_Ext = T.$5_9_Down_Ext;
			this.$5_9_Up_Closed = T.$5_9_Up_Closed;
			this.$5_9_Down_Closed = T.$5_9_Down_Closed;
			this.$6_1_Up = T.$6_1_Up;
			this.$6_1_Down = T.$6_1_Down;
			this.$6_2_Up = T.$6_2_Up;
			this.$6_2_Down = T.$6_2_Down;
			this.$6_4_Up = T.$6_4_Up;
			this.$6_4_Down = T.$6_4_Down;
			this.$6_5_Up = T.$6_5_Up;
			this.$6_5_Down = T.$6_5_Down;
			this.$6_6_Up = T.$6_6_Up;
			this.$6_6_Down = T.$6_6_Down;
			this.$6_7_Up = T.$6_7_Up;
			this.$6_7_Down = T.$6_7_Down;
			this.isolatedHigh = T.isolatedHigh;
			this.isolatedLow = T.isolatedLow;
			this.dottedLine = T.dottedLine;
			this.dottedLineHigh = T.dottedLineHigh;
			this.dottedLineLow = T.dottedLineLow;
			this.dottedLineValue = T.dottedLineValue;
			this.plDotMomentumIncreasing = T.plDotMomentumIncreasing;
			this.plDotMomentumDecreasing = T.plDotMomentumDecreasing;
			this.plDotMomentumEquidistant = T.plDotMomentumEquidistant;
			this.plDotExhausting = T.plDotExhausting;
			this.plDotAccelerating = T.plDotAccelerating;
			this.plDotDecelerating = T.plDotDecelerating;
			this.plDotSwitchedSides = T.plDotSwitchedSides;
			this.plDotInRange = T.plDotInRange;
			this.plDotInLowEndOfRange = T.plDotInLowEndOfRange;
			this.plDotInHighEndOfRange = T.plDotInHighEndOfRange;
			this.plDotInMiddleOfRange = T.plDotInMiddleOfRange;
			this.plDotOutsideRange = T.plDotOutsideRange;
			this.plDotAboveRange = T.plDotAboveRange;
			this.plDotBelowRange = T.plDotBelowRange;
			this.closeInHighEndOfRange = T.closeInHighEndOfRange;
			this.closeInLowEndOfRange = T.closeInLowEndOfRange;
			this.closeInMiddleOfRange = T.closeInMiddleOfRange;
			this.closeAbovePLDot = T.closeAbovePLDot;
			this.closeBelowPLDot = T.closeBelowPLDot;
			this.closeAboveCurrentPLDot = T.closeAboveCurrentPLDot;
			this.closeBelowCurrentPLDot = T.closeBelowCurrentPLDot;
			this.closeNearCurrentPLDot = T.closeNearCurrentPLDot;
			this.closeWellAboveCurrentPLDot = T.closeWellAboveCurrentPLDot;
			this.closeWellBelowCurrentPLDot = T.closeWellBelowCurrentPLDot;
			this.closeWellAboveMCL = T.closeWellAboveMCL;
			this.closeWellBelowMCL = T.closeWellBelowMCL;
			this.topSideBlockLevel= T.topSideBlockLevel;
			this.topSideBlockLevelValue= T.topSideBlockLevelValue;
			this.downSideBlockLevel = T.downSideBlockLevel;
			this.downSideBlockLevelValue = T.downSideBlockLevelValue;
			this.congestionParameterHigh = T.congestionParameterHigh;
			this.congestionParameterLow = T.congestionParameterLow;
			this.lastBlockToForm = T.lastBlockToForm;									//20181104  Added block level and congestion parameter functionality
			this.typeOfTrading = T.typeOfTrading;
			this.typeOfTradingDirection = T.typeOfTradingDirection;
			this.trendRunUp = T.trendRunUp;
			this.trendRunDown = T.trendRunDown;
			this.trendRunIntact  = T.trendRunIntact;
			this.trendRunSlowing = T.trendRunSlowing;
			this.trendRunAborting  = T.trendRunAborting;
			this.trendRunBlobbing  = T.trendRunBlobbing;
			this.congestionEntranceUp  = T.congestionEntranceUp;
			this.congestionEntranceUpDay1  = T.congestionEntranceUpDay1;
			this.congestionEntranceUpDay2  = T.congestionEntranceUpDay2;
			this.congestionEntranceDown  = T.congestionEntranceDown;
			this.congestionEntranceDownDay1  = T.congestionEntranceDownDay1;
			this.congestionEntranceDownDay2  = T.congestionEntranceDownDay2;
			this.congestionEntranceContinued  = T.congestionEntranceContinued;
			this.congestionAction  = T.congestionAction;
			this.congestionActionUp  = T.congestionActionUp;
			this.congestionActionDown  = T.congestionActionDown;
			this.congestionActionContinued  = T.congestionActionContinued;
			this.congestionActionContinuedUp  = T.congestionActionContinuedUp;
			this.congestionActionContinuedDown  = T.congestionActionContinuedDown;
			this.congestionExit  = T.congestionExit;
			this.congestionExitUp  = T.congestionExitUp;
			this.congestionExitDown  = T.congestionExitDown;
			this.congestionExitDay2  = T.congestionExitDay2;
			this.congestionExitDay2Up  = T.congestionExitDay2Up;
			this.congestionExitDay2Down  = T.congestionExitDay2Down;
			this.priceBarRange = T.priceBarRange;
			this.priceBarNear = T.priceBarNear;
			this.priceBarNear = T.priceBarNearCloseHigh;
			this.priceBarNear = T.priceBarNearCloseLow;
			this.zoneHigh = T.zoneHigh;
			this.zoneLow = T.zoneLow;
			this.zoneWidth = T.zoneWidth;
			this.zoneAHigh = T.zoneAHigh;
			this.zoneALow = T.zoneALow;
			this.zoneAPurpose = T.zoneAPurpose;
			this.zoneBHigh = T.zoneBHigh;
			this.zoneBLow = T.zoneBLow;
			this.zoneBPurpose = T.zoneBPurpose;
			this.zoneCHigh = T.zoneCHigh;
			this.zoneCLow = T.zoneCLow;
			this.zoneCPurpose = T.zoneCPurpose;
			this.zoneDHigh = T.zoneDHigh;
			this.zoneDLow = T.zoneDLow;
			this.zoneDPurpose = T.zoneDPurpose;
			this.alZoneATools = T.alZoneATools;
			this.alZoneBTools = T.alZoneBTools;
			this.alZoneCTools = T.alZoneCTools;
			this.alZoneDTools = T.alZoneDTools;

			}
    	//***************************************************************************************************************************************
		//* 
		//*  Constructor to create new instance of Price data with the Price values read in from the price file 
		//*
    	//***************************************************************************************************************************************
		public PriceData(String timePeriod, String priceDate, Double openPrice, Double highPrice, double lowPrice, Double closePrice)
		{
			this.timePeriod = timePeriod;
			this.priceDate = priceDate;
			this.openPrice = openPrice;
			this.highPrice = highPrice;
			this.lowPrice = lowPrice;
			this.closePrice = closePrice;
		}

    	//***************************************************************************************************************************************
		//* 
		//*  Setters for the class variables
		//*
    	//***************************************************************************************************************************************
		
		// Setter for Date
		public void setPriceDate (String priceDate)
		{
			this.priceDate = priceDate;
		}
		// Setter for Open Price
		public void setOpenPrice (Double openPrice)
		{
			this.openPrice = openPrice;
		}
		// Setter for High Price
		public void setHighPrice (Double highPrice)
		{
			this.highPrice = highPrice;
		}
		// Setter for Low Price
		public void setLowPrice (Double lowPrice)
		{
			this.lowPrice = lowPrice;
		}
		// Setter for Closing Price
		public void setClosePrice (Double closePrice)
		{
			this.closePrice = closePrice;
		}
		
    	//***************************************************************************************************************************************
		//* 
		//*  Method to calculate the average price for a single time period 
		//*
		//*  Input: High, low and closing price for a single time period
		//*  Output: average price price for the time period
		//*
    	//***************************************************************************************************************************************		
		public void setPriceAvg(PriceData T)
		{
		 	priceAvg = ((T.highPrice + T.lowPrice + T.closePrice)/3);		
		}
    	//***************************************************************************************************************************************
		//* 
		//*  Method to calculate the PLDot 
		//*
		//*  Input: Price objects for current time period and two previous time periods
		//*  Output: plDot for the current time period
		//*
    	//***************************************************************************************************************************************
		public void setPLDot(PriceData T, PriceData T1, PriceData T2)
		{
			plDot = ((T.priceAvg + T1.priceAvg + T2.priceAvg)/3);
		}
    	//***************************************************************************************************************************************
		//* 
		//*  Method to determine if the T1 price bar forms an isolated high or isolated low.
		//*  Determine if the T1 and T2 prices bars have the same high or low prices and together
		//*  form an isolated high or isolated low.
		//*
		//*  Input: Price objects for current time period and three previous time periods
		//*  Output: Price Object for first previous time period (T1) with updated isolated high and low value
		//*          Price Object for second previous time period (T2) with updated isolated high and low value
		//*
    	//***************************************************************************************************************************************
		public void setIsolatedHighandLow(PriceData T, PriceData T1, PriceData T2, PriceData T3)
		{
			isolatedHigh =  (T1.highPrice > T.highPrice) && (T1.highPrice > T2.highPrice);
			isolatedLow =  (T1.lowPrice < T.lowPrice) && (T1.lowPrice < T2.lowPrice);
			
			if (T1.highPrice == T2.highPrice)
			{
				isolatedHigh =  (T1.highPrice > T.highPrice) && (T1.highPrice > T3.highPrice);
				T2.isolatedHigh =  (T1.highPrice > T.highPrice) && (T1.highPrice > T3.highPrice);
			}
			
			if (T1.lowPrice == T2.lowPrice)
			{
				isolatedLow =  (T1.lowPrice < T.lowPrice) && (T1.lowPrice < T3.lowPrice);
				T2.isolatedLow =  (T1.lowPrice < T.lowPrice) && (T1.lowPrice < T3.lowPrice);
			}
			
		}
		
    	//***************************************************************************************************************************************
		//* 
		//*  NOT CURRENTLY USED:   Method to determine the isolated high for T1 and return value to be used to update array list for T1
		//*
		//*  Input: Price objects for current time period and two previous time periods
		//*  Output: Boolean value to indicate isolated high
		//*
    	//***************************************************************************************************************************************
		public boolean getIsolatedHigh(PriceData T, PriceData T1, PriceData T2)
		{
			return (T1.highPrice > T2.highPrice) && (T1.highPrice > T.highPrice);
		}
		
    	//***************************************************************************************************************************************
		//* 
		//*  NOT CURRENTLY USED:   Method to determine the isolated low for T1 and return value to be used to update array list for T1
		//*
		//*  Input: Price objects for current time period and two previous time periods
		//*  Output: Boolean value to indicate isolated low
		//*
    	//***************************************************************************************************************************************
		public boolean getIsolatedLow(PriceData T, PriceData T1, PriceData T2)
		{
			return (T1.lowPrice < T2.lowPrice) && (T1.lowPrice < T.lowPrice);
		}
		
		
    	//***************************************************************************************************************************************
		//* 
		//*  Method to determine where the dotted line has formed and define the dotted line portion of the congestion parameters
		//*
		//*  Logic: Check for Trend Run Up ending
		//*            Review last 4 days for isolated low
		//*            Look for scenario where last 2 price bars could make up isolated high
		//*         Check for Trend Run Down ending
		//*            Review last 4 days for isolated low
		//*            Look for scenario where last 2 price bars could make up isolated low
		//*         Check for scenario where T1 and T2 make up the isolated high
		//*         Check for scenario where T1 and T2 make up the isolated low
		//*
		//*  Input: Price objects for last 5 time periods (T thru T4)
		//*  Output: Updated price object for any of the last 4 time periods (T1 thru T4)
		//*
    	//***************************************************************************************************************************************
		public void setDottedLine(PriceData T, PriceData T1, PriceData T2, PriceData T3, PriceData T4)
		{	
		
		if ((! T.trendRunUp) && (T1.trendRunUp)) 
	    {
			if (T1.isolatedHigh)
	    	{
				T1.dottedLine = true;
				T1.dottedLineHigh = true;
				T1.dottedLineValue = T1.highPrice;
				T.congestionParameterHigh = T1.highPrice;											//20181104  Added block level and congestion parameter functionality
				LASTCONGESTIONPARAMETERHIGH = T1.highPrice;
				
    		}
   		   	else
    		{
   		   		if (T2.isolatedHigh)
   		   		{
   		   			T2.dottedLine = true;
   		   			T2.dottedLineHigh = true;
   		   			T2.dottedLineValue = T2.highPrice;
   		   			T.congestionParameterHigh = T2.highPrice;										//20181104  Added block level and congestion parameter functionality
   		   			LASTCONGESTIONPARAMETERHIGH = T2.highPrice;
   		   		}
   		   		else
   		   		{
   		   			if (T3.isolatedHigh)
   		   			{
   		   				T3.dottedLine = true;
   		   				T3.dottedLineHigh = true;
   		   				T3.dottedLineValue = T3.highPrice;
   		   				T.congestionParameterHigh = T3.highPrice;									//20181104  Added block level and congestion parameter functionality
   		   				LASTCONGESTIONPARAMETERHIGH = T3.highPrice;
   		   			}
   		   			else
   		   			{
   		   				if (T4.isolatedHigh)
   		   				{
   		   					T4.dottedLine = true;
   		   					T4.dottedLineHigh = true;
   		   					T4.dottedLineValue = T4.highPrice;
   		   					T.congestionParameterHigh = T4.highPrice;								//20181104  Added block level and congestion parameter functionality
   		   					LASTCONGESTIONPARAMETERHIGH = T4.highPrice;
   		   				}
   		   				else
   		   				{
   		   					if (T.highPrice != T1.highPrice)
   		   					{
   		   						T.dottedLine = true;
   		   						T.dottedLineHigh = true;
   		   						T.dottedLineValue = T.highPrice;
   		   						T.congestionParameterHigh = T.highPrice;							//20181104  Added block level and congestion parameter functionality
   		   						LASTCONGESTIONPARAMETERHIGH = T.highPrice;
   		   						//T1.priceDate = "Dotted Line Failed for Down Side Block Level";
   		   					}
   		   				}
   		   					
   		   			}
   		   		}
    		}
   	 	}
		
		if ((! T.trendRunDown) && (T1.trendRunDown)) 
	    {
			if (T1.isolatedLow)
	    	{
				T1.dottedLine = true;
				T1.dottedLineLow = true;
				T1.dottedLineValue = T1.lowPrice;
				T.congestionParameterLow = T1.lowPrice;												//20181104  Added block level and congestion parameter functionality
				LASTCONGESTIONPARAMETERLOW = T1.lowPrice;
    		}
   		   	else
    		{
   		   		if (T2.isolatedLow)
   		   		{
   		   			T2.dottedLine = true;
   		   			T2.dottedLineLow = true;
   		   			T2.dottedLineValue = T2.lowPrice;
   		   			T.congestionParameterLow = T2.lowPrice;											//20181104  Added block level and congestion parameter functionality
   		   			LASTCONGESTIONPARAMETERLOW = T2.lowPrice;
   		   		}
   		   		else
   		   		{
   		   			if (T3.isolatedLow)
   		   			{
   		   				T3.dottedLine = true;
   		   				T3.dottedLineLow = true;
   		   				T3.dottedLineValue = T3.lowPrice;
   		   				T.congestionParameterLow = T3.lowPrice;										//20181104  Added block level and congestion parameter functionality
   		   				LASTCONGESTIONPARAMETERLOW = T3.lowPrice;
   		   			}
   		   			else
   		   			{
   		   				if (T4.isolatedLow)
   		   				{
   		   					T4.dottedLine = true;
   		   					T4.dottedLineLow = true;
   		   					T4.dottedLineValue = T4.lowPrice;
   		   					T.congestionParameterLow = T4.lowPrice;									//20181104  Added block level and congestion parameter functionality
   		   					LASTCONGESTIONPARAMETERLOW = T4.lowPrice;
   		   				}
   		   				else
   		   				{
   		   					if (T.lowPrice != T1.lowPrice)
   		   					{
   		   						T.dottedLine = true;
   		   						T.dottedLineLow = true;
   		   						T.dottedLineValue = T.lowPrice;
   		   						T.congestionParameterLow = T.lowPrice;								//20181104  Added block level and congestion parameter functionality
   		   						LASTCONGESTIONPARAMETERLOW = T.lowPrice;
   		   						//T1.priceDate = "Dotted Line Failed for Top Side Block Level";
   		   					}
   		   				}
   		   			}
   		   		}
    		}
	    }
   	 	if (T1.highPrice == T2.highPrice)
   	 	{
   	 		if (T1.isolatedHigh && T2.isolatedHigh)
   	 		{
   	 			if ((! T1.trendRunUp) && (T2.trendRunUp))
   	 			{
   	 				T1.dottedLine = true;
   	 				T1.dottedLineHigh = true;
   	 				T1.dottedLineValue = T1.highPrice;
   	 				T1.congestionParameterHigh = T1.highPrice;
   	 				T2.dottedLine = true;
   	 				T2.dottedLineHigh = true;
   	 				T2.dottedLineValue = T2.highPrice;
   	 				T1.congestionParameterHigh = T2.highPrice;										//20181104  Added block level and congestion parameter functionality
   	 				LASTCONGESTIONPARAMETERHIGH = T2.highPrice;
   	 			}
   	 		}
   	 	}
   	 if (T1.lowPrice == T2.lowPrice)
	 	{
	 		if (T1.isolatedLow && T2.isolatedLow)
	 		{
	 			if ((! T1.trendRunDown) && (T2.trendRunDown))
	 			{
	 				T1.dottedLine = true;
	 				T1.dottedLineLow = true;
	 				T1.dottedLineValue = T1.lowPrice;
	 				T1.congestionParameterLow = T1.lowPrice;
	 				T2.dottedLine = true;
	 				T2.dottedLineLow = true;
	 				T2.dottedLineValue = T2.lowPrice;
	 				T1.congestionParameterLow = T2.lowPrice;										//20181104  Added block level and congestion parameter functionality
	 				LASTCONGESTIONPARAMETERLOW = T2.lowPrice;
	 			}
	 		}
	 	}
		}
				
    	//***************************************************************************************************************************************
		//* 
		//*  Method to calculate the PnL Tools for a single time period
		//*
		//*  Input: Price objects for current time period and two previous time periods
		//*  Output: Price Object for current time period with updated PnL Tools
		//*
		//*
    	//***************************************************************************************************************************************
		public void calcPLTools(PriceData T, PriceData T1, PriceData T2, Properties p, ZoneStatistics zs)  // 20181014 - Added the ZoneStatistics class 
		{
			
			//***************************************************************************************************************************************
			//Calculate the Pl Dot and the Red Bird Dot
	    	//***************************************************************************************************************************************
			plDot = ((T.priceAvg + T1.priceAvg + T2.priceAvg)/3);   
			redBirdDot = ((T.priceAvg + T1.priceAvg + T.closePrice)/3);
			
	    	//***************************************************************************************************************************************
			//Calculate range of price bar and the NEAR value for use in the Zone Rules
	    	//***************************************************************************************************************************************    
	    	priceBarRange  = T.highPrice - T.lowPrice;
	    	priceBarNear  = T.priceBarRange / Double.parseDouble(p.getProperty("ToolNearClosePercentage"));
	    	priceBarNearCloseHigh = T.closePrice + priceBarNear;
	    	priceBarNearCloseLow = T.closePrice - priceBarNear;
	    
	    	//***************************************************************************************************************************************
			// Calculate the Pl Dot distance and momentum
	    	//
	    	// Determine the absolute value of the distance between the PL dots
	    	// Define a distance buffer as the distance of yesterday's dot plus/minus 10% 
	    	// Measure the distance and set PL Dot momentum as Increasing, Decreasing, or Equidistant
	    	//
	    	//***************************************************************************************************************************************
			distanceBetweenPlDots = Math.abs((T.plDot - T1.plDot));
			double distanceBetweenPLDotsRange = .1;
			double distanceBetweenPLDotsHighEndOfRange = T1.distanceBetweenPlDots + (T1.distanceBetweenPlDots * distanceBetweenPLDotsRange);
			double distanceBetweenPLDotsLowEndOfRange = T1.distanceBetweenPlDots - (T1.distanceBetweenPlDots * distanceBetweenPLDotsRange);
			
			
			
			if (T.distanceBetweenPlDots > distanceBetweenPLDotsHighEndOfRange)
				{
				plDotMomentumIncreasing = true;
				}
			else
				{
				if (T.distanceBetweenPlDots < distanceBetweenPLDotsLowEndOfRange)
					{
					plDotMomentumDecreasing = true;
					}
				else
					{
					if ((T.distanceBetweenPlDots <= distanceBetweenPLDotsHighEndOfRange) && (T.distanceBetweenPlDots >= distanceBetweenPLDotsLowEndOfRange))
						{
						plDotMomentumEquidistant = true;
						}
					}
				}
			

						
			//***************************************************************************************************************************************
			// Determine where the PL Dot is in relation to the price bar
	    	//***************************************************************************************************************************************
			double priceBarMidPoint = T.lowPrice + (T.priceBarRange * .50);
			double priceBarUpperQtrPoint = T.highPrice - (T.priceBarRange * .25);
			double priceBarLowerQtrPoint = T.lowPrice + (T.priceBarRange * .25);
			
			if (T.plDot >= T.lowPrice  && T.plDot < priceBarMidPoint)
			{
				plDotInLowEndOfRange = true;
			}
			else
			{
				if (T.plDot >= priceBarMidPoint && T.plDot <= T.highPrice)
				{
					plDotInHighEndOfRange = true;
				}
					if (T.plDot > T.highPrice)
					{
						plDotAboveRange = true;
					}	
					else
					{
						if (T.plDot < T.lowPrice)
						{
							plDotBelowRange = true;
						}
					}
			}
			
			if (T.plDot >= priceBarLowerQtrPoint  && T.plDot <= priceBarUpperQtrPoint)
			{
				plDotInMiddleOfRange = true;
			}
			
			if (T.plDot >= T.lowPrice  && T.plDot <= T.highPrice)
			{
				plDotInRange = true;
			}
			
			if (T.plDot < T.lowPrice  || T.plDot > T.highPrice)
			{
				T.plDotOutsideRange = true;
			}
			
	    	//***************************************************************************************************************************************
			// Determine where the closing price is in relation to the price bar
	    	//***************************************************************************************************************************************			
			if (T.closePrice >= T.lowPrice  && T.closePrice < priceBarMidPoint)
			{
				closeInLowEndOfRange = true;
			}
			else
			{
				if (T.closePrice >= priceBarMidPoint && T.closePrice <= T.highPrice)
				{
					closeInHighEndOfRange = true;
				}
			}
			
			if (T.closePrice >= priceBarLowerQtrPoint  && T.closePrice < priceBarUpperQtrPoint)
			{
				closeInMiddleOfRange = true;
			}
			
	    	//***************************************************************************************************************************************
			// Determine if the closing price is well above or well below the current PL Dot
	    	//***************************************************************************************************************************************
			double priceBarWellAboveGap = T.priceBarRange * .4;
			
			if (T.closePrice > T.plDot)
			{
				if (T.closePrice > (T.plDot + priceBarWellAboveGap))
				{
					T.closeWellAboveCurrentPLDot = true;
				}
			}
			
			if (T.closePrice < T.plDot)
			{
				if (T.closePrice < (T.plDot - priceBarWellAboveGap))
				{
					T.closeWellBelowCurrentPLDot = true;
				}
			}
			//***************************************************************************************************************************************
			// Determine if the closing price is near the current PL Dot
	    	//***************************************************************************************************************************************
			double closeNearPLDotRange = T.priceBarRange * .2;
			double closeNearPLDotUpperPoint = T.closePrice + closeNearPLDotRange;
			double closeNearPLDotLowerPoint = T.closePrice - closeNearPLDotRange;
			
			if (T.plDot >= closeNearPLDotLowerPoint  && T.plDot <= closeNearPLDotUpperPoint)
			{
				closeNearCurrentPLDot = true;
			}
			
	    	//***************************************************************************************************************************************
	    	//Calculate Main Channel Line
	    	//***************************************************************************************************************************************
			mcLine = (T.plDot * 2) - T1.plDot;
	   
			
	    	//***************************************************************************************************************************************
			// Determine if the closing price is well above or well below the MCL
			// depending on the direction of the trend run.
	    	//***************************************************************************************************************************************
			if (T.closePrice > T.mcLine)
			{
				if (T.closePrice > (T.mcLine + priceBarWellAboveGap))
				{
					T.closeWellAboveMCL = true;
				}
			}
		
			if (T.closePrice < T.mcLine)
			{
				if (T.closePrice < (T.mcLine - priceBarWellAboveGap))
				{
					T.closeWellBelowMCL = true;
				}
			}
						
	    	//***************************************************************************************************************************************
			//Calculate 5/1 Up, 5/2 Down, 5/3 Down
	    	//***************************************************************************************************************************************
	    	if (T.highPrice >= T1.highPrice)
	    	{
	    		$5_2_Down = (T.highPrice * 2) - T1.highPrice;
		    	$5_2_Down_Ext = (T.$5_2_Down *2) - T.highPrice;
		    }
	    	
	    	if (T.highPrice < T1.highPrice)
	    	{
	    		if (((T.highPrice * 2) - T1.highPrice) < T.closePrice)
	    		{
	    		$5_1_Up = Math.round(((T.highPrice * 2) - T1.highPrice) * 100.0) / 100.0;
	    		}
	    		else
	    			{
	    			$5_3_Down = Math.round(((T.highPrice * 2) - T1.highPrice) * 100.0) / 100.0;
	    			}
	    	}
	    	
	    	//***************************************************************************************************************************************
	    	//Calculate 5/1 Down, 5/2 Up, 5/3 Up			
	    	//***************************************************************************************************************************************
	    	if (T.lowPrice <= T1.lowPrice)
	    	{
	    		$5_2_Up = Math.round(((T.lowPrice * 2) - T1.lowPrice) * 100.0) / 100.0;
		    	$5_2_Up_Ext = Math.round(((T.$5_2_Up *2) - T.lowPrice) * 100.0) / 100.0;
	    	}
	    	
	    	if (T.lowPrice > T1.lowPrice)
	    	{
	    		if (((T.lowPrice * 2) - T1.lowPrice) <= T.closePrice)
	    		{
	    			$5_3_Up = Math.round(((T.lowPrice * 2) - T1.lowPrice) * 100.0) / 100.0;	
	    		}
	    		else
	    		{
	    		$5_1_Down = Math.round(((T.lowPrice * 2) - T1.lowPrice) * 100.0) / 100.0;
	    		}
	    	   	
	    	}
	    	
	    	//***************************************************************************************************************************************
	    	//Calculate closed 5/1's 
	    	//***************************************************************************************************************************************
	    	if (T.lowPrice > T1.lowPrice)
	    	{
	    		if ( T.lowPrice > T1.closePrice)
	    			{
	    			if (((T1.closePrice * 2) - T1.lowPrice) > T.closePrice)
	    				{
	    				$5_1_Down_Closed = (T1.closePrice * 2) - T1.lowPrice;
	    				}
	    			}
	     	}
	    	
	    	if (T.highPrice < T1.highPrice)
	    		{
	    		if ( T.highPrice < T1.closePrice)
	    			{
	    			if (((T1.closePrice * 2) - T1.highPrice) < T.closePrice)
	    				{
	    				$5_1_Up_Closed = (T1.closePrice * 2) - T1.highPrice;
	    				}
	    			}
	    		}

	    	//***************************************************************************************************************************************
	    	//Calculate closed 5/3's 
	    	//***************************************************************************************************************************************
	    	if (T.highPrice < T1.highPrice)
	    	{
	    		if ( T.highPrice < T1.closePrice)
	    			{
	    			if (((T1.closePrice * 2) - T1.highPrice) > T.closePrice)
	    				{
	    				$5_3_Down_Closed = (T1.closePrice * 2) - T1.highPrice;
	    				}
	    			}
	    	}
	    	if (T.lowPrice > T1.lowPrice)
	    	{
	    		if ( T.lowPrice > T1.closePrice)
	    		{
	    			if (((T1.closePrice * 2) - T1.lowPrice) < T.closePrice)
					{
						$5_3_Up_Closed = (T1.closePrice * 2) - T1.lowPrice;
					}
	    		}
	     	}

	    	//***************************************************************************************************************************************
	    	//Calculate 5/9's and closed 5/9's 
	    	//***************************************************************************************************************************************
	    	$5_9_Up = Math.round(((T.lowPrice * 2) - T1.highPrice) * 100.0) / 100.0;
	    	$5_9_Down = Math.round(((T.highPrice * 2) - T1.lowPrice) * 100.0) / 100.0;
	    	if (T.highPrice < T1.closePrice)
	    	{
	    		$5_9_Down_Closed = Math.round(((T1.closePrice * 2) - T1.lowPrice) * 100.0) / 100.0;
	    	}
	    	
	    	if (T.lowPrice > T1.closePrice)
	    	{
	    		$5_9_Up_Closed  = Math.round(((T1.closePrice * 2) - T1.highPrice) * 100.0) / 100.0;
	    	}
	    	//***************************************************************************************************************************************
	    	//Calculate 5/9 Extensions
	    	//***************************************************************************************************************************************
	    	$5_9_Up_Ext = Math.round(((T.$5_9_Up *2) - T.lowPrice) * 100.0) / 100.0;
	    	$5_9_Down_Ext = Math.round(((T.$5_9_Down *2) - T.highPrice) * 100.0) / 100.0; 

	    	//***************************************************************************************************************************************
	    	//Calculate 6/1 Up, 6/5 Down, 6/7 Down
	    	//***************************************************************************************************************************************
	    	HIGHTHRUPLDOT = (T.plDot * 2) - T.highPrice;
	    	if (HIGHTHRUPLDOT < T.closePrice)
	    	{
	    		$6_1_Up = HIGHTHRUPLDOT;
	    	}
	    	
	    	if (T.plDotInRange)
	    	{
	    		if (HIGHTHRUPLDOT >= T.closePrice)
		    	{
		    		$6_5_Down = HIGHTHRUPLDOT;
		    	}	
	    	}
	    	
	    	if (T.plDotOutsideRange)
	    	{
	    		if (T.plDot > T.highPrice)
	    		{
	    			$6_7_Down = HIGHTHRUPLDOT;	
	    		}
	    	}

	    	
	    	//***************************************************************************************************************************************
	    	//Calculate 6/1 Down, 6/5 Up, 6/7 Up
	    	//***************************************************************************************************************************************
	    	LOWTHRUPLDOT = (T.plDot * 2) - T.lowPrice;
	    	if (LOWTHRUPLDOT > T.closePrice)
	    	{
	    		$6_1_Down = LOWTHRUPLDOT;
	    	}
	    	
	    	if (T.plDotInRange)
	    	{
	    		if (LOWTHRUPLDOT <= T.closePrice)
		    	{
		    		$6_5_Up = LOWTHRUPLDOT;
		    	}	
	    	}
	    	
	    	if (T.plDotOutsideRange)
	    	{
	    		if (T.plDot < T.lowPrice)
	    		{
	    			$6_7_Up = LOWTHRUPLDOT;	
	    		}
	    	}
	    	
	    	//***************************************************************************************************************************************
	    	// Calculate 6/2 Up, 6/4 Down, 6/6 Down
	    	// These tools are not used in the PL methodology
	    	//***************************************************************************************************************************************
	    	// $6_2_Up = ((T.highPrice * 4) - (T1.plDot *2) - T.highPrice);
	    	// $6_4_Down = ((T.highPrice * 4) - (T1.plDot *2) - T.highPrice);
	    	// $6_6_Down = ((T.highPrice * 4) - (T1.plDot *2) - T.highPrice);
    	
	    	//***************************************************************************************************************************************
	    	// Calculate 6/2 Down, 6/4 Up, 6/6 Up
	    	// These tools are not used in the PL methodology
	    	//***************************************************************************************************************************************
	    	// $6_2_Down = ((T.lowPrice * 4) - (T1.plDot *2) - T.lowPrice);
	    	// $6_4_Up = ((T.lowPrice * 4) - (T1.plDot *2) - T.lowPrice);
	    	// $6_6_Up = ((T.lowPrice * 4) - (T1.plDot *2) - T.lowPrice);
	
	    	//***************************************************************************************************************************************
	    	//Calculate 1/1 Dot, 1/1 High and 1/1 Low
	    	//***************************************************************************************************************************************
	    	$1_1_Dot = (T.priceAvg);
	    	$1_1_High = ((T.$1_1_Dot * 2) - T.lowPrice);
	    	$1_1_Low = ((T.$1_1_Dot * 2) - T.highPrice);
	    	
	    	//***************************************************************************************************************************************
	    	//Calculate 5/2 Extensions
	    	//***************************************************************************************************************************************
	    	// $5_2_Up_Ext = (T.$5_2_Up *2) - T.lowPrice;
	    	// $5_2_Down_Ext = (T.$5_2_Down *2) - T.highPrice; 	
	
	    	
	    	//***************************************************************************************************************************************
	    	//Determine of type of 5/2 Deep, Regular, or Short
	    	//***************************************************************************************************************************************	    	
			double overSizedZone = zs.getSizeOfOversizedZone();										 								// 20181014 - Added the ZoneStatistics class to get oversized zone size to determine 5/2 type
	    	if (T.lowPrice < T1.lowPrice)
	    	{
	    		if (T.$5_2_Up < (T.lowPrice - (2 * overSizedZone)))
	    		{
	    			T.$5_2_Up_Deep = true;
	    		}
	    		else
	    		{
	    			if (T.$5_2_Up > (T.lowPrice - overSizedZone))
	    			{
	    				T.$5_2_Up_Regular = true;	
	    			}
	    			else
	    			{
	    				T.$5_2_Up_Short = true;
	    			}
	    		}
	    	}
	    	
	    	if (T.highPrice < T1.highPrice)
	    	{
	    		if (T.$5_2_Down > (T.highPrice + (2 * overSizedZone)))
	    		{
	    			T.$5_2_Down_Deep = true;
	    		}
	    		else
	    		{
	    			if (T.$5_2_Down > (T.highPrice + overSizedZone))
	    			{
	    				T.$5_2_Down_Regular = true;	
	    			}
	    			else
	    			{
	    				T.$5_2_Down_Short = true;
	    			}
	    		}
	    	}

			
			
	    	//***************************************************************************************************************************************
			//Determine if price closes above or below previous PL Dot
	    	//***************************************************************************************************************************************
	    	if (T.closePrice > T1.plDot)
	    		{
	    		closeAbovePLDot = true;
	    		closeBelowPLDot = false;
	    		}
	    	else
	    		{
	    		closeAbovePLDot = false;
	    		closeBelowPLDot = true;
	    		}
	    	
	    	//***************************************************************************************************************************************
			//Determine if price closes above or below current PL Dot
	    	//***************************************************************************************************************************************
	    	if (T.closePrice > T.plDot)
	    		{
	    		closeAboveCurrentPLDot = true;
	    		closeBelowCurrentPLDot = false;
	    		}
	    	else
	    		{
	    		closeAboveCurrentPLDot = false;
	    		closeBelowCurrentPLDot = true;
	    		}
	    	//***************************************************************************************************************************************
			//Determine if market is in a trend run up or a trend run down
	    	//***************************************************************************************************************************************
	    	if ((T.closeAbovePLDot) && (T1.closeAbovePLDot) && (T2.closeAbovePLDot))
	    		{
	    		T.trendRunUp = true;
	    		T.trendRunDown = false;
	    		}
	    	else
	    		{
	    		if ((T.closeBelowPLDot) && (T1.closeBelowPLDot) && (T2.closeBelowPLDot))
	    			{
	    			T.trendRunUp = false;
		    		T.trendRunDown = true;
	    			}
	    		else
	    			{
	    			T.trendRunUp = false;
	    			T.trendRunDown = false;
	    			}
	    		}
	    	
	    	//***************************************************************************************************************************************
			//  Determine if the current price bars blocks and set the following:
	    	//            - block level indicator
	    	//            - block level value
	    	//            - last block to form
	    	//            - congestion parameter
	    	//
	    	//  Logic:
	    	//
	    	//		Check for Congestion Entrance Down, Congestion Action Down or Exit Down that switches to Congestion Action Up
	    	//			Set Block Level and value
	    	//		Check for Congestion Entrance Up, Congestion Action Up or Exit Up  that switches to Congestion Action Down
	    	//			Set Block Level and value
	    	//		If Congestion Entrance Down
			//			Set Block Level and value
	    	//		Else
	    	//			If Congestion Entrance Up
	    	//				Set Block Level and value
	    	//			Else
	    	//				Trend Run intact
	    	//				Set Block levels and values to their defaults
	    	//								
	    	//***************************************************************************************************************************************

//*	    	if (((T1.congestionEntranceDown == true) || (T1.congestionActionDown == true) || (T1.congestionActionContinuedDown == true) || (T1.congestionExitDown == true) || (T1.congestionExitDay2Down == true)) &&  (T.closeAbovePLDot == true))
	    	if (((T1.congestionEntranceDown) || (T1.congestionActionDown) || (T1.congestionActionContinuedDown) || (T1.congestionExitDown) || (T1.congestionExitDay2Down)) &&  (T.closeAbovePLDot))			// 20200309 Removed '== True' from stmt
	    		{
				T.downSideBlockLevel = false;
				T.topSideBlockLevel = true;
				T.downSideBlockLevelValue = 0;
				T.topSideBlockLevelValue = T.highPrice;
				T.lastBlockToForm = T.highPrice;													//20181104  Added block level and congestion parameter functionality
				}
			
//	    	if (((T1.congestionEntranceUp == true) || (T1.congestionActionUp == true) || (T1.congestionActionContinuedUp == true) || (T1.congestionExitUp == true) || (T1.congestionExitDay2Up == true)) && (T.closeBelowPLDot == true))
	    	if (((T1.congestionEntranceUp) || (T1.congestionActionUp) || (T1.congestionActionContinuedUp) || (T1.congestionExitUp) || (T1.congestionExitDay2Up)) && (T.closeBelowPLDot))					// 20200309 Removed '== True' from stmt
	    		{
				T.downSideBlockLevel = true;
				T.topSideBlockLevel = false;
				T.downSideBlockLevelValue = T.lowPrice;
				T.topSideBlockLevelValue = 0;
				T.lastBlockToForm = T.lowPrice;														//20181104  Added block level and congestion parameter functionality
				}
				
	    	if ((T1.trendRunUp) && (! T.trendRunUp))
	    		{
	    		T.downSideBlockLevel = true;
	    		T.topSideBlockLevel = false;
	    		T.downSideBlockLevelValue = T.lowPrice;
	    		T.topSideBlockLevelValue = 0;
	    		T.congestionParameterLow = T.lowPrice;												//20181104  Added block level and congestion parameter functionality
	    		T.lastBlockToForm = T.lowPrice;														//20181104  Added block level and congestion parameter functionality
	    		LASTCONGESTIONPARAMETERLOW = T.lowPrice;
	    		}
	    	else
	    		{
	    		if ((T1.trendRunDown) && (! T.trendRunDown))
	    			{
	    			T.downSideBlockLevel = false;
		    		T.topSideBlockLevel = true;
		    		T.downSideBlockLevelValue = 0;
		    		T.topSideBlockLevelValue = T.highPrice;
		    		T.congestionParameterHigh = T.highPrice;										//20181104  Added block level and congestion parameter functionality
		    		T.lastBlockToForm = T.highPrice;												//20181104  Added block level and congestion parameter functionality
		    		LASTCONGESTIONPARAMETERHIGH = T.highPrice;
	    			}
	    		else
	    			{
	    			if ((T1.trendRunUp) && (T.trendRunUp))
	    				{
	    				T.downSideBlockLevel = false;    			
	    				T.topSideBlockLevel = false;
	    				T.downSideBlockLevelValue = 0;
	    				T.topSideBlockLevelValue = 0;
	    				T.lastBlockToForm = 0;															
	    				}
	    			else
	    				{
	    				if ((T1.trendRunDown) && (T.trendRunDown))
	    					{
	    					T.downSideBlockLevel = false;    			
	    					T.topSideBlockLevel = false;
	    					T.downSideBlockLevelValue = 0;
	    					T.topSideBlockLevelValue = 0;
	    					T.lastBlockToForm = 0;
	    					}
	    				}
	    			}
	    		}
	    	if (((T1.congestionEntranceDown) || (T1.congestionActionDown) || (T1.congestionActionContinuedDown) || (T1.congestionExitDown) || (T1.congestionExitDay2Down)) &&  ((!T.trendRunDown) && (T.closeBelowPLDot)))			// 20200309 New LB2F logic
	    		{
	    		T.lastBlockToForm = T1.lastBlockToForm;
	    		}
	    	if (((T1.congestionEntranceUp) || (T1.congestionActionUp) || (T1.congestionActionContinuedUp) || (T1.congestionExitUp) || (T1.congestionExitDay2Up)) && ((!T.trendRunUp) && (T.closeAbovePLDot)))					// 20200309 New LB2F logic
	    		{
	    		T.lastBlockToForm = T1.lastBlockToForm;
	    		}
	    	//***************************************************************************************************************************************
			// 
	    	// Determine if the PL Dot is Accelerating, Decreasing or Exhausting
	    	//
	    	//***************************************************************************************************************************************
			double plDotDeceleratingVariance = .5;
			double plDotDeceleratingDistance  = (T1.distanceBetweenPlDots * (1 - plDotDeceleratingVariance));
			
			double plDotAcceleratingVariance = .4;			
			double plDotAcceleratingDistance  = (T1.distanceBetweenPlDots * (1 + plDotAcceleratingVariance));
			
			//*double plDotExhaustingVariance = .5;
			//*double plDotExhaustingDistance  = (T1.distanceBetweenPlDots * (1 - plDotExhaustingVariance));
			
			if ((T.trendRunUp) || (T.trendRunDown))
			{
				if (T.distanceBetweenPlDots >= plDotAcceleratingDistance)
					{
					T.plDotAccelerating = true;
					//System.out.println("PL Dot Accelerating on " + T.priceDate); 
					}
				else
				{
					if (T.distanceBetweenPlDots <= plDotDeceleratingDistance)
					{
						T.plDotDecelerating = true;
					}
				}
			}
			
				
			if ((T.trendRunUp) || (T.trendRunDown))
			{
					if (T1.plDotAccelerating)
						{
						if (T.plDotDecelerating)
							{
							T.plDotExhausting = true;
							//System.out.println("PL Dot Exhausting on " + T.priceDate); 
							}
						}	
			}
		}
		
    	//***************************************************************************************************************************************
		//* 
		//*  Method to determine the Type of Trading 
		//*
		//*  Input: 
		//*  Output: 
		//*
    	//***************************************************************************************************************************************
		public void setTypeOfTrading(PriceData T, PriceData T1, PriceData T2, PriceData T3, PriceData T4)
		{

			//***************************************************************************************************************************************
			//*  Identify if the Trend Run is Intact, Slowing, or Aborting
			//*
			//*  Reset Congestion Parameter when trend run trading
			//*
	    	//***************************************************************************************************************************************
			if ((T.trendRunUp) || (T.trendRunDown))
			{
				LASTCONGESTIONPARAMETERHIGH =0;
				LASTCONGESTIONPARAMETERLOW =0;
					if (T.trendRunUp)
					{
						if ((T.closeBelowCurrentPLDot) && (T.mcLine > T.closePrice))
						{
							T.trendRunAborting = true;
							T.typeOfTrading = "TRA";	
						}
						else
						{
							if ((T.mcLine > T.closePrice) || (T.$6_1_Down > T.closePrice)) // && ((T.closeInMiddleOfRange) || (T.closeInLowEndOfRange)))
							{
								T.trendRunSlowing = true;
								T.typeOfTrading = "TRS";
							}
							else
							{
								T.trendRunIntact = true;
								T.typeOfTrading = "TRI";
							}
						}
					}
					else
					{
						if ((T.closeAboveCurrentPLDot) && (T.mcLine < T.closePrice))
						{
							T.trendRunAborting = true;
							T.typeOfTrading = "TRA";	
						}
						else
						{
							if ((T.mcLine < T.closePrice) || ((T.$6_1_Up > 0) && (T.$6_1_Up < T.closePrice))) //&& ((T.closeInMiddleOfRange) || (T.closeInHighEndOfRange)))
							{
								T.trendRunSlowing = true;
								T.typeOfTrading = "TRS";
							}
							else
							{
								T.trendRunIntact = true;
								T.typeOfTrading = "TRI";
							}
						}
					}			
				if (T.trendRunUp)
				{
					T.typeOfTradingDirection = "Up";	
				}
				else
				{
					T.typeOfTradingDirection = "Down";
				}
				System.out.println("Date: "+ T.priceDate + " Type of Trading: " + T.typeOfTrading + T.typeOfTradingDirection );
			}
						

	    	//***************************************************************************************************************************************
			//Determine if the type of trading is in Day 1 of Congestion Entrance
	    	//***************************************************************************************************************************************
			if ((!T.trendRunUp) && (!T.trendRunDown))
			{
				if ((T1.trendRunUp) && (T.closeBelowPLDot))
				{
		    		T.congestionEntranceDown = true;
		    		T.congestionEntranceDownDay1 = true;
		    		T.typeOfTrading = "CEnt";
		    		T.typeOfTradingDirection = "Down";
		    		System.out.println("Congestion Entrance Down Day 1: " +" Date: "+ T.priceDate);
				}
		    	else 
		    	{
		    		if ((T1.trendRunDown) && (T.closeAbovePLDot))
					{
						T.congestionEntranceUp = true;
			    		T.congestionEntranceUpDay1 = true;
			    		T.typeOfTrading = "CEnt";
			    		T.typeOfTradingDirection = "Up";
			    		System.out.println("Congestion Entrance Up Day 1: " +" Date: "+ T.priceDate);	
					}
		    	}
				//T.congestionParameterLow = T1.congestionParameterLow;
				//T.congestionParameterHigh = T1.congestionParameterHigh;
			}

	    		
	    	//***************************************************************************************************************************************
			// Determine if the type of trading is in Day 2 of Congestion Entrance
	    	//***************************************************************************************************************************************
			if (( !T.trendRunUp) && (!T.trendRunDown))
			{
				if (((T2.trendRunUp) && (T1.congestionEntranceDown)) && ((T.closeBelowPLDot) && (T.closePrice >= LASTCONGESTIONPARAMETERLOW) && (T.closePrice <= LASTCONGESTIONPARAMETERHIGH)))
		    	{
		    		T.congestionEntranceDown = true;
		    		T.congestionEntranceDownDay2 = true;
		    		T.congestionEntranceContinued = true;
		    		T.typeOfTrading = "CEnt2";
		    		T.typeOfTradingDirection = "Down";
					T.congestionParameterLow = T1.congestionParameterLow;							//20181104  Added block level and congestion parameter functionality
					T.congestionParameterHigh = T1.congestionParameterHigh;							//20181104  Added block level and congestion parameter functionality
					T.lastBlockToForm = T1.lastBlockToForm;											//20181104  Added block level and congestion parameter functionality
		    		System.out.println("Congestion Entrance Down Day 2: " +" Date: "+ T.priceDate);
		    	}
		    	else
		    	{
		    		if (((T2.trendRunDown) && (T1.congestionEntranceUp)) && ((T.closeAbovePLDot) && (T.closePrice <= LASTCONGESTIONPARAMETERHIGH) && (T.closePrice >= LASTCONGESTIONPARAMETERLOW)))
		    		{
		    			T.congestionEntranceUp = true;
			    		T.congestionEntranceUpDay2 = true;
			    		T.congestionEntranceContinued = true;
			    		T.typeOfTrading = "CEnt2";
			    		T.typeOfTradingDirection = "Up";
						T.congestionParameterLow = T1.congestionParameterLow;						//20181104  Added block level and congestion parameter functionality
						T.congestionParameterHigh = T1.congestionParameterHigh;						//20181104  Added block level and congestion parameter functionality
						T.lastBlockToForm = T1.lastBlockToForm;										//20181104  Added block level and congestion parameter functionality
			    		System.out.println("Congestion Entrance Up Day 2: " +" Date: "+ T.priceDate);	
		    		}
		    	}
			}
			//***************************************************************************************************************************************
			// Determine if the type of trading is Congestion Action
	    	// Set block level on T
	    	//***************************************************************************************************************************************

			if ((!T.trendRunUp) && (!T.trendRunDown))
			{
				if (((T1.congestionEntranceUpDay1) || (T1.congestionEntranceUpDay2) || (T1.congestionActionUp) || (T1.congestionActionContinuedUp)) && ((T.closeBelowPLDot) && (T.closePrice >= LASTCONGESTIONPARAMETERLOW) && (T.closePrice <= LASTCONGESTIONPARAMETERHIGH)))
		    	{
		    		T.congestionAction = true;
		    		T.congestionActionDown  = true;
		    		T.typeOfTrading = "CAct";
		    		T.typeOfTradingDirection = "Down";
					T.congestionParameterLow = T1.congestionParameterLow;							//20181104  Added block level and congestion parameter functionality
					T.congestionParameterHigh = T1.congestionParameterHigh;							//20181104  Added block level and congestion parameter functionality
		    		System.out.println("Congestion Action Down: " +" Date: "+ T.priceDate);
		    	}
		    	else
		    	{
		    		if (((T1.congestionEntranceDownDay1) || (T1.congestionEntranceDownDay2) || (T1.congestionActionDown) || (T1.congestionActionContinuedDown)) && ((T.closeAbovePLDot) && (T.closePrice >= LASTCONGESTIONPARAMETERLOW) && (T.closePrice <= LASTCONGESTIONPARAMETERHIGH)))
		    		{
		    			T.congestionAction = true;
		    			T.congestionActionUp = true;
		    			T.typeOfTrading = "CAct";
			    		T.typeOfTradingDirection = "Up";
						T.congestionParameterLow = T1.congestionParameterLow;						//20181104  Added block level and congestion parameter functionality
						T.congestionParameterHigh = T1.congestionParameterHigh;						//20181104  Added block level and congestion parameter functionality
			    		System.out.println("Congestion Action Up: " +" Date: "+ T.priceDate);	
		    		}
		    	}	
	    		
	    		if ((((T1.congestionExitUp) || (T1.congestionExitDay2Up)) && (T.closeBelowPLDot)) || ((T1.congestionExitDown) && (T.closeBelowPLDot) && (T.closePrice >= LASTCONGESTIONPARAMETERLOW)))
		    	{
		    		T.congestionAction = true;
		    		T.congestionActionDown  = true;
		    		T.typeOfTrading = "CAct";
		    		T.typeOfTradingDirection = "Down";
					T.congestionParameterLow = T1.congestionParameterLow;							//20181104  Added block level and congestion parameter functionality
					T.congestionParameterHigh = T1.congestionParameterHigh;							//20181104  Added block level and congestion parameter functionality
		    		System.out.println("Congestion Action Down: " +" Date: "+ T.priceDate);
		    	}
		    	else
		    	{
		    		if ((((T1.congestionExitDown) || (T1.congestionExitDay2Down)) && (T.closeAbovePLDot)) || ((T1.congestionExitUp) && (T.closeAbovePLDot) && (T.closePrice <= LASTCONGESTIONPARAMETERHIGH)))
		    		{
		    			T.congestionAction = true;
		    			T.congestionActionUp = true;
		    			T.typeOfTrading = "CAct";
			    		T.typeOfTradingDirection = "Up";
						T.congestionParameterLow = T1.congestionParameterLow;						//20181104  Added block level and congestion parameter functionality
						T.congestionParameterHigh = T1.congestionParameterHigh;						//20181104  Added block level and congestion parameter functionality
			    		System.out.println("Congestion Action Up: " +" Date: "+ T.priceDate);	
		    		}
		    	}
			}	

	    	
	    	//***************************************************************************************************************************************
			// Determine if the type of trading is Congestion Action Continued
	    	//***************************************************************************************************************************************
			if ((!T.trendRunUp) && (!T.trendRunDown))
			{
				if ((T1.congestionActionUp) && (T.closeAbovePLDot) && (T.closePrice > LASTCONGESTIONPARAMETERLOW) && (T.closePrice < LASTCONGESTIONPARAMETERHIGH))
		    	{
		    		T.congestionActionContinued = true;
		    		T.congestionActionContinuedUp = true;
		    		T.typeOfTrading = "CAct2";
		    		T.typeOfTradingDirection = "Up";
					T.congestionParameterLow = T1.congestionParameterLow;							//20181104  Added block level and congestion parameter functionality
					T.congestionParameterHigh = T1.congestionParameterHigh;							//20181104  Added block level and congestion parameter functionality
					T.lastBlockToForm = T1.lastBlockToForm;											//20181104  Added block level and congestion parameter functionality
		    		System.out.println("Congestion Action Continued Up: " +" Date: "+ T.priceDate);
		    	}
		    	else
		    	{
		    		if ((T1.congestionActionDown) && (T.closeBelowPLDot) && (T.closePrice > LASTCONGESTIONPARAMETERLOW) && (T.closePrice < LASTCONGESTIONPARAMETERHIGH))
		    		{
		    			T.congestionActionContinued = true;
		    			T.congestionActionContinuedDown = true;
			    		T.typeOfTrading = "CAct2";
			    		T.typeOfTradingDirection = "Down";
						T.congestionParameterLow = T1.congestionParameterLow;						//20181104  Added block level and congestion parameter functionality
						T.congestionParameterHigh = T1.congestionParameterHigh;						//20181104  Added block level and congestion parameter functionality
						T.lastBlockToForm = T1.lastBlockToForm;										//20181104  Added block level and congestion parameter functionality
			    		System.out.println("Congestion Action Continued Down: " +" Date: "+ T.priceDate);
		    		}
		    	}
			}

	    	//***************************************************************************************************************************************
			// Determine if the type of trading is Congestion Exit (violating the block level) when in Congestion Entrance or Congestion Action Trading
	    	//***************************************************************************************************************************************
			if ((!T.trendRunUp) && (!T.trendRunDown))
			{
				if (((T1.congestionEntranceDown) || (T1.congestionEntranceDownDay2) || (T1.congestionEntranceUp) || (T1.congestionActionDown) || (T1.congestionActionUp) || (T1.congestionActionContinuedDown)) && (T.closePrice > LASTCONGESTIONPARAMETERHIGH))
// 20200310		if (((T1.congestionEntranceDown) || (T1.congestionEntranceDownDay2) || (T1.congestionEntranceUp) || (T1.congestionActionDown) || (T1.congestionActionUp) || (T1.congestionActionContinuedDown)) && ((T.closePrice > T.lastBlockToForm) && (T.closePrice > LASTCONGESTIONPARAMETERHIGH)))
		    	{
		    		T.congestionExit = true;
		    		T.congestionExitUp = true;
		    		T.typeOfTrading = "CExit";
		    		T.typeOfTradingDirection = "Up";
		    		System.out.println("Congestion Exit Up: " +" Date: "+ T.priceDate);
		    		System.out.println("T close Price: " + T.closePrice);
					T.congestionParameterLow = T1.congestionParameterLow;							//20181104  Added block level and congestion parameter functionality
					T.congestionParameterHigh = T1.congestionParameterHigh;							//20181104  Added block level and congestion parameter functionality
		    		System.out.println("LASTCONGESTIONPARAMETERHIGH "+ LASTCONGESTIONPARAMETERHIGH);
		    	}
		    	else
		    	{
		    		if (((T1.congestionEntranceDown) || (T1.congestionEntranceDownDay2) || (T1.congestionEntranceUp) || (T1.congestionActionUp) || (T1.congestionActionDown) || (T1.congestionActionContinuedUp)) && (T.closePrice < LASTCONGESTIONPARAMETERLOW))
// 20200310    		if (((T1.congestionEntranceDown) || (T1.congestionEntranceDownDay2) || (T1.congestionEntranceUp) || (T1.congestionActionUp) || (T1.congestionActionDown) || (T1.congestionActionContinuedUp)) && ((T.closePrice < T.lastBlockToForm) && (T.closePrice < LASTCONGESTIONPARAMETERLOW)))
		    		{
		    			T.congestionExit = true;
		    			T.congestionExitDown = true;
			    		T.typeOfTrading = "CExit";
			    		T.typeOfTradingDirection = "Down";
						T.congestionParameterLow = T1.congestionParameterLow;						//20181104  Added block level and congestion parameter functionality
						T.congestionParameterHigh = T1.congestionParameterHigh;						//20181104  Added block level and congestion parameter functionality
			    		System.out.println("Congestion Exit Down: " +" Date: "+ T.priceDate);
		    		}
		    	}
			}
	    	
	    	
	    	
	    	//***************************************************************************************************************************************
			// Determine if the type of trading is Congestion Exit Day 2 
	    	//***************************************************************************************************************************************
			if ((!T.trendRunUp) && (!T.trendRunDown))
			{
				if ((T1.congestionExitUp) && ((T2.congestionActionDown) || (T2.congestionActionContinuedDown) || (T2.congestionEntranceDownDay1) || (T2.congestionEntranceDownDay2))&& (T.closePrice > LASTCONGESTIONPARAMETERHIGH))
		    	{
		    		T.congestionExitDay2 = true;
		    		T.congestionExitDay2Up = true;
		    		T.typeOfTrading = "CExit2";
		    		T.typeOfTradingDirection = "Up";
					T.congestionParameterLow = T1.congestionParameterLow;							//20181104  Added block level and congestion parameter functionality
					T.congestionParameterHigh = T1.congestionParameterHigh;							//20181104  Added block level and congestion parameter functionality
					T.lastBlockToForm = T1.lastBlockToForm;											//20181104  Added block level and congestion parameter functionality
		    		System.out.println("Congestion Exit Day 2 Up: " +" Date: "+ T.priceDate);
		    	}
		    	else
		    	{
		    		if ((T1.congestionExitDown) && ((T2.congestionActionUp) || (T2.congestionActionContinuedUp) || (T2.congestionEntranceUpDay1) || (T2.congestionEntranceUpDay2)) && (T.closePrice < LASTCONGESTIONPARAMETERLOW))  //20200229 Add CAct2Up to If
		    		{
		    			T.congestionExitDay2 = true;
		    			T.congestionExitDay2Down = true;
			    		T.typeOfTrading = "CExit2";
			    		T.typeOfTradingDirection = "Down";
						T.congestionParameterLow = T1.congestionParameterLow;						//20181104  Added block level and congestion parameter functionality
						T.congestionParameterHigh = T1.congestionParameterHigh;						//20181104  Added block level and congestion parameter functionality
						T.lastBlockToForm = T1.lastBlockToForm;										//20181104  Added block level and congestion parameter functionality
			    		System.out.println("Congestion Exit Day2 Down: " +" Date: "+ T.priceDate);
		    		}
		    	}
			}

    	
	    	//*
	    	//* Capture missing type of trading
	    	//*
	    	if (T.typeOfTrading != null && !T.typeOfTrading.isEmpty())
	    	{
	    		//
	    	}
	    	else
	    	{
	    		System.out.println("Type of Trading is NULL: " +" Date: "+ T.priceDate);
	    	}
	    	
		}
		

					
					
		//******************************************
		//* 
		//*  Getters for the class variables
		//*
		//******************************************		
		
		//* Getter for Price Date
		public String getPriceDate()
		{
			return priceDate;
		}
		//* Getter for Open Price
		public Double getOpenPrice()
		{
			return openPrice;
		}
		//* Getter for High Price
		public Double getHighPrice()
		{
			return highPrice;
		}
		//* Getter for Low Price
		public Double getLowPrice()
		{
			return lowPrice;
		}	
		//* Getter for Close Price
		public Double getClosePrice()
		{
			return closePrice;
		}
}
