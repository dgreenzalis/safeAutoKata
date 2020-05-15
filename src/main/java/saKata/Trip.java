package saKata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trip {

	private long tripID;
	private String driverName;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double milesDriven;
	
	
	
	public long getTripID() {
		return tripID;
	}
	public void setTripID(long tripID) {
		this.tripID = tripID;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public LocalDateTime getStartTime() {
		
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public double getMilesDriven() {
		return milesDriven;
	}
	public void setMilesDriven(double milesDriven) {
		this.milesDriven = milesDriven;
	}
	
}
