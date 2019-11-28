package atm_departmen;

import atm.*;

import java.util.List;

public class DemoDepartment {
    public static void main(String[] args) {
        Department department = new DepartmentATM();
        ATMBuilder atmBuilder1 = new ATMBuilderImpl();
        atmBuilder1.setBanknotes(new Rub(Value.ONE_HUNDRED, 10));
        atmBuilder1.setBanknotes(new Rub(Value.FIVE_HUNDREDS, 10));
        atmBuilder1.setBanknotes(new Rub(Value.ONE_THOUSAND, 10));
        atmBuilder1.setBanknotes(new Rub(Value.FIVE_THOUSANDS, 10));
        department.addATM(atmBuilder1);
        System.out.println(department.getCurrentBalance());

        ATMBuilder atmBuilder2 = new ATMBuilderImpl();
        atmBuilder2.setBanknotes(new Rub(Value.ONE_HUNDRED, 20));
        atmBuilder2.setBanknotes(new Rub(Value.FIVE_HUNDREDS, 20));
        atmBuilder2.setBanknotes(new Rub(Value.ONE_THOUSAND, 20));
        atmBuilder2.setBanknotes(new Rub(Value.FIVE_THOUSANDS, 20));
        department.addATM(atmBuilder2);
        System.out.println(department.getCurrentBalance());

        List<ATM> atmList = department.getAtmList();

        for (ATM atm : atmList) {
            atm.putToCassette(new Rub(Value.ONE_HUNDRED, 10));
            atm.putToCassette(new Rub(Value.FIVE_HUNDREDS, 10));
            atm.putToCassette(new Rub(Value.ONE_THOUSAND, 10));
            atm.putToCassette(new Rub(Value.FIVE_THOUSANDS, 10));
        }
        System.out.println(department.getCurrentBalance());
        department.restoreATM();
        System.out.println(department.getCurrentBalance());
    }
}