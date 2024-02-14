package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillterValue {

    private List<String> ListInt = new ArrayList<String>();
    private List<String> ListFloat = new ArrayList<String>();
    private List<String> ListString = new ArrayList<String>();

    public FillterValue(){

    }

    public List<String> getListInt()  {
        return this.ListInt;
    }

    public List<String> getListFloat() {
        return this.ListFloat;
    }

    public List<String> getListString()  {
        return this.ListString;
    }

    public void add(FillterValue fillterValue){
        this.ListInt.addAll(fillterValue.ListInt);
        this.ListFloat.addAll(fillterValue.ListFloat);
        this.ListString.addAll(fillterValue.ListString);
    }

    public void set(List<String> stringArrayList) throws IOException {
        for (String line: stringArrayList){
            switch (FillterValue.wayThree(line)) {
                case "String":
                    ListString.add(line);
                break;
                case "int":
                    ListInt.add(line);
                break;
                case "float":
                    ListFloat.add(line);
                break;
                default:
                    System.out.println("NULL");
                break;
            }
        }
    }

    // Если что, мы используем 3 способ

    // --------------------------------------------------------------- 1 способ (нет)
    public static String wayOne(String s){
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
            return "String";
        } else {
            if (drob == true){
                return "float";
            }else {
                return "int";
            }
        }
    }


    // --------------------------------------------------------------- 2 способ (нет)
    public static String wayTwo(String s){
        // параметры запроса
        String result = "int";

        Pattern patternFillter1 = Pattern.compile("[A-DF-Za-df-zА-Яа-я]");
        Matcher matcherFillter1 = patternFillter1.matcher(s);
        while (matcherFillter1.find()){
            result = "String";
        }

        if (result.equals("int")) {
            Pattern patternFillter2 = Pattern.compile(".*[.].*");
            Matcher matcherFillter2 = patternFillter2.matcher(s);
            while (matcherFillter2.find()) {
                result = "float";
            }
        }

        return result;
    }


    // --------------------------------------------------------------- 3 способ (да)
    public static String wayThree(String s){
        // результат проверки
        if (isNum(s)){
            return "int";
        } else {
            if (isFloat(s)){
                return "float";
            }else {
                return "String";
            }
        }

    }

    public static boolean isFloat(String s){
        try {
            double d = Float.parseFloat(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    public static boolean isNum(String s){
        try {
            Long i = Long.parseLong(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
