package com.feministlibrary;

import org.junit.jupiter.api.*;

import com.feministlibrary.view.GenreView;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class GenreViewTest { //simula la entrada del usuario y captura lo que se imprime

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void restoreSystemIO() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testGetGenreName() {
        String simulatedUserInput = "Feminism\n";
        testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);

        GenreView view = new GenreView();
        String result = view.getGenreName();

        assertEquals("Feminism", result);
        assertTrue(testOut.toString().contains("Enter genre name:"));
    }

    @Test
    void testShowMessage() {
        GenreView view = new GenreView();
        view.showMessage("Genre added successfully");

        String output = testOut.toString();
        assertTrue(output.contains("Genre added successfully"));
    }
}
