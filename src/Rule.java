import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;

enum RuleType {Bullet, Blitz, Classic}

public class Rule {

    private LocalTime timeForPlayer;        //for all


    private int addSecondAfterMove;     //for bullet

    private LocalTime incrementInSeconds;         //for Blitz

    private boolean ifInFIDE;     //for Classic

    private EnumSet<RuleType> ruleKind;

    public Rule(LocalTime timeForPlayer, EnumSet<RuleType> ruleKind) throws Exception {
        if (timeForPlayer == null | ruleKind == null)
            throw new NullPointerException("This fields do not match requirements");
        this.timeForPlayer = timeForPlayer;
        this.ruleKind = ruleKind;

        //set default value
        if (this.ruleKind.contains(RuleType.Bullet)) {
            setAddSecondAfterMove(30);
        }
        if (this.ruleKind.contains(RuleType.Blitz)) {
            setIncrementInSeconds(LocalTime.of(0, 0, 30));
        }
        if (this.ruleKind.contains(RuleType.Classic)) {
            setIfInFIDE(true);
        }
    }

    public LocalTime getTimeForPlayer() {
        return timeForPlayer;
    }

    public void setTimeForPlayer(LocalTime timeForPlayer) {
        if (timeForPlayer != null)
            this.timeForPlayer = timeForPlayer;
    }

    public int hasAddSecondAfterMove() throws Exception {
        if (ruleKind.contains(RuleType.Bullet)) {
            return addSecondAfterMove;
        }

        throw new Exception("The rule is not a Bullet type!");
    }

    public void setAddSecondAfterMove(int addSecondAfterMove) throws Exception {
        if (ruleKind.contains(RuleType.Bullet)) {
            if (addSecondAfterMove >= 0)
                this.addSecondAfterMove = addSecondAfterMove;
            else
                System.out.println("Number of move can not be less then 0, nothing happened.");
        } else {
            throw new Exception("The rule is not a Bullet type!");
        }
    }

    public LocalTime hasIncrementInSeconds() throws Exception {
        if (ruleKind.contains(RuleType.Blitz)) {
            return incrementInSeconds;
        }

        throw new Exception("The rule is not a Blitz type!");
    }

    public void setIncrementInSeconds(LocalTime incrementInSeconds) throws Exception {
        if (ruleKind.contains(RuleType.Blitz)) {
            this.incrementInSeconds = incrementInSeconds;
        } else {
            throw new Exception("The rule is not a Blitz type!");
        }

    }

    public boolean hasIfInFIDE() throws Exception {
        if (ruleKind.contains(RuleType.Classic)) {
            return ifInFIDE;
        }

        throw new Exception("The rule is not a Classic type!");
    }

    public void setIfInFIDE(boolean ifInFIDE) throws Exception {
        if (ruleKind.contains(RuleType.Classic)) {
            this.ifInFIDE = ifInFIDE;
        } else {
            throw new Exception("The rule is not a Vlassic type!");
        }
    }

    @Override
    public String toString() {
        return "Rule\n" +
                "time for player " + timeForPlayer.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + '\n' +
                (this.ruleKind.contains(RuleType.Bullet) ? "add second after move " + addSecondAfterMove + '\n' : "") +
                (this.ruleKind.contains(RuleType.Blitz) ? "increment in seconds " + incrementInSeconds.getSecond() + '\n' : "") +
                (this.ruleKind.contains(RuleType.Classic) && ifInFIDE ? "This rule matches FIDE" : "This rule doesn't match FIDE") +
                "\n----------------------------------------------------------------------------------------------\n";
    }
}
