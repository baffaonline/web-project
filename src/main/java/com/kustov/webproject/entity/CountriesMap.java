package com.kustov.webproject.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class CountriesMap {
    private static CountriesMap instance;
    private Map<String, Country> countryMap;
    private static ReentrantLock lock = new ReentrantLock();
    
    private CountriesMap(){
        countryMap = new HashMap<>();
    }

    public static CountriesMap getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new CountriesMap();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }
    
    public void put(String countryName, Country country){
        countryMap.put(countryName, country);
    }
    
    public Country getValue(String countryName){
        return countryMap.get(countryName);
    }
    
    public int size(){
        return countryMap.size();
    }
}
