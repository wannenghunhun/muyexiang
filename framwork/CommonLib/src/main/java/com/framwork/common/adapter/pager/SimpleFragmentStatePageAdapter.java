package com.framwork.common.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ====================
 */
@SuppressWarnings({"unchecked",
        "unused",
        "WeakerAccess",
        "SameParameterValue"})
public class SimpleFragmentStatePageAdapter<T extends Fragment> extends FragmentStatePagerAdapter implements IViewPagerAdapterHelper<T, SimpleFragmentStatePageAdapter<T>> {


    private List<T> fragments = new ArrayList<>();

    private List<CharSequence> titles = new ArrayList<>();

    private FragmentManager mFragmentManager;

    public SimpleFragmentStatePageAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    public SimpleFragmentStatePageAdapter(FragmentManager fm, List<T> fragments, List<CharSequence> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(titles == null || titles.size() < 1) {
            return null;
        }
        if(position <= titles.size() - 1) {
            return titles.get(position);
        }
        return null;
    }

    @Override
    public T getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public SimpleFragmentStatePageAdapter<T> addTitle(CharSequence charSequence) {
        titles.add(charSequence);
        return this;
    }

    @Override
    public SimpleFragmentStatePageAdapter<T> addItem(T item) {
        fragments.add(item);
        return this;
    }

    @Override
    public SimpleFragmentStatePageAdapter<T> addItemTitles(List<CharSequence> itemTitles) {
        titles = itemTitles;
        return this;
    }

    @Override
    public SimpleFragmentStatePageAdapter<T> addItems(List<T> items) {
        fragments = items;
        return this;
    }
}
