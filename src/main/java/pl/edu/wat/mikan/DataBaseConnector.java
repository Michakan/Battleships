package pl.edu.wat.mikan;

import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static pl.edu.wat.mikan.db.tables.Player.PLAYER;

public class DataBaseConnector {

    public static String[] getPlayersLogins() throws SQLException {
        try (Connection conn = DriverManager.getConnection(Apk.dbURL, Apk.dbUserName, Apk.dbPassword)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            List<String> result = create.select(PLAYER.NAME).from(PLAYER).fetch().getValues(PLAYER.NAME);
            return result.toArray(new String[result.size()]);
        }

    }

    public static void addPlayer(String name, byte[] password, byte[] salt) throws SQLException {
        try (Connection conn = DriverManager.getConnection(Apk.dbURL, Apk.dbUserName, Apk.dbPassword)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            create.insertInto(PLAYER, PLAYER.NAME, PLAYER.PASSWORDHASH, PLAYER.SALT)
                    .values(name, password, salt).execute();
        }
    }

    public static GamePlayer getPlayer(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(Apk.dbURL, Apk.dbUserName, Apk.dbPassword)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);

            Record4<Integer, String, byte[], byte[]> result = create.select(PLAYER.ID, PLAYER.NAME, PLAYER.PASSWORDHASH, PLAYER.SALT).from(PLAYER).where(PLAYER.NAME.eq(name)).fetchOne();

            return result != null ? new GamePlayer(result.getValue(PLAYER.ID), result.getValue(PLAYER.NAME), result.getValue(PLAYER.SALT), result.getValue(PLAYER.PASSWORDHASH))
                    : null;
        }
    }

    public static boolean checkIsCreated(String name) throws SQLException {
        try (Connection conn = DriverManager.getConnection(Apk.dbURL, Apk.dbUserName, Apk.dbPassword)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);

            return create.select(PLAYER.NAME).from(PLAYER).where(PLAYER.NAME.eq(name)).fetchOne() != null;
        }
    }
}