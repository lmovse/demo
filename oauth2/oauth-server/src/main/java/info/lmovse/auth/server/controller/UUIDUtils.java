package info.lmovse.auth.server.controller;

import java.util.UUID;

public class UUIDUtils {

    public static String getID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String getCode() {
        return getID();
    }

    public static String getUUIDName(String filename) {
        return UUID.randomUUID().toString().replace("-", "")
                + filename.substring(filename.lastIndexOf("."), filename.length());
    }
}
