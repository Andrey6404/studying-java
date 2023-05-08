package com.StockPackage;

public class Stock {
    private String Name            = null;
    private double StartPrice      = 0.0;

    private double CurrentPrice    = 0.0;
    private double DailyVolatility = 0.0;
    private double RandDailyChange = 0.0;
    private StockPriceGenerator stockPriceGenerator;

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
        return CurrentPrice;
    }

    public double getCurrentPrice() {
        return CurrentPrice;
    }

}
