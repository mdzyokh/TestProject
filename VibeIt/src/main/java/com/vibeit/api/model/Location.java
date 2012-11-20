package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class Location implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("service_hash")
    private String serviceHash;
    @SerializedName("corporation_id")
    private int corporationId;
    @SerializedName("brand_id")
    private int brandId;
    @SerializedName("timezone")
    private String timeZone;
    @SerializedName("status")
    private boolean status;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("zip")
    private String zipCode;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lng")
    private double longitude;
    @SerializedName("phone")
    private String phone;
    @SerializedName("website")
    private String website;
    @SerializedName("picture")
    private String pictureUrl;
    @SerializedName("goal_overall")
    private float goalOverall;
    @SerializedName("goal_speed")
    private float goalSpeed;
    @SerializedName("goal_professionalism")
    private float goalProffesionalism;
    @SerializedName("goal_cleanliness")
    private float goalCleanliness;
    @SerializedName("goal_value")
    private float goalValue;
    @SerializedName("avg_overall")
    private float avgOverall;
    @SerializedName("avg_speed")
    private float avgSpeed;
    @SerializedName("avg_proffesionalism")
    private float avgProffesionalism;
    @SerializedName("avg_cleanliness")
    private float avgCleanliness;
    @SerializedName("avg_value")
    private float avgValue;
    @SerializedName("total_overall")
    private float totalOverall;
    @SerializedName("total_speed")
    private float totalSpeed;
    @SerializedName("total_proffesionalism")
    private float totalProffesionalism;
    @SerializedName("total_cleanliness")
    private float totalCleanliness;
    @SerializedName("total_value")
    private float totalValue;
    @SerializedName("location_vibe_count")
    private int locationVibeCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceHash() {
        return serviceHash;
    }

    public void setServiceHash(String serviceHash) {
        this.serviceHash = serviceHash;
    }

    public int getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(int corporationId) {
        this.corporationId = corporationId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public float getGoalOverall() {
        return goalOverall;
    }

    public void setGoalOverall(float goalOverall) {
        this.goalOverall = goalOverall;
    }

    public float getGoalSpeed() {
        return goalSpeed;
    }

    public void setGoalSpeed(float goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    public float getGoalProffesionalism() {
        return goalProffesionalism;
    }

    public void setGoalProffesionalism(float goalProffesionalism) {
        this.goalProffesionalism = goalProffesionalism;
    }

    public float getGoalCleanliness() {
        return goalCleanliness;
    }

    public void setGoalCleanliness(float goalCleanliness) {
        this.goalCleanliness = goalCleanliness;
    }

    public float getGoalValue() {
        return goalValue;
    }

    public void setGoalValue(float goalValue) {
        this.goalValue = goalValue;
    }

    public float getAvgOverall() {
        return avgOverall;
    }

    public void setAvgOverall(float avgOverall) {
        this.avgOverall = avgOverall;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public float getAvgProffesionalism() {
        return avgProffesionalism;
    }

    public void setAvgProffesionalism(float avgProffesionalism) {
        this.avgProffesionalism = avgProffesionalism;
    }

    public float getAvgCleanliness() {
        return avgCleanliness;
    }

    public void setAvgCleanliness(float avgCleanliness) {
        this.avgCleanliness = avgCleanliness;
    }

    public float getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(float avgValue) {
        this.avgValue = avgValue;
    }

    public float getTotalOverall() {
        return totalOverall;
    }

    public void setTotalOverall(float totalOverall) {
        this.totalOverall = totalOverall;
    }

    public float getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(float totalSpeed) {
        this.totalSpeed = totalSpeed;
    }

    public float getTotalProffesionalism() {
        return totalProffesionalism;
    }

    public void setTotalProffesionalism(float totalProffesionalism) {
        this.totalProffesionalism = totalProffesionalism;
    }

    public float getTotalCleanliness() {
        return totalCleanliness;
    }

    public void setTotalCleanliness(float totalCleanliness) {
        this.totalCleanliness = totalCleanliness;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public int getLocationVibeCount() {
        return locationVibeCount;
    }

    public void setLocationVibeCount(int locationVibeCount) {
        this.locationVibeCount = locationVibeCount;
    }

    public float getAverageRating() {
        return (getGoalCleanliness()+getGoalProffesionalism()+getGoalSpeed()+getGoalValue())/4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Float.compare(location.avgCleanliness, avgCleanliness) != 0) return false;
        if (Float.compare(location.avgOverall, avgOverall) != 0) return false;
        if (Float.compare(location.avgProffesionalism, avgProffesionalism) != 0) return false;
        if (Float.compare(location.avgSpeed, avgSpeed) != 0) return false;
        if (Float.compare(location.avgValue, avgValue) != 0) return false;
        if (brandId != location.brandId) return false;
        if (corporationId != location.corporationId) return false;
        if (Float.compare(location.goalCleanliness, goalCleanliness) != 0) return false;
        if (Float.compare(location.goalOverall, goalOverall) != 0) return false;
        if (Float.compare(location.goalProffesionalism, goalProffesionalism) != 0) return false;
        if (Float.compare(location.goalSpeed, goalSpeed) != 0) return false;
        if (Float.compare(location.goalValue, goalValue) != 0) return false;
        if (id != location.id) return false;
        if (Double.compare(location.latitude, latitude) != 0) return false;
        if (locationVibeCount != location.locationVibeCount) return false;
        if (Double.compare(location.longitude, longitude) != 0) return false;
        if (status != location.status) return false;
        if (Float.compare(location.totalCleanliness, totalCleanliness) != 0) return false;
        if (Float.compare(location.totalOverall, totalOverall) != 0) return false;
        if (Float.compare(location.totalProffesionalism, totalProffesionalism) != 0) return false;
        if (Float.compare(location.totalSpeed, totalSpeed) != 0) return false;
        if (Float.compare(location.totalValue, totalValue) != 0) return false;
        if (zipCode != location.zipCode) return false;
        if (address != null ? !address.equals(location.address) : location.address != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;
        if (name != null ? !name.equals(location.name) : location.name != null) return false;
        if (phone != null ? !phone.equals(location.phone) : location.phone != null) return false;
        if (pictureUrl != null ? !pictureUrl.equals(location.pictureUrl) : location.pictureUrl != null) return false;
        if (serviceHash != null ? !serviceHash.equals(location.serviceHash) : location.serviceHash != null)
            return false;
        if (state != null ? !state.equals(location.state) : location.state != null) return false;
        if (timeZone != null ? !timeZone.equals(location.timeZone) : location.timeZone != null) return false;
        if (website != null ? !website.equals(location.website) : location.website != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (serviceHash != null ? serviceHash.hashCode() : 0);
        result = 31 * result + corporationId;
        result = 31 * result + brandId;
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zipCode != null ? phone.hashCode() : 0);
        temp = latitude != +0.0d ? Double.doubleToLongBits(latitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = longitude != +0.0d ? Double.doubleToLongBits(longitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        result = 31 * result + (goalOverall != +0.0f ? Float.floatToIntBits(goalOverall) : 0);
        result = 31 * result + (goalSpeed != +0.0f ? Float.floatToIntBits(goalSpeed) : 0);
        result = 31 * result + (goalProffesionalism != +0.0f ? Float.floatToIntBits(goalProffesionalism) : 0);
        result = 31 * result + (goalCleanliness != +0.0f ? Float.floatToIntBits(goalCleanliness) : 0);
        result = 31 * result + (goalValue != +0.0f ? Float.floatToIntBits(goalValue) : 0);
        result = 31 * result + (avgOverall != +0.0f ? Float.floatToIntBits(avgOverall) : 0);
        result = 31 * result + (avgSpeed != +0.0f ? Float.floatToIntBits(avgSpeed) : 0);
        result = 31 * result + (avgProffesionalism != +0.0f ? Float.floatToIntBits(avgProffesionalism) : 0);
        result = 31 * result + (avgCleanliness != +0.0f ? Float.floatToIntBits(avgCleanliness) : 0);
        result = 31 * result + (avgValue != +0.0f ? Float.floatToIntBits(avgValue) : 0);
        result = 31 * result + (totalOverall != +0.0f ? Float.floatToIntBits(totalOverall) : 0);
        result = 31 * result + (totalSpeed != +0.0f ? Float.floatToIntBits(totalSpeed) : 0);
        result = 31 * result + (totalProffesionalism != +0.0f ? Float.floatToIntBits(totalProffesionalism) : 0);
        result = 31 * result + (totalCleanliness != +0.0f ? Float.floatToIntBits(totalCleanliness) : 0);
        result = 31 * result + (totalValue != +0.0f ? Float.floatToIntBits(totalValue) : 0);
        result = 31 * result + locationVibeCount;
        return result;
    }
}
