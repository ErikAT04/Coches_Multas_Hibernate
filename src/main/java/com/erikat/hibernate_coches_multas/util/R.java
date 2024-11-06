package com.erikat.hibernate_coches_multas.util;

import java.io.File;
import java.net.URL;

public class R {
    public static URL getConfig(String path){
        return Thread.currentThread().getContextClassLoader().getResource("config"+ File.separator+path);
    }

    public static URL getScene(String path){
        return Thread.currentThread().getContextClassLoader().getResource("ui/"+path);
    }
}
