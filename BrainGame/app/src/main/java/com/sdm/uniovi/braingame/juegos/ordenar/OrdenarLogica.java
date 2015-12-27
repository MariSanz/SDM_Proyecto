package com.sdm.uniovi.braingame.juegos.ordenar;

/**
 * Created by Eva on 27.12.2015.
 */
public class OrdenarLogica {


    public static OrdenarColor[] generizeColors(int number){
        OrdenarColor[] colors = new OrdenarColor[number];
        for (int i = 0; i < number; i++){
            colors[i] = OrdenarColor.getRandom();
        }
        return colors;
    }

}
