package com.petStore.controller;

import com.petStore.model.BuyHistory;
import com.petStore.model.PetResponse;
import com.petStore.model.UserResponse;
import com.petStore.service.PetStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/petStore")
public class PetStoreController {


    private final PetStoreService petStoreService;

    public PetStoreController(PetStoreService petStoreService){
        this.petStoreService = petStoreService;
    }

    @PostMapping("/users")
    public ResponseEntity<List<UserResponse>> createUsers(){
        ResponseEntity<List<UserResponse>> responseEntity = new ResponseEntity<>(petStoreService.createUsers(), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/pets")
    public ResponseEntity<List<PetResponse>> createPets(){
        ResponseEntity<List<PetResponse>> responseEntity = new ResponseEntity<>(petStoreService.createPets(), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/users")
    public List<UserResponse> listAllUsers(){
        List<UserResponse> userResponses = petStoreService.listAllUsers();
        return userResponses;
    }

    @GetMapping("/pets")
    public List<PetResponse> listAllPets(){
        List<PetResponse> petResponses = petStoreService.listAllPets();
        return petResponses;
    }

    @PutMapping("/buy")
    public void buyPets(){
        petStoreService.buyAll();
    }

    @GetMapping("/buyHistory")
    public List<BuyHistory> listOfBuyHistory(){
       List<BuyHistory> buyHistoryList = petStoreService.listAllBuyHistory();
       return buyHistoryList;
    }
}
