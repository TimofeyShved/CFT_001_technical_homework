package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsData {

    private List<Long> ListInt = new ArrayList<Long>();
    private List<Float> ListFloat = new ArrayList<Float>();
    private List<String> ListString = new ArrayList<String>();

    StatisticsData(FillterValue fillterValue){
        this.ListString = fillterValue.getListString();
        for (String s: fillterValue.getListInt()){
            ListInt.add(Long.parseLong(s));
        }
        for (String s: fillterValue.getListFloat()){
            ListFloat.add(Float.parseFloat(s));
        }
    }

    public void StatisticsFull(){
        this.StatisticsShort();
        if(ListInt.size()!=0) {
            System.out.println("----------- Integers/целые -----");
            System.out.println("MAX/Целые максимум:");
            System.out.println(Collections.max(ListInt));
            System.out.println("MIN/Целые минимум:");
            System.out.println(Collections.min(ListInt));
            long intSum = 0;
            for (long n : ListInt) {
                intSum += n;
            }
            System.out.println("SUM/Сумма целых:");
            System.out.println(intSum);

            System.out.println("AVERAGE/Среднее целых:");
            System.out.println((float) intSum / ListInt.size());
        }else{
            System.out.println("Unfortunately, there is no data of the type (Integers)");
            System.out.println("К сожелению данных типа (целые) нету");
        }

        if(ListInt.size()!=0) {
            System.out.println("---------- Floats/дробь -----");
            System.out.println("MAX/Дробные максимум:");
            System.out.println(Collections.max(ListFloat));
            System.out.println("MIN/Дробные минимум:");
            System.out.println(Collections.min(ListFloat));

            float intfloat = 0f;
            for (float n : ListFloat) {
                intfloat += n;
            }
            System.out.println("SUM/Сумма целых:");
            System.out.println(intfloat);

            System.out.println("AVERAGE/Среднее целых:");
            System.out.println(intfloat / ListFloat.size());
        }else {
            System.out.println("Unfortunately, there is no data of the type (Floats)");
            System.out.println("К сожелению данных типа (дробь) нету");
        }

        if(ListInt.size()!=0) {
            System.out.println("---------- Strings/строка -----");
            String maxS = ListString.get(0);
            String minS = ListString.get(0);
            for (String s : ListString) {
                if (maxS.length() < s.length()) maxS = s;
                if (minS.length() > s.length()) minS = s;
            }
            System.out.println("MAX/Строка максимум:");
            System.out.println(maxS);
            System.out.println("MIN/Строка минимум:");
            System.out.println(minS);
        }else {
            System.out.println("Unfortunately, there is no data of the type (Strings)");
            System.out.println("К сожелению данных типа (строка) нету");
        }
    }

    public void StatisticsShort(){
        System.out.println("Number of items written to outgoing files");
        System.out.println("Количество элементов записанных в исходящие файлы");
        System.out.println("Integers/Целые числа:");
        System.out.println(ListInt.size());
        System.out.println("Floats/Дробные числа:");
        System.out.println(ListFloat.size());
        System.out.println("Strings/Строки:");
        System.out.println(ListString.size());
    }
}
