package com.example.karorok.memoapp.Main;


import com.example.karorok.memoapp.Base.BasePresenter;
import com.example.karorok.memoapp.Data.DBHelper;
import com.example.karorok.memoapp.Data.Memo;

import java.util.ArrayList;

import io.realm.RealmResults;

public class MainPresenter<V extends MainContract.View> extends BasePresenter<V> implements MainContract.Presenter<V> {

    @Override
    public void floatingButtonClick() {
        int id = DBHelper.getInstance().makeNewMemo();
        getView().showWriteActivity(id);
    }

    @Override
    public void recyclerViewItemClick(int index) {
        int id = DBHelper.getInstance().getMemoByIndex(index).getId();
        getView().showWriteActivity(id);
    }

    @Override
    public void loadMemoList() {
        RealmResults<Memo> memos = DBHelper.getInstance().getMemos();
        ArrayList<Memo> memoArrayList = new ArrayList<>();
        memoArrayList.addAll(memos);
        checkMemoListSize(memoArrayList);
    }

    @Override
    public void checkMemoListSize(ArrayList<Memo> memoList) {
        if(memoList.size() == 0){
            getView().showNoMemoInfo();
        }else{
            getView().hideNoMemoInfo();
        }
        getView().showRecyclerView(memoList);
    }

    @Override
    public void handleText(String text) {
        DBHelper.getInstance().makeNewMemoWithText(text);
    }

    @Override
    public void handleImage(String path) {
        DBHelper.getInstance().makeNewMemoWithImage(path);
    }

    @Override
    public void handleImages(ArrayList<String> paths) {
        DBHelper.getInstance().makeNewMemoWithImages(paths);
    }
}
