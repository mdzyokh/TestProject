package com.vibeit.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class UserToken implements Serializable {
	@SerializedName("id")
	private int id;
	@SerializedName("user_id")
	private int userId;
	@SerializedName("token")
	private String token;
	@SerializedName("expires")
	private String expires;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserToken userToken = (UserToken) o;

		if (id != userToken.id) return false;
		if (userId != userToken.userId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + userId;
		return result;
	}
}
