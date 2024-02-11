package com.company;

import java.io.*;
import java.util.List;

public class WorkToFile {

    private BufferedReader readerIsFile;

    public WorkToFile(){
    }

    public WorkToFile(String s) throws FileNotFoundException {
        this.LoadFile(s);
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
        save("sample-integers.txt", fillterValue.getListInt());
        save("sample-floats.txt", fillterValue.getListFloat());
        save("sample-strings.txt", fillterValue.getListString());
    }


    private void save(String nameFile, List<String> listValue){
        // запись данных в файл
        try(FileWriter writer = new FileWriter(nameFile, false))
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
