package com.Portfolio;

public class Portfolio {
    private double deposit = 1000;
    private int stockCount = 0;

    public Portfolio(double deposit, int stockCount) {
        this.deposit = deposit;
        this.stockCount = stockCount;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
