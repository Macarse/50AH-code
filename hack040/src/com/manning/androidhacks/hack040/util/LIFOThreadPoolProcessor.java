/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack040.util;

import java.util.Comparator;
import java.util.concurrent.*;

public class LIFOThreadPoolProcessor {
  private BlockingQueue<Runnable> opsToRun = new PriorityBlockingQueue<Runnable>(
      64, new Comparator<Runnable>() {
        @Override
        public int compare(Runnable r0, Runnable r1) {
          if (r0 instanceof LIFOTask && r1 instanceof LIFOTask) {
            LIFOTask l0 = (LIFOTask) r0;
            LIFOTask l1 = (LIFOTask) r1;
            return l0.compareTo(l1);
          }
          return 0;
        }
      });

  private ThreadPoolExecutor executor;

  public LIFOThreadPoolProcessor(int threadCount) {
    executor = new ThreadPoolExecutor(threadCount, threadCount, 0,
        TimeUnit.SECONDS, opsToRun);
  }

  public Future<?> submitTask(LIFOTask task) {
    return executor.submit(task);
  }

  public void clear() {
    executor.purge();
  }
}
