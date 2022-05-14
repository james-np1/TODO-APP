package com.example.finalapp.Data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class TodoRoomDatabase_Impl extends TodoRoomDatabase {
  private volatile TodoDAO _todoDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `todo_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `todo_date` INTEGER, `isComplete` INTEGER, `priority` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"5345ac5e0578eedb2a45e8d238966761\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `todo_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTodoTable = new HashMap<String, TableInfo.Column>(6);
        _columnsTodoTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsTodoTable.put("title", new TableInfo.Column("title", "TEXT", true, 0));
        _columnsTodoTable.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsTodoTable.put("todo_date", new TableInfo.Column("todo_date", "INTEGER", false, 0));
        _columnsTodoTable.put("isComplete", new TableInfo.Column("isComplete", "INTEGER", false, 0));
        _columnsTodoTable.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTodoTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTodoTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTodoTable = new TableInfo("todo_table", _columnsTodoTable, _foreignKeysTodoTable, _indicesTodoTable);
        final TableInfo _existingTodoTable = TableInfo.read(_db, "todo_table");
        if (! _infoTodoTable.equals(_existingTodoTable)) {
          throw new IllegalStateException("Migration didn't properly handle todo_table(com.example.finalapp.model.ETodo).\n"
                  + " Expected:\n" + _infoTodoTable + "\n"
                  + " Found:\n" + _existingTodoTable);
        }
      }
    }, "5345ac5e0578eedb2a45e8d238966761", "fd1b5f8c57db15774438fd181cb8d06b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "todo_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `todo_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TodoDAO mTodoDAO() {
    if (_todoDAO != null) {
      return _todoDAO;
    } else {
      synchronized(this) {
        if(_todoDAO == null) {
          _todoDAO = new TodoDAO_Impl(this);
        }
        return _todoDAO;
      }
    }
  }
}
