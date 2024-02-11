package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        //--------------------------------------------- Получаем файл
        File file = new File("in1.txt");

        //создаем объект FileReader для объекта File
        FileReader fr = new FileReader(file);

        //создаем BufferedReader с существующего FileReader для построчного считывания
        BufferedReader reader = new BufferedReader(fr);

        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            if(!line.trim().isEmpty()){
                Main.Varieble1(line);
                System.out.println(line);
                System.out.println("-------------");
            }
        }



    }

    public static void Varieble1(String s){
        // параметры запроса
        boolean num=true; // число?
        boolean drob=false; // дробное?

        // массивы символов
        char[] digits= new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '+','E', 'e'};
        char[] charArray = s.toCharArray();

        // цикл проверки
        for(char c1: charArray) {
            // дробное?
            if (c1 == '.'){
                drob=true;
            }
            //проверка на возможные символы, не относящиеся к числу (не оптимизировано)
            for (char c2: digits){
                if(c1 == c2){
                    num=true;
                    break;
                }else {
                    num=false;
                }
            }
        }

        // результат проверки
        if (num==false){
            System.out.println("String");
        } else {
            if (drob == true){
                System.out.println("float");
            }else {
                System.out.println("int");
            }
        }
    }
}
