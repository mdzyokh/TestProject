package com.vibeit.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.cyberwalkabout.common.endlessadapter.EndlessAdapter;
import com.cyberwalkabout.common.endlessadapter.PageInfo;
import com.cyberwalkabout.common.endlessadapter.PageListener;
import com.cyberwalkabout.common.endlessadapter.PaginationInfo;
import com.vibeit.R;
import com.vibeit.adapter.LocationsAdapter;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.action.LoginAction;
import com.vibeit.api.model.Location;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.LocationPagePayload;
import com.vibeit.api.payload.LocationPayload;
import com.vibeit.app.VibeItApplication;
import com.vibeit.dialog.VibeItProgressDialog;
import com.vibeit.intent.IntentHelper;
import com.vibeit.model.SearchArgs;
import com.vibeit.screen.HomeScreen;


/**
 * @author Maria Dzyokh
 */
public abstract class AbstractFragment extends ListFragment implements EndlessAdapter.DataSource {

    public abstract boolean hasMenu();

    public abstract View getMenu();

    protected VibeItProgressDialog progressDialog;
    protected AQuery aq;
    protected String userToken;

    protected boolean prefsChanged;

    protected abstract void loadData(int page, String nextPageToken, boolean showProgress);

    private EndlessAdapter<LocationPagePayload> adapter;

    protected ApiResultReceiver locationsReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<LocationPayload> response = (ApiResponse<LocationPayload>) resultData.getSerializable(LoginAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    if (adapter == null) {
                        EndlessAdapter.Builder<LocationPagePayload> builder = new EndlessAdapter.Builder<LocationPagePayload>(getActivity());
                        builder.pendingResource(R.layout.pending_layout).dataSource(AbstractFragment.this);
                        builder.setPageInfo(new PageInfo<LocationPagePayload>(null, new PaginationInfo(response.getData().getPage(), response.getData().isLast(), response.getData().getNextPageToken())));
                        adapter = builder.adapter(new LocationsAdapter(getActivity(), response.getPayload().getLocations())).build();
                        setListAdapter(adapter);
                    } else if (response.getData().getLocations().length > 0) {
                        adapter.onNewPage(new PageInfo<LocationPagePayload>(response.getData().getLocations(), new PaginationInfo(response.getData().getPage(), response.getData().isLast(), response.getData().getNextPageToken())));
                    }
                } else {
                    setListAdapter(null);
                    aq.id(android.R.id.empty).text(response.getError());
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(getActivity(), getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    protected ApiResultReceiver locationDetailsReceiver = new ApiResultReceiver(new Handler(), new ApiResultReceiver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            hideProgress();
            if (AbstractAction.STATUS_SUCCESS == resultCode) {
                ApiResponse<LocationDetailsPayload> response = (ApiResponse<LocationDetailsPayload>) resultData.getSerializable(LoginAction.EXTRA_RESPONSE);
                if (!response.hasError()) {
                    IntentHelper.locationDetailsScreen(getActivity(), response.getPayload());
                } else {
                    aq.id(android.R.id.empty).text(response.getError());
                }
            }
            if (AbstractAction.STATUS_FAILURE == resultCode) {
                Toast.makeText(getActivity(), getString(R.string.unable_to_connect_message), Toast.LENGTH_SHORT).show();
            }
        }
    });

    protected VibeItApplication getApp() {
        return (VibeItApplication) getActivity().getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userToken = ((VibeItApplication) getActivity().getApplication()).getUserToken();
        progressDialog = new VibeItProgressDialog(getActivity(), "Please wait ...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_screen_content, container, false);
        aq = new AQuery(v);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (id != -1) {
            showProgress(getString(R.string.loading_location));
            Location location = ((LocationPagePayload) ((EndlessAdapter<LocationPagePayload>) getListAdapter()).getItem(position)).getLocation();
            VibeItApiUtils.getLocationDetails(getActivity(), locationDetailsReceiver, location.getServiceHash(), userToken);
        }
    }

    public boolean isPrefsChanged() {
        return this.prefsChanged;
    }

    public void setPrefsChanged(boolean arg) {
        this.prefsChanged = arg;
    }

    protected void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void requestData(int page, String nextPageToken, PageListener listener) {
        loadData(page, nextPageToken, false);
    }

    public void loadData() {
        adapter = null;
        loadData(1, "", true);
    }

    protected SearchArgs getSearchArgs() {
        return ((HomeScreen) getActivity()).getSearchArgs();
    }
}
