package com.daogen.clotheme;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table Style.
 */
public class Style {

    private long id;
    private String Style;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Style() {
    }

    public Style(long id) {
        this.id = id;
    }

    public Style(long id, String Style) {
        this.id = id;
        this.Style = Style;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String Style) {
        this.Style = Style;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}