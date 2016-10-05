package com.picoto.encriptador.test;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.picoto.util.encriptador.Encriptador;
import com.picoto.util.encriptador.EncriptadorImpl;

public class EncriptadorTest {

    private static final int RANGE = 10;
    
    public void testAdHoc() {
        Encriptador enc = new EncriptadorImpl();
        Assert.assertTrue(enc.validateSecurityToken("", RANGE));
    }
    
    
    @Test
    public void testEncriptar() {
        Encriptador enc = new EncriptadorImpl();
        Encriptador enc0 = new Encriptador0();
        Encriptador enc1 = new Encriptador1();
        Encriptador enc11 = new Encriptador11();
        Encriptador enc2 = new Encriptador2();
        Encriptador enc3 = new Encriptador3();
        Encriptador enc4 = new Encriptador4();
        Encriptador enc5 = new Encriptador5();

        String token = enc.getSecurityToken();
        String token0 = enc0.getSecurityToken();
        String token1 = enc1.getSecurityToken();
        String token11 = enc11.getSecurityToken();
        String token2 = enc2.getSecurityToken();
        String token3 = enc3.getSecurityToken();
        String token4 = enc4.getSecurityToken();
        String token5 = enc5.getSecurityToken();

        // Todos los token se deben validar correctamente con respecto a su
        // encriptador
        Assert.assertTrue(enc.validateSecurityToken(token, RANGE));
        Assert.assertTrue(enc0.validateSecurityToken(token0, RANGE));
        Assert.assertTrue(enc1.validateSecurityToken(token1, RANGE));
        Assert.assertTrue(enc11.validateSecurityToken(token11, RANGE));
        Assert.assertTrue(enc2.validateSecurityToken(token2, RANGE));
        Assert.assertTrue(enc3.validateSecurityToken(token3, RANGE));
        Assert.assertTrue(enc4.validateSecurityToken(token4, RANGE));
        Assert.assertTrue(enc5.validateSecurityToken(token5, RANGE));

        // El token0 cambia solo por segundos, asi que es valido
        Assert.assertTrue(enc.validateSecurityToken(token0, RANGE));
        Assert.assertTrue(enc.validateSecurityToken(token1, RANGE));

        // El resto de tokens cambia en mas de LIMIT, asi que no son validos
        Assert.assertFalse(enc.validateSecurityToken(token11, RANGE));
        Assert.assertFalse(enc.validateSecurityToken(token2, RANGE));
        Assert.assertFalse(enc.validateSecurityToken(token3, RANGE));
        Assert.assertFalse(enc.validateSecurityToken(token4, RANGE));
        Assert.assertFalse(enc.validateSecurityToken(token5, RANGE));

        // Comprobaciones adicionales comprobando varios tokens que no deben
        // validarse correctamente
        Assert.assertFalse(enc.validateSecurityToken(token2, RANGE));
        Assert.assertFalse(enc2.validateSecurityToken(token3, RANGE));
        Assert.assertFalse(enc3.validateSecurityToken(token4, RANGE));
        Assert.assertFalse(enc4.validateSecurityToken(token5, RANGE));
    }

    class Encriptador0 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 60 seconds");
            c = new Date(c.getTime() - 60000);
            return super.getDate(c);
        }

    }

    class Encriptador1 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 9 minutes");
            c = new Date(c.getTime() - 540000);
            return super.getDate(c);
        }
    }

    class Encriptador11 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 11 minutes");
            c = new Date(c.getTime() - 660000);
            return super.getDate(c);
        }
    }

    class Encriptador2 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 1 hour");
            c = new Date(c.getTime() - 3600000);
            return super.getDate(c);
        }
    }

    class Encriptador3 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 1 day");
            c = new Date(c.getTime() - 86400000);
            return super.getDate(c);
        }
    }

    class Encriptador4 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 1 month");
            c = new Date(c.getTime() - 2592000000L);
            return super.getDate(c);
        }
    }

    class Encriptador5 extends EncriptadorImpl {
        protected String getDate(Date c) {
            log("Caso 1 year");
            c = new Date(c.getTime() - 31104000000L);
            return super.getDate(c);
        }
    }

    public void log(String string) {
        return;
    }

}
