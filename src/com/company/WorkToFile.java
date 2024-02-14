package com.company;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    }

    // -------------------------------------------------------------------- Сохранение

    public void SaveFile(String nameFile){
        save(this.optionProject.getO()+this.optionProject.getP()+nameFile, stringArrayList);
    }

    private void save(String nameFile, List<String> listValue){
        // запись данных в файл
        File file = new File(nameFile);
        file.getParentFile().mkdirs();
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
