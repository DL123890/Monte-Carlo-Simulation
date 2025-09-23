package mcsim.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Option {
    private MarketData data;
    private OptionType type;
    private double strikePrice;
    private int quantity;
    private LocalDate maturityDate;

    Option(MarketData data, OptionType type, double strikePrice, int quantity, LocalDate maturityDate) {
        this.data = data;
        this.type = type;
        this.strikePrice = strikePrice;
        this.quantity = quantity;
        this.maturityDate = maturityDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public MarketData getData() {
        return data;
    }

    public OptionType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public double payoff(double spotAtMaturity) {
    if (type == OptionType.CALL) return Math.max(spotAtMaturity - strikePrice, 0) * quantity;
        else return Math.max(strikePrice - spotAtMaturity, 0) * quantity;
    }

    public double getDurationUntilMaturity(LocalDate start) {
        if (maturityDate.isBefore(start)) {
            throw new IllegalArgumentException("Maturity date is before start date");
        }
        long days = ChronoUnit.DAYS.between(start, maturityDate);
        return days / 365.0;
    }
}
