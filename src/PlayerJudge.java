import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Entity
public class PlayerJudge extends Judge implements PlayerInterface {
    private long id;
    public Player player;

    public PlayerJudge() {
    }

    public PlayerJudge(String name, String surname, Optional<String> secondName, GenderEnum gender, int startingJudgeYear, int ranking) throws Exception {
        super(name, surname, secondName, gender, startingJudgeYear);
        this.player = Player.addNew(null, null, null, ranking);


    }

    public PlayerJudge(String name, String surname, GenderEnum gender, int startingJudgeYear, int ranking) throws Exception {
        super(name, surname, Optional.empty(), gender, startingJudgeYear);
        this.player = Player.addNew(null, null, null, ranking);
    }


    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }


    @Override
    @Basic(optional = false)
    public int getRanking() {
        return player.getRanking();
    }

    @Override
    @OneToMany
    public List<PlayerCategory> getHistoryOfCategory() {
        return player.getHistoryOfCategory();
    }

    @Override
    @ManyToMany
    public List<Tournament> getTournaments() {
        return player.getTournaments();
    }

    private void setId(long id) {
        this.id = id;
    }


    @Override
    public void setRanking(int ranking) {
        if (player != null)
            player.setRanking(ranking);
    }

    @Override
    public void setTournaments(List<Tournament> tournaments) {
        if (player != null)
            player.setTournaments(tournaments);
    }

    @Override
    public void setHistoryOfCategory(List<PlayerCategory> historyOfCategory) {
        //       player.setHistoryOfCategory(historyOfCategory);
    }

    @Override
    public void addTournament(Tournament tournament) {
        player.addTournament(tournament);
    }

    @Override
    public void removePlayerCategory(PlayerCategory pc) {
        player.removePlayerCategory(pc);
    }

    @Override
    public void addPlayerCategory(PlayerCategory pc) {
        player.addPlayerCategory(pc);
    }

    @Override
    public void showHistoryOfCategory(PlayerCategory ele) {
        player.showHistoryOfCategory(ele);
    }

    @Override
    public StringBuilder showInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("\nJudge/Player data\n-------------------");
        stringBuilder.append("Judge/Player data\n");
        System.out.println(getName() + ' ' + (getSecondName().length() > 0 ? getSecondName() + " " : "") + getSurname() + (getGender() != null ? " (" + getGender().genderName.name() + ')' : ""));
        stringBuilder.append(getName()).append(' ').append(getSecondName().length() > 0 ? getSecondName() + " " : "")
                .append(getSurname()).append(getGender() != null ? " (" + getGender().genderName.name() + ')' : "").append("\n");
        System.out.println("Years of judging: " + getIntershipInYears() + "\n-------------------");
        stringBuilder.append("Years of judging: ").append(getIntershipInYears()).append("\n");
        if (!this.getTournamentQualif().isEmpty()) {
            System.out.println("\n-------------------\nTournaments judged:");
            System.out.println("Tournaments judged:\n");
            for (String t : getTournamentQualif().keySet()) {
                System.out.println(t);
                stringBuilder.append(t).append("\n");
            }
        }
        stringBuilder.append("History of chess category: \n");
        for (PlayerCategory ele : getHistoryOfCategory()) {
            showHistoryOfCategory(ele);
            stringBuilder.append(ele.getCategory().getCategoryName()).append(" ").append(ele.getReceiveDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");
        }
        stringBuilder.append("Rank: ").append(getRanking());
        System.out.println(getRanking() + "\n-------------------");
        if (!getTournaments().isEmpty()) {
            System.out.println("\n-------------------\nTournaments participated");
            stringBuilder.append("\nTournaments participated\n");
            for (Tournament t : getTournaments()) {
                System.out.println(t.getTournamentName());
                stringBuilder.append(t.getTournamentName()).append("\n");
            }
        }
        return stringBuilder;
    }
}
