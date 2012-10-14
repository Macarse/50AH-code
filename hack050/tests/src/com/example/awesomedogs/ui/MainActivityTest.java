package com.example.awesomedogs.ui;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.awesomedogs.data.DoggyData;
import com.example.awesomedogs.ui.DogDetailActivity;
import com.example.awesomedogs.ui.MainActivity;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testAssertDogsWereLoaded() {
        // List should show all dogs in the data class
        ListView listView = getListView();
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
        assertEquals(DoggyData.getDogs().size(), adapter.getCount());
    }

    public void testClickingDogOpensDetails() {
        // Clicking on any dog should open the details activity
        ListView listView = getListView();
        listView.performItemClick(null, 0, 0);

        Intent intent = getStartedActivityIntent();
        assertNotNull(intent);
        assertEquals(DogDetailActivity.class.getName(), intent.getComponent().getClassName());
        assertTrue(intent.hasExtra(DogDetailActivity.EXTRA_DOG_ID));
    }

    private ListView getListView() {
        Activity activity = startActivity(new Intent(), null, null);
        ListView listView = (ListView) activity.findViewById(android.R.id.list);
        assertNotNull(listView);
        return listView;
    }

}
