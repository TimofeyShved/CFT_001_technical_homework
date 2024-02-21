package com.company;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class WorkToFile {

    private String typeFile = "All";
    private OptionProject optionProject;
    private List<String> stringArrayList = new ArrayList<String>();

    // -------------------------------------------------------------------- Конструкторы

    public WorkToFile(){
    }

    public WorkToFile(String typeFile) throws IOException {
        this.typeFile = typeFile;
    }

    public WorkToFile(String s, OptionProject optionProject) throws IOException {
        this.LoadFile(s);
        this.optionProject = optionProject;
    }

    // -------------------------------------------------------------------- Загрузка

    public void LoadFile(String nameFile) throws IOException {
        try {
            //Получаем файл
            File file = new File(nameFile);

            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);

            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader readerIsFile = new BufferedReader(fr);

            String line;
            while ((line = readerIsFile.readLine()) != null) {
                if(!line.trim().isEmpty()){
                    this.stringArrayList.add(line);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Файл ("+nameFile+") не найден!");
            LoggClass.LOGGER.log(Level.WARNING,"Ошибка: Файл ("+nameFile+") не найден! "+ e.getMessage() , e);// логи
        }
    }

    // -------------------------------------------------------------------- Сохранение

    public void SaveFile(String nameFile){
        String p = this.optionProject.getP()+nameFile;
        String o = this.optionProject.getO();
        save(o+p, stringArrayList);
    }

    private void save(String nameFile, List<String> listValue){
        try {
            // запись данных в файл
            File file = new File(nameFile);
            //file.getParentFile().mkdirs();
            try(FileWriter writer = new FileWriter(file, this.optionProject.getA()))
            {
                // запись всей строки
                for (String s : listValue) { // пока не закончится наш список
                    writer.write(s); // записываем строку
                    writer.append('\n'); // переходим на новую строчку
                }
                //writer.flush(); // перезапись файла

            }
            catch(IOException ex){
                System.out.println(ex.getMessage()); // вывод ошибок при работе с файлом
                System.out.println("Файл не будет сохранён");
                LoggClass.LOGGER.log(Level.WARNING,"Ошибка: Файл не будет сохранён " + ex.getMessage() , ex);// логи
            }
        } catch (NullPointerException e){
            System.out.println("Ошибка: Файл не будет сохранён");
            LoggClass.LOGGER.log(Level.WARNING,"Ошибка: " + e.getMessage() , e);// логи
        }

    }

    // -------------------------------------------------------------------- Геттеры и сеттеры

    public List<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(List<String> stringArrayList) {
        this.stringArrayList.addAll(stringArrayList);
    }

    public OptionProject getOptionProject() {
        return optionProject;
    }

    public void setOptionProject(OptionProject optionProject) {
        this.optionProject = optionProject;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }
}
