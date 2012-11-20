package com.vibeit.screen;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.vibeit.R;
import com.vibeit.adapter.EmployeesAdapter;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.model.Employee;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.LocationPagePayload;
import com.vibeit.intent.IntentHelper;
import com.vibeit.util.Extras;

/**
 * @author Maria Dzyokh
 */
public class EmployeeRatingScreen extends AbstractActivity {

    private ApiResultReceiver receiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<Employee[]> response = (ApiResponse<Employee[]>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    EmployeeRatingScreen.this.finish();
                } else {
                    Toast.makeText(EmployeeRatingScreen.this, response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(EmployeeRatingScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    private LocationDetailsPayload locationDetails;
    private EmployeesAdapter employeesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_rating_screen);
        loadData();
        initViews();
    }

    private void loadData() {
        locationDetails = (LocationDetailsPayload) getIntent().getSerializableExtra(Extras.EXTRA_LOCATION);
    }

    private void initViews() {
        employeesAdapter = new EmployeesAdapter(this, locationDetails.getEmployees());
        aq.id(R.id.employees_list).adapter(employeesAdapter);
        aq.id(R.id.btn_submit_rating).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                VibeItApiUtils.rateEmployee(EmployeeRatingScreen.this, receiver, getApp().getUserToken(), locationDetails.getLocation().getId(), employeesAdapter.getEmployees());
            }
        });
        aq.id(R.id.btn_cancel).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeRatingScreen.this.finish();
            }
        });
        aq.id(R.id.company_name).text(locationDetails.getLocation().getName());
    }
}
