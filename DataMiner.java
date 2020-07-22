/**
 * Program to do basic data mining
 * Data obtained from https://data.cityofnewyork.us/Health/Popular-Baby-Names/25th-nujf
 * Columns used - Gender, Ethnicity, Child's first name, Count
 * Rows used - data with year 2016
 */


package edu.nyu.cs.jhp489;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class DataMiner {

	/**
	 * method to turn string into a 2d array
	 * @param data in a string form
	 * @return data in a 2d array
	 */
	public static String[][] getDataset(String data) {
		String[] row = data.split("\n");
		String[][] dataset = new String[row.length][];
		
		for (int j=0; j < row.length; j++) {
			dataset[j] = row[j].split(",");
		}
		return dataset;
	}
	
	/**
	 * method to obtain the index of an inner array value corresponding to user input
	 * @param 2d array
	 * @return index of the first inner array(which is the title row), -1 if the user input is not found 
	 */
	public static int getIndex(String[][] dataset) {
		System.out.print("Enter the category (gender, ethnicity, child's first name) you would like to search by: ");
		Scanner scn = new Scanner(System.in);
		String input = scn.nextLine().toLowerCase();
		for (int i=0; i < dataset[0].length; i++) {
			if (input.equals(dataset[0][i].toLowerCase())){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * method to obtain the 2d array only containing the values that the user asks for
	 * @param 2d array from the file
	 * @return 2d array only containing the values that the user searches for
	 */
	public static String[][] getFinalData(String[][] dataset) {
		int index = getIndex(dataset);
		System.out.print("Enter what " + dataset[0][index].toLowerCase() + " you would like to search by: ");
		Scanner scn = new Scanner(System.in);
		String input2 = scn.nextLine().toLowerCase();
		String finalString = "";
		for (int i=1; i < dataset.length; i++) {
			if (dataset[i][index].toLowerCase().equals(input2)) {
				for (int j=0; j < dataset[i].length; j++) {
					if (j < dataset[i].length - 1) {
					finalString += dataset[i][j] + ",";
				} else {
					finalString += dataset[i][j];
				}
				}
				finalString += "\n";

			}
		}
		String [][] finalData= getDataset(finalString);
		return finalData;
	}
	
	/**
	 * method to print in format
	 * @param the final 2d array only containing the values that the user searched for
	 */
	public static void print(String[][] dataset) {
		System.out.println("Welcome to the Popular Baby Name Finder App.");
		System.out.println("The data is obtained from https://data.cityofnewyork.us/Health/Popular-Baby-Names/25th-nujf");
		System.out.println("We show you information of the babies born in 2016 by gender, ethnicity, or name.");
		System.out.println();
		
		String[][] finalData = getFinalData(dataset);
		System.out.println(finalData.length - 1 + " results found.");
		
		System.out.println("The columns are " + Arrays.toString(dataset[0]) + ".");
		System.out.println("\n");
		Scanner scn = new Scanner(System.in);
		
		for (int i=1; i < finalData.length; i++) {
			//to print "Showing results ..." before the first value of each page
			if (i % 10 == 1) {
				if (i + 9 > finalData.length - 1) {
				System.out.println("Showing results " + i + "-" + (finalData.length - 1) + "\n");
				} else {
					System.out.println("Showing results " + i + "-" + (i + 9) + "\n");
				}
				for (String info: finalData[i]) {
					System.out.printf("%-30s", info);
				}
				System.out.println();
			//to print "Press enter ..." at the last value of each page
			} else if (i % 10 == 0) {
				for (String info: finalData[i]) {
					System.out.printf("%-30s", info);
				}
				System.out.println("\n");
				System.out.println("Press enter to view next page: ");
				System.out.println();
				String input3 = scn.nextLine().toLowerCase();
				// finish the program if the user does not want to go to the next page
				if (!input3.equals("")) {
					System.out.println("You typed in something. The program will terminate.");
					return;
				}
			} else {
				for (String info: finalData[i]) {
					System.out.printf("%-30s", info);
				}
				System.out.println();
			}
		}
			
			}
	
	/**
	 * main method that controls the flow of the program
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String data = "";
		Scanner scn = new Scanner(System.in);
		System.out.print("What is the file name? ");
		String fileName = scn.nextLine();
		//turn the csv file into a String
		try {
			Scanner file = new Scanner(new File(fileName));
			while (file.hasNextLine()) {
				data += file.nextLine() + "\n";
			}
			file.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println("File is not found");
			return;
		}
		
		String[][] dataset = getDataset(data);
		dataset[0][0] = "Gender"; // the very first value from the csv was getting read weirdly, so I had to hard code it in...
		
		print(dataset);
	}

}
