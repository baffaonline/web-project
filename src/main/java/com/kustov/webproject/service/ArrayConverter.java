package com.kustov.webproject.service;

public class ArrayConverter {
    public static int[] makeIntArrayFromString(String array[]){
        int intArray[];
        if (array != null) {
            intArray = new int[array.length];
            for (int i = 0; i < intArray.length; i++){
                intArray[i] = Integer.parseInt(array[i]);
            }
        }
        else {
            intArray = null;
        }
        return intArray;
    }
}
