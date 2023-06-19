import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Embeddable
public class PointsForPlace {
    private String type;
    private Map<Integer, Integer> pointsForEachPlace;
    private Tournament tournament;

    public PointsForPlace() {
    }

    private PointsForPlace(Tournament tournament, String type) {
        this.type = type;
        this.tournament = tournament;
        this.pointsForEachPlace = countScore(type);
    }

    public static PointsForPlace createPoints(Tournament tournament, String type) throws Exception {
        if (tournament == null) {
            throw new Exception("The given tournament does not exist!");
        }

        PointsForPlace pointsForPlace = new PointsForPlace(tournament, type);

        tournament.addPoints(pointsForPlace);

        return pointsForPlace;
    }


    @Basic(optional = false)
    public String getType() {
        return type;
    }

    @ManyToOne
    public Tournament getTournament() {
        return tournament;
    }

    @ElementCollection
    public Map<Integer, Integer> getPointsForEachPlace() {
        return pointsForEachPlace;
    }

    public void setPointsForEachPlace(Map<Integer, Integer> pointsForEachPlace) {
        this.pointsForEachPlace = pointsForEachPlace;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }


    private static Map<Integer, Integer> countScore(String rule) {
        int start;
        int jump;
        switch (rule) {
            case "WorldClass":
                start = 70;
                jump = 23;
                break;
            case "Continental":
                start = 25;
                jump = 7;
                break;
            default:
                start = 20;
                jump = 5;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            map.put(i, start);
            start -= jump;
        }
        return map;
    }


    public void setType(String type) {
        this.type = type;
        this.pointsForEachPlace = countScore(type);
    }

}
