package atm_departmen;

import atm.ATM;

import java.util.List;

public interface Department {
    void addATM(ATMBuilder atmBuilder);
    void restoreATM();
    int getCurrentBalance();

    //Метод для проверки работы отдельных банкоматов
    List<ATM> getAtmList();
}
