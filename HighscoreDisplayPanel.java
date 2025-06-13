package bubble_shooter;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

// it displays the highscore table with custom styling and transparency
public class HighscoreDisplayPanel extends JPanel {
    private JTable highscoreTable;
    private Highscores highscores;
    private JScrollPane scrollPane;
    private static final String fileName = "bubble_shooter_hs.dat";

     // Constructor initializes and styles the highscore table
    public HighscoreDisplayPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 180)); 

        highscores = loadHighscores();

        highscoreTable = new JTable(highscores);
        highscoreTable.setOpaque(false);
        ((DefaultTableCellRenderer) highscoreTable.getDefaultRenderer(Object.class)).setOpaque(false);

        highscoreTable.setFont(new Font("Verdana", Font.BOLD, 16));
        highscoreTable.setForeground(Color.WHITE);
        highscoreTable.setBackground(new Color(0, 0, 0, 0)); 
        highscoreTable.setRowHeight(32);
        highscoreTable.setGridColor(new Color(255, 255, 255, 60));

        JTableHeader header = highscoreTable.getTableHeader();
        header.setFont(new Font("Verdana", Font.BOLD, 18));
        header.setForeground(Color.YELLOW);
        header.setBackground(new Color(0, 0, 0, 180));
        header.setOpaque(false);

        scrollPane = new JScrollPane(highscoreTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    // Loads highscores from file
    private Highscores loadHighscores() {
        try {
            File f = new File(fileName);
            if (f.exists()) {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(fileName));
                Highscores hs = (Highscores) os.readObject();
                os.close();
                return hs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Highscores();
    }
}

