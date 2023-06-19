import java.util.Map;

public class Game {
    private Map<Player, Debut> contestant;
    private StringBuilder notation;
    private GameResultEnum result;


    private Rule rule;

    public Game(Map<Player, Debut> contestant, StringBuilder notation, GameResultEnum result, Rule rule) {
        if (contestant == null | notation.isEmpty() | result == null | rule == null)
            throw new NullPointerException("This field can not be empty");
        this.contestant = contestant;
        this.notation = notation;
        this.result = result;
        this.rule = rule;
    }

    public Map<Player, Debut> getContestant() {
        return contestant;
    }

    public void setContestant(Map<Player, Debut> contestant) {
        if (contestant != null)
            this.contestant = contestant;
    }

    public StringBuilder getNotation() {
        return notation;
    }

    public void setNotation(StringBuilder notation) {
        if (!notation.isEmpty())
            this.notation = notation;
    }

    public GameResultEnum getResult() {
        return result;
    }

    public void setResult(GameResultEnum result) {
        if (result != null)
            this.result = result;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        if (rule != null)
            this.rule = rule;
    }
}
