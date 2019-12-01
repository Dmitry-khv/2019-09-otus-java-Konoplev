package atm_departmen;

import atm.Listener;

import java.util.List;


public class GetBalance implements Command {
    private final List<Listener> listeners;

    public GetBalance(List<Listener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public Integer execute() {
        int currentBalance = 0;
        for(Listener listener : listeners)
            currentBalance += listener.getCurrentBalance();
        return currentBalance;
    }
}
