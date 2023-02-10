package com.br.jmacro;

import com.br.jmacro.exception.ConfigFileException;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws ConfigFileException {
        System.out.println("Jmacro is running...");

        if (args.length == 0) {
            throw new IllegalArgumentException("The fileName is required. Eg: java -jar JMacro.jar fileName");
        }

        String fileName = args[0];

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

        GlobalScreen.addNativeKeyListener(new Jmacro(fileName));
    }
}
