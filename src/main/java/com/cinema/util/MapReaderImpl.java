package com.cinema.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Dominik on 05.06.2017.
 */
@Component
public class MapReaderImpl  implements MapReader{

    @Override
    public int[][] mapReader(int idHall) {
        int [][] mapArray = new int[16][16];
        String path = String.valueOf(getClass().getResource("/Maps/stage" + idHall));
        path = path.substring(6, path.length());

        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String line;

            br = new BufferedReader(new FileReader(path));

            int row = 0;
            while((line = br.readLine()) != null){
                for(int i = 0; i<line.length(); i++){
                    mapArray[row][i] = Integer.valueOf(line.substring(i, i+1));
                }
                row++;
            }

        } catch (Exception e){
            e.printStackTrace();
        }


        return mapArray;
    }
}
