package mcsim.data;

import java.time.LocalDate;

public class Option {
    private MarketData data;
    private OptionType type;
    private double strikePrice;
    private int quantity;

    Option(MarketData data, OptionType type, double strikePrice, int quantity) {
        this.data = data;
        this.type = type;
        this.strikePrice = strikePrice;
        this.quantity = quantity;
    }

    public LocalDate getMaturityDate() {
        return data.getMaturityDate();
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
}
