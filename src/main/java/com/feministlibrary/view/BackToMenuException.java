package com.feministlibrary.view;

import com.feministlibrary.Style;

public class BackToMenuException extends RuntimeException {
    public BackToMenuException() {
        super(Style.styleBlue("Returning"));
    }
    
    public BackToMenuException(String message) {
        super(message);
        
    }
}
