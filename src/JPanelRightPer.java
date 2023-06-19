import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JPanelRightPer extends JPanel {
    private final JPanelLeftPer leftPanel;
    private final List<JTextArea> listJTextArea = new ArrayList<>();

    public JPanelRightPer(JPanelLeftPer leftPanel) {
        this.leftPanel = leftPanel;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void searching(String personInfo) {
        if (!listJTextArea.isEmpty()) {
            for (JTextArea ele : listJTextArea)
                remove(ele);
        }
        if (personInfo.isBlank()) {
            JOptionPane.showMessageDialog(null,
                    "Such player/judge does not exist in database!", "Error 404", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] tmp = personInfo.split(" ");
        String id = tmp[0].substring(0, tmp[0].length() - 1);
        String name = tmp[1];
        String surname = tmp[tmp.length - 1];

        String queryString = "from Person where id = :id and name = :name and surname = :surname";
        Query query = leftPanel.session.createQuery(queryString);
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        Person person = (Person) query.uniqueResult();
        if (person == null) {
            JOptionPane.showMessageDialog(null,
                    "Such person does not exist in database!", "Error 404", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StringBuilder stringBuilder = person.showInformation();

        JTextArea jTextArea = new JTextArea(stringBuilder.toString());
        jTextArea.setFont(new Font("Arial", Font.ITALIC, 20));
        jTextArea.setMargin(new Insets(10, 10, 10, 10));
        jTextArea.setEditable(false);
        listJTextArea.add(jTextArea);

        add(jTextArea);
        leftPanel.parentJFrame.pack();
    }
}
