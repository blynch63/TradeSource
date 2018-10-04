package com.stingraytradingsolutions.tradesource;

/**
 * This class is used to identify the Average Range and Statistics for a given security
 *
 */
public class ZoneStatistics {
	String ticker;
	String timePeriod;
	double averageRange;
	double minimumZoneSize;
	double sizeOfSingleToolZone;
	double sizeOfOversizedZone;
	
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String ticker) {
		this.timePeriod = timePeriod;
	}
	public double getAverageRange() {
		return averageRange;
	}
	public void setAverageRange(double averageRange) {
		this.averageRange = averageRange;
	}
	public double getMinimumZoneSize() {
		return minimumZoneSize;
	}
	public void setMinimumZoneSize(double minimumZoneSize) {
		this.minimumZoneSize = minimumZoneSize;
	}
	public double getSizeOfSingleToolZone() {
		return sizeOfSingleToolZone;
	}
	public void setSizeOfSingleToolZone(double sizeOfSingleToolZone) {
		this.sizeOfSingleToolZone = sizeOfSingleToolZone;
	}
	public double getSizeOfOversizedZone() {
		return sizeOfOversizedZone;
	}
	public void setSizeOfOversizedZone(double sizeOfOversizedZone) {
		this.sizeOfOversizedZone = sizeOfOversizedZone;
	}
}
