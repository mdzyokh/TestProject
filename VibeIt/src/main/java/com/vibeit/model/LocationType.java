package com.vibeit.model;

/**
 * @author Maria Dzyokh
 */
public enum LocationType {

    LOCAL_VIBES("LOCAL\nVIBES"), TOP_PERFORMERS("TOP\nPERFORMERS"), RISING_STARS("RISING\nSTARS"), MY_VIBES("MY\nVIBES");

    private final String name;

    private LocationType(String name) {this.name = name;}

    public String getName()
    {
        return name;
    }
}
