package com.example.activitylifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.activitylifecycle.util.StatusTracker;
import com.example.activitylifecycle.util.Utils;

public class ActivityA extends Activity {

    private String mActivityName;
    private TextView mTextView;       // 一番上のTextView -> 末尾に変数recreateCountを表示する
    private TextView mStatusView;     // イベントの呼び出しの履歴を表示
    private TextView mStatusAllView;  // 現在のアクティビティのステータスを表示
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    private int recreateCount;
    static final String RECREATE_COUNT = "recreateCount";   // 文字列保存のためのキー

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        mActivityName = getString(R.string.activity_a);
        mTextView = (TextView) findViewById(R.id.text_view_a);
        mStatusView = (TextView) findViewById(R.id.status_view_a);
        mStatusAllView = (TextView) findViewById(R.id.status_view_all_a);
        // 現在のon_createの文字列をステータスとして設定
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_create));
        // 各TextViewに表示
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    // アクティビティ再作成された際に呼び出される
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        recreateCount = savedInstanceState.getInt(RECREATE_COUNT);
        recreateCount++;
        mTextView.setText(getString(R.string.activity_a) + " " + recreateCount);
    }

    // アクティビティ破壊時にデータを保存するためのメソッド. 削除時に呼び出され、ここではrecreateCountの値がBundleに保存される
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);より前に保存処理を実行する必要がある
        outState.putInt(RECREATE_COUNT, recreateCount);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_restart));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));
        mStatusTracker.clear();
    }

    // レイアウトファイルで設定しているButton要素のメソッド（onClick）
    public void startDialog(View v) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    public void startActivityB(View v) {
        Intent intent = new Intent(ActivityA.this, ActivityB.class);
        startActivity(intent);
    }

    public void startActivityC(View v) {
        Intent intent = new Intent(ActivityA.this, ActivityC.class);
        startActivity(intent);
    }

    public void finishActivityA(View v) {
        ActivityA.this.finish();
    }

}