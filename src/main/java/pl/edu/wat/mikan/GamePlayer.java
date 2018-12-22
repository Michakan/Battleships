package pl.edu.wat.mikan;

import java.util.Arrays;

public class GamePlayer {
    private final long ID;
    private final String name;
    private int points;
    private boolean isActive;
    private byte[] salt = new byte[Apk.HASH_SIZE];
    private byte[] passwordHash = new byte[Apk.HASH_SIZE];

    public GamePlayer(long ID, String name)
    {
        this.ID=ID;
        this.name=name;
        points=0;
        isActive=false;
    }

    public GamePlayer(long ID, String name, byte[] salt, byte[] passwordHash)
    {
        this.ID=ID;
        this.name=name;
        points=0;
        isActive=false;
        this.salt=salt;
        this.passwordHash=passwordHash;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points)
    {
        this.points+=points;
    }

    public String getName() {
        return name;
    }

    public void reset()
    {
        points=0;
    }

    public boolean checkPassword(char[] password)
    {
        return Arrays.equals(Apk.genEncrypted(password, salt), passwordHash);
    }
}
