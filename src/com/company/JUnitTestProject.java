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
    public void loadFiles() throws Exception {
        String input="in1.txt -o /some/path dsf23sd45fewrg435fd.txt sadf4.txt -s -f -p result_  in3.txt";

        // шаблон для Regex, указываем что файлы будут текстовые
        String textFileR = "[^ ]\\w*.txt";

        //Вытаскиваем список имён файлов
        List<String> loadFiles = MainConsolVersion.loadFiles(input, textFileR);

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
        List<String> loadFiles = MainConsolVersion.loadFiles(input, textFileR);
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

        input="3.txt 324 1.txt in1.txt  -p-o-p-32-dfs -s-f -o sdaf w/safe in2.txt fef324/23-23- -p =s!#fsfr";
        // создаём настройки проекта
        option = input.replaceAll(textFileR, "");
        optionProject = new OptionProject(option);

        assertEquals(optionProject.getA(), false);
        assertEquals(optionProject.getO(), Paths.get(".").toAbsolutePath().normalize().toString()+"/sdafw/safefef324/23/");
        assertEquals(optionProject.getS(), true);
        assertEquals(optionProject.getF(), true);
        assertEquals(optionProject.getP(), "=s!#fsfr");
    }

    @Test
    public void fillterValue() throws Exception {
        List<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("Text");
        stringArrayList.add("-6464");
        stringArrayList.add("-15.245545454654");
        stringArrayList.add("125.5468");
        stringArrayList.add("2.74-e");
        stringArrayList.add("2.74E-1");
        stringArrayList.add("One");
        stringArrayList.add("404 error");
        stringArrayList.add("878768768768768874");
        stringArrayList.add("8787 6876 8768 7688 7468478");
        stringArrayList.add("E74");

        FillterValue allValue = new FillterValue();
        allValue.set(stringArrayList);

        List<String> ListInt = allValue.getListInt();
        List<String> ListFloat = allValue.getListFloat();
        List<String> ListString = allValue.getListString();

        assertEquals(ListInt.get(0), "-6464");
        assertEquals(ListInt.get(1), "878768768768768874");
        assertEquals(ListFloat.get(0), "-15.245545454654");
        assertEquals(ListFloat.get(1), "125.5468");
        assertEquals(ListFloat.get(2), "2.74E-1");
        assertEquals(ListString.get(0), "Text");
        assertEquals(ListString.get(1), "2.74-e");
        assertEquals(ListString.get(2), "One");
        assertEquals(ListString.get(3), "404 error");
        assertEquals(ListString.get(4), "8787 6876 8768 7688 7468478");
        assertEquals(ListString.get(5), "E74");
    }
}
