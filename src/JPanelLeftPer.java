import org.hibernate.Session;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JPanelLeftPer extends JPanel {

    public PerWin parentJFrame;
    public JPanelRightPer rightPanel = new JPanelRightPer(this);

    public Session session;

    public JPanelLeftPer(PerWin frame, Session session) {
        this.session = session;
        this.parentJFrame = frame;
        setUp();

    }

    private void setUp() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelTitle = new JLabel("Player/Judge finder information");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 30));
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 20, 10, 20);
        add(labelTitle, c);

        JLabel label = new JLabel("Choose player/judge to search");
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

        String[] options = getPlayerJudge();
        Arrays.sort(options);
        JComboBox<String> comboBox = new JComboBox<>(options);
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

        setBackground(Color.GRAY);
    }


    private String[] getPlayerJudge() {
        java.util.List<Person> allJudgePlayerFromDb = session.createQuery("from Person ").list();
        List<String> allJudgePlayerName = new ArrayList<>();
        for (var ele : allJudgePlayerFromDb) {
            allJudgePlayerName.add(ele.getId() + ". " + ele.getName() + " " + (ele.getSecondName().isBlank() ? "" : ele.getSecondName() + " ") + ele.getSurname());
        }
        return allJudgePlayerName.toArray(new String[0]);
    }
}
