package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {


        // -------------------------------------------------------------- Принимаем значения из командной строки
        // принимам значения из командной строки,
        // например такое "in1.txt -o /some/path in2.txt -s -f -p result_ "
        String input = "in1.txt -o /some/path in2.txt -s -f -p result_ ";

        // теперь достанем данные по поводу текстовых файлов
        String textFileR = "[^ ]\\w*.txt";

        //создаём лист куда запишем их названия
        List<String> loadFiles = new ArrayList<>();

        //находим и записываем их в коллекцию
        Pattern patternSearhTextFile = Pattern.compile(textFileR);
        Matcher matcherSearhTextFile = patternSearhTextFile.matcher(input);
        while (matcherSearhTextFile.find()){
            loadFiles.add(matcherSearhTextFile.group());
        }

        // создаём настройки проекта
        String option = input.replaceAll(textFileR, "");
        OptionProject optionProject = new OptionProject(option);

        // -------------------------------------------------------------- Теперь можно загрузить данные из файлов
        // Лист файлов
        List<WorkToFile> listWorks = new ArrayList<WorkToFile>();
        // загружем их данными, у каждого файла свои данные
        for (String s: loadFiles){
            listWorks.add(new WorkToFile(s, optionProject));
        }

        // -------------------------------------------------------------- Отфильтровать и сохранить
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
