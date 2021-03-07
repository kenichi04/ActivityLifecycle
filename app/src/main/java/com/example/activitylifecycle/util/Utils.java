package com.example.activitylifecycle.util;

import android.os.Handler;
import android.widget.TextView;

import java.util.List;

public class Utils {

    private static StatusTracker mStatusTracker = StatusTracker.getInstance();

    /**
     * Helper method to print out the lifecycle state of each Activity.
     * Note this has been wrapped in a Handler to delay the output due to overlaps
     * in lifecycle state changes as one Activity launches another.
     * @link https://developer.android.com/guide/topics/fundamentals/activities.heml#CoordinatingActivities
     * @param viewMethods TextView to list out the lifecycle methods called
     * @param viewStatus TextView to list out the status of all Activity classes
     */
    // StatusTrackerから取得した現在のアクティビティの状態と、過去のメソッド呼び出し履歴をそれぞれ表示する
    // ※変数にfinal修飾子をつける事で、匿名内部クラス内スコープから外側のスコープの変数を参照できるようになる
    // ※final修飾子を付けると再代入できない変数となる他、ここでは各TextViewにfinalをつけて、Runnableの匿名内部クラス内から参照している
    public static void printStatus(final TextView viewMethods, final TextView viewStatus) {
        // Handler: AndroidのUIスレッドに割り込みを行われるクラス. ここでは750ミリ秒に処理させる
        Handler handler = new Handler();
        // Runnable: HandlerのpostDelayedにて、指定されたミリ秒後に、runメソッドが呼ばれるクラス
        handler.postDelayed(new Runnable() {
            // 匿名内部クラスの定義
            @Override
            public void run() {
                // Get the stack of Activity lifecycle methods called and print to TextView
                StringBuilder sbMethods = new StringBuilder();
                List<String> listMethods = mStatusTracker.getMethodList();
                for (String method : listMethods) {
                    sbMethods.insert(0, method + "\n");
                }
                if(viewMethods != null) {
                    viewMethods.setText(sbMethods.toString());
                }

                // Get the status of all Activity classes and print to TextView
                StringBuilder sbStatus = new StringBuilder();
                for (String key : mStatusTracker.keySet()) {
                    sbStatus.insert(0, key + ": " + mStatusTracker.getStatus(key) + "\n");
                }
                if (viewStatus != null) {
                    viewStatus.setText(sbStatus.toString());
                }
            }
        }, 750);
    }
}
