package com.company;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {


        // -------------------------------------------------------------- Принимаем значения из командной строки
        // принимам значения из командной строки,
        // например такое "in1.txt -o /some/path in2.txt -s -f -p result_ "
        String input = "in1.txt -o /some/path in2.txt -s -a -f -p result_ ";

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
        Map<WorkToFile,FillterValue> listFillterValue = new HashMap<WorkToFile,FillterValue>();
        FillterValue allValue = new FillterValue();

        // вызываем фильтрацию
        for (WorkToFile work: listWorks){
            FillterValue fillterValue = new FillterValue();
            fillterValue.set(work.getStringArrayList());
            listFillterValue.put(work, fillterValue);
            allValue.add(fillterValue);
        }

        // сохранение
        Map<String, WorkToFile> mapSaveToFile = new HashMap<String, WorkToFile> ();
        mapSaveToFile.put("integers.txt", new WorkToFile("int"));
        mapSaveToFile.put("strings.txt", new WorkToFile("String"));
        mapSaveToFile.put("floats.txt", new WorkToFile("float"));

        for (Map.Entry<String, WorkToFile> entry : mapSaveToFile.entrySet()) {
            String nameFile = entry.getKey();
            WorkToFile newFile = entry.getValue();
            for (WorkToFile work: listWorks){
                FillterValue fillterValue = listFillterValue.get(work);
                switch (newFile.getTypeFile()){
                    case "String":
                        newFile.setStringArrayList(fillterValue.getListString());
                        break;
                    case "int":
                        newFile.setStringArrayList(fillterValue.getListInt());
                        break;
                    case "float":
                        newFile.setStringArrayList(fillterValue.getListFloat());
                        break;
                    default:
                        System.out.println("NULL");
                        break;
                }
            }
            newFile.setOptionProject(optionProject);
            newFile.SaveFile(nameFile);
        }


        // -------------------------------------------------------------- получаем статистику


        StatisticsData statisticsData = new StatisticsData(allValue);
        statisticsData.StatisticsFull();

    }

}
