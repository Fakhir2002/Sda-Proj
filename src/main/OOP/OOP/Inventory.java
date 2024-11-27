package OOP;

import Database.Inventory_Handler;

public class Inventory {
    private int inventoryID;
    private String hospital;
    private int medQuantity;
    private int stockQuantity;
    private int miscellaniousQuantity;

    // Constructor
    public Inventory(int inventoryID, String hospital, int medQuantity, int stockQuantity, int miscellaniousQuantity) {
        this.inventoryID = inventoryID;
        this.hospital = hospital;
        this.medQuantity = medQuantity;
        this.stockQuantity = stockQuantity;
        this.miscellaniousQuantity = miscellaniousQuantity;
    }

    // Getters and Setters
    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getHospitalID() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getMedQuantity() {
        return medQuantity;
    }

    public void setMedQuantity(int medQuantity) {
        this.medQuantity = medQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getMiscellaniousQuantity() {
        return miscellaniousQuantity;
    }

    public void setMiscellaniousQuantity(int miscellaniousQuantity) {
        this.miscellaniousQuantity = miscellaniousQuantity;
    }

    // Override toString() method for easy printing
    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryID=" + inventoryID +
                ", hospitalID=" + hospital +
                ", medQuantity=" + medQuantity +
                ", stockQuantity=" + stockQuantity +
                ", miscellaniousQuantity=" + miscellaniousQuantity +
                '}';
    }
    public static void addInventory(String hospital,int medStock, int stockStock, int miscStock) {
        // Calling the static method insertInventory from Inventory_Handler
        Inventory_Handler.insertInventory(hospital,medStock, stockStock, miscStock);
    }
}
