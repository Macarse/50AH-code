package com.example.awesomedogs.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.awesomedogs.R;
import com.example.awesomedogs.data.Dog;
import com.example.awesomedogs.data.DoggyData;

public class MainActivity extends ListActivity implements OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<Dog> adapter = new ArrayAdapter<Dog>(this, R.layout.list_row, DoggyData.getDogs()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_row, null, false);
                }

                Dog dog = getItem(position);

                TextView textView = (TextView) convertView;
                textView.setText(getItem(position).getName());
                textView.setCompoundDrawablesWithIntrinsicBounds(dog.getIcon(), 0, 0, 0);

                return textView;
            }
        };
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> list, View view, int position, long id) {
        // Open details activity for the dog with this ID
        String dogId = ((Dog) list.getItemAtPosition(position)).getId();
        DogDetailActivity.startActivity(this, dogId);
    }

}
