package com.br.jmacro;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Remap implements NativeKeyListener {

    // Open configRemap.properties file
    private Properties prop = new Properties();

    public Remap() {
        try {
            prop.load(new FileInputStream("configRemap.properties"));
        } catch (IOException e) {
            System.out.println("Could not load configRemap.properties file");
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

        // if keyCode == NativeKeyEvent.VC_EQUALS then execute config.properties
        if (keyCode == Integer.parseInt(prop.getProperty("key_a"))) {
            // press key_a without printing key_a
            GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                    (int) System.currentTimeMillis(), 0, Integer.parseInt(prop.getProperty("key_b")), NativeKeyEvent.CHAR_UNDEFINED));
        }

        // exit macro
        if (e.getKeyCode() == NativeKeyEvent.VC_DELETE) {
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

        GlobalScreen.addNativeKeyListener(new Remap());
    }
}