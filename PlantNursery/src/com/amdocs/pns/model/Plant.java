package com.amdocs.pns.model;

public class Plant {
    private int plantId;
    private String plantName;
    private String originCountryName;
    private boolean sunlightRequired;
    private String waterSupplyFrequency;
    private String plantType;
    private double cost;

    public Plant(int plantId, String plantName, String originCountryName, boolean sunlightRequired,
                 String waterSupplyFrequency, String plantType, double cost) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.originCountryName = originCountryName;
        this.sunlightRequired = sunlightRequired;
        this.waterSupplyFrequency = waterSupplyFrequency;
        this.plantType = plantType;
        this.cost = cost;
    }

    
	public int getPlantId() {
		return plantId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getOriginCountryName() {
		return originCountryName;
	}

	public void setOriginCountryName(String originCountryName) {
		this.originCountryName = originCountryName;
	}

	public boolean isSunlightRequired() {
		return sunlightRequired;
	}

	public void setSunlightRequired(boolean sunlightRequired) {
		this.sunlightRequired = sunlightRequired;
	}

	public String getWaterSupplyFrequency() {
		return waterSupplyFrequency;
	}

	public void setWaterSupplyFrequency(String waterSupplyFrequency) {
		this.waterSupplyFrequency = waterSupplyFrequency;
	}

	public String getPlantType() {
		return plantType;
	}

	public void setPlantType(String plantType) {
		this.plantType = plantType;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}


	@Override
	public String toString() {
		return "Plant [plantId=" + plantId + ", plantName=" + plantName + ", originCountryName=" + originCountryName
				+ ", sunlightRequired=" + sunlightRequired + ", waterSupplyFrequency=" + waterSupplyFrequency
				+ ", plantType=" + plantType + ", cost=" + cost + "]";
	}
	
}