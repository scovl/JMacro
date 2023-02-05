package com.br.jmacro;

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

    public Jmacro() {
        try {
            prop.load(new FileInputStream("configMacro.properties"));
        } catch (IOException e) {
            System.out.println("Could not load configRemap.properties file");
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == Integer.parseInt(prop.getProperty("macroStartKey"))) {
            IntStream.rangeClosed(1, Integer.parseInt(prop.getProperty("numberOfKeys")))
                    .mapToObj(i -> Integer.parseInt(prop.getProperty("key" + i)))
                    .forEach(numCode -> GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                            (int) System.currentTimeMillis(), 0, numCode, NativeKeyEvent.CHAR_UNDEFINED)));

            // time sleep 5 miliseconds
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        // exit macro
        if (e.getKeyCode() == Integer.parseInt(prop.getProperty("macroEndKey"))) {
            System.exit(0);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
    public static void main(String[] args) {
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