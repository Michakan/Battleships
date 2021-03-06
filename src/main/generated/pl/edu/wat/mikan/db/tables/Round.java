/*
 * This file is generated by jOOQ.
 */
package pl.edu.wat.mikan.db.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import pl.edu.wat.mikan.db.Indexes;
import pl.edu.wat.mikan.db.Keys;
import pl.edu.wat.mikan.db.Players;
import pl.edu.wat.mikan.db.tables.records.RoundRecord;


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
public class Round extends TableImpl<RoundRecord> {

    private static final long serialVersionUID = 988816346;

    /**
     * The reference instance of <code>Players.round</code>
     */
    public static final Round ROUND = new Round();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RoundRecord> getRecordType() {
        return RoundRecord.class;
    }

    /**
     * The column <code>Players.round.pla1_id</code>.
     */
    public final TableField<RoundRecord, Integer> PLA1_ID = createField("pla1_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>Players.round.pla2_id</code>.
     */
    public final TableField<RoundRecord, Integer> PLA2_ID = createField("pla2_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>Players.round.rou_id</code>.
     */
    public final TableField<RoundRecord, Long> ROU_ID = createField("rou_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>Players.round</code> table reference
     */
    public Round() {
        this(DSL.name("round"), null);
    }

    /**
     * Create an aliased <code>Players.round</code> table reference
     */
    public Round(String alias) {
        this(DSL.name(alias), ROUND);
    }

    /**
     * Create an aliased <code>Players.round</code> table reference
     */
    public Round(Name alias) {
        this(alias, ROUND);
    }

    private Round(Name alias, Table<RoundRecord> aliased) {
        this(alias, aliased, null);
    }

    private Round(Name alias, Table<RoundRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Round(Table<O> child, ForeignKey<O, RoundRecord> key) {
        super(child, key, ROUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Players.PLAYERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PK_ROUND, Indexes.PLAYER1_FK, Indexes.PLAYER2_FK, Indexes.RESULTS_FK, Indexes.ROUND_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RoundRecord> getPrimaryKey() {
        return Keys.PK_ROUND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RoundRecord>> getKeys() {
        return Arrays.<UniqueKey<RoundRecord>>asList(Keys.PK_ROUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<RoundRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RoundRecord, ?>>asList(Keys.ROUND__FK_PLAYER1_ROUND, Keys.ROUND__FK_PLAYER2_ROUND, Keys.ROUND__FK_ROUND_RESULTS_ROUND);
    }

    public Player round_FkPlayer1Round() {
        return new Player(this, Keys.ROUND__FK_PLAYER1_ROUND);
    }

    public Player round_FkPlayer2Round() {
        return new Player(this, Keys.ROUND__FK_PLAYER2_ROUND);
    }

    public Roundresult roundresult() {
        return new Roundresult(this, Keys.ROUND__FK_ROUND_RESULTS_ROUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Round as(String alias) {
        return new Round(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Round as(Name alias) {
        return new Round(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Round rename(String name) {
        return new Round(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Round rename(Name name) {
        return new Round(name, null);
    }
}
