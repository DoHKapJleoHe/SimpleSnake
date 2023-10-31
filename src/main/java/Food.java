public class Food
{
    private Point coords;
    private FoodType type = FoodType.NORMAL;

    public Food (Point p)
    {
        coords = p;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public FoodType getType() {
        return type;
    }

    public Point getCoords() {
        return coords;
    }
}
