package com.vibeit.api.action;

import android.content.Intent;
import android.util.Log;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.gson.GsonRestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 * @author Andrii Kovalov
 */
public abstract class PostAbstractAction extends AbstractAction<ApiResponse> {
	protected PostAbstractAction(Intent intent) {
		super(intent);
	}

	protected abstract MultiValueMap<String, String> getParams();

	@Override
	protected void doInBackground() {
		GsonRestTemplate restTemplate = new GsonRestTemplate(getType());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(getParams(), headers);
        Log.d("vibeitlog", requestEntity.getBody().toString());
		try {
			ResponseEntity<ApiResponse> response = restTemplate.exchange(getHost() + getUrl(), HttpMethod.POST, requestEntity, ApiResponse.class);
            success(response.getBody());
		} catch (RestClientException e) {
			// network error
			String message = e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getMessage();
			Log.e(TAG, message);
			failure(message);
		} catch (Exception e) {
			// unexpected exception
            String message = e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getMessage();
			Log.e(TAG, message);
            failure(message);
		}
	}
}
