package com.br.jmacro;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
//import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jmacro implements NativeKeyListener {

    private static KeyConfig keyConfig;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        keyConfig = new KeyConfig("VC_EQUALS", "VK_8", "VC_DELETE");

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new Jmacro());
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == keyConfig.getKeyStart()) {
            try {
                Robot robot = new Robot();
                robot.keyPress(keyConfig.getKey1());
                robot.keyRelease(keyConfig.getKey1());
                //robot.mouseMove(946, 500);
                //robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                //robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getKeyCode() == keyConfig.getKeyStop()) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Do nothing.
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Do nothing.
    }
}
