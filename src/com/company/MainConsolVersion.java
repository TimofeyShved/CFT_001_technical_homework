package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainConsolVersion {

    //------------------------------------------------------------------------------------ Основной класс ------------------------------------------------------------------

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println();

        // -------------------------------------------------------------- Принимаем значения из командной строки
        // принимам значения из командной строки,
        // например такое "in1.txt -o /some/path in2.txt -s -f -p result_ "
        //String input = "in1.txt -o /some/path in2.txt -s -f -p result_ ";
        System.out.println("------------------------------ EN -----------------------------------------");
        System.out.println("This utility allows you to filter data by type (int, float, string)");
        System.out.println("Specify the *.txt files and special keys");
        System.out.println("------------------------------ RU -----------------------------------------");
        System.out.println("Данная утилита, позволяет отфильтровать данные по типу (int, float, string)");
        System.out.println("Укажите файлы *.txt и специальные ключи");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("input|ввод:");
        try {
            Scanner sc = new Scanner(System.in);
            String input="";
            while (input.equals("")) {
                input = sc.nextLine();
            }

            // шаблон для Regex, указываем что файлы будут текстовые
            String textFileR = "[^ ]\\w*.txt";

            //Вытаскиваем список имён файлов
            List<String> loadFiles = loadFiles(input, textFileR);
            if (loadFiles.size()==0){
                throw new CustomException("Вы не указали файл с данными!");
            }

            // создаём настройки проекта
            String option = input.replaceAll(textFileR, "");
            OptionProject optionProject = new OptionProject(option);

            // -------------------------------------------------------------- Загрузка данных из файлов
            // Лист файлов
            List<WorkToFile> listWorks = new ArrayList<WorkToFile>();

            // создаем счётчик потоков
            CountDownLatch countDownLatch = new CountDownLatch(loadFiles.size()); // создаем счётчик

            // загружем их данными, у каждого файла свои данные
            for (String fileName: loadFiles){
                new LoadFilesInThreads(fileName, optionProject, listWorks, countDownLatch); // запихиваем в цикле, наши файлы в потоки, а так же присваиваем нашим потокам счётчик
                //listWorks.add(new WorkToFile(fileName, optionProject));
            }

            countDownLatch.await(); // данный поток, ждёт пока счётчик потоков не зоплнится

            // -------------------------------------------------------------- Фильтрация
            // общий список фильтрованных данных
            Map<WorkToFile,FillterValue> listFillterValue = new HashMap<WorkToFile,FillterValue>();
            FillterValue allValue = new FillterValue();

            // создаем счётчик потоков
            countDownLatch = new CountDownLatch(listWorks.size()); // создаем счётчик

            // вызываем фильтрацию
            for (WorkToFile work: listWorks){
                new FillterValueInThreads(work, listFillterValue, allValue, countDownLatch);
            }

            countDownLatch.await(); // данный поток, ждёт пока счётчик потоков не зоплнится

            // -------------------------------------------------------------- Сохранение
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
                if (newFile.getStringArrayList()!=null) {
                    newFile.SaveFile(nameFile);
                }else {
                    throw new CustomException("Данные типа ("+newFile.getTypeFile()+"), отсутвуют!");
                }
            }


            // -------------------------------------------------------------- Статистика

            StatisticsData statisticsData = new StatisticsData(allValue);
            if (optionProject.getF()) {
                System.out.println("------------------------------ FULL Statistic| Полная статистика -----------------------------------------");
                statisticsData.StatisticsFull();
            }else if(optionProject.getS()){
                System.out.println("------------------------------ SHORT Statistic| Кратка статистика -----------------------------------------");
                statisticsData.StatisticsShort();
            }else {
                System.out.println("Statistic disabled | Статистика отключена");
            }

            System.out.println("------------------------------ END|Конец -----------------------------------------");
            System.out.println("На этом фильтрация закончилась! Спасибо, что выбрали нашу утилиту ╰(▔∀▔)╯");
            input = sc.nextLine();
        } catch (CustomException e) {
            // Обрабатываем исключение
            System.out.println("Ошибка: " + e.getMessage());
            LoggClass.LOGGER.log(Level.WARNING,"Ошибка: " + e.getMessage() , e);// логи
        }
    }

    // Создаем собственное исключение
    static class CustomException extends Exception {
        public CustomException(String message) {
            super(message);
        }
    }

    public static List<String> loadFiles(String input, String textFileR){

        //создаём лист куда запишем их названия
        List<String> loadFiles = new ArrayList<>();

        //находим и записываем их в коллекцию
        Pattern patternSearhTextFile = Pattern.compile(textFileR);
        Matcher matcherSearhTextFile = patternSearhTextFile.matcher(input);
        while (matcherSearhTextFile.find()){
            loadFiles.add(matcherSearhTextFile.group());
        }

        return  loadFiles;
    }

    //------------------------------------------------------------------------------------ ПОТОКИ ------------------------------------------------------------------

    //------------------------------------------------------------------------------------ ПОТОК 1 - Загрузка
    // наш с вами поток
    static class LoadFilesInThreads extends Thread{
        String fileName; // файловое имя
        CountDownLatch countDownLatch; // счётчик
        List<WorkToFile> listWorks;
        OptionProject optionProject;

        public LoadFilesInThreads(String fileName, OptionProject optionProject, List<WorkToFile> listWorks, CountDownLatch countDownLatch) {
            this.fileName = fileName;// передаем файловое имя
            this.optionProject = optionProject;
            this.listWorks = listWorks;
            this.countDownLatch = countDownLatch; // передаем счётчик
            start(); // запускаем поток, run()
        }

        @Override
        public void run() {
            // построчное считывание файла
            try {
                listWorks.add(new WorkToFile(fileName, optionProject));
            } catch (IOException e) {
                e.printStackTrace();
                LoggClass.LOGGER.log(Level.WARNING,"Ошибка: " + e.getMessage() , e);// логи
            }
            countDownLatch.countDown(); // прибовляем к счётчику +1
        }
    }

    //------------------------------------------------------------------------------------ ПОТОК 2 - Фильтрация
    // наш с вами поток
    static class FillterValueInThreads extends Thread{
        WorkToFile work; // файл
        CountDownLatch countDownLatch; // счётчик
        Map<WorkToFile,FillterValue> listFillterValue;
        FillterValue allValue;

        public FillterValueInThreads(WorkToFile work, Map<WorkToFile,FillterValue> listFillterValue, FillterValue allValue, CountDownLatch countDownLatch) {
            this.work = work;// передаем файл
            this.listFillterValue = listFillterValue;
            this.allValue = allValue;
            this.countDownLatch = countDownLatch; // передаем счётчик
            start(); // запускаем поток, run()
        }

        @Override
        public void run() {
            // построчное считывание файла
            try {
                FillterValue fillterValue = new FillterValue();
                fillterValue.set(work.getStringArrayList());
                this.listFillterValue.put(work, fillterValue);
                this.allValue.add(fillterValue);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                LoggClass.LOGGER.log(Level.WARNING,"Ошибка: " + e.getMessage() , e);// логи
            } catch (IOException e) {
                e.printStackTrace();
                LoggClass.LOGGER.log(Level.WARNING,"Ошибка: " + e.getMessage() , e);// логи
            }
            countDownLatch.countDown(); // прибовляем к счётчику +1
        }
    }
}

