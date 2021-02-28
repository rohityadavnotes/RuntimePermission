package com.runtime.permission.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityUtil {

    /**
     * Method to launch activity
     *
     * @param context     - context
     * @param packageName - package name
     * @param className   - class name
     * @return true or false
     */
    public static boolean isExistActivity(Context context,
                                          @NonNull final String packageName,
                                          @NonNull final String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * Method to launch activity
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     */
    public static void launchActivity(@NonNull final Activity currentActivity,
                                      @NonNull final Class<? extends Activity> nextActivityClass) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        currentActivity.startActivity(goToNextActivity);
    }

    /**
     * Method to launch activity with animation
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     * @param enterAnim
     * @param exitAnim
     */
    public static void launchActivityWithAnimation(@NonNull final Activity currentActivity,
                                                   @NonNull final Class<? extends Activity> nextActivityClass,
                                                   @AnimRes final int enterAnim,
                                                   @AnimRes final int exitAnim) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        currentActivity.startActivity(goToNextActivity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            currentActivity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Method to launch next activity with bundle
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     * @param bundle            - Pass bundle from one class to another
     */
    public static void launchActivityWithBundle(@NonNull final Activity currentActivity,
                                                @NonNull final Class<? extends Activity> nextActivityClass,
                                                @Nullable final Bundle bundle) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        goToNextActivity.putExtra("BUNDLE", bundle);
    }

    /**
     * Method to launch next activity with bundle and animation
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     * @param bundle            - Pass bundle from one class to another
     * @param enterAnim
     * @param exitAnim
     */
    public static void launchActivityWithBundleAndAnimation(@NonNull final Activity currentActivity,
                                                            @NonNull final Class<? extends Activity> nextActivityClass,
                                                            @Nullable final Bundle bundle,
                                                            @AnimRes final int enterAnim,
                                                            @AnimRes final int exitAnim) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        goToNextActivity.putExtra("BUNDLE", bundle);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            currentActivity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public static void finishWithAnimation(@NonNull final Activity nextActivityClass) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nextActivityClass.finishAfterTransition();
        } else {
            nextActivityClass.finish();
        }
    }

    /**
     * Method to launch activity with finish
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     */
    public static void launchActivityWithFinish(@NonNull final Activity currentActivity,
                                                @NonNull final Class<? extends Activity> nextActivityClass) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        currentActivity.startActivity(goToNextActivity);
        finishWithAnimation(currentActivity);
    }

    /**
     * Method to launch activity with finish and bundle
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     */
    public static void launchActivityWithFinish(@NonNull final Activity currentActivity,
                                                @NonNull final Class<? extends Activity> nextActivityClass,
                                                @Nullable final Bundle bundle) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        goToNextActivity.putExtra("BUNDLE", bundle);
        currentActivity.startActivity(goToNextActivity);
        finishWithAnimation(currentActivity);
    }

    /**
     * Method to launch next activity with finish all BackStack activity
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     */
    public static void launchActivityWithClearBackStack(@NonNull final Activity currentActivity,
                                                        @NonNull final Class<? extends Activity> nextActivityClass) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(goToNextActivity);
        finishWithAnimation(currentActivity);
    }

    /**
     * Method to launch next activity with finish all BackStack activity and bundle
     *
     * @param currentActivity   - Current activity
     * @param nextActivityClass - Activity class that should be launched
     * @param bundle            - Pass bundle from one class to another
     */
    public static void launchActivityWithClearBackStack(@NonNull final Activity currentActivity,
                                                        @NonNull final Class<? extends Activity> nextActivityClass,
                                                        @Nullable final Bundle bundle) {
        Intent goToNextActivity = new Intent(currentActivity, nextActivityClass);
        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        goToNextActivity.putExtra("BUNDLE", bundle);
        currentActivity.startActivity(goToNextActivity);
        finishWithAnimation(currentActivity);
    }

    /**
     * Method to get previous activity bundle data
     *
     * @param currentActivity - Current activity
     */
    public static Bundle getDataFromPreviousActivity(@NonNull final Activity currentActivity) {
        Intent data = currentActivity.getIntent();
        return data.getBundleExtra("BUNDLE");
    }

    private ActivityUtil() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }
}
