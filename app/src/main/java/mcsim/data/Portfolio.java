package mcsim.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Portfolio {
    List<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        options.add(option);
    }

    public void removeOption(Option option) {
        options.remove(option);
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Option> getOptionsByTicker(String ticker) {
        List<Option> tOptions = options.stream().filter(o -> o.getData().getTicker() == ticker).collect(Collectors.toList());
        return tOptions;
    }
}
