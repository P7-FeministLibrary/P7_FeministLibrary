package com.feministlibrary;

import com.feministlibrary.config.DBManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        DBManager.init();
        DBManager.close();
    }
}
