package saKata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ServiceLayer {

//	private File textDoc = new File("TripsAndDriver.txt");
	private File textDoc;
	private Scanner inputScanner = new Scanner(System.in);
	private File textDocReWrite;
	
	public ServiceLayer(String fileName) {
		textDoc = new File(fileName + ".txt");
		textDocReWrite = new File(fileName + "TEMP.txt");
		if (!textDoc.exists()) {
			try {
				textDoc.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	public boolean addDriver(String inputName) {
		boolean methodExecuted = false;
		
		try {
			if (getDriverNames(textDoc).contains(inputName)) {
				System.out.println("Driver already exists in file - Please try again!");
				System.out.println("");
				System.out.println("");
			} else {
				FileWriter fileWriter = new FileWriter(textDoc, true);
				PrintWriter auditPrinter = new PrintWriter(fileWriter);
				auditPrinter.println("\n" + "Driver,"+ inputName);
				auditPrinter.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		methodExecuted = true;
		return methodExecuted;
	}

	public boolean removeDriver(String inputName) {
		boolean methodExecuted = false;
		try {
			if (!getDriverNames(textDoc).contains(inputName)) {
				System.out.println("Driver does not exist in file - Please try again!");
				System.out.println("");
				System.out.println("");
			} else {
				//Check if temp file already exists. If does, delete and recreate
				if (textDocReWrite.exists()) {
					textDocReWrite.delete();
					textDocReWrite.createNewFile();
				} else {
					textDocReWrite.createNewFile();
				}
				// Rewrite contents of file over to temp with driver and driver's trips deleted 
				try (Scanner readerScan = new Scanner(textDoc)) {
					try (PrintWriter writer = new PrintWriter(textDocReWrite)) {
						
						while (readerScan.hasNextLine()) {
							
							String currentLine = readerScan.nextLine();
							String[] currentLineArray = currentLine.split(",");
							//If driver is found in line, line deleted
							if (currentLineArray[0].equalsIgnoreCase("Driver") && currentLineArray[1].equals(inputName)) {
								currentLine = "";
								writer.println(currentLine);
							} else if(currentLineArray[0].equalsIgnoreCase("Trip") && currentLineArray[2].equals(inputName)){
								currentLine = "";
								writer.println(currentLine);
							}
							else {
								writer.println(currentLine);
							}
						}
					}
				}
				//delete original file, rename temp file to original file's name
				textDoc.delete();
				textDocReWrite.renameTo(textDoc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		methodExecuted = true;
		return methodExecuted;
	}

	public boolean addtrip() {
		boolean methodExecuted = false;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-kk:mm");
		Trip trip = new Trip();

		while (true) {

			// find the last id from the trips in the file and set the trip id to the nextnumber
			try {
				trip.setTripID(getNextTripId(textDoc) + 1);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			// Enter trip driver name
			System.out.println("Please enter the name of the driver: ");
			String driverName = inputScanner.nextLine();
			
			// Verify string is made of only letters, else break loop and return to menu
			if(!stringValid(driverName)) {
				System.out.println("Invalid input - Please try again!");
				System.out.println("");
				System.out.println("");
				break;
			}
			
			// Verify driver exists in file, else break loop and return to menu
			try {
				if (!getDriverNames(textDoc).contains(driverName)) {
					System.out.println("Driver does not exist in file - Please try again!");
					System.out.println("");
					System.out.println("");
					break;
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			// Add valid driver name to trip object			
			trip.setDriverName(driverName);
			
			// Enter and validate trip start time
			System.out.println("Please enter the trip start date and time (MM-DD-YYYY-HH:MM): ");
			LocalDateTime start;
			
			try {
				String[] startArray = inputScanner.nextLine().split("-");
				
				// Parse/further break down Strings from array and verify values are ints, else break loop and return to menu
				start = LocalDateTime.of(Integer.parseInt(startArray[2]), Month.of(Integer.parseInt(startArray[0])), Integer.parseInt(startArray[1]),
						Integer.parseInt(startArray[3].split(":")[0]), Integer.parseInt(startArray[3].split(":")[1]));
				
				// Add valid start time to trip object
				trip.setStartTime(start);
			}
			catch (Exception e) {
				System.out.println("Invalid input - Please try again!");
				System.out.println("");
				System.out.println("");
				e.printStackTrace();
				break;
			}

			// Enter trip end time
			System.out.println("Please enter the trip end date and time (MM-DD-YYYY-HH:MM): ");

			try {
				String[] endArray = inputScanner.nextLine().split("-");

				// Parse/further break down Strings from array and verify values are ints, else break loop and return to menu
				LocalDateTime end = LocalDateTime.of(Integer.parseInt(endArray[2]), Month.of(Integer.parseInt(endArray[0])), Integer.parseInt(endArray[1]),
						Integer.parseInt(endArray[3].split(":")[0]), Integer.parseInt(endArray[3].split(":")[1]));
				
				// Verify if end date is actually after start date, else break loop and return to menu
				if(end.isBefore(start)) {
					System.out.println("End date is before start date - Please try again!");
					System.out.println("");
					System.out.println("");
					break;
				}
				else {
					// Add valid end time to trip object
					trip.setEndTime(end);
				}
				
			}
			// If entered values not ints, return to start menu
			catch (Exception e) {
				System.out.println("Invalid input - Please try again!");
				break;
			}

			// Enter trip mileage
			System.out.println("Please enter the trip mileage; ");

			try {
				String mileageString = inputScanner.nextLine();
				if (mileageString.contains(".")) {
					trip.setMilesDriven(Double.parseDouble(mileageString));
				} else {
					trip.setMilesDriven(Integer.parseInt(mileageString));
				}
			}
			// If entered values not ints, return to start menu
			catch (Exception e) {
				System.out.println("Invalid input - Please try again!");
				System.out.println("");
				System.out.println("");
				break;
			}
			
			// Append trip to end of the input file
			try {
				FileWriter fileWriter;
				fileWriter = new FileWriter(textDoc, true);
				PrintWriter auditPrinter = new PrintWriter(fileWriter);
				auditPrinter.println("Trip,"+ trip.getTripID() + "," + trip.getDriverName() + "," + trip.getStartTime().format(formatter) + "," + trip.getEndTime().format(formatter) + "," + (int)(trip.getMilesDriven()));
				auditPrinter.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Print tripId to console verifying addition
			System.out.println("Trip,"+ trip.getTripID() + "added to the input file");
			break;
		}
		methodExecuted = true;
		return methodExecuted;
	}

	public boolean removeTrip(String inputId) {
		boolean methodExecuted = false;
		
		try {
			List<Trip> trips = getAllTrips(textDoc);
			boolean tripExisits = false;
			// Verify input is correct type
			Long tripId = Long.parseLong(inputId);

			//Check if trip with matching ID exists in file
			for (Trip t : trips) {
				if (t.getTripID() == tripId) {
					tripExisits = true;
				}
			}
			if (tripExisits == false) {
				System.out.println("Trip does not exist in file - Please try again!");
				System.out.println("");
				System.out.println("");
			} else {
				//Check if temp file already exists. If does, delete and recreate
				if (textDocReWrite.exists()) {
					textDocReWrite.delete();
					textDocReWrite.createNewFile();
				} else {
					textDocReWrite.createNewFile();
				}
				
				try (Scanner readerScan = new Scanner(textDoc)) {

					try (PrintWriter writer = new PrintWriter(textDocReWrite)) {

						while (readerScan.hasNextLine()) {
							
							String currentLine = readerScan.nextLine();
							String[] currentLineArray = currentLine.split(",");

							if (currentLineArray[0].equalsIgnoreCase("Trip") && Long.parseLong(currentLineArray[1])==(tripId)) {
								currentLine = "";
								writer.println(currentLine);
							} else {
								writer.println(currentLine);
							}
						}
					}
				}
				//delete original file, rename temp file to original file's name
				textDoc.delete();
				textDocReWrite.renameTo(textDoc);
			}
		} catch (Exception e) {
			System.out.println("Invalid input - Please try again!");
			System.out.println("");
			System.out.println("");

		}

		methodExecuted = true;
		return methodExecuted;
	}

	public boolean report() {
		boolean methodExecuted = false;
		
		List<Driver> drivers = new ArrayList<Driver>();

		try {
			List<String> names = getDriverNames(textDoc);
			List<Trip> trips = getAllTrips(textDoc);

			for (String s : names) {
				Driver driver = new Driver();
				List<Trip> driverTrips = new ArrayList<Trip>();
				driver.setName(s);
				for (Trip t : trips) {
					if (t.getDriverName().equals(s)) {
						driverTrips.add(t);
					}
				}
				driver.setAllTrips(driverTrips);
				drivers.add(driver);

			}
			
			System.out.println("Driver Report");
			for (Driver d : drivers) {
				
				if (d.getAverageMph() > 0) {
					System.out.println(d.getName() + " drove " + d.getTotalMiles() + " miles at an average speed of "
							+ d.getAverageMph() + " mph");
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		methodExecuted = true;
		return methodExecuted;
	}

	public void help() {
		System.out.println("AddDriver: Adds a driver to the program input file");
		System.out.println("RemoveDriver: Removes a driver from the program input file based on a name");
		System.out.println("AddTrip: Adds a trip to the program input file");
		System.out.println("RemoveTrip: Removes a trip from the input file based on a trip id");
		System.out.println("Report: Generate a report of each driver, their total distance traveled, and their average speed");
		System.out.println("Exit: Stop program from running");
		System.out.println("");
		System.out.println("");
	}

	private List<String> getDriverNames(File file) throws FileNotFoundException {
		List<String> drivers = new ArrayList<String>();
		try (Scanner textReader = new Scanner(textDoc)) {

			while (textReader.hasNextLine()) {
				String[] currentLine = textReader.nextLine().split(",");
				if (currentLine[0].equalsIgnoreCase("Driver")) {
					drivers.add(currentLine[1]);
				}
			}
		}

		return drivers;
	}

	private long getNextTripId(File file) throws FileNotFoundException {
		
		List<Integer> idList = new ArrayList<Integer>();

		try (Scanner textReader = new Scanner(textDoc)) {

			while (textReader.hasNextLine()) {
				String[] currentLine = textReader.nextLine().split(",");
				if (currentLine[0].equalsIgnoreCase("Trip")) {
					idList.add(Integer.parseInt(currentLine[1]));
				}
			}
		}
		Collections.sort(idList);
		return idList.get(idList.size() - 1);

	}

	private List<Trip> getAllTrips(File file) throws FileNotFoundException {
		List<Trip> trips = new ArrayList<Trip>();
		try (Scanner textReader = new Scanner(textDoc)) {

			while (textReader.hasNextLine()) {
				String[] currentLine = textReader.nextLine().split(",");
				
				if (currentLine[0].equalsIgnoreCase("Trip")) {
					
					Trip trip = new Trip();
					
					trip.setTripID(Long.parseLong(currentLine[1]));
					
					trip.setDriverName(currentLine[2]);
					String[] startTime = currentLine[3].split("-");
					
					trip.setStartTime(
							LocalDateTime.of(Integer.parseInt(startTime[2]), Month.of(Integer.parseInt(startTime[0])),
									Integer.parseInt(startTime[1]), Integer.parseInt(startTime[3].split(":")[0]),
									Integer.parseInt(startTime[3].split(":")[1])));

					String[] endTime = currentLine[4].split("-");
					trip.setEndTime(LocalDateTime.of(Integer.parseInt(endTime[2]),
							Month.of(Integer.parseInt(endTime[0])), Integer.parseInt(endTime[1]),
							Integer.parseInt(endTime[3].split(":")[0]), Integer.parseInt(endTime[3].split(":")[1])));

					trip.setMilesDriven(Double.parseDouble(currentLine[5]));
					trips.add(trip);
				}
			}
		}
		return trips;
	}

	private boolean stringValid(String str) {
		boolean isValid = false;
		
		char[] chars = str.toLowerCase().toCharArray();
		for(char c: chars) {
			if ((c >= 'a' && c <= 'z')) {
				isValid = true;
			}
		}
		return isValid;
	}
}
