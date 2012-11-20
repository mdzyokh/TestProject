package com.vibeit.api.action;

import android.content.Intent;
import android.util.Log;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.gson.GsonRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 * @author Andrii Kovalov
 */
public abstract class GetAbstractAction extends AbstractAction<ApiResponse> {

	protected GetAbstractAction(Intent intent) {
		super(intent);
	}

	protected abstract MultiValueMap<String, String> getParams();

	@Override
	protected void doInBackground() {
		GsonRestTemplate restTemplate = new GsonRestTemplate(getType());
		try {
			ResponseEntity<ApiResponse> response;
			MultiValueMap<String, String> params = getParams();
			if (params != null && !params.isEmpty()) {
				response = restTemplate.getForEntity(getHost() + getUrl(), ApiResponse.class, params);
			} else {
				response = restTemplate.getForEntity(getHost() + getUrl(), ApiResponse.class);
			}
			success(response.getBody());
		} catch (RestClientException e) {
			// network error
			String message = e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getMessage();
			Log.e(TAG, message);
			failure(message);
		} catch (Exception e) {
			// unexpected exception
			Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getMessage());
		}
	}
}
