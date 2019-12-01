package atm_departmen;

import atm.*;

import java.util.ArrayList;
import java.util.List;

public class DepartmentATM implements Department{
    private List<ATM> atmList = new ArrayList<>();
    private List<Listener> listeners = new ArrayList<>();
    private final History history = new History();

    @Override
    public void addATM(Banknotes...banknotes) {
        ATMBuilder builder = new ATMBuilderImpl();
        builder.setBanknotes(banknotes);
        ATM atm = builder.getNewATM();
        saveState(atm);
        atmList.add(atm);
        subscribeATM(atm);
    }

    public void subscribeATM(ATM atm) {
        listeners.add(atm);
    }

    public void unsubscribeATM(ATM atm) {
        listeners.remove(atm);
    }

    @Override
    public void saveState(ATM atm) {
        history.saveState(atm);
//        new SaveState(atm, history);
    }

    @Override
    public void restore() {
        atmList = new RestoreAllATM(history).execute();
        listeners = new ArrayList<>(atmList);
    }

    @Override
    public int getCurrentBalance() {
        return new GetBalance(listeners).execute();
    }

    //Метод для проверки работы отдельных банкоматов
    public List<ATM> getAtmList() {
        return atmList;
    }
}