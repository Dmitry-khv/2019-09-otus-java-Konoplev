package atm_departmen;

import atm.ATM;

import java.util.*;

public class History {
    private final List<Memento> historyList = new ArrayList<>();

    public void saveState(ATM atm) {
        historyList.add(atm.saveState());
    }

    public List<ATM> restore() {
        List <ATM> list = new ArrayList<>();
        for(Memento memento : historyList)
            list.add(memento.getAtm());
        return list;
    }
}
