package com.framwork.common.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Des:  fix notifyDataSetChanged 不生效
 * ====================
 */
@SuppressWarnings({"unchecked",
        "unused",
        "WeakerAccess",
        "SameParameterValue"})
public class SimpleRefreshFragmentPageAdapter<T extends Fragment> extends FragmentPagerAdapter implements IViewPagerAdapterHelper<T, SimpleRefreshFragmentPageAdapter<T>> {


    private List<T> fragments = new ArrayList<>();

    private List<CharSequence> titles = new ArrayList<>();

    private FragmentManager mFragmentManager;

    public SimpleRefreshFragmentPageAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    public SimpleRefreshFragmentPageAdapter(FragmentManager fm, List<T> fragments, List<CharSequence> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null || titles.size() < 1) {
            return null;
        }
        if (position <= titles.size() - 1) {
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
    public SimpleRefreshFragmentPageAdapter<T> addTitle(CharSequence charSequence) {
        titles.add(charSequence);
        return this;
    }

    @Override
    public SimpleRefreshFragmentPageAdapter<T> addItem(T item) {
        fragments.add(item);
        return this;
    }

    @Override
    public SimpleRefreshFragmentPageAdapter<T> addItemTitles(List<CharSequence> itemTitles) {
        titles = itemTitles;
        return this;
    }

    @Override
    public SimpleRefreshFragmentPageAdapter<T> addItems(List<T> items) {
        fragments = items;
        return this;
    }


    public void resetList(List<T> items, List<CharSequence> itemTitles) {
        if (this.fragments != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (Fragment f : this.fragments) {
                fragmentTransaction.remove(f);
            }
            fragmentTransaction.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
        this.fragments = items;
        this.titles = itemTitles;
        notifyDataSetChanged();
    }

    public void resetList(List<T> items) {
        if (this.fragments != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (Fragment f : this.fragments) {
                fragmentTransaction.remove(f);
            }
            fragmentTransaction.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
        this.fragments = items;
        this.titles.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}
