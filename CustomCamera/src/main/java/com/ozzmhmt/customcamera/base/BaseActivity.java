package com.ozzmhmt.customcamera.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;


public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onBefore();
        super.onCreate(savedInstanceState);
        initialize();
/*
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
        tintManager.setTintColor(getResources().getColor(R.color.title_bg));*/
/*// set a custom navigation bar resource
        tintManager.setNavigationBarTintResource(R.drawable.my_tint);
// set a custom status bar drawable
        tintManager.setStatusBarTintDrawable(MyDrawable);*/

        initView();
        initData();
        onAfter();
    }


    protected void onBefore() {
    }


    protected abstract void initialize();


    protected abstract void initView();


    protected void initData() {
    }


    protected void onAfter() {
    }


    /**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onPressBack()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @return
     */
    protected boolean onPressBack() {
        return false;
    }
}
