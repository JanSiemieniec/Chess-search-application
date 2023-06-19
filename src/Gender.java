import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Gender {
    @Enumerated(EnumType.STRING)
    public GenderEnum genderName;


    public static Gender createGender(Person person, GenderEnum type) throws Exception {      //xor
        if (person == null) {
            throw new Exception("The given person does not exist!");
        }
        Gender gender;
        if (type == GenderEnum.MAN) {
            gender = new Man();
        } else {
            gender = new Woman();
        }
        person.addGender(gender);

        return gender;
    }

    //   public abstract String getGenderName();
//    public abstract void setGenderName(GenderEnum name);
}

@Entity
class Man extends Gender {

    private long id;

    public Man() {
        this.genderName = GenderEnum.MAN;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }
}


@Entity
class Woman extends Gender {
    private long id;

    public Woman() {
        this.genderName = GenderEnum.WOMAN;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

}

