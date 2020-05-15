package saKata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class SafeAutoKataCLI {

	
	private static Scanner inputScanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		
		SafeAutoKataCLI cli = new SafeAutoKataCLI();
		
		boolean continueRunning = true;
		
		while(continueRunning) {
			
			cli.displayMenuOptions();
			continueRunning = cli.chooseMenuOptions(inputScanner.nextLine());
			
		}
	}
	
	private void displayMenuOptions() {
		
		System.out.println("");
		System.out.println("You have the following options:");
		System.out.println("AddDriver");
		System.out.println("RemoveDriver");
		System.out.println("AddTrip");
		System.out.println("RemoveTrip");
		System.out.println("Report");
		System.out.println("Help");
		System.out.println("Exit");
		System.out.println("");
		System.out.println("Please enter desired option:");
		System.out.println("");
		
	}
	
	private boolean chooseMenuOptions (String input) {
		boolean continueRunning = true;
		ServiceLayer service = new ServiceLayer("TripsAndDriver");
		
		if (input.equalsIgnoreCase("AddDriver")) {
			System.out.println("Please enter the name of the driver to add: ");
			
			service.addDriver(inputScanner.nextLine());
			
		}else if (input.equalsIgnoreCase("RemoveDriver")) {
			System.out.println("Please enter the name of the driver to remove: ");
			
			service.removeDriver(inputScanner.nextLine());
			
		}else if (input.equalsIgnoreCase("AddTrip")) {
			
			service.addtrip();
			
		}else if (input.equalsIgnoreCase("RemoveTrip")) {
			System.out.println("Please enter the id of the trip to remove: ");
			
			service.removeTrip(inputScanner.nextLine());
			
		}else if (input.equalsIgnoreCase("Report")) {
			
			service.report();
			
		}else if(input.equalsIgnoreCase("Help")) {
			
			service.help();
			
		}else if(input.equalsIgnoreCase("Exit")) {
			
			continueRunning = false;
		}
		
		return continueRunning;
	}
	
	
}
