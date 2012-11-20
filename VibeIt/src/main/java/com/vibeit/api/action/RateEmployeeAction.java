package com.vibeit.api.action;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.model.Employee;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;

/**
 * @author Maria Dzyokh
 */
public class RateEmployeeAction extends PostAbstractAction {

    public static final String EXTRA_USER_TOKEN = "EXTRA_USER_TOKEN";
    public static final String EXTRA_LOCATION_ID = "EXTRA_LOCATION_ID";
    public static final String EXTRA_EMPLOYEES = "EXTRA_EMPLOYEES";

    private final String userToken;
    private final int locationId;
    private final Parcelable[] employees;

    public RateEmployeeAction(Intent intent) {
        super(intent);
        this.userToken = intent.getStringExtra(EXTRA_USER_TOKEN);
        this.locationId = intent.getIntExtra(EXTRA_LOCATION_ID, 0);
        this.employees = intent.getParcelableArrayExtra(EXTRA_EMPLOYEES);
    }

    @Override
    protected MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("user_token", userToken);
        params.add("location_id", String.valueOf(locationId));
        params.add("employees", composeEmployeesJSON());
        return params;
    }

    @Override
    protected String getUrl() {
        return "/api/locations/rateEmployee";
    }

    @Override
    protected Type getType() {
        return new TypeToken<ApiResponse<Employee[]>>() {}.getType();
    }

    private String composeEmployeesJSON() {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < employees.length; i++) {
                Employee item = (Employee)employees[i];
                JSONObject employee = new JSONObject();
                employee.put("location_employee_id", item.getId());
                employee.put("rating", item.getUserRating());
                employee.put("comment", item.getUserComment());
                array.put(employee);
            }
        } catch (JSONException e) {
            Log.e(RateEmployeeAction.class.getSimpleName(), e.getMessage(), e);
        }
        return array.toString();
    }
}
