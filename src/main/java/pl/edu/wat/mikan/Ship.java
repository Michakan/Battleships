package pl.edu.wat.mikan;

import java.awt.*;
import java.util.Arrays;

public class Ship {
    public enum Type{
        Destroyer(2, 3)
        , Submarine(3, 2)
        , Cruiser(4, 1)
        , Battleship(5, 1)
        , Carrier(6, 1);


        private final int size;
        private final int number;

        Type(int size, int number)
        {
            this.size = size;
            this.number=number;
        }

        public int getSize()
        {
            return size;
        }

        public int getNumber()
        {
            return number;
        }

        public static int getNumberOfAllShips()
        {
            return Destroyer.number+Submarine.number+Cruiser.number+Battleship.number+Carrier.number;
        }
    }
    public enum Direction{
        Vertical, Horizontal
    }
    /////////////////////////////////
    private final Type type;
    private Point position;
    private Direction direction;
    private boolean[] shootedPieces;
    ////////////////////////////////

    Ship(Type type)
    {
        this.type=type;
        direction=Direction.Horizontal;

        shootedPieces=new boolean[type.number];
        Arrays.fill(shootedPieces, false);
    }

    public int getLenght()
    {
        return type.size;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void changeDirection()
    {
        if(direction==Direction.Vertical)
            direction=Direction.Horizontal;
        else direction=Direction.Vertical;
    }

    Point getPosition()
    {
        return position;
    }
    void setPosition(Point pos)
    {
        position=pos;
    }

    boolean shootPiece(int pos) throws IllegalArgumentException {
        if(pos>type.size)
            throw new IllegalArgumentException();
        shootedPieces[pos]=true;

        for(boolean piece:shootedPieces)
            if(!piece)return true;

        return false;
    }
}
