package com.amdocs.pns.main;
import java.sql.SQLException;
import java.util.*;

import com.amdocs.pns.dao.*;
import com.amdocs.pns.exception.DuplicatePlantNameException;
import com.amdocs.pns.exception.PlantNotFoundException;
import com.amdocs.pns.model.Plant;
public class Main {
    public static void main(String[] args) throws SQLException, DuplicatePlantNameException, PlantNotFoundException {
        Scanner scanner = new Scanner(System.in);
        plantDAO plantDAO = new plantDAO();

        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Add new plant");
            System.out.println("2. Update plant cost");
            System.out.println("3. Delete plant");
            System.out.println("4. View all plants");
            System.out.println("5. Find plant by origin country name");
            System.out.println("6. Find outdoor plants which require sunlight");
            System.out.println("7. Count plants by water supply frequency");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            Plant p=new Plant(choice, null, null, false, null, null, choice);
            switch (choice) {
                case 1:
                	// Option 1: Add new plant
                    // Read plant details from the user
                	System.out.print("Enter plant ID: ");
                    int plantId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter plant name: ");
                    String plantName = scanner.nextLine();
                    System.out.print("Enter origin country name: ");
                    String originCountryName = scanner.nextLine();
                    System.out.print("Is sunlight required (true/false): ");
                    boolean sunlightRequired = scanner.nextBoolean();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter water supply frequency: ");
                    String waterSupplyFrequency = scanner.nextLine();
                    System.out.print("Enter plant type (indoor/outdoor): ");
                    String plantType = scanner.nextLine();
                    System.out.print("Enter plant cost: ");
                    double cost = scanner.nextDouble();

                    // Create a Plant object
                    Plant newPlant = new Plant(plantId, plantName, originCountryName, sunlightRequired, waterSupplyFrequency, plantType, cost);

                    // Call the addPlant method to insert the plant into the database
                    try {
                    	plantDAO.addPlant(plantId, newPlant);
                    }
                    catch(DuplicatePlantNameException e){
                    	System.out.println("Duplicate Name! Plant not added");
                    	System.out.println();
                    }
                    break;

                case 2:
                    // Implement updating plant cost
                	 System.out.print("Enter the plant ID to update cost: ");
                     int plantIdToUpdate = scanner.nextInt();
                     System.out.print("Enter the new cost: ");
                     double newCost = scanner.nextDouble();

                     // Call the updatePlantCost method to update the cost
                     try {
                    	 plantDAO.updatePlantCost(plantIdToUpdate, newCost);
                     }
                     catch(PlantNotFoundException e) {
                    	 System.out.println("Plant Not found! Cannot Update");
                    	 System.out.println();
                     }
                     break;
                    
                case 3:
                    // Implement deleting a plant
                	System.out.print("Enter the plant ID to delete: ");
                    int plantIdToDelete = scanner.nextInt();

                    // Call the deletePlantById method to delete the plant
                    try {
                    	plantDAO.deletePlantById(plantIdToDelete);
                    }
                    catch(PlantNotFoundException e) {
                   	 System.out.println("Plant Not found! Cannot Delete");
                   	 System.out.println();
                    }
                    break;
                case 4:
                    List<Plant> allPlants = plantDAO.getAllPlants();
                    
                    if (!allPlants.isEmpty()) {
                        System.out.println("List of all plants:");
                        for (Plant plant : allPlants) {
                            System.out.println(plant); // Assuming you have overridden the toString method in the Plant class
                        }
                    } else {
                        System.out.println("No plants found.");
                    }
                    break;
                case 5:
                    // Implement finding plants by origin country name
                	 System.out.print("Enter the origin country name to search for: ");
                     String originCountryName1 = scanner.nextLine();
                     
                     List<Plant> plantsByOriginCountry = plantDAO.getPlantsByOriginCountry(originCountryName1);
                     
                     if (!plantsByOriginCountry.isEmpty()) {
                         System.out.println("Plants from " + originCountryName1 + ":");
                         for (Plant plant : plantsByOriginCountry) {
                             System.out.println(plant); // Assuming you have overridden the toString method in the Plant class
                         }
                     } else {
                         System.out.println("No plants found from " + originCountryName1 + ".");
                     }
                     break;
                case 6:
                    // Implement finding outdoor plants requiring sunlight
                	List<Plant> outdoorPlantsWithSunlight = plantDAO.getOutdoorPlantsWithSunlight();
                    
                    if (!outdoorPlantsWithSunlight.isEmpty()) {
                        System.out.println("Outdoor plants requiring sunlight:");
                        for (Plant plant : outdoorPlantsWithSunlight) {
                            System.out.println(plant); // Assuming you have overridden the toString method in the Plant class
                        }
                    } else {
                        System.out.println("No outdoor plants requiring sunlight found.");
                    }
                    break;
                case 7:
                    // Implement counting plants by water supply frequency
                	System.out.print("Enter the water supply frequency to count (e.g., daily/weekly/alternate days): ");
                    String waterSupplyFrequency1 = scanner.nextLine();
                    
                    int plantCount = plantDAO.countPlantsByWaterSupplyFrequency(waterSupplyFrequency1);
                    
                    System.out.println("Number of plants with water supply frequency '" + waterSupplyFrequency1 + "': " + plantCount);
                    break;
                case 8:
                	 System.out.println("Exiting the Plant Nursery System.");
                     plantDAO.close(); // Close the database connection before exiting
                     System.exit(0); // Exit the program
                     break;
                 default:
                     System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}