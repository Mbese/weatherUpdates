package com.example.weatherupdates.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.weatherupdates.R;
import com.google.android.material.tabs.TabLayout;

public class WeatherDetailsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tablayout);

        pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new MondayFragment(), getString(R.string.monday));
        pagerAdapter.addFragment(new TuesdayFragment(), getString(R.string.tuesday));
        pagerAdapter.addFragment(new WednesdayFragment(), getString(R.string.wednesday));
        pagerAdapter.addFragment(new ThursdayFragment(), getString(R.string.thursday));
        pagerAdapter.addFragment(new FridayFragment(), getString(R.string.friday));
        pagerAdapter.addFragment(new SaturdayFragment(), getString(R.string.saturday));
        pagerAdapter.addFragment(new SundayFragment(), getString(R.string.sunday));

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
