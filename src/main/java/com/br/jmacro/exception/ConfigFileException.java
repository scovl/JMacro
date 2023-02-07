package com.br.jmacro.exception;

import java.io.IOException;

public class ConfigFileException extends IOException {
        public ConfigFileException(String message) {
            super(message);
        }

        public ConfigFileException(String message, Throwable cause) {
            super(message, cause);
        }
}
