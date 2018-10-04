package com.stingraytradingsolutions.tradesource;

/**
 * This class is used to expand a single tool zone and define the zone boundaries
 *
 */
public class SingleToolZone {
	double originalToolValue;
	double expandedHighValue;
	double expandedLowValue;

	public void setOriginalToolValue(Double originalToolValue) {
		this.originalToolValue = originalToolValue;
	}
	public Double getExpandedHighValue() {
		return expandedHighValue;
	}

	public Double getExpandedLowValue() {
		return expandedLowValue;
	}

	//***************************************************************************************************************************************
	//* 
	//*  Method to calculate the expanded high and low values for the single tool zone 
	//*
	//*  Input: 
	//*  Output: Update the SingleZoneTool class with the high and low values
	//*
	//***************************************************************************************************************************************		
	public void setExpandedZoneValues(ZoneStatistics zs) {
					expandedHighValue = originalToolValue + zs.minimumZoneSize/2;
					expandedLowValue = originalToolValue - zs.minimumZoneSize/2;
	}	
}
