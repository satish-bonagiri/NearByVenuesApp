package com.satti.fs.android.nbv.frag;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.satti.fs.android.nbv.R;
import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.location.NBVLocationListener;
import com.satti.fs.android.nbv.network.client.NBVRetrofitNetworkClient;
import com.satti.fs.android.nbv.network.service.RetrofitOnDownloadListener;
import com.satti.fs.android.nbv.util.ProgressUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satish on 10/07/18.
 */

public class VenueListingFragment extends BaseFragment implements RetrofitOnDownloadListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.currentLocation_textview)
    TextView currentLocatinTextView;

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





    private void downloadFeed() {
        ProgressUtil.displayProgressDialog(getActivity(), new ProgressUtil.DialogListener() {
            @Override
            public void onButtonPressed() {

            }
        });
        NBVRetrofitNetworkClient.getNearByVenues(this);
    }

    NBVLocationListener nbvLocationListener = (location) ->{
        if(location != null)
            currentLocatinTextView.setText(location.toString());
        downloadFeed();
    };


    @Override
    public void onDownloadComplete(List<AdapterModel> nearByVenues) {
        ProgressUtil.hideProgressDialog();
    }
}
