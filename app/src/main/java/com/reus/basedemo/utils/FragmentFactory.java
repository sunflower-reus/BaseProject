package com.reus.basedemo.utils;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;

import com.reus.basedemo.modules.home.view.HomeFragment;
import com.reus.basedemo.modules.list.view.ListFragment;
import com.reus.basedemo.modules.mine.view.MineFragment;

/**
 * Fragment的工厂.
 */
public class FragmentFactory {
    public static SparseArray<Fragment> fragments = new SparseArray<>();
    private static final int Home_FRAGMENT = 0;
    private static final int List_FRAGMENT = 1;
    private static final int Mine_FRAGMENT = 2;

    //创建Fragment对象.
    public static Fragment createFragment(int position) {
        Fragment mFragment = fragments.get(position);

        if (mFragment == null) {
            switch (position) {
                case Home_FRAGMENT:
                    mFragment = new HomeFragment();
                    break;
                case List_FRAGMENT:
                    mFragment = new ListFragment();
                    break;
                case Mine_FRAGMENT:
                    mFragment = new MineFragment();
                    break;
            }
            fragments.put(position, mFragment);
        }
        return mFragment;
    }
}