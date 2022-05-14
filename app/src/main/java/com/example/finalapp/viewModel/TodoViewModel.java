package com.example.finalapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalapp.Data.TodoRepository;
import com.example.finalapp.model.ETodo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mTodoRepository;
    public LiveData<List<ETodo>> mAllTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);

        mTodoRepository= new TodoRepository(application);
        mAllTodos= mTodoRepository.getmAllTodiList();
    }

    public void deleteById(ETodo todo)
    {
        mTodoRepository.deleteById(todo);
    }
    public void deleteAll()
    {
        mTodoRepository.deleteAll();
    }
    public void update(ETodo todo)
    {
        mTodoRepository.update(todo);
    }
    public void insert(ETodo todo)
    {
        mTodoRepository.insert(todo);

    }
    public LiveData<List<ETodo>> getmAllTodos() {
        return mAllTodos;
    }
    public ETodo getTodoById(int id)
    {
        return mTodoRepository.getTodoById(id);

    }
}
