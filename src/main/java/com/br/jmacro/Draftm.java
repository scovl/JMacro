package com.br.jmacro;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Draftm implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_EQUALS) {
            GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                    (int) System.currentTimeMillis(), 0, NativeKeyEvent.VC_SPACE, NativeKeyEvent.CHAR_UNDEFINED));

            // time sleep 5 miliseconds
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            // VC_8
            GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                    (int) System.currentTimeMillis(), 0, NativeKeyEvent.VC_8, NativeKeyEvent.CHAR_UNDEFINED));

            // mouse moved = (946, 500)
            GlobalScreen.postNativeEvent(new NativeMouseEvent(NativeMouseEvent.NATIVE_MOUSE_MOVED,
                    (int) System.currentTimeMillis(), 946, 500, 0, NativeMouseEvent.BUTTON1));

            // VC_v
            GlobalScreen.postNativeEvent(new NativeKeyEvent(NativeKeyEvent.NATIVE_KEY_PRESSED,
                    (int) System.currentTimeMillis(), 0, NativeKeyEvent.VC_V, NativeKeyEvent.CHAR_UNDEFINED));
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

        GlobalScreen.addNativeKeyListener(new Draftm());
    }
}