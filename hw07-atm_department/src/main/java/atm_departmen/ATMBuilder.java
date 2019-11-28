package atm_departmen;

import atm.ATM;
import atm.Banknotes;

public interface ATMBuilder {
    void setBanknotes(Banknotes banknotes);
    ATM getNewATM();
}