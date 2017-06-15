/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.fuelgauge.anomaly;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.android.settings.fuelgauge.anomaly.action.AnomalyAction;
import com.android.settings.fuelgauge.anomaly.action.BackgroundCheckAction;
import com.android.settings.fuelgauge.anomaly.action.ForceStopAction;
import com.android.settings.fuelgauge.anomaly.checker.AnomalyDetector;
import com.android.settings.fuelgauge.anomaly.checker.BluetoothScanAnomalyDetector;
import com.android.settings.fuelgauge.anomaly.checker.WakeLockAnomalyDetector;
import com.android.settings.fuelgauge.anomaly.checker.WakeupAlarmAnomalyDetector;

/**
 * Utility class for anomaly detection
 */
public class AnomalyUtils {
    private Context mContext;
    private static AnomalyUtils sInstance;

    @VisibleForTesting
    AnomalyUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public static AnomalyUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AnomalyUtils(context);
        }
        return sInstance;
    }

    /**
     * Return the corresponding {@link AnomalyAction} according to
     * {@link com.android.settings.fuelgauge.anomaly.Anomaly.AnomalyType}
     *
     * @return corresponding {@link AnomalyAction}, or null if cannot find it.
     */
    public AnomalyAction getAnomalyAction(@Anomaly.AnomalyType int anomalyType) {
        switch (anomalyType) {
            case Anomaly.AnomalyType.WAKE_LOCK:
                return new ForceStopAction(mContext);
            case Anomaly.AnomalyType.WAKEUP_ALARM:
            case Anomaly.AnomalyType.BLUETOOTH_SCAN:
                return new BackgroundCheckAction(mContext);
            default:
                return null;
        }
    }

    /**
     * Return the corresponding {@link AnomalyDetector} according to
     * {@link com.android.settings.fuelgauge.anomaly.Anomaly.AnomalyType}
     *
     * @return corresponding {@link AnomalyDetector}, or null if cannot find it.
     */
    public AnomalyDetector getAnomalyDetector(@Anomaly.AnomalyType int anomalyType) {
        switch (anomalyType) {
            case Anomaly.AnomalyType.WAKE_LOCK:
                return new WakeLockAnomalyDetector(mContext);
            case Anomaly.AnomalyType.WAKEUP_ALARM:
                return new WakeupAlarmAnomalyDetector(mContext);
            case Anomaly.AnomalyType.BLUETOOTH_SCAN:
                return new BluetoothScanAnomalyDetector(mContext);
            default:
                return null;
        }
    }
}
