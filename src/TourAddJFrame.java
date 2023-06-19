import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class TourAddJFrame extends JFrame {
    private Session session;
    private JPanelLeftTour panel;

    public TourAddJFrame(Session session, JPanelLeftTour panel) {
        this.session = session;
        this.panel = panel;
        setUp();
    }

    private void setUp() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelTitle = new JLabel("Tournament adder");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 30));
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 20, 10, 20);
        add(labelTitle, c);

        JLabel label = new JLabel("Name: ");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 10, 20);
        add(label, c);

        JTextField nameField = new JTextField(2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 10, 20);
        add(nameField, c);

        JLabel countryLabel = new JLabel("Country: ");
        countryLabel.setHorizontalAlignment(JLabel.CENTER);
        countryLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 20, 10, 20);
        add(countryLabel, c);

        JTextField countryField = new JTextField(2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(10, 20, 10, 20);
        add(countryField, c);

        JLabel cityLabel = new JLabel("City: ");
        cityLabel.setHorizontalAlignment(JLabel.CENTER);
        cityLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(10, 20, 10, 20);
        add(cityLabel, c);

        JTextField cityField = new JTextField(2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(10, 20, 10, 20);
        add(cityField, c);

        JLabel streetLabel = new JLabel("Street: ");
        streetLabel.setHorizontalAlignment(JLabel.CENTER);
        streetLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(10, 20, 10, 20);
        add(streetLabel, c);

        JTextField streetField = new JTextField(2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(10, 20, 10, 20);
        add(streetField, c);


        JLabel apartmentLabel = new JLabel("Building / apartment no.:");
        apartmentLabel.setHorizontalAlignment(JLabel.CENTER);
        apartmentLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(10, 20, 10, 20);
        add(apartmentLabel, c);

        JPanel buildingApartmentJPanel = new JPanel();
        buildingApartmentJPanel.setLayout(new GridBagLayout());

        GridBagConstraints cTmp = new GridBagConstraints();
        cTmp.fill = GridBagConstraints.HORIZONTAL;
        cTmp.weightx = 1.0;
        JTextField buildingField = new JTextField(2);
        cTmp.insets = new Insets(0, 0, 0, 5);
        cTmp.gridx = 0;
        cTmp.gridy = 0;
        buildingApartmentJPanel.add(buildingField, cTmp);
        JTextField apartmentField = new JTextField(2);
        cTmp.insets = new Insets(0, 5, 0, 0);
        cTmp.gridx = 1;
        cTmp.gridy = 0;
        buildingApartmentJPanel.add(apartmentField, cTmp);

        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.insets = new Insets(10, 20, 10, 20);
        add(buildingApartmentJPanel, c);


        JLabel labelStarting = new JLabel("Starting date: ");
        labelStarting.setHorizontalAlignment(JLabel.CENTER);
        labelStarting.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(10, 20, 10, 20);
        add(labelStarting, c);

        UtilDateModel model = new UtilDateModel();
//model.setDate(20,04,2014);
// Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
// Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 6;
        c.insets = new Insets(10, 20, 10, 20);
        add(datePicker, c);

        JLabel labelEnding = new JLabel("Ending date: ");
        labelEnding.setHorizontalAlignment(JLabel.CENTER);
        labelEnding.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(10, 20, 10, 20);
        add(labelEnding, c);

        UtilDateModel modelEnd = new UtilDateModel();
//model.setDate(20,04,2014);
// Need this...
        Properties pEnd = new Properties();
        pEnd.put("text.today", "Today");
        pEnd.put("text.month", "Month");
        pEnd.put("text.year", "Year");
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, pEnd);
// Don't know about the formatter, but there it is...
        JDatePickerImpl datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateLabelFormatter());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 7;
        c.insets = new Insets(10, 20, 10, 20);
        add(datePickerEnd, c);

        JLabel labelPrize = new JLabel("Prize: ");
        labelPrize.setHorizontalAlignment(JLabel.CENTER);
        labelPrize.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        c.insets = new Insets(10, 20, 10, 20);
        add(labelPrize, c);

        JSpinner prizeSpinner = new JSpinner();
        prizeSpinner.setModel(new SpinnerNumberModel(10_000, 10_000, 1_000_000, 10_000));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 8;
        c.insets = new Insets(10, 20, 10, 20);
        add(prizeSpinner, c);

        JLabel judgeLabel = new JLabel("Judge: ");
        judgeLabel.setHorizontalAlignment(JLabel.CENTER);
        judgeLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        c.insets = new Insets(10, 20, 10, 20);
        add(judgeLabel, c);

        String[] options = getJudge();
        Arrays.sort(options);
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 9;
        c.insets = new Insets(10, 20, 10, 20);
        add(comboBox, c);

        Button addButton = new Button("Add");
        addButton.addActionListener(e -> {
            try {
                addNewTournament(nameField.getText(), countryField.getText(), cityField.getText(),
                        streetField.getText(), buildingField.getText(), apartmentField.getText(),
                        (model.getValue() != null ? model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null),
                        (modelEnd.getValue() != null ? modelEnd.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null),
                        prizeSpinner.getValue().toString(), comboBox.getSelectedItem().toString());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 10;
        c.insets = new Insets(10, 20, 10, 20);
        add(addButton, c);


        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addNewTournament(String name, String country, String city, String street, String building, String apartment, LocalDate startDate, LocalDate endDate, String prize, String judge) throws Exception {

        if (name.isBlank() || country.isBlank() || city.isBlank() || street.isBlank() || building.isBlank() || startDate == null || prize.isBlank() || judge == null) {
            JOptionPane.showMessageDialog(null,
                    "Some information is missing!", "Error 500", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String queryString = "from Tournament where tournamentName = :tournamentName";
        Query query = session.createQuery(queryString);
        query.setParameter("tournamentName", name);
        Tournament tournament = (Tournament) query.uniqueResult();
        if (tournament != null) {
            JOptionPane.showMessageDialog(null,
                    "Such tournament name exists in database!", "Error 500", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (endDate.isBefore(startDate)) {
            JOptionPane.showMessageDialog(null,
                    "Starting tornament date can not be later then ending date", "Error 500", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] tmp = judge.split(" ");
        String idJudge = tmp[0].substring(0, tmp[0].length() - 1);
        String nameJudge = tmp[1];
        String surnameJudge = tmp[tmp.length - 1];
        queryString = "from Judge where id = :id and name = :name and surname = :surname";
        query = session.createQuery(queryString);
        query.setParameter("id", idJudge);
        query.setParameter("name", nameJudge);
        query.setParameter("surname", surnameJudge);
        Judge person = (Judge) query.uniqueResult();
        if (person == null) {
            JOptionPane.showMessageDialog(null,
                    "Such judge does not exist in database!", "Error 500", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tournament tournamentToAdd;
        if (apartment.isBlank())
            tournamentToAdd = Tournament.addTournament(name, startDate, endDate, Integer.parseInt(prize), Adress.addAdress(country, city, street, building));
        else
            tournamentToAdd = Tournament.addTournament(name, startDate, endDate, Integer.parseInt(prize), Adress.addAdress(country, city, street, building, apartment));
        person.addTournamentQualif(tournamentToAdd);
        PointsForPlace.createPoints(tournamentToAdd, "WorldClass");
        tournamentToAdd.showInformation();
        session.save(tournamentToAdd);
        panel.comboBox.setModel(new DefaultComboBoxModel<>(panel.getTournaments()));
        JOptionPane.showMessageDialog(null, "Tournament successful added to data base.", "200 OK", JOptionPane.INFORMATION_MESSAGE);
        dispose();


    }

    private String[] getJudge() {
        java.util.List<Person> allJudgeFromDb = session.createQuery("from Judge").list();
        List<String> allJudgeName = new ArrayList<>();
        for (var ele : allJudgeFromDb) {
            allJudgeName.add(ele.getId() + ". " + ele.getName() + " " + (ele.getSecondName().isBlank() ? "" : ele.getSecondName() + " ") + ele.getSurname());
        }
        return allJudgeName.toArray(new String[0]);
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}