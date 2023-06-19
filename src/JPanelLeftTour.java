import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JPanelLeftTour extends JPanel {
    public TourWin parentJFrame;
    public JPanelRightTour rightPanel = new JPanelRightTour(this);

    public Session session;

    public String[] options;

    public JComboBox<String> comboBox;

    public JPanelLeftTour(TourWin frame, Session session) {
        this.session = session;
        this.parentJFrame = frame;
        setUp();

    }

    private void setUp() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelTitle = new JLabel("Tournament finder information");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 30));
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 20, 10, 20);
        add(labelTitle, c);

        JLabel label = new JLabel("Choose tournament to search");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(10, 20, 10, 20);
        add(label, c);

        JTextField spinner = new JTextField(2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 2;
        c.insets = new Insets(10, 20, 10, 20);
        add(spinner, c);

        options = getTournaments();
        Arrays.sort(options);
        comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.insets = new Insets(10, 20, 10, 20);
        c.gridy = 1;
        add(comboBox, c);

        spinner.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateComboBox();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateComboBox();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateComboBox();
            }

            private void updateComboBox() {

                String spinnerText = spinner.getText().trim().toLowerCase();
                if (spinnerText.isEmpty()) {
                    comboBox.setModel(new DefaultComboBoxModel<>(options));
                } else {
                    String[] filteredOptions = Arrays.stream(options)
                            .filter(option -> option.toLowerCase().contains(spinnerText.toLowerCase()))
                            .toArray(String[]::new);
                    comboBox.setModel(new DefaultComboBoxModel<>(filteredOptions));
                }
            }
        });


        Button search = new Button("Search");
        search.addActionListener(e ->
                rightPanel.searching((comboBox.getSelectedItem()) != null ? comboBox.getSelectedItem().toString() : ""));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 3;
        c.insets = new Insets(10, 20, 10, 20);
        add(search, c);

        Button addButton = new Button("Add");
        addButton.addActionListener(e -> {
            TourAddJFrame tourAddJFrame = new TourAddJFrame(session, this);
            System.out.println(Arrays.toString(Arrays.stream(options).toArray()));


        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 4;
        c.insets = new Insets(10, 20, 10, 20);
        add(addButton, c);


        setBackground(Color.GRAY);
    }

    public String[] getTournaments() {

        List<Tournament> tournamentsFromDb = session.createQuery("from Tournament ").list();
        List<String> torunamentsName = new ArrayList<>();
        for (var ele : tournamentsFromDb) {
            torunamentsName.add(ele.getTournamentName());
        }
        return torunamentsName.toArray(new String[0]);
    }
}
