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
import com.satti.fs.android.nbv.location.NBVLocationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by satish on 10/07/18.
 */

public class VenueListingFragment extends BaseFragment{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.currentLocation_textview)
    TextView currentLocatinTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root =  inflater.inflate(R.layout.fragment_venu_listing, container, false);
        ButterKnife.bind(this, root);
        setLocationListener(nbvLocationListener);
        return root;
    }


    NBVLocationListener nbvLocationListener = (location) ->{
        if(location != null)
            currentLocatinTextView.setText(location.toString());

    };





}
