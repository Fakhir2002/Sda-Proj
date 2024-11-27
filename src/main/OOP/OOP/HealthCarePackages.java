package OOP;

import Database.HealthCarePackage_Handler;
import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.List;

public class HealthCarePackages {
    private  StringProperty name;
    private  StringProperty hospitalName;
    private  ObjectProperty<LocalDate> startDate;
    private  ObjectProperty<LocalDate> endDate;
    private  DoubleProperty price;
    private  StringProperty description;
    HealthCarePackage_Handler packageHandler;

    public HealthCarePackages(String name, String hospitalName, LocalDate startDate, LocalDate endDate, double price, String description) {
        this.name = new SimpleStringProperty(name);
        this.hospitalName = new SimpleStringProperty(hospitalName);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
    }

    public HealthCarePackages() {
        packageHandler=new HealthCarePackage_Handler();

    }

    // Properties
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty hospitalNameProperty() {
        return hospitalName;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Getters
    public String getName() {
        return name.get();
    }

    public String getHospitalName() {
        return hospitalName.get();
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getDescription() {
        return description.get();
    }

    // Setters
    public void setName(String name) {
        this.name.set(name);
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName.set(hospitalName);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public List<HealthCarePackages>  getAllPackages() {
        return packageHandler.getAllPackages();
    }
}
