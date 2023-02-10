package com.br.jmacro;

import com.br.jmacro.exception.ConfigFileException;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
//import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.Properties;
import java.util.stream.IntStream;


public class Jmacro implements NativeKeyListener {

    private final Properties prop = new Properties();

    public Jmacro(String fileName) throws ConfigFileException {
        try (InputStream inputStream = new FileInputStream(fileName)) {
            prop.load(inputStream);
        } catch (IOException e) {
            throw new ConfigFileException("Error reading the config file.", e);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

        // macro 1
        // numberOfMacros variable is used to define the number of macros that will be used.
        int numberOfMacros = Integer.parseInt(prop.getProperty("numberOfMacros"));

        final String macro = "macro";

        // if numberOfMacros is greater than 1, then macro1_StartKey is "macro" + i + "_StartKey"
        IntStream.rangeClosed(1, numberOfMacros)
                .forEach(i -> {
                    if (keyCode == Integer.parseInt(prop.getProperty(macro + i + "_StartKey"))) {
                        IntStream.rangeClosed(1, Integer.parseInt(prop.getProperty(macro + i + "_numberOfKeys")))
                                .mapToObj(j -> {
                                    String keyValue = prop.getProperty(macro + i + "_key" + j);
                                    if (keyValue == null || keyValue.isEmpty()) {
                                        throw new IllegalArgumentException("The " + macro + i + "_key" + j + " is not defined in the config file." );
                                    }
                                    return Integer.parseInt(keyValue);
                                })
                                .forEach(numCode -> GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                                        (int) System.currentTimeMillis(), 0, numCode, NativeKeyEvent.CHAR_UNDEFINED)));
                    }
                });

        // exit macro
        if (e.getKeyCode() == Integer.parseInt(prop.getProperty("macroEndKey"))) {
            System.out.println("Jmacro is exiting...");
            System.exit(0);
        }
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // No code needed here for now
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // No code needed here for now
    }
}