package atm_departmen;

import atm.ATM;
import atm.Banknotes;

import java.util.List;
import java.util.Set;

public interface Department {
    void addATM(Banknotes...banknotes);
    void restore();
    int getCurrentBalance();
    void saveState(ATM atm);

//    Метод для проверки работы отдельных банкоматов
    List<ATM> getAtmList();
}
