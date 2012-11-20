package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class User implements Serializable {
	@SerializedName("id")
	private int id;
	@SerializedName("status")
	private Status status;
	@SerializedName("email")
	private String email;
	@SerializedName("first_name")
	private String firstName;
	@SerializedName("last_name")
	private String lastName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}

	public enum Status {
		Active
	}
}
