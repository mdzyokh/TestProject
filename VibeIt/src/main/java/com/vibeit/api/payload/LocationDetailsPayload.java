package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;
import com.vibeit.api.model.Employee;
import com.vibeit.api.model.LocationVibe;
import com.vibeit.api.model.User;
import com.vibeit.api.model.VibeAuthor;

/**
 * @author Maria Dzyokh
 */
public class LocationDetailsPayload extends LocationPagePayload {

    @SerializedName("LocationEmployee")
    private Employee[] employees;
    @SerializedName("LocationVibe")
    private LocationVibe[] vibes;
    @SerializedName("User")
    private VibeAuthor[] authors;

    public Employee[] getEmployees() {
        return employees;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }

    public LocationVibe[] getVibes() {
        return vibes;
    }

    public void setVibes(LocationVibe[] vibes) {
        this.vibes = vibes;
    }

    public VibeAuthor[] getAuthors() {
        return authors;
    }

    public void setAuthors(VibeAuthor[] authors) {
        this.authors = authors;
    }

    public boolean hasEmployees() {
        return employees.length > 0;
    }

    public int getRewardsCount() {
        return reward!=null?reward.length:0;
    }
}
