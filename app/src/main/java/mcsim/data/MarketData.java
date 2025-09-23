package mcsim.data;

public class MarketData {
    private double spotPrice;
    private double rfr;
    private double volatility;
    private double divYield;
    private String ticker;

    public MarketData(double spotPrice, double rfr, double volatility, 
                      double divYield, String ticker) {
        this.spotPrice = spotPrice;
        this.rfr = rfr;
        this.volatility = volatility;
        this.divYield = divYield;
        this.ticker = ticker;
    }

    public double getSpotPrice() {
        return spotPrice;
    }

    public double getRfr() {
        return rfr;
    }

    public double getVolatility() {
        return volatility;
    }

    public double getDivYield() {
        return divYield;
    }

    public String getTicker() {
        return ticker;
    }
}
