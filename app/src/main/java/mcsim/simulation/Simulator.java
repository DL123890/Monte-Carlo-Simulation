package mcsim.simulation;

import mcsim.data.MarketData;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Simulator {
    private final Random random = new Random();
    public double[][] simulatePaths(MarketData data,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    int steps,
                                    int numPaths) {

        double S0 = data.getSpotPrice();
        double sigma = data.getVolatility();
        double r = data.getRfr();
        double q = data.getDivYield();

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        double dt = ((double) totalDays) / (365.0 * steps);

        double[][] paths = new double[numPaths][steps + 1];

        for (int p = 0; p < numPaths; p++) {
            paths[p][0] = S0;
            for (int t = 1; t <= steps; t++) {
                double Z = random.nextGaussian();
                double drift = (r - q - 0.5 * sigma * sigma) * dt;
                double diffusion = sigma * Math.sqrt(dt) * Z;
                paths[p][t] = paths[p][t - 1] * Math.exp(drift + diffusion);
            }
        }

        return paths;
    }
}
