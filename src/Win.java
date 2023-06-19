import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;

public class Win extends JFrame {

    public Session session;

    public Win(Session session) {
        this.session = session;
        setUp();
    }

    private void setUp() {

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("lib/chess.png");
                Image image = imageIcon.getImage();
                int width = getWidth();
                int height = getHeight();
                g.drawImage(image, 0, 0, width, height, this);
            }
        };

        setContentPane(backgroundPanel);


        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel labelTitle = new JLabel("Chess finder");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 30));
        c.insets = new Insets(0, 20, 10, 20);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;

        add(labelTitle, c);

        JButton buttonPlayerJudge = new JButton("Players/Judges");
        buttonPlayerJudge.setBackground(new Color(137, 207, 240));
        buttonPlayerJudge.setFont(new Font("Arial", Font.ITALIC, 18));
        buttonPlayerJudge.addActionListener(e -> new PerWin(session));
        c.insets = new Insets(60, 20, 40, 20);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(buttonPlayerJudge, c);

        JButton buttonTournament = new JButton("Tournaments");
        buttonTournament.setBackground(new Color(137, 207, 240));
        buttonTournament.setFont(new Font("Arial", Font.ITALIC, 18));
        buttonTournament.addActionListener(e -> {
            new TourWin(this.session);
        });
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        add(buttonTournament, c);

        JButton buttonQuit = new JButton("Quit");
        buttonQuit.setBackground(new Color(255, 0, 0));
        buttonQuit.setFont(new Font("Arial", Font.BOLD, 18));
        buttonQuit.addActionListener(e -> dispose());
        c.insets = new Insets(60, 0, 20, 0);
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        add(buttonQuit, c);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
