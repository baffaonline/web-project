package com.kustov.webproject.service;

import com.kustov.webproject.entity.Film;

import java.util.Comparator;

public class FilmRatingComparator implements Comparator<Film>{
    @Override
    public int compare(Film o1, Film o2) {
        return Double.compare(o1.getRating(), o2.getRating());
    }
}
