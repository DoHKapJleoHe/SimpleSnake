import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame implements ScoreObserver
{
    private final int width = 1100;
    private final int height = 637;
    private GamingField gamingField;

    private JLabel scoreLabel;
    private DefaultTableModel scoreTableModel;
    private JTable scoreTable;

    private NewPlayerDialog playerDialog = new NewPlayerDialog();

    public MainFrame()
    {
        this.setTitle("Змейка");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setBounds(10, 10, width, height);
        this.setVisible(true);

        initGamingField();
        initScore();
        initScoreBoard();
    }

    private void initScoreBoard()
    {
        scoreTableModel = new DefaultTableModel(new Object[] {"Player", "Score"}, 0);
        scoreTable = new JTable(scoreTableModel);

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(650, 150, 400, 200);
        this.add(scrollPane);
    }

    private void initScore()
    {
        scoreLabel = new JLabel("Score : " + 0);
        scoreLabel.setBounds(650, 40, 400, 20);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.add(scoreLabel);
    }

    private void initGamingField()
    {
        gamingField = new GamingField();
        gamingField.setScoreObserver(this);
        this.add(gamingField);
    }

    @Override
    public void updateScore(int score)
    {
        scoreLabel.setText("Score : "+score);
    }

    /**
     * This method updates score board on the main game frame.
     * If players name already exist in the table, then the score
     * will be updated only if new score is bigger than current score
     *
     * @param snake
     */
    @Override
    public void updateScoreTable(Snake snake)
    {
        playerDialog.setSnake(snake);
        if (JOptionPane.showConfirmDialog(null, playerDialog, "New Player", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            String playerName = snake.getName();
            int newScore = snake.getScore();

            boolean updated = false;

            for (int row = 0; row < scoreTableModel.getRowCount(); row++)
            {
                String existingName = (String) scoreTableModel.getValueAt(row, 0);

                if (existingName.equals(playerName))
                {
                    int curScore = (int) scoreTableModel.getValueAt(row, 1);
                    if (newScore > curScore)
                        scoreTableModel.setValueAt(newScore, row, 1);

                    updated = true;
                    break;
                }
            }

            if(!updated)
                scoreTableModel.addRow(new Object[]{snake.getName(), snake.getScore()});
        }
    }
}
