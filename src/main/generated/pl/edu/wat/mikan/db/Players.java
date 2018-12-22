/*
 * This file is generated by jOOQ.
 */
package pl.edu.wat.mikan.db;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import pl.edu.wat.mikan.db.tables.Player;
import pl.edu.wat.mikan.db.tables.Round;
import pl.edu.wat.mikan.db.tables.Roundresult;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Players extends SchemaImpl {

    private static final long serialVersionUID = 96049942;

    /**
     * The reference instance of <code>Players</code>
     */
    public static final Players PLAYERS = new Players();

    /**
     * The table <code>Players.player</code>.
     */
    public final Player PLAYER = pl.edu.wat.mikan.db.tables.Player.PLAYER;

    /**
     * The table <code>Players.round</code>.
     */
    public final Round ROUND = pl.edu.wat.mikan.db.tables.Round.ROUND;

    /**
     * The table <code>Players.roundresult</code>.
     */
    public final Roundresult ROUNDRESULT = pl.edu.wat.mikan.db.tables.Roundresult.ROUNDRESULT;

    /**
     * No further instances allowed
     */
    private Players() {
        super("Players", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Player.PLAYER,
            Round.ROUND,
            Roundresult.ROUNDRESULT);
    }
}
