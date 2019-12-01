package atm_departmen;

import atm.*;

import java.util.List;

public class DemoDepartment {
    public static void main(String[] args) {
        Department department = new DepartmentATM();
        department.addATM(new Rub(Value.ONE_HUNDRED, 10), new Rub(Value.FIVE_HUNDREDS, 10),
                new Rub(Value.ONE_THOUSAND, 10), new Rub(Value.FIVE_THOUSANDS, 10));
        System.out.println(department.getCurrentBalance());

        department.addATM(new Rub(Value.ONE_HUNDRED, 20), new Rub(Value.FIVE_HUNDREDS, 20),
                new Rub(Value.ONE_THOUSAND, 20), new Rub(Value.FIVE_THOUSANDS, 20));
        System.out.println(department.getCurrentBalance());

        List<ATM> atmList = department.getAtmList();


        for (ATM atm : atmList) {
            atm.putToCassette(new Rub(Value.ONE_HUNDRED, 10));
            atm.putToCassette(new Rub(Value.FIVE_HUNDREDS, 10));
            atm.putToCassette(new Rub(Value.ONE_THOUSAND, 10));
            atm.putToCassette(new Rub(Value.FIVE_THOUSANDS, 10));
        }

        System.out.println(department.getCurrentBalance());
        department.restore();
        System.out.println(department.getCurrentBalance());
    }
}