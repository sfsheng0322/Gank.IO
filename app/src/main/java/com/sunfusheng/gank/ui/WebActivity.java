package com.sunfusheng.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.util.CollectionUtil;
import com.sunfusheng.gank.util.MoreActionHelper;
import com.sunfusheng.gank.widget.WebViewLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author by sunfusheng on 2017/1/22.
 */
public class WebActivity extends BaseActivity {

    @BindView(R.id.webViewLayout)
    WebViewLayout webViewLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    public static void startActivity(Context context, GankItem gank) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("gank", gank);
        context.startActivity(intent);
    }

    private GankItem gank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        gank = (GankItem) getIntent().getSerializableExtra("gank");

        toolbar.setTitle(gank.desc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webViewLayout.loadUrl(gank.url);
    }

    @Override
    protected void setStatusBarTranslucent(boolean isLightStatusBar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_more_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item_check_image);
        if (!CollectionUtil.isEmpty(gank.images)) {
            menuItem.setVisible(true);
            menuItem.setTitle(getString(R.string.tip_check_image, gank.images.size()));
        } else {
            menuItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_check_image:
                ImagesActivity.open(this, gank.images);
                return true;
            case R.id.item_copy_url:
                MoreActionHelper.copy(this, gank.url);
                return true;
            case R.id.item_share:
                MoreActionHelper.share(this, gank.desc + "\n" + gank.url);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewLayout.onPause();
    }

    @Override
    protected void onDestroy() {
        webViewLayout.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewLayout.goBack()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
