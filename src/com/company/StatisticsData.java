package com.company;

public class StatisticsData {

    private FillterValue fillterValue;

    StatisticsData(FillterValue fillterValue){
        this.fillterValue = fillterValue;
    }

    public void StatisticsFull(){
        System.out.println(fillterValue.getListInt().size());
        System.out.println(fillterValue.getListFloat().size());
        System.out.println(fillterValue.getListString().size());
    }
}
