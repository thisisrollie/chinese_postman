package com.rolliedev.util;

import com.google.common.base.Stopwatch;

public final class TimeUtils {

    private TimeUtils() {
    }

    /**
     * This method helps to track time, how long the task runs
     *
     * @param runnable given any task
     */
    public static void displayTimeExecution(Runnable runnable) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            runnable.run();
        } finally {
            var duration = stopwatch.elapsed();
            System.out.println("Time: " + duration);
        }
    }
}
