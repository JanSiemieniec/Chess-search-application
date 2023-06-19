import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;

public class PerWin extends JFrame {
    public Session session;

    private JPanelLeftPer leftPanel;


    public PerWin(Session session) {
        this.session = session;
        leftPanel = new JPanelLeftPer(this, session);
        setUp();
    }

    private void setUp() {
        setLayout(new BorderLayout());

        settingLeftJPanel();
        settingRightJPanel();
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void settingLeftJPanel() {
        add(leftPanel, BorderLayout.WEST);
    }

    public void settingRightJPanel() {
        add(leftPanel.rightPanel, BorderLayout.CENTER);
    }
}
