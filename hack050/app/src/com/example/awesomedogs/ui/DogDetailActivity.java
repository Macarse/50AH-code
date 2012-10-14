package com.example.awesomedogs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesomedogs.R;
import com.example.awesomedogs.data.Dog;
import com.example.awesomedogs.data.Dog.Country;
import com.example.awesomedogs.data.DoggyData;

/** Shows the details of an individual dog. */
public class DogDetailActivity extends Activity {

    /** Intent extra: Dog ID to show (string). */
    public static final String EXTRA_DOG_ID = "dog_id";

    private Dog mDog;

    static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, DogDetailActivity.class);
        intent.putExtra(EXTRA_DOG_ID, id);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Determine which dog to show
        String id = getIntent().getStringExtra(EXTRA_DOG_ID);
        if (id == null) {
            finish();
            return;
        }
        mDog = DoggyData.getDog(id);

        setContentView(R.layout.detail);
        setTitle(mDog.getName());
        showDetails();
    }

    private void showDetails() {
        setText(R.id.name, mDog.getName());
        setText(R.id.breed, mDog.getBreed());
        setText(R.id.age, String.format(getString(R.string.age_template), mDog.getAge()));

        String description = mDog.getDescription(this);
        if (description.isEmpty()) { // Uh oh, this API is only supported on Android 2.2+
            description = getString(R.string.no_description);
        } else {
            description += " "+ getString(R.string.lorem_ipsum);
        }
        setText(R.id.description, description);

        Country country = mDog.getCountry();
        TextView tv = (TextView) findViewById(R.id.country);
        tv.setText(country.getName());
        tv.setCompoundDrawablesWithIntrinsicBounds(country.getIcon(), 0, 0, 0);

        ((ImageView) findViewById(R.id.photo)).setImageResource(mDog.getPhoto());
    }

    private void setText(int viewId, String text) {
        ((TextView) findViewById(viewId)).setText(text);
    }

}
