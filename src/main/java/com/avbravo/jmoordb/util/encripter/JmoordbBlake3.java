/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordb.util.encripter;

import io.github.rctcwyvrn.blake3.Blake3;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author avbravo
 */
public class JmoordbBlake3 {
/** 
 * Genera un texto encriptado 
 * @param textToEncripter
 * @param numberOfBytes (128/256)
 * @return 
 */
    public static String encripter(String textToEncripter, Integer... numberOfBytes) {
        String encripterGenerated = "";
        try {
             Integer bytesSize=256;
        if (numberOfBytes.length != 0) {
            bytesSize = numberOfBytes[0];

        }
    
            Blake3 hasher = Blake3.newInstance();
            hasher.update(textToEncripter.getBytes(StandardCharsets.UTF_8));
            String hexhash = hasher.hexdigest(bytesSize);
            encripterGenerated = hexhash;
        } catch (Exception e) {
            System.out.println("encripter() " + e.getLocalizedMessage());
        }

        return encripterGenerated;

    }
}
