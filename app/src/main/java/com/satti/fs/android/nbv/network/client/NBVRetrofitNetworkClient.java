package com.satti.fs.android.nbv.network.client;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.common.Constants;
import com.satti.fs.android.nbv.network.entities.FSResponse;
import com.satti.fs.android.nbv.network.entities.Item;
import com.satti.fs.android.nbv.network.service.NBVNetworkService;
import com.satti.fs.android.nbv.network.service.RetrofitOnDownloadListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
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
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void getNearByVenues(final RetrofitOnDownloadListener retrofitOnDownloadListener){

        final NBVNetworkService nbvNetworkService  = NBVRetrofitNetworkClient.getClient().create(NBVNetworkService.class);

        Call<FSResponse> responseBodyCall = nbvNetworkService.getApiResponse(Constants.CLIENT_ID,Constants.CLIENT_SECRET,"20180707","12.95469,77.69895");

        responseBodyCall.enqueue(new Callback<FSResponse>() {
            @Override
            public void onResponse(@NonNull Call<FSResponse> call, @NonNull Response<FSResponse> response) {
                String jsonStr = null;
                try {
                    jsonStr = response.body().toString();
                    if(!jsonStr.isEmpty()){
                        Log.e("SATTI","Network  Response Code ::" +response.body().getMeta().getCode());
                        Log.e("SATTI","Network  Response  SIZE ::" +response.body().getResponse().getGroups().get(0).getItems().size());
                        Log.e("SATTI","Network  Response ::" +response);
                        Log.e("SATTI","Network  Response ::" +response.toString());
                        Log.e("SATTI","Network Response Body::" +response.body());
                        Log.e("SATTI","Network Response Body toString::" +response.body().toString());
                     //   Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject("dsadas");
                     //   FSResponse fsResponse = gson.fromJson(jsonObject.toString(),FSResponse.class);

                        if(response != null){
                            ArrayList<AdapterModel> adapterModels = new ArrayList<AdapterModel>();
                            //FIXME fix the logic here
                            if(response!= null){
//                                for(int i=0 ; i < 9 ;i++){
//                                    Item item = flickrModel.getItems().get(i);
//                                    AdapterModel adapterModel = new AdapterModel();
//                                    adapterModel.setUrl(item.getMedia().getM());
//                                    adapterModels.add(adapterModel);
//                                }
                                retrofitOnDownloadListener.onDownloadComplete(adapterModels);
                            }
                        }else{
                            retrofitOnDownloadListener.onDownloadComplete(null);
                        }
                    }
/*                } catch (IOException e) {
                    e.printStackTrace();
                    retrofitOnDownloadListener.onDownloadComplete(null);*/
               } catch (JSONException e) {
                    e.printStackTrace();
                    retrofitOnDownloadListener.onDownloadComplete(null);
                }
            }

            @Override
            public void onFailure(Call<FSResponse> call, Throwable t) {
                retrofitOnDownloadListener.onDownloadComplete(null);
            }
        });

    }

}
