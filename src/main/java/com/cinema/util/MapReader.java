package com.cinema.util;

import org.springframework.stereotype.Component;

/**
 * Created by Dominik on 05.06.2017.
 */
public interface MapReader {
    /**
     * Created by Dominik on 05.06.2017.
     * this method reads map from file for cinema hall based on id of that cinema hall.
     * @param idHall represents (integer value) of unique id for cinema hall map
     * @return int[][] that represents seats, screen and blanc space in cinema hall.
     */
    int [][] mapReader(int idHall);

}
