package atm_departmen;

import atm.ATM;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private final ATM atm;

    public Memento(ATM atm) {
        this.atm = atm;
    }

    public ATM getAtm() {
        return atm;
    }
}
