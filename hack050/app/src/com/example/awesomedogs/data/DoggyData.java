package com.example.awesomedogs.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.awesomedogs.R;
import com.example.awesomedogs.data.Dog.Country;

public class DoggyData {

    private static Map<String, Dog> DOGS = new HashMap<String, Dog>();

    static {
        addItem(new Dog("Bonnie", "Eurasier", 5, Country.DE, 0, R.drawable.bonnie_icon, R.drawable.bonnie_photo));
        addItem(new Dog("Chiva", "Eurasier", 3, Country.DE, R.string.chiva_desc, R.drawable.chiva_icon, R.drawable.chiva_photo));
        addItem(new Dog("Mac", "Border collie", 11, Country.SCO, R.string.mac_desc, R.drawable.mac_icon, R.drawable.mac_photo));
        addItem(new Dog("Bruno", "Hovawart", 7, Country.DE, R.string.bruno_desc, R.drawable.bruno_icon, R.drawable.bruno_photo));
    }

    public static List<Dog> getDogs() {
       return new ArrayList<Dog>(DOGS.values());
    }

    public static Dog getDog(String id) {
        return DOGS.get(id);
    }

    private static void addItem(Dog dog) {
        DOGS.put(dog.getId(), dog);
    }

}
