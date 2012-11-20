package com.vibeit.screen;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;
import com.cyberwalkabout.common.util.StringUtils;
import com.vibeit.R;
import com.vibeit.adapter.SurveyQuestionsAdapter;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.model.SurveyChoice;
import com.vibeit.api.model.SurveyReward;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.SurveyPayload;
import com.vibeit.util.Extras;

import java.util.ArrayList;

/**
 * @author Maria Dzyoh
 */
public class SurveyScreen extends AbstractActivity implements SurveyQuestionsAdapter.SurveyCompletedListener{

    private LocationDetailsPayload location;
    private SurveyPayload survey;

    private ApiResultReceiver surveyReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
           hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<SurveyReward> response = (ApiResponse<SurveyReward>) resultData.getSerializable(AbstractAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    SurveyScreen.this.finish();
                } else {
                    Toast.makeText(SurveyScreen.this, response.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(SurveyScreen.this, getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_screen);
        loadData();
        initViews();
    }

    private void loadData() {
       location = (LocationDetailsPayload)getIntent().getSerializableExtra(Extras.EXTRA_LOCATION);
       survey = (SurveyPayload)getIntent().getSerializableExtra(Extras.EXTRA_SURVEY);
    }

    private void initViews() {
       aq.id(R.id.btn_close).clicked(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
       aq.id(R.id.company_name).text(location.getLocation().getName());
       if (location.hasSurveyReward()) {
           SurveyReward reward = location.getReward()[0];
           aq.id(R.id.vibeit_reward_teaser).visible();
           aq.id(R.id.reward_info).text(reward.getName()+" ("+ StringUtils.convertToUSDollars(reward.getValue())+" value)");
       }else {
           aq.id(R.id.reward_teaser).gone();
       }
       aq.id(R.id.survey).adapter(new SurveyQuestionsAdapter(this, survey, this));
    }

    @Override
    public void onSurveyCompleted(ArrayList<SurveyChoice> choices, int surveyId) {
        showProgress();
        VibeItApiUtils.takeSurvey(this, surveyReceiver, getApp().getUserToken(), location.getLocation().getId(), surveyId, choices);
    }
}
