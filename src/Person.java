import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@MappedSuperclass
public abstract class Person {
    private long id;
    private String name;
    private String surname;

    private String secondName;
    public Gender gender;
    public static Set<Gender> genderSet = new HashSet<>();

    public abstract StringBuilder showInformation();

    public Person() {
    }

    public Person(String name, String surname, Optional<String> secondName, GenderEnum gender) throws Exception {
//        if (name == null || surname == null || gender == null) {
//            throw new NullPointerException("Fill all fields");
//        }
        this.name = name;
        this.surname = surname;
        this.secondName = secondName.orElse("");
        Gender.createGender(this, gender);
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }


    @Basic(optional = false)
    public String getName() {
        return name;
    }

    @Basic(optional = false)
    public String getSurname() {
        return surname;
    }

    @Basic(optional = true)
    public String getSecondName() {
        return secondName;
    }

    @Embedded
    public Gender getGender() {
        return gender;
    }

    @Transient
    public static Set<Gender> getGenderSet() {
        return genderSet;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    public void setSurname(String surname) {
        if (surname != null)
            this.surname = surname;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setGender(Gender gender) {
        if (gender != null)
            this.gender = gender;
    }

    public void addGender(Gender gender) throws Exception {
        if (this.gender != gender) {
            if (genderSet.contains(gender)) {
                throw new Exception("Gender are already connected with a Person.");
            }
        }
        this.gender = gender;
        genderSet.add(gender);

    }


}
