import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Player extends Person {
    private long id;
    private int ranking;

    private List<Tournament> tournaments = new ArrayList<>();
    private List<PlayerCategory> historyOfCategory = new ArrayList<>();

    public Player() {
    }

    private Player(String name, String surname, Optional<String> secondName, GenderEnum gender, int ranking) throws Exception {
        super(name, surname, secondName, gender);
        this.ranking = Math.max(ranking, 800);

    }

    public void showHistoryOfCategory(PlayerCategory ele) {
        System.out.println("Category:" + ele.getCategory().getCategoryName() + " received at " + ele.getReceiveDate());
    }

    public void sortCategoryByDate() {
        this.historyOfCategory.sort((x, y) -> {
            Comparator<LocalDate> dateComparator = Comparator.comparing(LocalDate::toEpochDay);
            return dateComparator.compare(x.getReceiveDate(), y.getReceiveDate());
        });
    }

    @Override
    public StringBuilder showInformation() {
        System.out.println("\nPlayer data\n-------------------");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Player data\n");
        System.out.println(getName() + ' ' + (getSecondName().length() > 0 ? getSecondName() + " " : "") + getSurname() + (getGender() != null ? " (" + getGender().genderName.name() + ")" : ""));
        stringBuilder.append(getName()).append(' ').append(getSecondName().length() > 0 ? getSecondName() + " " : "").append(getSurname()).append(getGender() != null ? " (" + getGender().genderName.name() + ")" : "").append("\n");
        System.out.println(getRanking() + "\n-------------------");
        stringBuilder.append("Rank: ").append(getRanking()).append("\n");
        stringBuilder.append("History of chess category: \n");
        for (PlayerCategory ele : getHistoryOfCategory()) {
            showHistoryOfCategory(ele);
            stringBuilder.append(ele.getCategory().getCategoryName()).append(" ").append(ele.getReceiveDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");
        }
        if (tournaments.isEmpty()) return stringBuilder;
        System.out.println("\n-------------------\nTournaments participated:");
        stringBuilder.append("Tournaments participated\n");
        for (Tournament t : tournaments) {
            System.out.println(t.getTournamentName());
            stringBuilder.append(t.getTournamentName()).append("\n");
        }
        return stringBuilder;
    }

    public static Player addNew(String name, String surname, Optional<String> secondName, GenderEnum gender, int ranking) throws Exception {
        return new Player(name, surname, secondName, gender, ranking);
    }

    public static Player addNew(String name, String surname, GenderEnum gender, int ranking) throws Exception {
        return new Player(name, surname, Optional.empty(), gender, ranking);
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }


    @Basic(optional = false)
    public int getRanking() {
        return ranking;
    }

    @OneToMany
    public List<PlayerCategory> getHistoryOfCategory() {
        return historyOfCategory;
    }

    @ManyToMany
    @JoinTable(name = "player_tournament",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    public List<Tournament> getTournaments() {
        return tournaments;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }


    public void setRanking(int ranking) {
        if (ranking > 800) {
            this.ranking = ranking;
        } else {
            throw new IllegalArgumentException("Ranking can not be lower then 800");
        }
    }

    public void setHistoryOfCategory(List<PlayerCategory> historyOfCategory) {
        if (historyOfCategory != null) {
            this.historyOfCategory = historyOfCategory;
        } else {
            throw new NullPointerException("History of chess category can not be empty");
        }


    }

    public void addTournament(Tournament tournament) {
        if (!tournaments.contains(tournament)) {
            tournaments.add(tournament);

            tournament.addPlayer(this);
        }
    }

    public void removePlayerCategory(PlayerCategory pc) {
        if (historyOfCategory.contains(pc))
            historyOfCategory.remove(pc);
    }

    public void addPlayerCategory(PlayerCategory pc) {

        if (!historyOfCategory.contains(pc)) {
            historyOfCategory.add(pc);
        }
    }


}
