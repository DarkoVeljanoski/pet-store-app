package com.petStore.service;

import com.petStore.model.BuyHistory;
import com.petStore.model.PetResponse;
import com.petStore.model.UserResponse;

import java.util.List;

public interface PetStoreService {
    List<UserResponse> createUsers();

    List<PetResponse> createPets();

    List<UserResponse> listAllUsers();

    List<PetResponse> listAllPets();

    BuyHistory buyAll();

    List<BuyHistory> listAllBuyHistory();
}
