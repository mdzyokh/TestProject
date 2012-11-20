package com.vibeit.api.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.model.Survey;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.LocationPagePayload;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Kovalov
 */
public class GsonRestTemplate extends RestTemplate {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	public GsonRestTemplate(Type type) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new ApiResponseTypeAdapterFactory(ApiResponse.class));
        builder.registerTypeAdapter(LocationPagePayload.class, new JsonDeserializer<LocationPagePayload>() {

            @Override
            public LocationPagePayload deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    JsonObject locationObj = jsonElement.getAsJsonObject();
                    Gson g = new Gson();
                    LocationPagePayload page = g.fromJson(jsonElement, LocationPagePayload.class);
                    if (locationObj.has("Survey")) {
                        Survey[] survey;
                        if (locationObj.get("Survey").isJsonArray()) {
                            survey = g.fromJson(locationObj.get("Survey"), new TypeToken<Survey[]>() {}.getType());
                        } else {
                            survey = new Survey[1];
                            survey[0] = g.fromJson(locationObj.get("Survey"), Survey.class);
                        }
                        page.setSurvey(survey);
                    }
                    return page;
            }
        });

        builder.registerTypeAdapter(LocationDetailsPayload.class, new JsonDeserializer<LocationDetailsPayload>() {

            @Override
            public LocationDetailsPayload deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonObject locationObj = jsonElement.getAsJsonObject();
                Gson g = new Gson();
                LocationDetailsPayload page = g.fromJson(jsonElement, LocationDetailsPayload.class);
                if (locationObj.has("Survey")) {
                    Survey[] survey;
                    if (locationObj.get("Survey").isJsonArray()) {
                        survey = g.fromJson(locationObj.get("Survey"), new TypeToken<Survey[]>() {}.getType());
                    } else {
                        survey = new Survey[1];
                        survey[0] = g.fromJson(locationObj.get("Survey"), Survey.class);
                    }
                    page.setSurvey(survey);
                }
                return page;
            }
        });

		GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter(builder.create());
		gsonConverter.setType(type);
		List<MediaType> gsonMediaTypes = new ArrayList<MediaType>();
		gsonMediaTypes.addAll(gsonConverter.getSupportedMediaTypes());
		gsonMediaTypes.add(new MediaType("text", "html", UTF_8));
		gsonConverter.setSupportedMediaTypes(gsonMediaTypes);

		getMessageConverters().add(gsonConverter);
		getMessageConverters().add(new FormHttpMessageConverter());
	}
}