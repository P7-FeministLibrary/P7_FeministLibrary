package com.feministlibrary;

import org.junit.jupiter.api.Test;

import com.feministlibrary.view.AuthorView;

import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorViewTest {

    @Test
    void test_Get_Author_Full_Name() {
        String simulatedInput = "Angela Davis";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        AuthorView view = new AuthorView();
        String fullName = view.getAuthorFullName();

        assertEquals("Angela Davis", fullName);
    }
}
