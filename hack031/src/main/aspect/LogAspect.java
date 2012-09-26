/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
import android.util.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspect {

  @Pointcut("within(com.manning.androidhacks.hack022.MainActivity)")
  private void mainActivity() {
  }

  @Pointcut("execution(* onCreate(..))")
  private void onCreate() {
  }

  @AfterReturning(pointcut = "mainActivity() && onCreate()")
  public void logAfterOnCreateOnMainActivity() {
    Log.d("TAG", "OnCreate() has been called!");
  }
}
