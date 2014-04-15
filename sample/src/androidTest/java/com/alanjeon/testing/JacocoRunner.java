package com.alanjeon.testing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.apps.common.testing.testrunner.GoogleInstrumentationTestRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by skyisle on 2/12/14.
 */
public class JacocoRunner extends GoogleInstrumentationTestRunner {
    private static final String TAG = "JacocoRunner";
    private static final String DEFAULT_COVERAGE_FILE_NAME_FORMAT = "%sJacoco.exec";

    public static final String ACTION_END_COVERAGE = "com.alanjeon.testing.END_COVERAGE";

    public class EndCoverageBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "EndCoverageBroadcast broadcast received!");

            endCoverage();
            // once coverage is dumped, the processes is ended.
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void saveJacocoResult(String packageName) throws IOException {
        Log.d(TAG, "saveJacocoResult called , trying to save coverage file to " + getCoverageFilePath(packageName));
        OutputStream out = new FileOutputStream(getCoverageFilePath(getTargetContext().getPackageName()), false);
        try {
            out.write(org.jacoco.agent.rt.RT.getAgent().getExecutionData(false));
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            out.close();
        }
    }

    private void endCoverage() {

        Log.d(TAG, "endCoverage started!");
        // reflection is used so emma doesn't cause problems for other build targets
        // that do not include emma.
        OutputStream out = null;
        try {
            out = new FileOutputStream(getCoverageFilePath(getTargetContext().getPackageName()), false);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);

            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(getTargetContext()).registerReceiver(
                new EndCoverageBroadcast(), new IntentFilter(ACTION_END_COVERAGE));
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        endCoverage();
        super.finish(resultCode, results);
    }

    private String getCoverageFilePath(String packageName) {
        return Environment.getExternalStorageDirectory() + File.separator + String.format(DEFAULT_COVERAGE_FILE_NAME_FORMAT, packageName);
    }
}
