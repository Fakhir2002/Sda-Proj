package com.example.proj;

import com.example.temp.DB_HANDLER.Inventory_Handler;

public class Inventory {
    private int inventoryID;
    private int hospitalID;
    private int medQuantity;
    private int stockQuantity;
    private int miscellaniousQuantity;

    // Constructor
    public Inventory(int inventoryID, int hospitalID, int medQuantity, int stockQuantity, int miscellaniousQuantity) {
        this.inventoryID = inventoryID;
        this.hospitalID = hospitalID;
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

    public int getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(int hospitalID) {
        this.hospitalID = hospitalID;
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
                ", hospitalID=" + hospitalID +
                ", medQuantity=" + medQuantity +
                ", stockQuantity=" + stockQuantity +
                ", miscellaniousQuantity=" + miscellaniousQuantity +
                '}';
    }
    public static void addInventory(int medStock, int stockStock, int miscStock) {
        // Calling the static method insertInventory from Inventory_Handler
        Inventory_Handler.insertInventory(medStock, stockStock, miscStock);
    }
}
