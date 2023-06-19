import java.util.List;

public interface PlayerInterface {
    void showHistoryOfCategory(PlayerCategory ele);

    StringBuilder showInformation();

    int getRanking();

    void setRanking(int ranking);

    List<PlayerCategory> getHistoryOfCategory();

    void setHistoryOfCategory(List<PlayerCategory> historyOfCategory);

    void addTournament(Tournament tournament);

    void removePlayerCategory(PlayerCategory pc);

    void addPlayerCategory(PlayerCategory pc);

    List<Tournament> getTournaments();

    void setTournaments(List<Tournament> tournaments);

}
