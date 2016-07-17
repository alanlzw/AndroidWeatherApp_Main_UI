package com.example.akchen.main_ui.Activity;

import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.akchen.main_ui.Fragment.MainUIFragment;
import com.example.akchen.main_ui.R;
import com.example.akchen.main_ui.Adapter.SectionsPagerAdapter;
import com.example.akchen.main_ui.others.utils.WeatherDB;
import com.example.akchen.main_ui.others.widget.LocationSelectorDialogBuilder;
import java.util.ArrayList;


public class MainUIActivity extends AppCompatActivity implements View.OnClickListener, LocationSelectorDialogBuilder.OnSaveLocationLister {

    private static SectionsPagerAdapter mSectionAdapter = null;
    private static ViewPager mViewPager;
    private WeatherDB weatherDB;
    private Button main_add = null;
    private Button main_share = null;
    private LocationSelectorDialogBuilder locationBuilder;
//    private TPWeatherManager weatherManager;
    private  static ArrayList<MainUIFragment> fragmentsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        getSupportActionBar().hide();

        SectionsPagerAdapter.getLocationList().add("beijing");
       //..........

        mSectionAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionAdapter);


        main_share = (Button) findViewById(R.id.main_share);
        main_add = (Button) findViewById(R.id.main_add);

        main_add.setOnClickListener(this);
        main_share.setOnClickListener(this);
    }


    @Override
    public void onSaveLocation(String location, String provinceId, String cityId) {

        SectionsPagerAdapter.getLocationList().add("shanghai");
        //..................
        mSectionAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_share:
                System.out.println("ffffff");
                break;
            case R.id.main_add:
                if (locationBuilder == null) {
                    locationBuilder = LocationSelectorDialogBuilder.getInstance(this);
                    locationBuilder.setOnSaveLocationLister(this);
                }
                locationBuilder.show();
                break;
        }
    }
}
