package atm_departmen;

import atm.ATM;
import atm.ATMImpl;
import atm.Banknotes;

import java.util.Set;
import java.util.TreeSet;

public class ATMBuilderImpl implements ATMBuilder{
    private final Set<Banknotes> banknotes;

    ATMBuilderImpl () {
        banknotes = new TreeSet<>();
    }

    @Override
    public void setBanknotes(Banknotes banknote) {
        banknotes.add(banknote);
    }

    @Override
    public ATM getNewATM() {
        return new ATMImpl(banknotes);
    }
}