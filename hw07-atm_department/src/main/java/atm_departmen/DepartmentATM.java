package atm_departmen;

import atm.ATM;

import java.util.ArrayList;
import java.util.List;

public class DepartmentATM implements Department{
    private List<ATM>atmList = new ArrayList<>();
    private final List<ATM>savedATM = new ArrayList<>();

    @Override
    public void addATM(ATMBuilder atmBuilder) {
        ATM atm = atmBuilder.getNewATM();
        atmList.add(atm);
        savedATM.add(atm.clone());
    }

    @Override
    public void restoreATM() {
        atmList = savedATM;
    }

    @Override
    public int getCurrentBalance() {
        int currentBalance = 0;
        for (ATM atm : atmList)
            currentBalance += atm.getCurrentBalance();
        return currentBalance;
    }

    //Метод для проверки работы отдельных банкоматов
    public List<ATM> getAtmList() {
        return atmList;
    }
}