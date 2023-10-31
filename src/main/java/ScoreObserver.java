// Special interface that helps MainFrame to
// change score label each time snake eats food
public interface ScoreObserver
{
    void updateScore(int score);
    void updateScoreTable(Snake snake);
}
