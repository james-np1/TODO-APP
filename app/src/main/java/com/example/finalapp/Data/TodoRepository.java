package com.example.finalapp.Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.finalapp.model.ETodo;

import java.util.List;

public class TodoRepository {

    private TodoDAO mTodoDAO;
    private LiveData<List<ETodo>> mAllTodiList;

    public TodoRepository(Application application) {
        TodoRoomDatabase database= TodoRoomDatabase.getDatabse(application);
        mTodoDAO= database.mTodoDAO();
        mAllTodiList= mTodoDAO.getAllTodos();
    }

    public LiveData<List<ETodo>> getmAllTodiList() {
        return mAllTodiList;
    }

    public ETodo getTodoById(int id)
    {
        return mTodoDAO.getTodoById(id);
    }
    public void insert(ETodo todo)
    {
        new insertAsynchTask(mTodoDAO).execute(todo);
    }
    public void update(ETodo todo)
    {
        new updateAsynchTask(mTodoDAO).execute(todo);

    }
    public void deleteAll()
    {
        new deleteAllAsynchTask(mTodoDAO).execute();
    }
    public void deleteById(ETodo todo)
    {
        new deleteByIdAsynchTask(mTodoDAO).execute(todo);
    }

    private static class insertAsynchTask extends AsyncTask<ETodo, Void, Void>
    {
        private TodoDAO mTodoDAO;
        private insertAsynchTask(TodoDAO todoDAO)
        {
            mTodoDAO= todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo...todos)
        {
            mTodoDAO.insert(todos[0]);
            return null;
        }
    }
    private static class updateAsynchTask extends AsyncTask<ETodo, Void, Void>
    {
        private TodoDAO mTodoDAO;
        private updateAsynchTask(TodoDAO todoDAO)
        {
            mTodoDAO= todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo...todos)
        {
            mTodoDAO.update(todos[0]);
            return null;
        }
    }
    private static class deleteAllAsynchTask extends AsyncTask<ETodo, Void, Void>
    {
        private TodoDAO mTodoDAO;
        private deleteAllAsynchTask(TodoDAO todoDAO)
        {
            mTodoDAO= todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo...todos)
        {
            mTodoDAO.deleteAll();
            return null;
        }
    }
    private static class deleteByIdAsynchTask extends AsyncTask<ETodo, Void, Void>
    {
        private TodoDAO mTodoDAO;
        private deleteByIdAsynchTask(TodoDAO todoDAO)
        {
            mTodoDAO= todoDAO;
        }

        @Override
        protected Void doInBackground(ETodo...todos)
        {
            mTodoDAO.deleteById(todos[0]);
            return null;
        }
    }
}
