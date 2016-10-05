package com.picoto.util.encriptador;

public interface Encriptador {

    String getSecurityToken();
        
    boolean validateSecurityToken(String token, int range);
    
}
