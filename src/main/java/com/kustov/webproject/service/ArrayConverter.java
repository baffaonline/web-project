package com.kustov.webproject.service;


/**
 * The Class ArrayConverter.
 */
public class ArrayConverter {

    /**
     * Make int array from string.
     *
     * @param array the array
     * @return the int[]
     */
    public static int[] makeIntArrayFromString(String array[]) {
        int intArray[];
        if (array != null) {
            intArray = new int[array.length];
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = Integer.parseInt(array[i]);
            }
        } else {
            intArray = null;
        }
        return intArray;
    }
}
