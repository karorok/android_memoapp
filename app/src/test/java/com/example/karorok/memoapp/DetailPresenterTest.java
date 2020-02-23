package com.example.karorok.memoapp;

import com.example.karorok.memoapp.Detail.DetailContract;
import com.example.karorok.memoapp.Detail.DetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {
    @Mock
    DetailContract.View mockView;

    DetailPresenter<DetailContract.View> presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new DetailPresenter<>();
        presenter.setView(mockView);
    }

    @Test
    public void detailPresenterTest_closeClickButton_isEdit_true(){
        boolean isEdit = true;
        presenter.closeButtonClick(isEdit);
        Mockito.verify(mockView).showEditCompleteAlert();
    }

    @Test
    public void detailPresenterTest_closeClickButton_isEdit_false(){
        boolean isEdit = false;
        presenter.closeButtonClick(isEdit);
        Mockito.verify(mockView).deTailViewFinish();
    }

    @Test
    public void detailPresenterTest_editButtonClick_isEdit_close(){
        boolean isEdit = false;
        presenter.editButtonClick("","",isEdit);
        Mockito.verify(mockView).showBottomView();
    }

}
