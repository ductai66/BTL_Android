package com.tai06.dothetai.danhba.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tai06.dothetai.danhba.Adapter.ViewAdapter;
import com.tai06.dothetai.danhba.Fragment.Frag_HistoryCall;
import com.tai06.dothetai.danhba.Fragment.Frag_ListPhone;
import com.tai06.dothetai.danhba.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_paper);
        adapter = new ViewAdapter(getSupportFragmentManager());

        //Add Fragment
        adapter.AddFragment(new Frag_ListPhone(),"");
        adapter.AddFragment(new Frag_HistoryCall(),"");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_people);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history);
    }
}
