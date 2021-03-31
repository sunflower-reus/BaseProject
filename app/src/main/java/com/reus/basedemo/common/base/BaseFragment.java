package com.reus.basedemo.common.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.reus.basedemo.R;
import com.views.tools.utils.PermissionCheck;


/**
 * Description: Fragment基类
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheck.getInstance().onRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }

    public void open(Fragment f1, Fragment f2, int layoutId) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().hide(f1).setCustomAnimations(R.anim.common_in, R.anim.commen_no).
                add(layoutId, f2).commit();
    }

    public void remove() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().setCustomAnimations(R.anim.commen_no, R.anim.common_out).remove(this).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getFocus() {
        getView().setFocusable(true);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    remove();
                    return true;// 未处理
                }
                return false;
            }
        });
    }
}