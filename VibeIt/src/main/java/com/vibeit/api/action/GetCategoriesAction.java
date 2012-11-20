package com.vibeit.api.action;

import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.payload.CategoryPayload;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Andrii Kovalov
 */
public class GetCategoriesAction extends GetAbstractAction {

	public GetCategoriesAction(Intent intent) {
		super(intent);
	}

	@Override
	protected String getUrl() { return "/api/categories/getCategories"; }

	@Override
	protected Type getType() {
		return new TypeToken<ApiResponse<CategoryPayload[]>>() {}.getType();
	}

	@Override
	protected MultiValueMap<String, String> getParams() { return null; }
}
