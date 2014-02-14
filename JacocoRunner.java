package com.alanjeon.testing;

import android.os.Bundle;
import android.os.Environment;
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
  private static final Object DEFAULT_COVERAGE_FILE_NAME = "jacoco.exec";

  @Override
  public void onCreate(Bundle arguments) {
    Log.d(TAG, "onCreate");

    super.onCreate(arguments);

  }

  @Override
  public void finish(int resultCode, Bundle results) {
    try {
      saveJacocoResult();
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.finish(resultCode, results);

  }

  private void saveJacocoResult() throws IOException {
    Log.d(TAG, "saveJacocoResult called , coverage is saved to file = " + getCoverageFilePath());
    OutputStream out = new FileOutputStream(getCoverageFilePath(), false);
    try {
      out.write(org.jacoco.agent.rt.RT.getAgent().getExecutionData(false));
    } finally {
      out.close();
    }
  }

  private String getCoverageFilePath() {
    return Environment.getExternalStorageDirectory() + File.separator +
        getTargetContext().getPackageName() + DEFAULT_COVERAGE_FILE_NAME;
  }
}
