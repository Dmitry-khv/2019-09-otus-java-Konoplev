package atm_departmen;

import atm.ATM;
import atm.ATMImpl;
import atm.Banknotes;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class ATMBuilderImpl implements ATMBuilder{
    private final Set<Banknotes> bundle = new TreeSet<>();

    @Override
    public void setBanknotes(Banknotes...banknotes) {
        bundle.addAll(Arrays.asList(banknotes));
    }

    @Override
    public ATM getNewATM() {
        return new ATMImpl(bundle);
    }
}