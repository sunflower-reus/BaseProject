package com.reus.basedemo.main.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.reus.basedemo.R;
import com.reus.basedemo.common.base.BaseActivity;
import com.reus.basedemo.databinding.ActivityMainBinding;
import com.reus.basedemo.utils.FragmentFactory;

/**
 * Created by reus on 2021/3/4.
 * 整体架构：MVVM + DataBinding.  JetPack组件库.
 */
public class MainAct extends BaseActivity implements View.OnClickListener{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        changeSelectedTabState(0);
        initBottomItemClick();
        initMainViewPager();
    }

    /**
     * 初始化ViewPage.
     */
    private void initMainViewPager() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        //给ViewPage设置适配器.
        binding.mainViewpager.setAdapter(adapter);
        //设置默认缓存的页数.
        binding.mainViewpager.setOffscreenPageLimit(3);
        //使得ViewPage不可左右滑动.
        binding.mainViewpager.setNoScroll(true);
    }

    /**
     * 初始化底部Tab点击事件.
     */
    private void initBottomItemClick() {
        binding.home.setOnClickListener(this);
        binding.list.setOnClickListener(this);
        binding.mine.setOnClickListener(this);
    }

    /**
     * 底部导航栏被选中的 选项.
     *
     * @param position
     */
    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                //首页.
                binding.home.setTextColor(Color.parseColor("#0BA8C3"));
                binding.list.setTextColor(Color.parseColor("#000000"));
                binding.mine.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                //列表.
                binding.home.setTextColor(Color.parseColor("#000000"));
                binding.list.setTextColor(Color.parseColor("#0BA8C3"));
                binding.mine.setTextColor(Color.parseColor("#000000"));
                break;
            case 2:
                //我的.
                binding.home.setTextColor(Color.parseColor("#000000"));
                binding.list.setTextColor(Color.parseColor("#000000"));
                binding.mine.setTextColor(Color.parseColor("#0BA8C3"));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                changeSelectedTabState(0);
                binding.mainViewpager.setCurrentItem(0);
                break;
            case R.id.list:
                changeSelectedTabState(1);
                binding.mainViewpager.setCurrentItem(1);
                break;
            case R.id.mine:
                changeSelectedTabState(2);
                binding.mainViewpager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    /**
     * Fragment适配器   是状态发生改变的适配器.   ViewPage的滑动.
     */
    static class ViewPageAdapter extends FragmentStatePagerAdapter {
        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);//创建相应的Fragment.
        }

        @Override
        public int getCount() {
            return 3;//返回导航栏按钮的数量。
        }
    }
}