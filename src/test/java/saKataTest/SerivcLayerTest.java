package saKataTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import saKata.ServiceLayer;

public class SerivcLayerTest {

	private ServiceLayer service;
	private File testDoc = new File("Test.txt");;
	private Scanner readScanner;


	@Before
	public void setUp() {
		try {
			service = new ServiceLayer("Test");
				
			readScanner = new Scanner(testDoc);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void addDriver() {
		
		assertTrue(service.addDriver("Dave"));
		assertTrue(service.addDriver("Chris"));
		assertTrue(service.addDriver("Patrick"));
		boolean hasDave = false;
		boolean hasChris = false;
		boolean hasPatrick = false;
		
		while(readScanner.hasNextLine()) {
			String[] currentLine = readScanner.nextLine().split(",");
			if(currentLine.length>1) {
				if(currentLine[1].equals("Dave")) {
					hasDave = true;
				}else if(currentLine[1].equals("Chris")) {
					hasChris = true;
				}else if(currentLine[1].equals("Patrick")) {
					hasPatrick = true;
				}
			}
		}
		assertTrue(hasDave);
		assertTrue(hasChris);
		assertTrue(hasPatrick);
	}
	
	@Test
	public void removeTrip() {
		
		boolean hasTrip = false;
		
		while(readScanner.hasNextLine()) {
			String[] currentLine = readScanner.nextLine().split(",");
			if(currentLine.length>1) {
				if(currentLine[1].equals("1")) {
					hasTrip = true;
				}
			}
		}
		assertTrue(hasTrip);
		
		service.removeTrip("4");
	
		boolean noTrip = true;
		while(readScanner.hasNextLine()) {
			String[] currentLine = readScanner.nextLine().split(",");
			if(currentLine.length>1) {
				if(currentLine[1].equals("1")) {
					noTrip = false;
				}
			}
		}
		assertTrue(noTrip);
		
	}
	
	
@Test
	
	public void removeDrivers() {
		service.addDriver("Test1");
	
		service.removeDriver("Test1");
		
		boolean noChris = true;
		
		while(readScanner.hasNextLine()) {
			String[] currentLine = readScanner.nextLine().split(",");
			if(currentLine.length>1) {
				if(currentLine[1].equals("Chris")) {
					noChris = false;
				}
			}
			
		}
		assertTrue(noChris);
		
	}

	
	
	
	
	
}
