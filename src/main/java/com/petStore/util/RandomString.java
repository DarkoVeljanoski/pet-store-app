package com.petStore.util;

public class RandomString {

    public static String getAlphaNumericString(int n)
    {
        String AlphaString = "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaString.length()
                    * Math.random());

            sb.append(AlphaString
                    .charAt(index));
        }

        return sb.toString();
    }
}
