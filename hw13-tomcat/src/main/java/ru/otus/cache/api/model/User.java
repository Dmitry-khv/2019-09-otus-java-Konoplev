package ru.otus.cache.api.model;


import java.util.HashSet;
import java.util.Set;

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

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private ROLE role;

  @OneToOne(targetEntity = AddressDataSet.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  private AddressDataSet addressDataSet;

  @OneToMany(targetEntity = PhoneDataSet.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private Set<PhoneDataSet> phoneDataSet = new HashSet<>();

  public User() {
  }

  public User(long id, String name, int age, String login, String password, ROLE role) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.login = login;
    this.password = password;
    this.role = role;
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

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ROLE getRole() {
    return role;
  }

  public void setRole(ROLE role) {
    this.role = role;
  }

  public AddressDataSet getAddressDataSet() {
    return addressDataSet;
  }

  public void setAddressDataSet(AddressDataSet addressDataSet) {
    this.addressDataSet = addressDataSet;
  }

  public Set<PhoneDataSet> getPhoneDataSet() {
    return phoneDataSet;
  }

  public void setPhoneDataSet(PhoneDataSet phone) {
    phoneDataSet.add(phone);
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

  public enum ROLE {
    ADMIN, USER
  }
}
