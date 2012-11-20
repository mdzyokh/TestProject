package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * @author Maria Dzyokh
 */
public class UserQuestion {

    @SerializedName("id")
    private int id;
    @SerializedName("order")
    private int order;
    @SerializedName("question")
    private String question;
    @SerializedName("multiple")
    private boolean multiple;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserQuestion that = (UserQuestion) o;

        if (id != that.id) return false;
        if (multiple != that.multiple) return false;
        if (order != that.order) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + order;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (multiple ? 1 : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
