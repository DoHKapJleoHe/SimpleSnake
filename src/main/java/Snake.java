import java.util.ArrayList;

public class Snake
{
    private ArrayList<Point> body;
    private SnakeState state = SnakeState.ALIVE;
    private Direction direction;
    private int score = 0;
    private String name = "Default";

    public Snake()
    {
        body = new ArrayList<>();
        body.add(new Point(10, 10));
        body.add(new Point(9, 10));
        direction = Direction.setRandomDirection();
    }

    public ArrayList<Point> getBody()
    {
        return this.body;
    }

    public int getSnakeLength()
    {
        return body.size();
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setState(SnakeState state)
    {
        this.state = state;
    }

    public SnakeState getState()
    {
        return state;
    }

    public void addScore()
    {
        this.score++;
    }

    public void add2Score(){
        this.score += 2;
    }

    public int getScore()
    {
        return score;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
