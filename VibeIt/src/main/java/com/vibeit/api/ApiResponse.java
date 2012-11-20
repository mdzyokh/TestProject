package com.vibeit.api;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrii Kovalov
 */
public class ApiResponse<T extends Serializable> implements Serializable {
	@SerializedName("Success")
	private T data;
	@SerializedName("Error")
	private String error;
	private transient boolean success;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getPayload() {
		return data;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean hasError() {
		return !TextUtils.isEmpty(error);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
