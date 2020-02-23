package com.example.karorok.memoapp.Base;


import com.example.karorok.memoapp.Data.DBHelper;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V>{

    private V view;


    private DBHelper dbHelper;

    public BasePresenter(){
        this.dbHelper = DBHelper.getInstance();
    }

    public BasePresenter(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void releaseView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }
}
