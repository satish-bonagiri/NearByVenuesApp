package com.satti.fs.android.nbv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.satti.fs.android.nbv.adapter.AdapterModel;
import com.satti.fs.android.nbv.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.satti.fs.android.nbv.common.Constants.INTENT_BUNDLE_DATA;

public class VenueDetailActivity extends AppCompatActivity {

    @BindView(R.id.venue_detail_name_textView)
    TextView mNameTextView;

    @BindView(R.id.venue_detail_address_textView)
    TextView mAddressTextView;

    @BindView(R.id.venue_detail_distance_textView)
    TextView mDistanceTextView;

    @BindView(R.id.venue_detail_postal_code_textView)
    TextView mPostalCodeTextView;

    @BindView(R.id.venue_detail_postal_code_label_textView)
    TextView getmPostalCodeLabelTextView;

    @BindView(R.id.venue_detail_city_textView)
    TextView mCityTextView;

    @BindView(R.id.venue_detail_state_textView)
    TextView mStateTextView;

    @BindView(R.id.venue_detail_country_textView)
    TextView mCountryTextView;

    @BindView(R.id.venue_detail_imageView)
    ImageView mVenueImageView;

    @BindView(R.id.fab)
    FloatingActionButton mFabButton;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;


    private AdapterModel adapterModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mCollapsingToolbarLayout.setTitle(" ");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            adapterModel = (AdapterModel) bundle.getSerializable(INTENT_BUNDLE_DATA);
        }

        if (adapterModel != null) {
            mNameTextView.setText(adapterModel.getVenueName());
            mAddressTextView.setText(adapterModel.getLocation().getFormattedAddress().toString());
            mDistanceTextView.setText(getResources().getString(R.string.distance_in_meters, adapterModel.getLocation().getDistance()));

            if(TextUtils.isEmpty(adapterModel.getLocation().getPostalCode())){
                mPostalCodeTextView.setVisibility(View.GONE);
                getmPostalCodeLabelTextView.setVisibility(View.GONE);
            }else{
                mPostalCodeTextView.setText(adapterModel.getLocation().getPostalCode());
                getmPostalCodeLabelTextView.setVisibility(View.VISIBLE);
                mPostalCodeTextView.setVisibility(View.VISIBLE);
            }

            mCityTextView.setText(adapterModel.getLocation().getCity());
            mStateTextView.setText(adapterModel.getLocation().getState());
            mCountryTextView.setText(adapterModel.getLocation().getCountry());

            Glide.with(this).load(adapterModel.getCategoryBgIconUrl())
                    .crossFade()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mVenueImageView);
        }


        mFabButton.setOnClickListener( (v) -> {
            openGoogleMap();
        });



        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            boolean isShow = true;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapsingToolbarLayout.setTitle(getString(R.string.venue_detail_details));
                    isShow = true;
                } else if(isShow) {
                    mCollapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void openGoogleMap() {
        double latitude = 0.0;
        double longitude = 0.0;
        if (adapterModel.getLocation() != null) {
            latitude = adapterModel.getLocation().getLat();
            longitude = adapterModel.getLocation().getLng();
        }
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latitude + "," + longitude+ " (" + adapterModel.getVenueName() + ")" );
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(Constants.MAP_PACKAGE);
        if (mapIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
