package com.example.karorok.memoapp;

import com.example.karorok.memoapp.Data.Memo;
import com.example.karorok.memoapp.Data.MemoImage;
import com.example.karorok.memoapp.Main.MainContract;
import com.example.karorok.memoapp.Main.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainContract.View mockView;

    MainPresenter<MainContract.View> presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter<>();
        presenter.setView(mockView);
    }

    @Test
    public void mainPresenterTest_checkMemoListSize_sizeNotZero(){
        ArrayList<Memo> memos = getList();
        presenter.checkMemoListSize(memos);
        Mockito.verify(mockView).hideNoMemoInfo();
    }

    @Test
    public void mainPresenterTest_checkMemoListSize_sizeZero(){
        ArrayList<Memo> memos = new ArrayList<>();
        presenter.checkMemoListSize(memos);
        Mockito.verify(mockView).showNoMemoInfo();
    }

    private ArrayList<Memo> getList(){
        ArrayList<Memo> list = new ArrayList<>();
        int randomNum = (int)(Math.random() * 10);
        for(int i=1;i<randomNum;i++){
            if( i % 2 == 1){
                list.add(new Memo(i,new Date(),""+i,""));
            }else{
                list.add(new Memo(i,new Date(),""+i,"",getImages(i)));
            }
        }
        return list;
    }

    private RealmList<MemoImage> getImages(int id){
        RealmList<MemoImage> images = new RealmList<>();
        for(int i=0;i<id;i++){
            images.add(new MemoImage(""+(id+i)));
        }
        return images;
    }
}
