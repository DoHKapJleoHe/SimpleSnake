import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class NewPlayerDialog extends JPanel
{
    private final JTextField nameInput;
    private Snake snake;

    private String name = " ";

    public NewPlayerDialog()
    {
        this.setPreferredSize(new Dimension(300, 100));

        JLabel nameLabel = new JLabel("Введите своё имя");
        this.add(nameLabel);

        nameInput = new JTextField();
        nameInput.setPreferredSize(new Dimension(100, 20));
        this.add(nameInput);

        nameInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                name = nameInput.getText();
                snake.setName(name);
            }
        });
        /*nameInput.addActionListener(e -> {
            name = nameInput.getText();
            snake.setName(name);
        });*/

    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}
