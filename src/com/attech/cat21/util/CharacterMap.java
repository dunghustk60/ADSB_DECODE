/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat21.util;

/**
 *
 * @author Andh
 */
public class CharacterMap {
    private static final char [] map = new char [64];
    public static char[] getCharacterMap() {
        for (int i = 1; i <= 26; i++) map[i] = (char) (i + 64);
        for (int i = 48; i <= 57; i++) map[i] = (char) (i);
        map[32] = ' ';
        return map;
    }
}
