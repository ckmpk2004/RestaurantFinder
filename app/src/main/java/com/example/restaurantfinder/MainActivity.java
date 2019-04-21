package com.example.restaurantfinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragments;

    //Fragments related layout
    private LinearLayout tabRestaurant;
    private LinearLayout tabStoredList;

    //Image of tab buttons
    private ImageButton restaurantImage;
    private ImageButton storedListImage;

    private TextView restaurantText;
    private TextView storedListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        connectViews();
        tabClickEvent();
        createFragments();
    }

    //Create 2 Fragment into list
    private void createFragments() {
        fragments = new ArrayList<>();

        fragments.add(new restaurantFragment()); //Page Restaurant
        fragments.add(new storedListFragment()); //Page Stored List

        //Init Fragment adapter
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            //Count number of Existing Fragments
            @Override
            public int getCount() {
                return fragments.size();
            }

        };
        //View Pager Adapter
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                viewPager.setCurrentItem(position);
                resetText();
                resetBackground();
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //Tab click event handler
    private void tabClickEvent() {
        tabRestaurant.setOnClickListener(this);
        tabStoredList.setOnClickListener(this);
    }

    //Create Views connection to layout
    private void connectViews() {
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        tabRestaurant = (LinearLayout) findViewById(R.id.tab_Restaurant);
        tabStoredList = (LinearLayout) findViewById(R.id.tab_StoredList);

        restaurantImage = (ImageButton) findViewById(R.id.tab_Restaurant_image);
        storedListImage = (ImageButton) findViewById(R.id.tab_StoredList_image);

        restaurantText = (TextView) findViewById(R.id.tab_Restaurant_text);
        storedListText = (TextView) findViewById(R.id.tab_StoredList_text);

    }

    @Override
    public void onClick(View v) {
        //Reset Tab Color
        resetText();
        resetBackground();

        //Select Tabs
        switch (v.getId()) {
            case R.id.tab_Restaurant:
                selectTab(0);
                break;
            case R.id.tab_StoredList:
                selectTab(1);
                break;
        }
    }

    private void selectTab(int i) {
        //Current using Tab will show different color
        switch (i) {
            case 0:
                restaurantText.setTextColor(getResources().getColor(R.color.limegreen));
                tabRestaurant.setBackgroundColor(getResources().getColor(R.color.slateblue));
                break;
            case 1:
                storedListText.setTextColor(getResources().getColor(R.color.limegreen));
                tabStoredList.setBackgroundColor(getResources().getColor(R.color.slateblue));
                break;
        }

        //Change view by selecting Tab
        viewPager.setCurrentItem(i);
    }

    //Reset Tab Text color to White
    private void resetText() {
        restaurantText.setTextColor(getResources().getColor(R.color.white));
        storedListText.setTextColor(getResources().getColor(R.color.white));
    }
    //Reset Tab backgroud color to origin blue
    private void resetBackground() {
        tabRestaurant.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        tabStoredList.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
    }
}