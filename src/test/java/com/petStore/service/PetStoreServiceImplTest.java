package com.petStore.service;

import com.petStore.model.*;
import com.petStore.repository.BuyHistoryRepository;
import com.petStore.repository.PetRepository;
import com.petStore.repository.UserRepository;
import com.petStore.service.utils.PetUtils;
import com.petStore.service.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetStoreServiceImplTest {

    @InjectMocks @Spy
    private PetStoreServiceImpl petStoreService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private BuyHistoryRepository buyHistoryRepository;

    @Spy
    private ModelMapper modelMapper;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp(){
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void calculateAge(){
        LocalDate dateOfBirth1 = LocalDate.of(2018, 1, 1);
        LocalDate dateOfBirth2 = LocalDate.of(2015, 7, 21);

        int expectedAge1 = LocalDate.now().getYear() - dateOfBirth1.getYear();
        int expectedAge2 = LocalDate.now().getYear() - dateOfBirth2.getYear();

        int actualAge1 = petStoreService.calculateAge(dateOfBirth1);
        int actualAge2 = petStoreService.calculateAge(dateOfBirth2);

        assertEquals(expectedAge1, actualAge1);
        assertEquals(expectedAge2, actualAge2);
    }

    @Test
    void calculatePriceForDogs(){
        Pet pet = new Pet();
        pet.setPetType(Type.DOG);
        pet.setRating(5);
        pet.setDateOfBirth(LocalDate.of(2013, 7, 10));

        Integer age = petStoreService.calculateAge(pet.getDateOfBirth());
        Double expectedPrice = age + pet.getRating() * 1.0;

        Double actualPrice = petStoreService.calculatePrice(pet);

        assertEquals(expectedPrice,actualPrice);
    }

    @Test
    void calculatePriceForCats(){
        Pet pet = new Pet();
        pet.setPetType(Type.CAT);
        pet.setDateOfBirth(LocalDate.of(2015, 5, 16));

        Integer age = petStoreService.calculateAge(pet.getDateOfBirth());
        Double expectedPrice = age * 1.0;

        Double actualPrice = petStoreService.calculatePrice(pet);

        assertEquals(expectedPrice,actualPrice);
    }

    @Test
    void createPetWithTypeDog(){
        Pet expectedPet = PetUtils.getExpectedDog();

        when(petRepository.save(any())).thenReturn(expectedPet);

        Pet actualPet = petStoreService.createPet(Type.DOG);

        assertEquals(expectedPet, actualPet);
    }

    @Test
    void createPetWithTypeCat(){
        Pet expectedPet = PetUtils.getExpectedCat();

        when(petRepository.save(any())).thenReturn(expectedPet);

        Pet actualPet = petStoreService.createPet(Type.CAT);

        assertEquals(expectedPet, actualPet);
    }

    @Test
    void createUser(){
        User expectedUser = UserUtil.getExpectedUser();

        when(userRepository.save(any())).thenReturn(expectedUser);

        User actualUser = petStoreService.createUser();

        verify(userRepository).save(any(User.class));

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void createUsers() {
        User expectedUser = UserUtil.getExpectedUser();
        when(petStoreService.createUser()).thenReturn(expectedUser);
        List<UserResponse> userResponses = petStoreService.createUsers();
        assertEquals(10, userResponses.size());
    }

    @Test
    void createPets() {
        Pet expectedDog = PetUtils.getExpectedDog();

        Pet expectedCat = PetUtils.getExpectedCat();

        when(petStoreService.createPet(Type.DOG)).thenReturn(expectedDog);
        when(petStoreService.createPet(Type.CAT)).thenReturn(expectedCat);

        List<PetResponse> petResponses = petStoreService.createPets();
        assertEquals(20, petResponses.size());

    }

    @Test
    void listAllUsers() {
        User user = new User();
        user.setId(1l);
        user.setFirstName("Bob");
        List<User> expectedListOfUsers = new ArrayList<>();
        expectedListOfUsers.add(user);
        when(userRepository.findAll()).thenReturn(expectedListOfUsers);
        List<UserResponse> actualUserList = petStoreService.listAllUsers();

        assertEquals(expectedListOfUsers.size(), actualUserList.size());
        assertEquals(expectedListOfUsers.get(0).getFirstName(), actualUserList.get(0).getFirstName());
    }

    @Test
    void listAllPets() {
        Pet pet = new Pet();
        pet.setId(1l);
        pet.setName("Barbie");
        List<Pet> expectedListOfPets = new ArrayList<>();
        expectedListOfPets.add(pet);
        when(petRepository.findAll()).thenReturn(expectedListOfPets);
        List<PetResponse> actualPetList = petStoreService.listAllPets();

        assertEquals(expectedListOfPets.size(), actualPetList.size());
        assertEquals(expectedListOfPets.get(0).getName(), actualPetList.get(0).getName());
    }

    @Test
    void checkIfUserCanBuy_whenNotEnoughBudget(){
        User user = new User();
        user.setBudget(10.0);
        Pet pet = new Pet();
        pet.setPrice(20.0);
        assertFalse(petStoreService.checkIfUserCanBuy(user, pet));
    }

    @Test
    void checkIfUserCanBuy_whenPetAlreadyHasOwner(){
        User user = new User();
        user.setBudget(10.0);
        Pet pet = new Pet();
        pet.setPrice(5.0);
        pet.setOwner(new User());

        assertFalse(petStoreService.checkIfUserCanBuy(user, pet));
    }

    @Test
    void checkIfUserCanBuy(){
        User user = new User();
        user.setBudget(20.0);
        Pet pet = new Pet();
        pet.setPrice(15.0);

        assertTrue(petStoreService.checkIfUserCanBuy(user, pet));
        assertEquals(5.0, user.getBudget());
        assertEquals(user, pet.getOwner());

        verify(userRepository, times(1)).save(user);
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void listAllBuyHistory() {
        BuyHistory buyHistory = new BuyHistory();
        buyHistory.setNumberOfFailures(5);
        buyHistory.setNumberOfSuccesses(5);
        List<BuyHistory> expectedBuyHistory = new ArrayList<>();
        expectedBuyHistory.add(buyHistory);
        when(buyHistoryRepository.findAll()).thenReturn(expectedBuyHistory);
        List<BuyHistory> actualBuyHistory = petStoreService.listAllBuyHistory();

        assertEquals(expectedBuyHistory.size(), actualBuyHistory.size());
        assertEquals(expectedBuyHistory.get(0).getNumberOfFailures(), actualBuyHistory.get(0).getNumberOfFailures());
        assertEquals(expectedBuyHistory.get(0).getNumberOfSuccesses(), actualBuyHistory.get(0).getNumberOfSuccesses());
    }
}