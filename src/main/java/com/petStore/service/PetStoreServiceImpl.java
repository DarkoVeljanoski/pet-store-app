package com.petStore.service;

import com.petStore.model.*;
import com.petStore.repository.BuyHistoryRepository;
import com.petStore.repository.PetRepository;
import com.petStore.repository.UserRepository;
import com.petStore.util.NamesUtil;
import com.petStore.util.RandomDate;
import com.petStore.util.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetStoreServiceImpl implements PetStoreService{

    private UserRepository userRepository;
    private PetRepository petRepository;

    private ModelMapper modelMapper;

    private BuyHistoryRepository buyHistoryRepository;

    public PetStoreServiceImpl(UserRepository userRepository, PetRepository petRepository, ModelMapper modelMapper, BuyHistoryRepository buyHistoryRepository){
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
        this.buyHistoryRepository = buyHistoryRepository;
    }


    public Pet createPet(Type type){
        Pet pet = new Pet();
        Random random = new Random();
        pet.setName(NamesUtil.listOfPetNames[random.nextInt(27)]);
        pet.setPetType(type);
        pet.setDescription(RandomString.getAlphaNumericString(10));
        RandomDate randomDate = new RandomDate(LocalDate.of(2010, 1, 1), LocalDate.of(2023,1,1));
        pet.setDateOfBirth(randomDate.nextDate());
        if (pet.getPetType() == Type.DOG){
            pet.setRating(random.nextInt(10));
        }
        Double price = calculatePrice(pet);
        pet.setPrice(price);

        Pet savedPet = petRepository.save(pet);
        return savedPet;
    }

    public User createUser(){
        User user = new User();
        Random random = new Random();
        user.setFirstName(NamesUtil.listOfNames[random.nextInt(20)]);
        user.setLastName(NamesUtil.listOfLastNames[random.nextInt(11)]);
        String email = RandomString.getAlphaNumericString(7) + "@gmail.com";
        user.setEmail(email);
        user.setBudget(random.nextDouble(20.0));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public Double calculatePrice(Pet pet){
        Integer age = calculateAge(pet.getDateOfBirth());
        Double price=1.0;
        if (pet.getPetType() == Type.DOG){
            price = age + pet.getRating() * 1.0;
        } else if (pet.getPetType() == Type.CAT){
            price = age * 1.0;
        }
        return price;
    }

    public Integer calculateAge(LocalDate dateOfBirth){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public List<UserResponse> createUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (int i=0;i<10;i++){
            User user = createUser();
            userResponses.add(modelMapper.map(user, UserResponse.class));
        }
        return userResponses;
    }

    @Override
    public List<PetResponse> createPets() {
        List<PetResponse> petResponses = new ArrayList<>();
        Pet pet;
        for (int i=0;i<20;i++){
            if(i % 2 == 0){
                pet = createPet(Type.DOG);
            } else {
                pet = createPet(Type.CAT);
            }
            petResponses.add(modelMapper.map(pet, PetResponse.class));
        }
        return petResponses;
    }

    @Override
    public List<UserResponse> listAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses =
                users.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public List<PetResponse> listAllPets() {
        List<Pet> pets = petRepository.findAll();
        List<PetResponse> petResponses =
                pets.stream().map(pet -> modelMapper.map(pet, PetResponse.class)).collect(Collectors.toList());
        return petResponses;
    }

    @Override
    public BuyHistory buyAll() {
        List<User> users = listAllUsers().stream().map(user -> modelMapper.map(user, User.class)).collect(Collectors.toList());
        List<Pet> pets = listAllPets().stream().map(pet -> modelMapper.map(pet, Pet.class)).collect(Collectors.toList());
        BuyHistory buyHistory = new BuyHistory();

        int numberOfSuccesses = (int) users.stream().filter(user ->
                pets.stream().anyMatch(pet -> checkIfUserCanBuy(user,pet))).count();

        buyHistory.setNumberOfSuccesses(numberOfSuccesses);
        buyHistory.setNumberOfFailures(users.size()- numberOfSuccesses );
        BuyHistory savedBuyHistory = buyHistoryRepository.save(buyHistory);
        return savedBuyHistory;
    }

    @Override
    public List<BuyHistory> listAllBuyHistory() {
        return buyHistoryRepository.findAll();
    }

    public Boolean checkIfUserCanBuy(User user, Pet pet){

        if(user.getBudget() < pet.getPrice()){
            return false;
        }
        if(pet.getOwner() != null){
            return false;
        }
        Double newBudget = user.getBudget() - pet.getPrice();
        user.setBudget(newBudget);

        pet.setOwner(user);

        User savedUser = userRepository.save(user);
        Pet savedPet = petRepository.save(pet);

        if (pet.getPetType() == Type.CAT){
            String str  = String.format("Meow cat %s has owner %s", pet.getName(), user.getFirstName());
            System.out.println(str);
        }
        if(pet.getPetType() == Type.DOG){
            String str  = String.format("Woof dog %s has owner %s", pet.getName(), user.getFirstName());
            System.out.println(str);
        }
        return true;
    }
}
