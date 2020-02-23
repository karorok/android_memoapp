package com.example.karorok.memoapp.Main;


import com.example.karorok.memoapp.Base.BaseContract;
import com.example.karorok.memoapp.Data.Memo;

import java.util.ArrayList;


public interface MainContract {

    interface View extends BaseContract.View{

        void showRecyclerView(ArrayList<Memo> memos);

        void showWriteActivity(int id);

        void showNoMemoInfo();

        void hideNoMemoInfo();
    }

    interface Presenter<V extends View> extends BaseContract.Presenter<V>{

        void floatingButtonClick();

        void recyclerViewItemClick(int index);

        void loadMemoList();

        void checkMemoListSize(ArrayList<Memo> memoList);

        void handleText(String text);

        void handleImage(String path);

        void handleImages(ArrayList<String> paths);

    }
}
