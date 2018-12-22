/*
 * This file is generated by jOOQ.
 */
package pl.edu.wat.mikan.db.tables.records;


import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import pl.edu.wat.mikan.db.tables.Player;


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
public class PlayerRecord extends UpdatableRecordImpl<PlayerRecord> implements Record6<Integer, String, byte[], Boolean, byte[], String> {

    private static final long serialVersionUID = -1308762647;

    /**
     * Setter for <code>Players.player.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>Players.player.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>Players.player.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>Players.player.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>Players.player.passwordhash</code>.
     */
    public void setPasswordhash(byte... value) {
        set(2, value);
    }

    /**
     * Getter for <code>Players.player.passwordhash</code>.
     */
    public byte[] getPasswordhash() {
        return (byte[]) get(2);
    }

    /**
     * Setter for <code>Players.player.isactive</code>.
     */
    public void setIsactive(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>Players.player.isactive</code>.
     */
    public Boolean getIsactive() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>Players.player.salt</code>.
     */
    public void setSalt(byte... value) {
        set(4, value);
    }

    /**
     * Getter for <code>Players.player.salt</code>.
     */
    public byte[] getSalt() {
        return (byte[]) get(4);
    }

    /**
     * Setter for <code>Players.player.ipadress</code>.
     */
    public void setIpadress(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>Players.player.ipadress</code>.
     */
    public String getIpadress() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, byte[], Boolean, byte[], String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, byte[], Boolean, byte[], String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Player.PLAYER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Player.PLAYER.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<byte[]> field3() {
        return Player.PLAYER.PASSWORDHASH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field4() {
        return Player.PLAYER.ISACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<byte[]> field5() {
        return Player.PLAYER.SALT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Player.PLAYER.IPADRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] component3() {
        return getPasswordhash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component4() {
        return getIsactive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] component5() {
        return getSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getIpadress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] value3() {
        return getPasswordhash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value4() {
        return getIsactive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] value5() {
        return getSalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getIpadress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord value3(byte... value) {
        setPasswordhash(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord value4(Boolean value) {
        setIsactive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord value5(byte... value) {
        setSalt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord value6(String value) {
        setIpadress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerRecord values(Integer value1, String value2, byte[] value3, Boolean value4, byte[] value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlayerRecord
     */
    public PlayerRecord() {
        super(Player.PLAYER);
    }

    /**
     * Create a detached, initialised PlayerRecord
     */
    public PlayerRecord(Integer id, String name, byte[] passwordhash, Boolean isactive, byte[] salt, String ipadress) {
        super(Player.PLAYER);

        set(0, id);
        set(1, name);
        set(2, passwordhash);
        set(3, isactive);
        set(4, salt);
        set(5, ipadress);
    }
}
