package com.amdocs.pns.dao;
import oracle.net.aso.p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.amdocs.pns.exception.DuplicatePlantNameException;
import com.amdocs.pns.exception.PlantNotFoundException;
import com.amdocs.pns.model.*;

public class plantDAO {
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	public plantDAO()
	{	
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver"); //registration 
			con=DriverManager.getConnection("Jdbc:Oracle:thin:@localhost:1521:orcl","scott","tiger"); //connection
			stmt=con.createStatement();
			rs=stmt.executeQuery("Select plantid,plantname,origincountryname,sunlightrequired,watersupplyfrequency,planttype,cost from plant");

//			while(rs.next())
//				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getBoolean(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getDouble(7));		
			}
		catch(Exception e){e.printStackTrace();}}

			 public int addPlant(int plantId, Plant plant) throws DuplicatePlantNameException {
        
				 if (isDuplicatePlantName(plant.getPlantName())) {
			            throw new DuplicatePlantNameException("Duplicate plant name: " + plant.getPlantName());
			        }
				 
				 String insertSql = "INSERT INTO Plant (plantId, plantName, originCountryName, sunlightRequired, waterSupplyFrequency, plantType, cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement insertStatement = con.prepareStatement(insertSql)) {
            insertStatement.setInt(1, plantId);
            insertStatement.setString(2, plant.getPlantName());
            insertStatement.setString(3, plant.getOriginCountryName());
            insertStatement.setBoolean(4, plant.isSunlightRequired());
            insertStatement.setString(5, plant.getWaterSupplyFrequency());
            insertStatement.setString(6, plant.getPlantType());
            insertStatement.setDouble(7, plant.getCost());
            
            // Execute the INSERT statement
            int rowsAffected = insertStatement.executeUpdate();
            
            if (rowsAffected == 1) {
                return plantId; // Return the specified plantId
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related errors here
        }
        
        // Return -1 to indicate failure
        return -1;
    }
			 public boolean updatePlantCost(int plantId, double newCost) throws PlantNotFoundException {
				 
				 if (!plantExists(plantId)) {
			            throw new PlantNotFoundException("Plant with ID " + plantId + " not found. Cannot update cost.");
			        }
			        String updateSql = "UPDATE Plant SET cost = ? WHERE plantId = ?";
			        
			        try (PreparedStatement updateStatement = con.prepareStatement(updateSql)) {
			            updateStatement.setDouble(1, newCost);
			            updateStatement.setInt(2, plantId);
			            
			            // Execute the UPDATE statement
			            int rowsAffected = updateStatement.executeUpdate();
			            
			            return rowsAffected > 0; // If rowsAffected > 0, the update was successful
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        
			        return false; // Update failed
			    }
			 public boolean deletePlantById(int plantId) throws PlantNotFoundException {
				 
				 if (!plantExists(plantId)) {
			            throw new PlantNotFoundException("Plant with ID " + plantId + " not found. Cannot update cost.");
			        }
			        String deleteSql = "DELETE FROM Plant WHERE plantId = ?";
			        
			        try (PreparedStatement deleteStatement = con.prepareStatement(deleteSql)) {
			            deleteStatement.setInt(1, plantId);
			            
			            // Execute the DELETE statement
			            int rowsAffected = deleteStatement.executeUpdate();
			            
			            return rowsAffected > 0; // If rowsAffected > 0, the deletion was successful
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        
			        return false; // Deletion failed
			    }
			 public List<Plant> getAllPlants() {
			        List<Plant> plants = new ArrayList<>();
			        String selectSql = "SELECT * FROM Plant";
			        
			        try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
			            ResultSet resultSet = selectStatement.executeQuery();
			            
			            while (resultSet.next()) {
			                int plantId = resultSet.getInt("plantId");
			                String plantName = resultSet.getString("plantName");
			                String originCountryName = resultSet.getString("originCountryName");
			                boolean sunlightRequired = resultSet.getBoolean("sunlightRequired");
			                String waterSupplyFrequency = resultSet.getString("waterSupplyFrequency");
			                String plantType = resultSet.getString("plantType");
			                double cost = resultSet.getDouble("cost");
			                
			                Plant plant = new Plant(plantId, plantName, originCountryName, sunlightRequired, waterSupplyFrequency, plantType, cost);
			                plants.add(plant);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        
			        return plants;
			    }
			 public List<Plant> getPlantsByOriginCountry(String originCountryName) {
			        List<Plant> plants = new ArrayList<>();
			        String selectSql = "SELECT * FROM Plant WHERE originCountryName = ?";
			        
			        try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
			            selectStatement.setString(1, originCountryName);
			            
			            ResultSet resultSet = selectStatement.executeQuery();
			            
			            while (resultSet.next()) {
			                int plantId = resultSet.getInt("plantId");
			                String plantName = resultSet.getString("plantName");
			                boolean sunlightRequired = resultSet.getBoolean("sunlightRequired");
			                String waterSupplyFrequency = resultSet.getString("waterSupplyFrequency");
			                String plantType = resultSet.getString("plantType");
			                double cost = resultSet.getDouble("cost");
			                
			                Plant plant = new Plant(plantId, plantName, originCountryName, sunlightRequired, waterSupplyFrequency, plantType, cost);
			                plants.add(plant);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        
			        return plants;
			    }
			 public List<Plant> getOutdoorPlantsWithSunlight() {
			        List<Plant> plants = new ArrayList<>();
			        String selectSql = "SELECT * FROM Plant WHERE plantType = 'outdoor' AND sunlightRequired = 1";
			        
			        try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
			            ResultSet resultSet = selectStatement.executeQuery();
			            
			            while (resultSet.next()) {
			                int plantId = resultSet.getInt("plantId");
			                String plantName = resultSet.getString("plantName");
			                String originCountryName = resultSet.getString("originCountryName");
			                String waterSupplyFrequency = resultSet.getString("waterSupplyFrequency");
			                double cost = resultSet.getDouble("cost");
			                
			                Plant plant = new Plant(plantId, plantName, originCountryName, true, waterSupplyFrequency, "outdoor", cost);
			                plants.add(plant);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        
			        return plants;
			    }
			 public int countPlantsByWaterSupplyFrequency(String waterSupplyFrequency) {
			        int count = 0;
			        String selectSql = "SELECT COUNT(*) FROM Plant WHERE waterSupplyFrequency = ?";
			        
			        try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
			            selectStatement.setString(1, waterSupplyFrequency);
			            
			            ResultSet resultSet = selectStatement.executeQuery();
			            
			            if (resultSet.next()) {
			                count = resultSet.getInt(1);
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        
			        return count;
			    }
			 public void close() {
			        if (con != null) {
			            try {
			                con.close();
			                System.out.println("Database connection closed.");
			            } catch (SQLException e) {
			                e.printStackTrace();
			                // Handle any errors that occur during connection closing
			            }
			        }
			    }

			 private boolean isDuplicatePlantName(String plantName) {
			        String selectSql = "SELECT COUNT(*) FROM Plant WHERE plantName = ?";
			        try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
			            selectStatement.setString(1, plantName);
			            ResultSet resultSet = selectStatement.executeQuery();
			            if (resultSet.next()) {
			                int count = resultSet.getInt(1);
			                return count > 0; // If count is greater than 0, it's a duplicate
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        return false; // If there was an error or no duplicate found, return false
			    }
			 private boolean plantExists(int plantId) {
			        String selectSql = "SELECT COUNT(*) FROM Plant WHERE plantId = ?";
			        try (PreparedStatement selectStatement = con.prepareStatement(selectSql)) {
			            selectStatement.setInt(1, plantId);
			            ResultSet resultSet = selectStatement.executeQuery();
			            if (resultSet.next()) {
			                int count = resultSet.getInt(1);
			                return count > 0; // If count is greater than 0, the plant exists
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			            // Handle any database-related errors here
			        }
			        return false; // If there was an error or no plant with the specified ID was found, return false
			    }


}
