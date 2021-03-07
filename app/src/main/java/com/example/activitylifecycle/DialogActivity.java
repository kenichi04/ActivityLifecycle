package com.example.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;

public class DialogActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ウィンドウにタイトルを表示させないオプション
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
    }

    /**
     * Callback method defined by the View
     * @param v
     */
    // レイアウトファイルで設定しているButton要素のメソッド（android:onClick="finishDialog"）
    public void finishDialog(View v) {
        DialogActivity.this.finish();
    }
}
