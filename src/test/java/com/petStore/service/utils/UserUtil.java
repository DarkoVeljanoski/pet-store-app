package com.petStore.service.utils;

import com.petStore.model.User;

public class UserUtil {

    public static User getExpectedUser(){
        User expectedUser = new User();
        expectedUser.setFirstName("FirstName");
        expectedUser.setLastName("LastName");
        expectedUser.setEmail("Test@Mail.com");
        expectedUser.setBudget(20.0);
        return expectedUser;
    }
}
