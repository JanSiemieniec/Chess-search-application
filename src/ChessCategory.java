import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ChessCategory")
public class ChessCategory {
    private long id;
    private ChessCategoryEnum categoryName;
    private List<PlayerCategory> historyOfCategory = new ArrayList<>();

    public ChessCategory() {
    }

    public ChessCategory(ChessCategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    public static void showCategory() {
        System.out.println("\nCategories: ");
        for (ChessCategoryEnum ele : ChessCategoryEnum.values()) {
            System.out.print(ele + " ");
        }
        System.out.println("\n-------------------");
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    @Enumerated
    public ChessCategoryEnum getCategoryName() {
        return categoryName;
    }

    @OneToMany
    public List<PlayerCategory> getHistoryOfCategory() {
        return historyOfCategory;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setCategoryName(ChessCategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    public void setHistoryOfCategory(List<PlayerCategory> historyOfCategory) {
        this.historyOfCategory = historyOfCategory;
    }

    public void removePlayerCategory(PlayerCategory pc) {
        historyOfCategory.remove(pc);
        pc.setCategory(null);
    }

    public void addPlayerCategory(PlayerCategory pc) {

        if (!historyOfCategory.contains(pc)) {
            historyOfCategory.add(pc);
        }
    }

}
