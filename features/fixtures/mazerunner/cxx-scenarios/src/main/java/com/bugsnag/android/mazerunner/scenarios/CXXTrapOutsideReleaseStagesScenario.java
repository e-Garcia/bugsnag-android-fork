package com.bugsnag.android.mazerunner.scenarios;

import android.content.Context;

import com.bugsnag.android.Bugsnag;
import com.bugsnag.android.Configuration;

import java.util.Collections;

import androidx.annotation.NonNull;

public class CXXTrapOutsideReleaseStagesScenario extends Scenario {

    static {
        System.loadLibrary("cxx-scenarios");
    }

    public native void crash();

    public CXXTrapOutsideReleaseStagesScenario(@NonNull Configuration config, @NonNull Context context) {
        super(config, context);
        config.setAutoTrackSessions(false);
        config.setEnabledReleaseStages(Collections.singleton("fee-fi-fo-fum"));
    }

    @Override
    public void run() {
        super.run();
        String metadata = getEventMetaData();
        if (metadata != null && metadata.equals("non-crashy")) {
            return;
        }
        crash();
    }
}
