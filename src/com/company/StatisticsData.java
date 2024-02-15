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
        System.out.println("----------- целые -----");
        System.out.println("Целые максимум:");
        System.out.println(Collections.max(ListInt));
        System.out.println("Целые минимум:");
        System.out.println(Collections.min(ListInt));

        long intSum = 0;
        for (long n:ListInt){
            intSum+=n;
        }
        System.out.println("Сумма целых:");
        System.out.println(intSum);

        System.out.println("Среднее целых:");
        System.out.println((float) intSum/ListInt.size());

        System.out.println("---------- дробь -----");
        System.out.println("Дробные максимум:");
        System.out.println(Collections.max(ListFloat));
        System.out.println("Дробные минимум:");
        System.out.println(Collections.min(ListFloat));

        float intfloat = 0f;
        for (float n:ListFloat){
            intfloat+=n;
        }
        System.out.println("Сумма целых:");
        System.out.println(intfloat);

        System.out.println("Среднее целых:");
        System.out.println(intfloat/ListFloat.size());

        System.out.println("---------- строка -----");
        String maxS = ListString.get(0);
        String minS = ListString.get(0);
        for (String s:ListString){
            if (maxS.length()<s.length()) maxS=s;
            if (minS.length()>s.length()) minS=s;
        }
        System.out.println("Строка максимум:");
        System.out.println(maxS);
        System.out.println("Строка минимум:");
        System.out.println(minS);
    }

    public void StatisticsShort(){
        System.out.println("Количество элементов записанных в исходящие файлы:");
        System.out.println("Целые числа:");
        System.out.println(ListInt.size());
        System.out.println("Дробные числа:");
        System.out.println(ListFloat.size());
        System.out.println("Строки:");
        System.out.println(ListString.size());
    }
}
