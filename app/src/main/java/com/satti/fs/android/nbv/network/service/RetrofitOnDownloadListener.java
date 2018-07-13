package com.satti.fs.android.nbv.network.service;

import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.network.entities.Item;

import java.util.List;

/**
 * Created by satish on 11/07/18.
 */

public interface RetrofitOnDownloadListener {
    void onDownloadComplete(List<AdapterModel> nearByVenues);
}
