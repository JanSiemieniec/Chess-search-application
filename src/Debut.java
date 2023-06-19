import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Debut implements Serializable {
    private final String debutName;
    private final StringBuilder description;

    private static List<Debut> extent = new ArrayList<>();

    public Debut(String debutName, StringBuilder description) {
        if (description.isEmpty() | debutName.isBlank())
            throw new NullPointerException("This field can not be empty");
        this.debutName = debutName;
        this.description = description;
        addDebut(this);
    }

    private static void addDebut(Debut debut) {
        extent.add(debut);
    }

    private static void removeDebut(Debut debut) {
        extent.remove(debut);
    }

    public static void showAllDebuts() {
        System.out.println("--------------------------------------------------------------------------------------");
        for (Debut debut : extent) {
            System.out.println(debut.debutName + ": " + debut.description);
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    public static void writeExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extent);
    }

    public static void readExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extent = (ArrayList<Debut>) stream.readObject();
    }

}
