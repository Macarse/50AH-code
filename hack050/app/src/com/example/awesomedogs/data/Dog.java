package com.example.awesomedogs.data;

import android.content.Context;

import com.example.awesomedogs.R;

public class Dog {

    /** Used to allocate unique IDs. */
    private static int sHighestId;

    private final String mId;
    private final String mName;
    private final String mBreed;
    private final int mAge;
    private final Country mCountry;
    private final int mDescription;
    private final int mIcon;
    private final int mPhoto;

    public Dog(String name, String breed, int age, Country country, int descriptionResId, int iconResId, int photoResId) {
        mId = String.valueOf(sHighestId++);
        mName = name;
        mBreed = breed;
        mAge = age;
        mCountry = country;
        mDescription = descriptionResId;
        mIcon = iconResId;
        mPhoto = photoResId;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getBreed() {
        return mBreed;
    }

    public int getAge() {
        return mAge;
    }

    public Country getCountry() {
        return mCountry;
    }

    public String getDescription(Context context) {
        if (mDescription <= 0) {
            return "";
        }
        return context.getString(mDescription);
    }

    public int getIcon() {
        return mIcon;
    }

    public int getPhoto() {
        return mPhoto;
    }

    public static enum Country {

        DE(R.string.country_de, R.drawable.flag_de),
        SCO(R.string.country_sco, R.drawable.flag_sco);

        private final int mName;
        private final int mIcon;

        Country(int name, int icon) {
            mName = name;
            mIcon = icon;
        }

        public int getName() {
            return mName;
        }

        public int getIcon() {
            return mIcon;
        }

    }

}