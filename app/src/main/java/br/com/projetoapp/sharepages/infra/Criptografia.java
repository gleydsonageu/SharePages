package br.com.projetoapp.sharepages.infra;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
    private MessageDigest algoritmo;

    public String setSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        algoritmo = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        String senhaCriptografada = hexString.toString();
        return senhaCriptografada;
    }
}