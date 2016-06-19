package br.com.projetoapp.sharepages.infra;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by gleydson on 19/06/16.
 */

public class Criptografia {
    private MessageDigest algoritmo;

    public String setSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        algoritmo = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        Log.i("SCRIPT","SHA_256"+ senha);
        String senha1 = hexString.toString();
        return senha1;
    }
}