import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JPanelRightTour extends JPanel {

    private final JPanelLeftTour leftPanel;
    private final List<JTextArea> listJTextArea = new ArrayList<>();

    public JPanelRightTour(JPanelLeftTour leftPanel) {
        this.leftPanel = leftPanel;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void searching(String tournamentName) {
        if (!listJTextArea.isEmpty()) {
            for (JTextArea ele : listJTextArea)
                remove(ele);
        }

        String queryString = "from Tournament where tournamentName = :tournamentName";
        Query query = leftPanel.session.createQuery(queryString);
        query.setParameter("tournamentName", tournamentName);
        Tournament tournament = (Tournament) query.uniqueResult();
        if (tournament == null) {
            JOptionPane.showMessageDialog(null,
                    "Such tournament does not exist in database!", "Error 404", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringBuilder stringBuilder = getInfoAboutTournament(tournament);

        JTextArea jTextArea = new JTextArea(stringBuilder.toString());
        jTextArea.setFont(new Font("Arial", Font.ITALIC, 20));
        jTextArea.setMargin(new Insets(10, 10, 10, 10));
        jTextArea.setEditable(false);
        listJTextArea.add(jTextArea);

        add(jTextArea);
        leftPanel.parentJFrame.pack();
    }

    private StringBuilder getInfoAboutTournament(Tournament tournament) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Name: ").append(tournament.getTournamentName()).append("\n");

        stringBuilder.append("Address: ").append(tournament.getAdress().getCountryName())
                .append(", ").append(tournament.getAdress().getCityName())
                .append(" ").append(tournament.getAdress().getStreetName())
                .append(" ").append(tournament.getAdress().getBuildingNumber());
        if (!tournament.getAdress().getApartmentNumber().isEmpty())
            stringBuilder.append("/").append(tournament.getAdress().getApartmentNumber()).append("\n");
        else
            stringBuilder.append("\n");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        stringBuilder.append("Starting time: ").append(tournament.getStartingDate().format(dateFormatter)).append("\n");
        stringBuilder.append("End time: ").append(tournament.getStartingDate().format(dateFormatter)).append("\n");

        stringBuilder.append("Prize: ").append(tournament.getPrize()).append("\n");

        stringBuilder.append("Judge: ").append(tournament.getJudgeOfTournament().getName()).append(" ")
                .append(tournament.getJudgeOfTournament().getSecondName().isBlank() ? "" : tournament.getJudgeOfTournament().getSecondName() + " ")
                .append(tournament.getJudgeOfTournament().getSurname()).append("\n");

        stringBuilder.append("Players: ").append("\n");
        for (int i = 0; i < tournament.getPlayers().size(); i++) {
            Player ele = tournament.getPlayers().get(i);
            if (ele.getName() == null) continue;
            stringBuilder.append("- ").append(ele.getName()).append(" ")
                    .append(ele.getSecondName().isBlank() ? "" : ele.getSecondName() + " ")
                    .append(ele.getSurname());
            if (i < tournament.getPlayers().size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder;
    }

}
