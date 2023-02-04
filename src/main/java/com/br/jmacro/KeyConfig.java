package com.br.jmacro;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyConfig {
    private static final Properties keyProperties = new Properties();

    static {
        try {
            keyProperties.load(new FileInputStream("settings.cfg"));
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de configuração");
            System.exit(1);
        }
    }

    private int keyStart;
    private int key1;
    private int keyStop;

    public KeyConfig() throws NoSuchFieldException, IllegalAccessException {
        keyStart = NativeKeyEvent.class.getField(keyProperties.getProperty("keyStart")).getInt(null);
        key1 = NativeKeyEvent.class.getField(keyProperties.getProperty("key1")).getInt(null);
        keyStop = NativeKeyEvent.class.getField(keyProperties.getProperty("keyStop")).getInt(null);
    }

    public KeyConfig(String vcEquals, String vk8, String vcDelete) {
    }

    public int getKeyStart() {
        return keyStart;
    }

    public int getKey1() {
        return key1;
    }

    public int getKeyStop() {
        return keyStop;
    }
}