package com.satti.fs.android.nbv.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.satti.fs.android.nbv.R;

import java.util.List;

/**
 * Created by satish on 13/07/18.
 */

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {


    private Context mContext;
    private List<AdapterModel> adapterModelList;
    private Resources resources;

    public VenuesAdapter(Context context,List<AdapterModel> adapterModels,Resources resources){
        this.mContext = context;
        this.adapterModelList = adapterModels;
        this.resources = resources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.venu_card_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdapterModel adapterModel = adapterModelList.get(position);
        holder.venueNameTextView.setText(adapterModel.getVenueName());

        holder.distanceTextView.setText(resources.getString(R.string.distance_in_meters, adapterModel.getLocation().getDistance()));

        Glide.with(mContext).load(adapterModel.getCategoryBgIconUrl())
                .crossFade()
                .into(holder.categoryImageView);

    }

    @Override
    public int getItemCount() {
        return adapterModelList.size();
    }

    public void setVenusList(List<AdapterModel> nearByVenuesItemList) {
        adapterModelList = nearByVenuesItemList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView venueNameTextView, distanceTextView;
        public ImageView categoryImageView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            venueNameTextView = (TextView)v.findViewById(R.id.venue_name_textView);
            distanceTextView = (TextView)v.findViewById(R.id.distance_textView);
            categoryImageView = (ImageView)v.findViewById(R.id.venue_thumbnail_view);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    private static ClickListener clickListener;
    public void setOnItemClickListener(ClickListener clickListener) {
        VenuesAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }


}
