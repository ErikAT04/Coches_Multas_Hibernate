package com.erikat.hibernate_coches_multas.util;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class R {
    public static InputStream getConfig(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("config"+ File.separator+path);
    }

    public static URL getResource(String path){
        return Thread.currentThread().getContextClassLoader().getResource("ui/"+path);
    }
}
