package atm_departmen;

import atm.ATM;

import java.util.ArrayList;
import java.util.List;

public class RestoreAllATM implements Command {
    private final History history;


    public RestoreAllATM(History history) {
        this.history = history;

    }

    @Override
    public List<ATM> execute() {
        return new ArrayList<>(history.restore());
    }
}
