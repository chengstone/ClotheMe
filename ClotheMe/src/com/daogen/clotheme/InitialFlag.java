package com.daogen.clotheme;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table INITIAL_FLAG.
 */
public class InitialFlag {

    private long id;
    private Boolean InitialFlag;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public InitialFlag() {
    }

    public InitialFlag(long id) {
        this.id = id;
    }

    public InitialFlag(long id, Boolean InitialFlag) {
        this.id = id;
        this.InitialFlag = InitialFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getInitialFlag() {
        return InitialFlag;
    }

    public void setInitialFlag(Boolean InitialFlag) {
        this.InitialFlag = InitialFlag;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
