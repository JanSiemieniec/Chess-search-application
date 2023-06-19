import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Judge extends Person {
    private long id;
    public int startingJudgeYear;
    public final static float minimumRate = 20;

    private float rate = minimumRate;


    private Map<String, Tournament> tournamentQualif = new TreeMap<>();

    public Judge() {
    }

    public Judge(String name, String surname, Optional<String> secondName, GenderEnum gender, int startingJudgeYear) throws Exception {
        super(name, surname, secondName, gender);
        this.startingJudgeYear = startingJudgeYear;

    }

    @Override
    public StringBuilder showInformation() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("\nJudge data\n-------------------");
        stringBuilder.append("Judge data\n");
        System.out.println(getName() + ' ' + (getSecondName().length() > 0 ? getSecondName() + " " : "") + getSurname() + (getGender() != null ? " (" + getGender().genderName.name() + ')' : ""));
        stringBuilder.append(getName() + ' ' + (getSecondName().length() > 0 ? getSecondName() + " " : "") + getSurname() + (getGender() != null ? " (" + getGender().genderName.name() + ')' : "") + "\n");
        System.out.println("Years of judging: " + getIntershipInYears() + "\n-------------------");
        stringBuilder.append("Years of judging: " + getIntershipInYears());
        if (tournamentQualif.isEmpty()) return stringBuilder;
        System.out.println("\n-------------------\nTournaments judged");
        stringBuilder.append("\nTournaments judged: \n");
        for (String t : tournamentQualif.keySet()) {
            System.out.println(t);
            stringBuilder.append(t).append("\n");
        }
        return stringBuilder;
    }

    public static Judge addNew(String name, String surname, Optional<String> secondName, GenderEnum gender, int startingJudgeYear) throws Exception {
        return new Judge(name, surname, secondName, gender, startingJudgeYear);
    }

    public static Judge addNew(String name, String surname, GenderEnum gender, int startingJudgeYear) throws Exception {
        return new Judge(name, surname, Optional.empty(), gender, startingJudgeYear);
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    @Basic(optional = false)
    public float getRate() {
        return rate;
    }

    @Basic(optional = false)
    public int getStartingJudgeYear() {
        return startingJudgeYear;
    }

    @OneToMany
    public Map<String, Tournament> getTournamentQualif() {
        return tournamentQualif;
    }

    public void setTournamentQualif(Map<String, Tournament> tournamentQualif) {
        this.tournamentQualif = tournamentQualif;
    }

    @Transient
    int getIntershipInYears() {
        return LocalDate.now().getYear() - startingJudgeYear;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setStartingJudgeYear(int startingJudgeYear) {
        if (startingJudgeYear <= LocalDate.now().getYear())
            this.startingJudgeYear = startingJudgeYear;
    }

    public void addTournamentQualif(Tournament tournament) {
        if (!tournamentQualif.containsKey(tournament.getTournamentName())) {
            if (tournament.getJudgeOfTournament() != null)
                tournament.removeJudge(tournament.getJudgeOfTournament());
            tournamentQualif.put(tournament.getTournamentName(), tournament);
            tournament.setJudgeToTournament(this);
        }
    }

    public void removeTournamentQualif(Tournament tournament) {

        if (tournament != null && tournamentQualif.containsKey(tournament.getTournamentName())) {
            tournamentQualif.remove(tournament.getTournamentName());
            tournament.removeJudge(this);
        }
    }

    public Tournament findTournamentQualif(String tourName) throws Exception {
        if (!tournamentQualif.containsKey(tourName)) {
            throw new Exception("Unable to find a movie: " + tourName);
        }

        return tournamentQualif.get(tourName);

    }


    public void setRate(float rate) throws Exception {
        if (rate < minimumRate) {       //statyczne
            throw new Exception(String.format("New rate (%s) has to be at least %s", rate, minimumRate));
        }
        this.rate = rate;
        if (rate < getRate()) {     //dynamiczne
            throw new Exception(String.format("The rate (%s) cannot be decreased! (previous was %s)", rate, getRate()));
        }

    }

}
