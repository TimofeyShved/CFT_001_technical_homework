package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> loadFiles = new ArrayList<>();
        loadFiles.add("in1.txt");
        loadFiles.add("in2.txt");

        // Лист файлов
        List<WorkToFile> listWorks = new ArrayList<WorkToFile>();
        // загружем их данными, у каждого файла свои данные
        for (String s: loadFiles){
            listWorks.add(new WorkToFile(s));
        }

        // общий список фильтрованных данных
        FillterValue fillterValue = new FillterValue();
        // вызываем фильтрацию
        for (WorkToFile work: listWorks){
            fillterValue.set(work.get());
        }

        // сохранение
        for (WorkToFile work: listWorks){
            work.SaveFile(fillterValue);
        }




    }

}
