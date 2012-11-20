package com.vibeit.api.model;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Maria Dzyokh
 */
public class LocationVibe implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("location_id")
    private int locationId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("overall")
    private float overall;
    @SerializedName("speed")
    private float speed;
    @SerializedName("professionalism")
    private float professionalism;
    @SerializedName("cleanliness")
    private float cleanliness;
    @SerializedName("value")
    private float value;
    @SerializedName("comment")
    private String comment;
    @SerializedName("public_reply")
    private String publicReply;
    @SerializedName(" private_reply")
    private String privateReply;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getOverall() {
        return overall;
    }

    public void setOverall(float overall) {
        this.overall = overall;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getProfessionalism() {
        return professionalism;
    }

    public void setProfessionalism(float professionalism) {
        this.professionalism = professionalism;
    }

    public float getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(float cleanliness) {
        this.cleanliness = cleanliness;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublicReply() {
        return publicReply;
    }

    public void setPublicReply(String publicReply) {
        this.publicReply = publicReply;
    }

    public String getPrivateReply() {
        return privateReply;
    }

    public void setPrivateReply(String privateReply) {
        this.privateReply = privateReply;
    }

    public boolean hasComment() {
        return !TextUtils.isEmpty(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationVibe that = (LocationVibe) o;

        if (Float.compare(that.cleanliness, cleanliness) != 0) return false;
        if (id != that.id) return false;
        if (locationId != that.locationId) return false;
        if (Float.compare(that.overall, overall) != 0) return false;
        if (Float.compare(that.professionalism, professionalism) != 0) return false;
        if (Float.compare(that.speed, speed) != 0) return false;
        if (userId != that.userId) return false;
        if (Float.compare(that.value, value) != 0) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (privateReply != null ? !privateReply.equals(that.privateReply) : that.privateReply != null) return false;
        if (publicReply != null ? !publicReply.equals(that.publicReply) : that.publicReply != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + locationId;
        result = 31 * result + userId;
        result = 31 * result + (overall != +0.0f ? Float.floatToIntBits(overall) : 0);
        result = 31 * result + (speed != +0.0f ? Float.floatToIntBits(speed) : 0);
        result = 31 * result + (professionalism != +0.0f ? Float.floatToIntBits(professionalism) : 0);
        result = 31 * result + (cleanliness != +0.0f ? Float.floatToIntBits(cleanliness) : 0);
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (publicReply != null ? publicReply.hashCode() : 0);
        result = 31 * result + (privateReply != null ? privateReply.hashCode() : 0);
        return result;
    }
}
