package pl.edu.wat.mikan;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {
    private enum PieceType{
        Clear, Missed, ShipPiece, MovingShipPiece, ShootedShip, DestroyedShip
    }
    /////////////////////////////////////////////////////////
    private static final int sizeX=10;
    private static final int sizeY=10;
    /////////////////////////////////////////////////////////

    private PieceType[][] grid = new PieceType[sizeX][sizeY];
    private HashMap<Ship, ArrayList<Point> >  ships = new HashMap<>();
    private Ship movingShip;

    public Board()
    {
        clear();
    }

    public void clear()
    {
        for (PieceType[] row: grid)
            Arrays.fill(row, PieceType.Clear);

        ships.clear();
    }

    public boolean tryShot(int x, int y) throws NotPossibleMove
    {
        switch (grid[x][y])
        {
            case Clear:
                grid[x][y]=PieceType.Missed;
                return false;
            case ShipPiece:
                grid[x][y]=PieceType.ShootedShip;
                return checkShip(findShip(new Point( x, y)) );
            default:
                throw new NotPossibleMove();
        }

    }

    public void addShip(@org.jetbrains.annotations.NotNull Ship ship) throws NotPossibleMove, IllegalArgumentException {
        Point pos = ship.getPosition();

        if (pos.x < 0 || pos.y < 0
                || pos.x >= sizeY || pos.y >= sizeY
                || (ship.getDirection() == Ship.Direction.Horizontal && pos.x + ship.getLenght() >= sizeX)
                || (ship.getDirection() == Ship.Direction.Vertical && pos.y + ship.getLenght() >= sizeY))
            throw new IllegalArgumentException();
        //

        int startX, startY, lastX, lastY;

        if (ship.getDirection() == Ship.Direction.Vertical) {
            startX = (pos.x == 0 ? 0 : pos.x - 1);
            lastX = (pos.x == sizeX - 1 ? sizeX - 1 : pos.x + 1);
            startY = (pos.y == 0 ? 0 : pos.y - 1);
            lastY = (pos.y + ship.getLenght() < sizeY ? ship.getLenght() : pos.y + 1);
        } else {
            startX = (pos.x == 0 ? 0 : pos.x - 1);
            lastX = (pos.x + ship.getLenght() < sizeX ? ship.getLenght() : pos.x + 1);
            startY = (pos.y == 0 ? 0 : pos.y - 1);
            lastY = (pos.x == sizeY - 1 ? sizeY - 1 : pos.y + 1);
        }

        for (int y = startY; y <= lastY; y++) {
            for(int x=startX;x<=lastX;x++) {
                if (grid[x][y] != PieceType.Clear)
                    throw new NotPossibleMove();
            }
        }

        if(ship.getDirection() == Ship.Direction.Vertical)
        {
            ships.put(ship, new ArrayList<>() );
            for(int y=pos.y;y<ship.getLenght();y++)
            {
                grid[pos.x][y]=PieceType.ShipPiece;
                ships.get(ship).add(new Point(pos.x, y));
            }
        }else{
            for(int x=pos.x;x<ship.getLenght();x++)
            {
                grid[x][pos.y]=PieceType.ShipPiece;
                ships.get(ship).add(new Point(x, pos.y));
            }
        }
    }

    public void tryMoveShip(@org.jetbrains.annotations.NotNull Ship ship)
    {
        movingShip=ship;
        for(Point point :ships.get(ship))
            grid[point.x][point.y]=PieceType.MovingShipPiece;
    }

    private void shipDestroyed(@org.jetbrains.annotations.NotNull Ship ship)
    {
        Point pos = ship.getPosition();


        if(ship.getDirection() == Ship.Direction.Vertical)
        {

            int start=(pos.y == 0 ? 0 : pos.y - 1);
            int last=(pos.y + ship.getLenght() < sizeY ? ship.getLenght() : pos.y + 1);

            if(pos.x!=0) {
                for (int y = start; y <= last; y++)
                    grid[pos.x - 1][y] = PieceType.Missed;
            }

            if(pos.x!=sizeX-1) {
                for (int y = start; y <= last; y++)
                    grid[pos.x + 1][y] = PieceType.Missed;
            }

            if(pos.y!=0)
                grid[pos.x][pos.y-1] = PieceType.Missed;
            if(pos.y!=sizeY-1)
                grid[pos.x][pos.y+1] = PieceType.Missed;

            for(int y=pos.y;y<ship.getLenght();y++)
                grid[pos.x][y]=PieceType.ShipPiece;
        }else{

            int start= (pos.x == 0 ? 0 : pos.x - 1);
            int last= (pos.x + ship.getLenght() < sizeX ? ship.getLenght() : pos.x + 1);

            if(pos.y!=0) {
                for (int x = start; x <= last; x++)
                    grid[x][pos.y-1] = PieceType.Missed;
            }

            if(pos.y!=sizeY-1) {
                for (int x = start; x <= last; x++)
                    grid[x][pos.y+1] = PieceType.Missed;
            }

            if(pos.x!=0)
                grid[pos.x-1][pos.y] = PieceType.Missed;
            if(pos.x!=sizeX-1)
                grid[pos.x+1][pos.y] = PieceType.Missed;

            for(int x=pos.x;x<ship.getLenght();x++)
                grid[x][pos.y]=PieceType.ShipPiece;
        }
    }

    private boolean checkShip(@org.jetbrains.annotations.NotNull Ship ship)
    {
        Point pos = ship.getPosition();

        if(ship.getDirection() == Ship.Direction.Vertical)
        {
            for(int y=pos.y;y<ship.getLenght();y++)
                if(grid[pos.x][y]==PieceType.ShipPiece) return false;
        }else{
            for(int x=pos.x;x<ship.getLenght();x++)
                if(grid[x][pos.y]==PieceType.ShipPiece) return false;
        }

        return true;
    }

    private Ship findShip(Point point)
    {
       for(Ship ship:ships.keySet())
       {
           for(Point piece:ships.get(ship))
               if(piece==point)return ship;
       }
       return new Ship(Ship.Type.Destroyer);
    }
}
