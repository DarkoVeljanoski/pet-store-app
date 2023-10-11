package com.petStore.service.utils;

import com.petStore.model.Pet;
import com.petStore.model.Type;

import java.time.LocalDate;

public class PetUtils {

    public static Pet getExpectedDog(){
        Pet expectedPet = new Pet();
        expectedPet.setName("DogName");
        expectedPet.setPetType(Type.DOG);
        expectedPet.setDescription("DogDescription");
        expectedPet.setDateOfBirth(LocalDate.of(2011, 2, 3));
        expectedPet.setRating(5);
        expectedPet.setPrice(15.0);
        return expectedPet;
    }

    public static Pet getExpectedCat(){
        Pet expectedPet = new Pet();
        expectedPet.setName("CatName");
        expectedPet.setPetType(Type.CAT);
        expectedPet.setDescription("CatDescription");
        expectedPet.setDateOfBirth(LocalDate.of(2013, 3, 6));
        expectedPet.setPrice(15.0);
        return expectedPet;
    }


}
