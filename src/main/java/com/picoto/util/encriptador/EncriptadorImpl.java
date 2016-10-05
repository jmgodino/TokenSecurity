package com.picoto.util.encriptador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.DigestUtils;

public class EncriptadorImpl implements Encriptador {

    public static final int ONE_MINUTE = 60000;

    protected static final ResourceBundle bundle = ResourceBundle.getBundle("encriptador");

    public String getSecurityToken() {
        return getSecurityToken(new Date());
    }

    protected String getSecurityToken(Date d) {
        String data = getDate(d) + bundle.getString("private.key");
        return new Base32(false).encodeAsString(DigestUtils.sha256(data));
    }

    protected String getDate(Date d) {
        return "[" + getTimeSlot(d) + "]";
    }

    protected String getTimeSlot(Date d) {
        // Devuelve la hora en minutos
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(d);
    }

    public boolean validateSecurityToken(String token, int range) {
        if (token == null) {
            return false;
        }
        Date d = new Date();

        // Validamos intervalor [hora - rangeMinutos, hora + rangeMinutos]
        d = new Date(d.getTime() + range*ONE_MINUTE);

        boolean found = false;
        // Por si hubiera algun desajuste entre los relojes de las maquinas
        for (int i = 0; i <= 2*range; i++) {
            String generatedToken = getSecurityToken(d);
            if (token.equals(generatedToken)) {
                // Si uno de los token coincide, no hay que buscar mas
                found = true;
                break;
            }
            d = new Date(d.getTime() - ONE_MINUTE);
        }

        // Si no ha habido matching, entonces no se valida el token
        return found;

    }

}
