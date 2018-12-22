/*
 * This file is generated by jOOQ.
 */
package pl.edu.wat.mikan.db;


import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import pl.edu.wat.mikan.db.tables.Player;
import pl.edu.wat.mikan.db.tables.Round;
import pl.edu.wat.mikan.db.tables.Roundresult;


/**
 * A class modelling indexes of tables of the <code>Players</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index AK_NAME_PLAYER = Indexes0.AK_NAME_PLAYER;
    public static final Index PK_PLAYER = Indexes0.PK_PLAYER;
    public static final Index PLAYER_AK_NAME = Indexes0.PLAYER_AK_NAME;
    public static final Index PLAYER_PK = Indexes0.PLAYER_PK;
    public static final Index PK_ROUND = Indexes0.PK_ROUND;
    public static final Index PLAYER1_FK = Indexes0.PLAYER1_FK;
    public static final Index PLAYER2_FK = Indexes0.PLAYER2_FK;
    public static final Index RESULTS_FK = Indexes0.RESULTS_FK;
    public static final Index ROUND_PK = Indexes0.ROUND_PK;
    public static final Index PK_ROUNDRESULT = Indexes0.PK_ROUNDRESULT;
    public static final Index ROUNDRESULT_PK = Indexes0.ROUNDRESULT_PK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index AK_NAME_PLAYER = Internal.createIndex("ak_name_player", Player.PLAYER, new OrderField[] { Player.PLAYER.NAME }, true);
        public static Index PK_PLAYER = Internal.createIndex("pk_player", Player.PLAYER, new OrderField[] { Player.PLAYER.ID }, true);
        public static Index PLAYER_AK_NAME = Internal.createIndex("player_ak_name", Player.PLAYER, new OrderField[] { Player.PLAYER.NAME }, true);
        public static Index PLAYER_PK = Internal.createIndex("player_pk", Player.PLAYER, new OrderField[] { Player.PLAYER.ID }, true);
        public static Index PK_ROUND = Internal.createIndex("pk_round", Round.ROUND, new OrderField[] { Round.ROUND.PLA1_ID, Round.ROUND.PLA2_ID, Round.ROUND.ROU_ID }, true);
        public static Index PLAYER1_FK = Internal.createIndex("player1_fk", Round.ROUND, new OrderField[] { Round.ROUND.PLA1_ID }, false);
        public static Index PLAYER2_FK = Internal.createIndex("player2_fk", Round.ROUND, new OrderField[] { Round.ROUND.PLA2_ID }, false);
        public static Index RESULTS_FK = Internal.createIndex("results_fk", Round.ROUND, new OrderField[] { Round.ROUND.ROU_ID }, false);
        public static Index ROUND_PK = Internal.createIndex("round_pk", Round.ROUND, new OrderField[] { Round.ROUND.PLA1_ID, Round.ROUND.PLA2_ID, Round.ROUND.ROU_ID }, true);
        public static Index PK_ROUNDRESULT = Internal.createIndex("pk_roundresult", Roundresult.ROUNDRESULT, new OrderField[] { Roundresult.ROUNDRESULT.ID }, true);
        public static Index ROUNDRESULT_PK = Internal.createIndex("roundresult_pk", Roundresult.ROUNDRESULT, new OrderField[] { Roundresult.ROUNDRESULT.ID }, true);
    }
}