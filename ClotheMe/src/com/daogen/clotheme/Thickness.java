package com.daogen.clotheme;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table Thickness.
 */
public class Thickness {

    private long id;
    private String Thickness;
    private Integer Temperature;
    private String Whether;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Thickness() {
    }

    public Thickness(long id) {
        this.id = id;
    }

    public Thickness(long id, String Thickness, Integer Temperature, String Whether) {
        this.id = id;
        this.Thickness = Thickness;
        this.Temperature = Temperature;
        this.Whether = Whether;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThickness() {
        return Thickness;
    }

    public void setThickness(String Thickness) {
        this.Thickness = Thickness;
    }

    public Integer getTemperature() {
        return Temperature;
    }

    public void setTemperature(Integer Temperature) {
        this.Temperature = Temperature;
    }

    public String getWhether() {
        return Whether;
    }

    public void setWhether(String Whether) {
        this.Whether = Whether;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
