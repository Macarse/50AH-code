package roboguice.calculator.activity;

import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.calculator.R;
import roboguice.calculator.util.RpnStack;
import roboguice.calculator.view.TickerTapeView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.math.BigDecimal;
import java.math.MathContext;

@ContentView(R.layout.main)
public class CalculatorActivity extends RoboActivity {
    @InjectView(R.id.tape) TickerTapeView tapeView;
    @InjectView(R.id.enter) Button enterButton;
    @InjectView(R.id.delete) Button deleteButton;
    @InjectView(R.id.plus) Button plusButton;
    @InjectView(R.id.minus) Button minusButton;
    @InjectView(R.id.multiply) Button multiplyButton;
    @InjectView(R.id.divide) Button divideButton;

    @Inject RpnStack stack;

    public void onDigitClicked( View digit ) {
        stack.appendToDigitAccumulator( ((TextView) digit).getText() );
        refreshDisplay();
    }
    
    public void onOperationClicked( View operation ) {

        // Any operation will automatically push the current digits onto the stack as if the user hit 'enter'
        stack.pushDigitAccumulatorOnStack();

        BigDecimal tmp;
        switch( operation.getId() ) {
            case R.id.plus:
                if( stack.size()<2 ) break;
                stack.push(stack.pop().add(stack.pop()));
                break;

            case R.id.minus:
                if( stack.size()<2 ) break;
                tmp = stack.pop();
                stack.push( stack.pop().subtract(tmp) );
                break;

            case R.id.multiply:
                if( stack.size()<2 ) break;
                stack.push(stack.pop().multiply( stack.pop()));
                break;

            case R.id.divide:
                if( stack.size()<2 ) break;
                tmp = stack.pop();
                stack.push(stack.pop().divide(tmp, MathContext.DECIMAL32 ));
                break;
            
            case R.id.delete:
                if( stack.size()>=1 )
                    stack.pop();
                break;
            
        }

        refreshDisplay();
    }

    protected void refreshDisplay() {
        tapeView.refresh();

        boolean linesHasAtLeastTwoItems = tapeView.getText().toString().split("\n").length >= 2;
        int digitAccumulatorLength = stack.getDigitAccumulator().length();

        enterButton.setEnabled( digitAccumulatorLength > 0);
        deleteButton.setEnabled(stack.size() > 0 || digitAccumulatorLength > 0);
        plusButton.setEnabled(linesHasAtLeastTwoItems);
        minusButton.setEnabled(linesHasAtLeastTwoItems);
        divideButton.setEnabled(linesHasAtLeastTwoItems);
        multiplyButton.setEnabled(linesHasAtLeastTwoItems);
    }





}


