package mcsim.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

public class Portfolio {
    private List<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        options.add(option);
    }

    public void removeOption(Option option) {
        options.remove(option);
    }

    public List<Option> getOptions() {
        return List.copyOf(options);
    }

    public List<Option> getOptionsByTicker(String ticker) {
        return options.stream().filter(o -> o.getData().getTicker().equals(ticker)).collect(Collectors.toList());
    }

    public List<Option> getOptionsByMaturity(LocalDate maturityDate) {
        return options.stream().filter(o -> o.getMaturityDate().equals(maturityDate)).collect(Collectors.toList());
    }

    public double totalPayoff(Map<String, Double> spotPrices, LocalDate maturityDate) {
        double total = 0.0;
        for (Option option : options) {
            if (option.getMaturityDate().equals(maturityDate)) {
                String ticker = option.getData().getTicker();
                Double spotAtMaturity = spotPrices.get(ticker);
                if (spotAtMaturity == null) {
                    throw new IllegalArgumentException("Missing spot price for ticker: " + ticker);
                }
                total += option.payoff(spotAtMaturity);
            }
        }
        return total;
    }

    public double totalDiscountedPayoff(Map<String, Double> spotPrices,
                                    LocalDate maturityDate,
                                    double r,
                                    LocalDate valuationDate) {
        double payoff = totalPayoff(spotPrices, maturityDate);

        long days = ChronoUnit.DAYS.between(valuationDate, maturityDate);
        double T = days / 365.0;

        if (T < 0) {
            throw new IllegalArgumentException("Maturity date is before valuation date");
        }
        return payoff * Math.exp(-r * T);
    }

    public double totalPayoffByTicker(Map<String, Double> spotPrices, LocalDate maturityDate, String ticker) {
        return getOptionsByTicker(ticker).stream()
        .filter(o -> o.getMaturityDate().equals(maturityDate))
        .mapToDouble(o -> {
            Double spot = spotPrices.get(ticker);
            if (spot == null) throw new IllegalArgumentException("Missing spot price for ticker: " + ticker);
            return o.payoff(spot);
        }).sum();
    }

    public Map<String, Double> totalPayoffByAllTickers(Map<String, Double> spotPrices, LocalDate maturityDate) {
        return options.stream()
            .filter(o -> o.getMaturityDate().equals(maturityDate))
            .collect(Collectors.groupingBy(
                o -> o.getData().getTicker(),
                Collectors.summingDouble(o -> {
                    Double spot = spotPrices.get(o.getData().getTicker());
                    if (spot == null) throw new IllegalArgumentException("Missing spot price for ticker: " + o.getData().getTicker());
                    return o.payoff(spot);
                })
            ));
    }
}
