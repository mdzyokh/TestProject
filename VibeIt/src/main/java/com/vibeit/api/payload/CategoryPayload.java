package com.vibeit.api.payload;

import com.google.gson.annotations.SerializedName;
import com.vibeit.api.model.Category;

/**
 * @author Andrii Kovalov
 */
public class CategoryPayload extends BasicPayload {
	@SerializedName("Category")
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
