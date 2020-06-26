package com.diandou.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.diandou.fragment.MineLikeFragment;
import com.diandou.fragment.MineWorkFragment;

import java.util.ArrayList;
import java.util.List;

public class MinePagerAdapter extends FragmentPagerAdapter {

    private List<String> titles = new ArrayList<>();

    public void addTitle(String title) {
        titles.add(title);
    }

    public MinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MineWorkFragment();
        } else if (position == 1) {
            return new MineLikeFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && position <= titles.size()) {
            return titles.get(position);
        }
        return super.getPageTitle(position);
    }

    // 动态设置我们标题的方法
    public void setPageTitle(int position, String title) {
        if (position >= 0 && position < titles.size()) {
            titles.set(position, title);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }
}