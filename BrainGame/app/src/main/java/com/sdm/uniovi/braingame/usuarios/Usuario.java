package com.sdm.uniovi.braingame.usuarios;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luism_000 on 27/11/2015.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String clave;


    public Usuario(String nombre, String clave) {
        this.nombre = nombre;
        this.id = id;
        this.clave = SHA1(clave);
    }



    public int getId() {
        return id;
    }

    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    private String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                }
                else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    private String SHA1(String text)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            Log.v("ERROR", "Error al encriptar clave");
        }
        byte[] sha1hash = new byte[40];
        try {
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e) {
            Log.v("ERROR", "Error al encriptar clave");
        }
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}
