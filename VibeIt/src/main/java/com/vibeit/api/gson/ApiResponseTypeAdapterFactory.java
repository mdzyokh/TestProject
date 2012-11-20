package com.vibeit.api.gson;

import android.util.Log;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.vibeit.api.ApiResponse;

import java.io.IOException;

/**
 * @author Andrii Kovalov
 */
public class ApiResponseTypeAdapterFactory implements TypeAdapterFactory {
	private static final String SUCCESS = "Success";
	private final Class<? extends ApiResponse> customizedClass;

	public ApiResponseTypeAdapterFactory(Class<? extends ApiResponse> customizedClass) {
		this.customizedClass = customizedClass;
	}

	@SuppressWarnings("unchecked") // we use a runtime check to guarantee that 'C' and 'T' are equal
	public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		return type.getRawType() == customizedClass ? (TypeAdapter<T>) createInternal(gson, (TypeToken<ApiResponse>) type) : null;
	}

	private TypeAdapter<? extends ApiResponse> createInternal(Gson gson, TypeToken<ApiResponse> type) {
		final TypeAdapter<ApiResponse> delegate = gson.getDelegateAdapter(this, type);
		final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
		return new TypeAdapter<ApiResponse>() {
			@Override
			public void write(JsonWriter out, ApiResponse value) throws IOException {
				JsonElement tree = delegate.toJsonTree(value);
				elementAdapter.write(out, tree);
			}

			@Override
			public ApiResponse read(JsonReader in) throws IOException {
				JsonElement tree = elementAdapter.read(in);
				if (tree instanceof JsonObject) {
					JsonObject json = (JsonObject) tree;
                    Log.d("vibeitlog", json.toString());
					if (json.has(SUCCESS) && json.get(SUCCESS).isJsonPrimitive()) {
						boolean success;

						try {
							success = json.get(SUCCESS).getAsBoolean();
						} catch (Exception e) {
							success = false;
						}

						ApiResponse response = new ApiResponse();
						response.setSuccess(success);
						return response;
					}
				}
				ApiResponse response = delegate.fromJsonTree(tree);
				response.setSuccess(!response.hasError());
				return response;
			}
		};
	}
}