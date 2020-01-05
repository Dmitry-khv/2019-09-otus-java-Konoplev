package ru.otus.api.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id", nullable = false, unique = true)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private int age;

//  @Column(name = "address", table = "addresses")
  @OneToOne(targetEntity = AddressDataSet.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
          CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  private AddressDataSet addressDataSet;

  @OneToOne(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "phone_id")
  private PhoneDataSet phoneDataSet;

  public User() {
  }

  public User(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public User(String name, int age, AddressDataSet addressDataSet, PhoneDataSet phoneDataSet) {
    this.name = name;
    this.age = age;
    this.addressDataSet = addressDataSet;
    this.phoneDataSet = phoneDataSet;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setId(long id) {
    this.id = id;
  }

  public AddressDataSet getAddressDataSet() {
    return addressDataSet;
  }

  public void setAddressDataSet(AddressDataSet addressDataSet) {
    this.addressDataSet = addressDataSet;
  }

  public PhoneDataSet getPhoneDataSet() {
    return phoneDataSet;
  }

  public void setPhoneDataSet(PhoneDataSet phoneDataSet) {
    this.phoneDataSet = phoneDataSet;
  }

  @Override
  public String toString() {
    return "\nUser{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", addressDataSet=" + addressDataSet +
            ", phoneDataSet=" + phoneDataSet +
            '}';
  }
}
