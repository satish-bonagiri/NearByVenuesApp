package com.satti.fs.android.nbv;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.satti.fs.android.nbv.frag.VenueListingFragment;

public class MainActivity extends AppCompatActivity {

    private static final String VenueListingFragmentTag = "VenueListingFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment venueListingFragment = getSupportFragmentManager().findFragmentByTag(VenueListingFragmentTag);

        if (venueListingFragment == null) {
            venueListingFragment = new VenueListingFragment();
        }
        FragmentTransaction fragmentTx = getSupportFragmentManager().beginTransaction();
        fragmentTx.replace(R.id.fragment_container, venueListingFragment, VenueListingFragmentTag);
        fragmentTx.commitAllowingStateLoss();
    }
}
