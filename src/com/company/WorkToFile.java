package com.company;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public class WorkToFile {

    private BufferedReader readerIsFile;
    private OptionProject optionProject;

    public WorkToFile(){
    }

    public WorkToFile(String s, OptionProject optionProject) throws FileNotFoundException {
        this.LoadFile(s);
        this.optionProject = optionProject;
    }

    public void LoadFile(String nameFile) throws FileNotFoundException {
        //Получаем файл
        File file = new File(nameFile);

        //создаем объект FileReader для объекта File
        FileReader fr = new FileReader(file);

        //создаем BufferedReader с существующего FileReader для построчного считывания
        readerIsFile = new BufferedReader(fr);
    }

    public BufferedReader get(){
        return this.readerIsFile;
    }

    public void set(BufferedReader readerIsFile){
        this.readerIsFile = readerIsFile;
    }

    public void SaveFile(FillterValue fillterValue){
        save(this.optionProject.getO()+this.optionProject.getP()+"integers.txt", fillterValue.getListInt());
        save(this.optionProject.getO()+this.optionProject.getP()+"floats.txt", fillterValue.getListFloat());
        save(this.optionProject.getO()+this.optionProject.getP()+"strings.txt", fillterValue.getListString());
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
}
