package com.arash.altafi.alarmmanager.sample2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.JobIntentService;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class ScheduledService extends JobIntentService {
    private static final int UNIQUE_JOB_ID = 1337;

    static void enqueueWork(Context context) {
        enqueueWork(context, ScheduledService.class, UNIQUE_JOB_ID, new Intent(context, ScheduledService.class));
    }

    @Override
    public void onHandleWork(@NotNull Intent intent) {
        Log.d(getClass().getSimpleName(), "I ran!");
        Log.d(getClass().getSimpleName(), Calendar.getInstance().getTime().toString());
    }
}
