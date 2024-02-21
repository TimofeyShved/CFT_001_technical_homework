package com.company;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    //------------------------------------------------------------------------------------ Основной класс ------------------------------------------------------------------

    public static void main(String[] args) throws IOException, InterruptedException {
        MainConsolVersion mainConsolVersion = new MainConsolVersion();
        mainConsolVersion.main(args);
    }
}
