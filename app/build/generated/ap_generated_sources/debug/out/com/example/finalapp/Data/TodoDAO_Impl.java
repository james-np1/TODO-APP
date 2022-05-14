package com.example.finalapp.Data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.finalapp.model.ETodo;
import com.example.finalapp.utility.DateConverter;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class TodoDAO_Impl implements TodoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfETodo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfETodo;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfETodo;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TodoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfETodo = new EntityInsertionAdapter<ETodo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `todo_table`(`id`,`title`,`description`,`todo_date`,`isComplete`,`priority`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ETodo value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getTodo_date());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getComplete() == null ? null : (value.getComplete() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        stmt.bindLong(6, value.getPriority());
      }
    };
    this.__deletionAdapterOfETodo = new EntityDeletionOrUpdateAdapter<ETodo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `todo_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ETodo value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfETodo = new EntityDeletionOrUpdateAdapter<ETodo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `todo_table` SET `id` = ?,`title` = ?,`description` = ?,`todo_date` = ?,`isComplete` = ?,`priority` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ETodo value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getTodo_date());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        final Integer _tmp_1;
        _tmp_1 = value.getComplete() == null ? null : (value.getComplete() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp_1);
        }
        stmt.bindLong(6, value.getPriority());
        stmt.bindLong(7, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM todo_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(ETodo todo) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfETodo.insert(todo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(ETodo todo) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfETodo.handle(todo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(ETodo... todos) {
    __db.beginTransaction();
    try {
      __updateAdapterOfETodo.handleMultiple(todos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ETodo>> getAllTodos() {
    final String _sql = "SELECT * FROM todo_table ORDER BY todo_date, priority DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<ETodo>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<ETodo> compute() {
        if (_observer == null) {
          _observer = new Observer("todo_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfTodoDate = _cursor.getColumnIndexOrThrow("todo_date");
          final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("isComplete");
          final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
          final List<ETodo> _result = new ArrayList<ETodo>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ETodo _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final Date _tmpTodo_date;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTodoDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTodoDate);
            }
            _tmpTodo_date = DateConverter.toDate(_tmp);
            final Boolean _tmpIsComplete;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIsComplete)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfIsComplete);
            }
            _tmpIsComplete = _tmp_1 == null ? null : _tmp_1 != 0;
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            _item = new ETodo(_tmpTitle,_tmpDescription,_tmpTodo_date,_tmpIsComplete,_tmpPriority);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public ETodo getTodoById(int id) {
    final String _sql = "SELECT * FROM todo_table WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfTodoDate = _cursor.getColumnIndexOrThrow("todo_date");
      final int _cursorIndexOfIsComplete = _cursor.getColumnIndexOrThrow("isComplete");
      final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
      final ETodo _result;
      if(_cursor.moveToFirst()) {
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final Date _tmpTodo_date;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfTodoDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfTodoDate);
        }
        _tmpTodo_date = DateConverter.toDate(_tmp);
        final Boolean _tmpIsComplete;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfIsComplete)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsComplete);
        }
        _tmpIsComplete = _tmp_1 == null ? null : _tmp_1 != 0;
        final int _tmpPriority;
        _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
        _result = new ETodo(_tmpTitle,_tmpDescription,_tmpTodo_date,_tmpIsComplete,_tmpPriority);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
