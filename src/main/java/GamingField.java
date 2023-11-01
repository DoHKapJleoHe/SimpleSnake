import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamingField extends JPanel implements KeyListener
{
    private final int width = 30;
    private final int height = 30;
    private final int dim = 20;

    private Snake snake;
    private Food food;
    private final Timer timer;

    private boolean isStopped = false;

    private ScoreObserver scoreObserver;

    public GamingField()
    {
        super();
        this.setSize(width*dim, height*dim);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setVisible(true);


        food = new Food(new Point(13, 18));
        timer = new Timer(200, e -> {
            moveSnake();
            repaint();
        });

        createNewSnake();
    }

    public void setScoreObserver(ScoreObserver observer)
    {
        scoreObserver = observer;
    }

    private void createNewSnake()
    {
        if (snake == null)
        {
            snake = new Snake();
            timer.start();
        }
        else
        {
            String prevSnakeName = snake.getName();
            snake = new Snake();
            snake.setName(prevSnakeName);

            timer.start();
        }
    }

    private void killSnake()
    {
        snake.setState(SnakeState.DEAD);
        scoreObserver.updateScoreTable(snake);

        createNewSnake();
    }

    private void moveSnake()
    {
        if (snake.getState() == SnakeState.ALIVE)
        {
            Point head = snake.getBody().get(0);
            switch (snake.getDirection())
            {
                case RIGHT -> {
                    Point temp = checkCoords(new Point(
                            head.getX() + 1,
                            head.getY()
                    ));
                    if (temp == null)
                        return;

                    snake.getBody().add(0, temp);
                }

                case LEFT -> {
                    Point temp = checkCoords(new Point(
                            head.getX() - 1,
                            head.getY()
                    ));
                    if (temp == null)
                        return;

                    snake.getBody().add(0, temp);
                }

                case UP -> {
                    Point temp = checkCoords(new Point(
                            head.getX(),
                            head.getY() - 1
                    ));
                    if (temp == null)
                        return;

                    snake.getBody().add(0, temp);
                }

                case DOWN -> {
                    Point temp = checkCoords(new Point(
                            head.getX(),
                            head.getY() + 1
                    ));
                    if (temp == null)
                        return;

                    snake.getBody().add(0, temp);
                }
            }

            if (!onFood(snake.getBody().get(0)))
                snake.getBody().remove(snake.getSnakeLength() - 1);
            else
            {
                if (food.getType() == FoodType.NORMAL)
                    snake.addScore();
                else
                    snake.add2Score();

                spawnNewFood();
                scoreObserver.updateScore(snake.getScore());
            }
        }

        if (checkSelfCollision())
        {
            killSnake();
        }
    }

    private void spawnNewFood()
    {
        Random r = new Random();
        int x = 0, y = 0;

        boolean ok = false;
        while (!ok)
        {
            x = r.nextInt(width);
            y = r.nextInt(height);
            ok = true;

            for (Point p : snake.getBody())
            {
                if (x == p.getX() && y == p.getY())
                {
                    ok = false;
                    break;
                }
            }

            if (!ok)
            {
                x = r.nextInt(width);
                y = r.nextInt(height);
            }
        }

        food.setCoords(new Point(x, y));

        int specialFoodChance = r.nextInt(10);
        if (specialFoodChance == 5)
            food.setType(FoodType.SPECIAL);
        else
            food.setType(FoodType.NORMAL);
    }

    private Point checkCoords(Point p)
    {
        /*int x = p.getX() > width ? 0 : p.getX();
        int y = p.getY() > height ? 0 : p.getY();

        return new Point(x, y);*/
        if (p.getY() >= height || p.getY() < 0 || p.getX() < 0 || p.getX() >= width)
        {
            timer.stop();
            JOptionPane.showConfirmDialog(this, "Game over!", "Snake", JOptionPane.YES_NO_OPTION);
            killSnake();
            return null;
        }

        return p;
    }

    private boolean checkSelfCollision()
    {
        Point head = snake.getBody().get(0);

        for (int i = 1; i < snake.getSnakeLength(); i++)
        {
            Point segment = snake.getBody().get(i);
            if (head.equals(segment))
            {
                return true;
            }
        }

        return false;
    }

    private boolean onFood (Point head)
    {
        return head.getX() == food.getCoords().getX() && head.getY() == food.getCoords().getY();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);


        g.setColor(Color.GREEN);

        for (Point p : snake.getBody())
        {
            g.fillRect(p.getX()*dim, p.getY()*dim, dim, dim);
        }

        if (food.getType() == FoodType.NORMAL)
            g.setColor(Color.RED);
        else
            g.setColor(Color.MAGENTA);

        g.fillRect(food.getCoords().getX() * dim, food.getCoords().getY() * dim, dim, dim);
        g.setColor(Color.WHITE);

        // Рисуем вертикальные линии
        for (int x = 0; x <= width; x++) {
            g.drawLine(x * dim, 0, x * dim, height * dim);
        }

        // Рисуем горизонтальные линии
        for (int y = 0; y <= height; y++) {
            g.drawLine(0, y * dim, width * dim, y * dim);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT)
        {
            if (snake.getDirection() == Direction.RIGHT)
                return;

            snake.setDirection(Direction.LEFT);
        }
        else if (code == KeyEvent.VK_RIGHT)
        {
            if (snake.getDirection() == Direction.LEFT)
                return;

            snake.setDirection(Direction.RIGHT);
        }
        else if (code == KeyEvent.VK_UP)
        {
            if (snake.getDirection() == Direction.DOWN)
                return;

            snake.setDirection(Direction.UP);
        }
        else if (code == KeyEvent.VK_DOWN)
        {
            if (snake.getDirection() == Direction.UP)
                return;

            snake.setDirection(Direction.DOWN);
        }
        else
        {
            isStopped = !isStopped;

            if (isStopped)
                timer.stop();
            else
                timer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
