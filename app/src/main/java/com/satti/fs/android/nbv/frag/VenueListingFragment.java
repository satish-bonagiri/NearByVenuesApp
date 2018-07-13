package com.satti.fs.android.nbv.frag;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.satti.fs.android.nbv.R;
import com.satti.fs.android.nbv.VenueDetailActivity;
import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.adapter.VenuesAdapter;
import com.satti.fs.android.nbv.location.NBVLocationListener;
import com.satti.fs.android.nbv.network.client.NBVRetrofitNetworkClient;
import com.satti.fs.android.nbv.network.service.RetrofitOnDownloadListener;
import com.satti.fs.android.nbv.util.DateUtil;
import com.satti.fs.android.nbv.util.ProgressUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.satti.fs.android.nbv.common.Constants.INTENT_BUNDLE_DATA;

/**
 * Created by satish on 10/07/18.
 */

public class VenueListingFragment extends BaseFragment implements RetrofitOnDownloadListener {

    private static final String TAG = VenueListingFragment.class.getSimpleName();

    @BindView(R.id.venues_recyclerView)
    RecyclerView mVenuesRecyclerView;

    @BindView(R.id.no_near_by_venues_layout)
    LinearLayout mNoNearByVenuesLayout;

    @BindView(R.id.no_near_by_venues_textView)
    TextView mNoNearByVenuesTextView;

    ProgressUtil progressUtil;

    private VenuesAdapter mVenuesAdapter;
    List<AdapterModel> adapterModelList;
    private ViewMode selectedViewMode = ViewMode.GRID;

    enum ViewMode {
        GRID, LIST, STAGGERED_GRID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_venu_listing, container, false);
        ButterKnife.bind(this, root);
        setLocationListener(nbvLocationListener);
        setHasOptionsMenu(true);
        adapterModelList = new ArrayList<>();
        mVenuesAdapter = new VenuesAdapter(getActivity(), adapterModelList, getResources());
        retainUserSelectedViewMode(selectedViewMode);
        mVenuesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        VenuesAdapter.ClickListener clickListener = (position, v) -> {
            Intent detailIntent = new Intent(getActivity(), VenueDetailActivity.class);
            detailIntent.putExtra(INTENT_BUNDLE_DATA, adapterModelList.get(position));
            startActivity(detailIntent);
        };
        mVenuesAdapter.setOnItemClickListener(clickListener);
        mVenuesRecyclerView.setAdapter(mVenuesAdapter);
        return root;
    }


    private void downloadFeed(String langlat) {
        progressUtil = new ProgressUtil();
        progressUtil.displayProgressDialog(getActivity(), getString(R.string.please_wait_msg));
        NBVRetrofitNetworkClient.getNearByVenues(this, DateUtil.currentDate(), langlat);
    }

    NBVLocationListener nbvLocationListener = (location) -> {
        if (location != null) {
            //build the lat,long string
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(location.getLatitude());
            stringBuilder.append(",");
            stringBuilder.append(location.getLongitude());

            if (adapterModelList != null && adapterModelList.size() > 0) {
                //data already is there !
            } else {
                downloadFeed(stringBuilder.toString());
            }
        } else {
            updateUIOnError(getString(R.string.location_not_available));
        }
    };


    @Override
    public void onDownloadComplete(List<AdapterModel> nearByVenuesItemList) {
        progressUtil.hideProgressDialog();
        if (nearByVenuesItemList.size() == 0) {
            updateUIOnError(getString(R.string.venues_not_available));
        } else {
            mNoNearByVenuesLayout.setVisibility(View.GONE);
            mVenuesRecyclerView.setVisibility(View.VISIBLE);

            adapterModelList.addAll(nearByVenuesItemList);
            mVenuesAdapter.setVenusList(adapterModelList);
            mVenuesAdapter.notifyDataSetChanged();
        }
    }


    private void updateUIOnError(String errorMsg) {
        mNoNearByVenuesTextView.setText(errorMsg);
        mNoNearByVenuesLayout.setVisibility(View.VISIBLE);
        mVenuesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
            mVenuesRecyclerView.setLayoutManager(mLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            retainUserSelectedViewMode(selectedViewMode);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.venue_list_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sort_by_distance:
                Collections.sort(adapterModelList, (t1, t2) -> {
                    int d1 = t1.getLocation().getDistance();
                    int d2 = t2.getLocation().getDistance();
                    return d1 - d2;
                });
                mVenuesAdapter.notifyDataSetChanged();
                return true;

            case R.id.menu_grid_view:
                selectedViewMode = ViewMode.GRID;
                retainUserSelectedViewMode(ViewMode.GRID);
                return true;

            case R.id.menu_list_view:
                selectedViewMode = ViewMode.LIST;
                retainUserSelectedViewMode(ViewMode.LIST);
                return true;

            case R.id.menu_Staggered_grid:
                selectedViewMode = ViewMode.STAGGERED_GRID;
                retainUserSelectedViewMode(ViewMode.STAGGERED_GRID);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void retainUserSelectedViewMode(ViewMode selectedViewMode) {
        switch (selectedViewMode) {
            case GRID:
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mVenuesRecyclerView.setLayoutManager(mLayoutManager);
                break;
            case LIST:
                RecyclerView.LayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                mVenuesRecyclerView.setLayoutManager(mLinearLayoutManager);
                break;
            case STAGGERED_GRID:
                RecyclerView.LayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, 1);
                mVenuesRecyclerView.setLayoutManager(mStaggeredLayoutManager);
                break;
            default:
                break;
        }
    }

}
