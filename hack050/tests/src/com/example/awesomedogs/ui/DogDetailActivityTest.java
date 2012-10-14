package com.example.awesomedogs.ui;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class DogDetailActivityTest extends ActivityUnitTestCase<DogDetailActivity> {

    public DogDetailActivityTest() {
        super(DogDetailActivity.class);
    }

    public void testActivityFinishesIfNoDogProvided() {
        // If no Intent extra is passed with the Dog ID, we should gracefully finish()
        startActivity(new Intent(), null, null);
        assertTrue(isFinishCalled());
    }

}
