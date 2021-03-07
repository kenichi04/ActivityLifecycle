package com.example.activitylifecycle.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatusTracker {
    // key: アクティビティ名、value: ステータス名 / アクティビティの現在のステータスを保持
    private Map<String, String> mStatusMap;
    // 過去呼び出された、アクティビティの状態変化時に呼び出されたメソッド名
    // 過去のイベント変化の歴史を保持
    private List<String> mMethodList;

    // シングルトンというデザインパターンの実装方法の一つ, コンストラクタがprivateのため、このクラス内でのみインスタンス化できる
    private static StatusTracker ourInstance = new StatusTracker();
    private static final String STATUS_SUFFIX = "ed";

    // インスタンス取得（フィールドにインスタンスをnewしているため、staticメソッドでどこからでも利用できる）
    public static StatusTracker getInstance() {
        return ourInstance;
    }

    // プライベートコンストラクタ
    private StatusTracker() {
        // LinkedHashMapは順番を保持する
        mStatusMap = new LinkedHashMap<String, String>();
        mMethodList = new ArrayList<String>();
    }

    // メソッドが呼ばれた履歴のリストを取得
    public List<String> getMethodList() {
        return mMethodList;
    }

    public void clear() {
        mMethodList.clear();
        mStatusMap.clear();
    }

    /**
     * Adds the status value for the given activityName into the Map.
     *
     * @param activityName
     * @param status
     */
    // アクティビティの状態を設定
    public void setStatus(String activityName, String status) {
        mMethodList.add(activityName + "." + status + "()");
        if (mStatusMap.containsKey(activityName)) mStatusMap.remove(activityName);
        mStatusMap.put(activityName, status);
    }

    /**
     * Gets the status value for the given activityName.
     *
     * @param activityName
     * @return
     */
    // アクティビティの状態を取得/ ※状態取得ではPauseをPaused、StopをStoppedにするなど
    // 例えば、onCreate->Createdとしてstatusを返す
    public String getStatus(String activityName) {
        String status = mStatusMap.get(activityName);
        status = status.substring(2, status.length());

        // String manipulation to ensure the status value is spelled correctly.
        if (status.endsWith("e")) {
            status = status.substring(0, status.length() - 1);
        }
        if (status.endsWith("p")) {
            status = status + "p";
        }
        status = status + STATUS_SUFFIX;
        return status;
    }

    // 連想配列のキーの集合を取得
    public Set<String> keySet() {
        return mStatusMap.keySet();
    }

}
