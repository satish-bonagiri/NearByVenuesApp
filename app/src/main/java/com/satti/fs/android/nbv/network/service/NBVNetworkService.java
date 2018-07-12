package com.satti.fs.android.nbv.network.service;

import com.satti.fs.android.nbv.network.entities.FSResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by satish on 11/07/18.
 */

public interface NBVNetworkService {




//    https://api.foursquare.com/v2/venues/explore?client_id=YCTIACAUGRHECUUEOS5YWICTSLQM05LXCXAQIYMJMXZOW0PB&client_secret=KFOLXSKOXNU4U5RKJXX0PECRFJAQ2ZFY4I3XJMI0N1KAP4X3&v=20180707&ll=12.95469,77.69895

    @GET("explore")
    Call<FSResponse> getApiResponse(@Query("client_id") String clientID ,
                                      @Query("client_secret") String clientSecret,
                                      @Query("v") String newDate,
                                      @Query("ll") String latlong);

}
