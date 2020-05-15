package saKata;

import java.time.Duration;
import java.util.List;

public class Driver {

	private String name;
	private long totalMiles;
	private long averageMph;
	private List<Trip> allTrips;
	
		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getTotalMiles() {
		long miles = 0;
		for(Trip t: this.allTrips) {
			Duration duration = Duration.between(t.getStartTime(), t.getEndTime());
			Long hours = duration.toHours();
			if(t.getMilesDriven()/hours >=5 && t.getMilesDriven()/hours <= 100) {
			miles += t.getMilesDriven();
			}
			
		}
		this.totalMiles = miles;
		return totalMiles;
	}
	
	public void setTotalMiles(long totalMiles) {
		this.totalMiles = totalMiles;
	}
	
	public long getAverageMph() {
		long totalTime = 0;
		if(this.allTrips.size()>0) {
			for(Trip t: this.allTrips) {
				Duration duration = Duration.between(t.getStartTime(), t.getEndTime());
				Long hours = duration.toHours();
				
				if(t.getMilesDriven()/hours >=5 && t.getMilesDriven()/hours <= 100) {
					
					totalTime += hours;
				}
			}
			
			if(totalTime > 0) {
				this.averageMph = getTotalMiles()/totalTime;
			}else {
				this.averageMph = 0;
			}
			
		}
		
		
		
		return averageMph;
	}
	
	public void setAverageMph(long averageMph) {
		this.averageMph = averageMph;
	}
	
	public List<Trip> getAllTrips() {
		return allTrips;
	}
	
	public void setAllTrips(List<Trip> allTrips) {
		this.allTrips = allTrips;
	}
	
	
	
	
	
}
