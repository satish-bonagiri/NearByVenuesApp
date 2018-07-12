package com.satti.fs.android.nbv.frag;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.satti.fs.android.nbv.R;
import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.location.NBVLocationListener;
import com.satti.fs.android.nbv.network.client.NBVRetrofitNetworkClient;
import com.satti.fs.android.nbv.network.entities.Item;
import com.satti.fs.android.nbv.network.service.RetrofitOnDownloadListener;
import com.satti.fs.android.nbv.util.DateUtil;
import com.satti.fs.android.nbv.util.ProgressUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satish on 10/07/18.
 */

public class VenueListingFragment extends BaseFragment implements RetrofitOnDownloadListener{

    private static final String TAG = VenueListingFragment.class.getSimpleName();

    @BindView(R.id.venues_recyclerView)
    RecyclerView mVenuesRecyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.no_near_by_venues_layout)
    LinearLayout mNoNearByVenuesLayout;

    @BindView(R.id.no_near_by_venues_textView)
    TextView mNoNearByVenuesTextView;

    ProgressUtil progressUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root =  inflater.inflate(R.layout.fragment_venu_listing, container, false);
        ButterKnife.bind(this, root);
        setLocationListener(nbvLocationListener);


        return root;
    }


    private void downloadFeed(String langlat) {
        progressUtil = new ProgressUtil();
        progressUtil.displayProgressDialog(getActivity(), () ->{ },getString(R.string.please_wait_msg));
        NBVRetrofitNetworkClient.getNearByVenues(this,DateUtil.currentDate(),langlat);
    }

    NBVLocationListener nbvLocationListener = (location) ->{
        if(location != null){
            //build the lat,long string
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(location.getLatitude());
            stringBuilder.append(",");
            stringBuilder.append(location.getLongitude());

            downloadFeed(stringBuilder.toString());
        }else{
            Log.e(TAG,"Not able to read the current location ");
            updateUIOnError(getString(R.string.location_not_available));
        }
    };


    @Override
    public void onDownloadComplete(List<AdapterModel> nearByVenuesItemList) {
        progressUtil.hideProgressDialog();
        if(nearByVenuesItemList.size() == 0){
            updateUIOnError(getString(R.string.venues_not_available));
        }else{
            mNoNearByVenuesLayout.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
    }


    private void updateUIOnError(String errorMsg){
        mNoNearByVenuesTextView.setText(errorMsg);
        mNoNearByVenuesLayout.setVisibility(View.VISIBLE);
        mRefreshLayout.setVisibility(View.GONE);
    }

}
