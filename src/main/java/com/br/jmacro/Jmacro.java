package com.br.jmacro;

import com.br.jmacro.exception.ConfigFileException;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
//import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.IntStream;


public class Jmacro implements NativeKeyListener {

    private Properties prop = new Properties();

    public Jmacro() throws ConfigFileException {
        try {
            prop.load(new FileInputStream("configMacro.properties"));
        } catch (IOException e) {
            throw new ConfigFileException("Error reading the config file.", e);
        }
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == Integer.parseInt(prop.getProperty("macroStartKey"))) {
            IntStream.rangeClosed(1, Integer.parseInt(prop.getProperty("numberOfKeys")))
                    .mapToObj(i -> {
                        String keyValue = prop.getProperty("key" + i);
                        if (keyValue == null || keyValue.isEmpty()) {
                            throw new IllegalArgumentException("The key " + i + " is invalid.");
                        }
                        return Integer.parseInt(keyValue);
                    })
                    .forEach(numCode -> {
                        GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                                (int) System.currentTimeMillis(), 0, numCode, NativeKeyEvent.CHAR_UNDEFINED));
                    });

        }

        // exit macro
        if (e.getKeyCode() == Integer.parseInt(prop.getProperty("macroEndKey"))) {
            System.out.println("Jmacro is exiting...");
            System.exit(0);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
    public static void main(String[] args) throws ConfigFileException {
        System.out.println("Jmacro is running...");
        try {
            LogManager.getLogManager().reset();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Jmacro());
    }
}