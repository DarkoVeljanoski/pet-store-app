package com.petStore.graphql;

import com.petStore.model.BuyHistory;
import com.petStore.model.PetResponse;
import com.petStore.model.UserResponse;
import com.petStore.service.PetStoreService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class GraphQLController {

    private final PetStoreService petStoreService;

    public GraphQLController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    @QueryMapping
    List<UserResponse> users(){
        return petStoreService.listAllUsers();
    }

    @QueryMapping
    List<PetResponse> pets(){
        return petStoreService.listAllPets();
    }

    @MutationMapping
    List<UserResponse> createUsers(){
        return petStoreService.createUsers();
    }

    @MutationMapping
    List<PetResponse> createPets(){
        return petStoreService.createPets();
    }

    @QueryMapping
    List<BuyHistory> buyHistories(){
        return petStoreService.listAllBuyHistory();
    }
}
