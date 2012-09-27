package roboguice.calculator.util;

import android.content.SharedPreferences;
import com.google.inject.Inject;
import roboguice.activity.event.OnPauseEvent;
import roboguice.activity.event.OnResumeEvent;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * A Stack of numbers that can be operated on.
 *
 * In addition to maintaining a stack, this class also maintains an accumulator register that
 * can be used to keep track of the digits that a user is typing up until they hit 'enter'
 * to push the accumulated digits onto the stack.
 *
 * Persists itself to shared preferences.
 *
 * Is a singleton.
 */
@ContextSingleton
public class RpnStack extends Stack<BigDecimal> {

    @Inject SharedPreferences prefs;

    String digitAccumulator = "";

    public String getDigitAccumulator() {
        return digitAccumulator;
    }

    public void setDigitAccumulator(String digitAccumulator) {
        this.digitAccumulator = digitAccumulator;
    }

    public void appendToDigitAccumulator(CharSequence text) {
        digitAccumulator += text;
    }

    public void pushDigitAccumulatorOnStack() {
        if( digitAccumulator.length()>0 ) {
            push(new BigDecimal(digitAccumulator));
            digitAccumulator="";
        }
    }

    public void restoreOnResume(@Observes OnResumeEvent e) {
        for( int i=0; prefs.contains(String.valueOf(i)); ++i)
            insertElementAt(new BigDecimal(prefs.getString(String.valueOf(i), null)), i);
    }
    
    public void persistOnPause(@Observes OnPauseEvent e) {
        final SharedPreferences.Editor edit = prefs.edit();

        edit.clear();

        for( int i=0; i< this.size(); ++i )
            edit.putString(String.valueOf(i), this.get(i).toString());

        edit.commit();

    }


}


