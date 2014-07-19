package com.daogen.clotheme;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.daogen.clotheme.PersonInformation;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PersonInformation.
*/
public class PersonInformationDao extends AbstractDao<PersonInformation, Long> {

    public static final String TABLENAME = "PersonInformation";

    /**
     * Properties of entity PersonInformation.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property PersonName = new Property(1, String.class, "PersonName", false, "PERSON_NAME");
        public final static Property StyleID = new Property(2, Integer.class, "StyleID", false, "STYLE_ID");
    };


    public PersonInformationDao(DaoConfig config) {
        super(config);
    }
    
    public PersonInformationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PersonInformation' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "'PERSON_NAME' TEXT," + // 1: PersonName
                "'STYLE_ID' INTEGER);"); // 2: StyleID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PersonInformation'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PersonInformation entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String PersonName = entity.getPersonName();
        if (PersonName != null) {
            stmt.bindString(2, PersonName);
        }
 
        Integer StyleID = entity.getStyleID();
        if (StyleID != null) {
            stmt.bindLong(3, StyleID);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PersonInformation readEntity(Cursor cursor, int offset) {
        PersonInformation entity = new PersonInformation( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // PersonName
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2) // StyleID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PersonInformation entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setPersonName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setStyleID(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PersonInformation entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PersonInformation entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}