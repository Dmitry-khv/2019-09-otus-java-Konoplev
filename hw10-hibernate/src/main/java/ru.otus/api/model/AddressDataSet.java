package ru.otus.api.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "street")
    private String street;


    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet that = (AddressDataSet) o;
        return Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street);
    }
}
