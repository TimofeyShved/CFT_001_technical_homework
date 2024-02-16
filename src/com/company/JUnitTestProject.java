package com.company;

import org.junit.Test;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JUnitTestProject {

    @Test
    public void LoadFiles() throws Exception {
        String input="in1.txt -o /some/path dsf23sd45fewrg435fd.txt sadf4.txt -s -f -p result_  in3.txt";

        // шаблон для Regex, указываем что файлы будут текстовые
        String textFileR = "[^ ]\\w*.txt";

        //Вытаскиваем список имён файлов
        List<String> loadFiles = Main.loadFiles(input, textFileR);

        assertEquals(loadFiles.get(0), "in1.txt");
        assertEquals(loadFiles.get(1), "dsf23sd45fewrg435fd.txt");
        assertEquals(loadFiles.get(2), "sadf4.txt");
        assertEquals(loadFiles.get(3), "in3.txt");
    }

    @Test
    public void optionProject() throws Exception {
        String input="in1.txt -o /some/path -a -s -f -p result_";

        // шаблон для Regex, указываем что файлы будут текстовые
        String textFileR = "[^ ]\\w*.txt";

        //Вытаскиваем список имён файлов
        List<String> loadFiles = Main.loadFiles(input, textFileR);
        // создаём настройки проекта
        String option = input.replaceAll(textFileR, "");
        OptionProject optionProject = new OptionProject(option);

        assertEquals(optionProject.getA(), true);
        assertEquals(optionProject.getO(), Paths.get(".").toAbsolutePath().normalize().toString()+"/some/path"+"/");
        assertEquals(optionProject.getS(), true);
        assertEquals(optionProject.getF(), true);
        assertEquals(optionProject.getP(), "result_");

        input="in1.txt -o /path -s result_";

        // создаём настройки проекта
        option = input.replaceAll(textFileR, "");
        optionProject = new OptionProject(option);

        assertEquals(optionProject.getA(), false);
        assertEquals(optionProject.getO(), Paths.get(".").toAbsolutePath().normalize().toString()+"/path"+"/");
        assertEquals(optionProject.getS(), true);
        assertEquals(optionProject.getF(), false);
        assertEquals(optionProject.getP(), "");

        input="in1.txt";

        // создаём настройки проекта
        option = input.replaceAll(textFileR, "");
        optionProject = new OptionProject(option);

        assertEquals(optionProject.getA(), false);
        assertEquals(optionProject.getO(), "");
        assertEquals(optionProject.getS(), false);
        assertEquals(optionProject.getF(), false);
        assertEquals(optionProject.getP(), "");
    }

}
