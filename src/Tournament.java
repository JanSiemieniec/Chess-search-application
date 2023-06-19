import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Tournament {       //WYKOMENTOWALEM WSZYSTKO CO MA GAME ZEBY UCIAC DALSZA BAZE
    private long id;
    private String tournamentName;
    private LocalDate startingDate;
    private LocalDate endTime;
    private int prize;
    private Adress adress;
    private Judge judgeOfTournament;

    private static Map<String, Tournament> allTournament = new HashMap<>();

    //   private List<Game> gamesOnTournament = new ArrayList<>();

    private List<Player> players = new ArrayList<>();

    private PointsForPlace pointsForPlace;
    private static Set<PointsForPlace> allPointsSet = new HashSet<>();

    public Tournament() {
    }

    private Tournament(String tournamentName, LocalDate startingDate, LocalDate endTime, int prize, Adress adress, Judge judgeOfTournament) {
        if (tournamentName.isBlank() | startingDate == null | prize < 1000 | adress == null)
            throw new NullPointerException("This field can not be empty");
        this.tournamentName = tournamentName;
        this.startingDate = startingDate;
        this.endTime = endTime;
        this.prize = prize;
        this.adress = adress;
        this.judgeOfTournament = judgeOfTournament;
    }

    public static Tournament addTournament(String tournamentName, LocalDate srartingDate, LocalDate endTime, int prize, Adress adress, Judge judgeOfTournament) throws Exception {
        if (allTournament.containsKey(tournamentName)) {
            throw new Exception("There exists tournament on same name");
        }
        Tournament tmp = new Tournament(tournamentName, srartingDate, endTime, prize, adress, judgeOfTournament);
        allTournament.put(tmp.getTournamentName(), tmp);
        return tmp;
    }

    public static Tournament addTournament(String tournamentName, LocalDate srartingDate, LocalDate endTime, int prize, Adress adress) throws Exception {
        if (allTournament.containsKey(tournamentName)) {
            throw new Exception("There exists tournament on same name");
        }
        Tournament tmp = new Tournament(tournamentName, srartingDate, endTime, prize, adress, null);
        allTournament.put(tmp.getTournamentName(), tmp);
        return tmp;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    @Basic(optional = false)
    public String getTournamentName() {
        return tournamentName;
    }

    @Basic(optional = false)
    public LocalDate getStartingDate() {
        return startingDate;
    }

    @Basic(optional = false)
    public LocalDate getEndTime() {
        return endTime;
    }

    @Basic(optional = false)
    public int getPrize() {
        return prize;
    }

    @Embedded
    public Adress getAdress() {
        return adress;
    }

    @ManyToOne
    public Judge getJudgeOfTournament() {
        return judgeOfTournament;
    }

    @Embedded
    public PointsForPlace getPointsForPlace() {
        return pointsForPlace;
    }

    public void setPointsForPlace(PointsForPlace pointsForPlaceList) {
        this.pointsForPlace = pointsForPlaceList;
    }

    @Transient
    public static Map<String, Tournament> getAllTournament() {
        return allTournament;
    }

    public static void setAllTournament(Map<String, Tournament> allTournament) {
        Tournament.allTournament = allTournament;
    }

    public static void setAllPointsSet(Set<PointsForPlace> allPointsSet) {
        Tournament.allPointsSet = allPointsSet;
    }

    public void setJudgeOfTournament(Judge judgeOfTournament) {
        //  setJudgeToTournament(judgeOfTournament);
    }

    @ManyToMany(mappedBy = "tournaments")
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    //    public List<Game> getGamesOnTournament() {
//        return gamesOnTournament;
//    }

//    public void addGameToTournament(Game game) {
//        gamesOnTournament.add(game);
//    }

    private void setId(long id) {
        this.id = id;
    }

    public void setTournamentName(String tournamentName) {
        if (!tournamentName.isBlank())
            this.tournamentName = tournamentName;
    }


    public void setStartingDate(LocalDate startingDate) {
        if (startingDate != null)
            this.startingDate = startingDate;
    }


    public void setEndTime(LocalDate endTime) {
        if (endTime != null)
            this.endTime = endTime;
    }


    public void setPrize(int prize) {
        if (prize < 10_000) {
            throw new IllegalArgumentException("Prize can not be lower then 1000");
        }
        this.prize = prize;
    }


    public void setAdress(Adress adress) {
        if (adress != null)
            this.adress = adress;
    }


    public void setJudgeToTournament(Judge judgeOfTournament) {

        if ((this.judgeOfTournament != null) && this.judgeOfTournament != judgeOfTournament) {
            this.judgeOfTournament.removeTournamentQualif(this);
        }
        this.judgeOfTournament = judgeOfTournament;
        judgeOfTournament.addTournamentQualif(this);

    }


    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);

            player.addTournament(this);
        }
    }

    public void addPoints(PointsForPlace points) throws Exception {
        if (this.pointsForPlace != points) {
            if (allPointsSet.contains(points)) {
                throw new Exception("Points are already connected with a tournament.");
            }

            this.pointsForPlace = points;
            allPointsSet.add(points);
        }
    }


    public void showInformation() {
        System.out.println("\nTournament data\n-------------------");
        System.out.println(getTournamentName());
        System.out.println("Time schedule: from " + getStartingDate() + " to " + getEndTime());
        System.out.println("Prize: " + getPrize());
        System.out.println("Adress: " + getAdress());
        if (getJudgeOfTournament() != null) getJudgeOfTournament().showInformation();
        if (pointsForPlace != null) {
            System.out.println("\n-------------------\nPoints");

            for (Map.Entry<Integer, Integer> entry : pointsForPlace.getPointsForEachPlace().entrySet()) {
                System.out.print(entry.getKey() + ") " + entry.getValue() + "\t|\t");
            }
        }
        System.out.println();

        if (players.isEmpty()) return;

        System.out.println("\n-------------------\nParticipants");
        for (Player p : players) {
            System.out.println(p.getName() + ' ' + (p.getSecondName().isBlank() ? p.getSecondName() + " " : "") + p.getSurname());
        }
    }


    public void removeJudge(Judge judge) {
        if (this.judgeOfTournament != null) {
            this.judgeOfTournament.removeTournamentQualif(this);
            this.judgeOfTournament = null;
        }
    }
}
