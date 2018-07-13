package com.satti.fs.android.nbv.network.client;

import android.support.annotation.NonNull;

import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.common.Constants;
import com.satti.fs.android.nbv.network.entities.Category;
import com.satti.fs.android.nbv.network.entities.FSResponse;
import com.satti.fs.android.nbv.network.entities.Item;
import com.satti.fs.android.nbv.network.entities.Meta;
import com.satti.fs.android.nbv.network.service.NBVNetworkService;
import com.satti.fs.android.nbv.network.service.RetrofitOnDownloadListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by satish on 11/07/18.
 */

public class NBVRetrofitNetworkClient {

    public static final String BASE_URL = "https://api.foursquare.com/v2/venues/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void getNearByVenues(final RetrofitOnDownloadListener retrofitOnDownloadListener,String currentDate,String langlat) {

        final NBVNetworkService nbvNetworkService = NBVRetrofitNetworkClient.getClient().create(NBVNetworkService.class);

        Call<FSResponse> responseBodyCall = nbvNetworkService.getApiResponse(Constants.CLIENT_ID, Constants.CLIENT_SECRET, currentDate, langlat);

        responseBodyCall.enqueue(new Callback<FSResponse>() {
            @Override
            public void onResponse(@NonNull Call<FSResponse> call, @NonNull Response<FSResponse> response) {

                Meta metaData = response.body().getMeta();
                if (metaData != null) {
                    int stausCode = metaData.getCode();
                    if (stausCode == 200) {
                        List<Item> itemList = response.body().getResponse().getGroups().get(0).getItems();
                        ArrayList<AdapterModel> adapterModels = new ArrayList<AdapterModel>();
                        if(itemList != null){
                            for(int i=0 ; i < itemList.size() ;i++){
                                AdapterModel adapterModel = new AdapterModel();
                                adapterModel.setVenueName(itemList.get(i).getVenue().getName());
                                adapterModel.setLocation(itemList.get(i).getVenue().getLocation());
                                Category category = itemList.get(i).getVenue().getCategories().get(0);
                                adapterModel.setCategoryIconUrl(category.getIcon().getPrefix()+"88"+ category.getIcon().getSuffix());
                                adapterModel.setCategoryBgIconUrl(category.getIcon().getPrefix()+"bg_88"+category.getIcon().getSuffix());
                                adapterModels.add(adapterModel);
                            }
                            retrofitOnDownloadListener.onDownloadComplete(adapterModels);
                        }else{
                            retrofitOnDownloadListener.onDownloadComplete(null);
                        }
                    } else {
                        retrofitOnDownloadListener.onDownloadComplete(null);
                    }
                } else {
                    retrofitOnDownloadListener.onDownloadComplete(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FSResponse> call, @NonNull Throwable t) {
                retrofitOnDownloadListener.onDownloadComplete(null);
                t.printStackTrace();
            }
        });

    }

}
