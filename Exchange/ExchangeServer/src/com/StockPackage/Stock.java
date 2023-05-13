package com.StockPackage;

import java.util.ArrayList;

public class Stock {
    private String Name            = null;
    private double StartPrice      = 0.0;

    private double CurrentPrice    = 0.0;
    private double DailyVolatility = 0.0;
    private double RandDailyChange = 0.0;
    private StockPriceGenerator stockPriceGenerator;

    private ArrayList<Double> Story = new ArrayList<>();

    public Stock(String Name, double StartPrice, double DailyVolatility) {
        this.Name = Name;
        this.StartPrice = StartPrice;
        CurrentPrice = this.StartPrice;
        this.DailyVolatility = DailyVolatility;
        this.RandDailyChange = Math.random();
        this.stockPriceGenerator = new StockPriceGenerator(DailyVolatility, StartPrice);
    }

    public double GenerateNewPrice() {
        RandDailyChange = Math.random();
        CurrentPrice = stockPriceGenerator.GetCurrentPrice(RandDailyChange);
        Story.add(CurrentPrice);
        return CurrentPrice;
    }

    public double getCurrentPrice() {
        return CurrentPrice;
    }

    public int getStoryLength() {
        return Story.size();
    }
    public ArrayList<Double> getStoryPrise() {
        return Story;
    }
    public ArrayList<Double> getStoryPrise(int StartDay){
        ArrayList<Double> tmpArr = new ArrayList<>();
        for(int i=StartDay;i<Story.size();i++){
            tmpArr.add(Story.get(i));
        }
        return tmpArr;
    }
    public ArrayList<Double> getStoryPrise(int StartDay, int LastDay){
        ArrayList<Double> tmpArr = new ArrayList<>();
        for(int i=StartDay;i<=LastDay;i++){
            tmpArr.add(Story.get(i));
        }
        return tmpArr;
    }
}
