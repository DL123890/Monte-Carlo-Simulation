package mcsim.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class MarketData {
    private double spotPrice;
    private double rfr;
    private double volatility;
    private double divYield;
    private LocalDate maturityDate;

    public MarketData(double spotPrice, double rfr, double volatility, 
                      double divYield, LocalDate maturityDate) {
        this.spotPrice = spotPrice;
        this.rfr = rfr;
        this.volatility = volatility;
        this.divYield = divYield;
        this.maturityDate = maturityDate;
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

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public double getDurationUntilMaturity(LocalDate start) {
        if (maturityDate.isBefore(start)) {
            throw new IllegalArgumentException("Maturity date is before start date");
        }
        long days = ChronoUnit.DAYS.between(start, maturityDate);
        return days / 365.0;
    }
}
