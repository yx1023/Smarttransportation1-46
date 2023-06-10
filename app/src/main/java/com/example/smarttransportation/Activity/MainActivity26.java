package com.example.smarttransportation.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smarttransportation.Fragment.T_26_1;
import com.example.smarttransportation.Fragment.T_26_2;
import com.example.smarttransportation.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity26 extends AppCompatActivity {

    private ImageView mMune17;
    private ViewPager mVp;
    private TextView mYuan1;
    private TextView mYuan2;
    MyAdapter adapter;
    List<Fragment> list = new ArrayList<>();
    T_26_1 t_26_1 = new T_26_1();
    T_26_2 t_26_2 = new T_26_2();
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main26);
        initView();
        adapter = new MyAdapter(this.getSupportFragmentManager(), list);
        mVp.setOffscreenPageLimit(2);
        mVp.setAdapter(adapter);
    }

    private void initView() {
        mMune17 = (ImageView) findViewById(R.id.mune17);
        mVp = (ViewPager) findViewById(R.id.vp);
        mYuan1 = (TextView) findViewById(R.id.yuan_1);
        mYuan2 = (TextView) findViewById(R.id.yuan_2);
        mText = (TextView) findViewById(R.id.text);

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mYuan1.setBackgroundResource(R.drawable.ag1);
                    mYuan2.setBackgroundResource(R.drawable.ag2);
                    mText.setText("有无”重复违章记录的车辆“的占比统计");
                } else {
                    mYuan1.setBackgroundResource(R.drawable.ag2);
                    mYuan2.setBackgroundResource(R.drawable.ag1);
                    mText.setText("违章车辆的违章次数占比分布图统计");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        list.add(t_26_1);
        list.add(t_26_2);

    }

    class MyAdapter extends FragmentPagerAdapter {

        List<Fragment> list = new ArrayList<>();

        public MyAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}