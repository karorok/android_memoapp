package com.example.karorok.memoapp.Detail;

import android.net.Uri;

import com.example.karorok.memoapp.Base.BaseContract;

import java.util.List;

public interface DetailContract {

    interface View extends BaseContract.View{

        void showTitle(String titleText);

        void showDate(String date);

        void showText(String text);

        void showAddImage(String imagePath);

        void showAddImageUrl(String imagePath);

        void showAddImageNotExist();

        void deleteImage(int index);

        void showEditCompleteAlert();

        void showDeleteAlert();

        void deTailViewFinish();

        void changeEditState(String buttonName, boolean isEdit);

        void showBottomView();

        void hideBottomView();
    }

    interface Presenter<V extends View> extends BaseContract.Presenter<V>{

        void setMemoId(int id);

        void loadMemo();

        void addImage(String imagePath);

        void addImages(List<Uri> uriList);

        void imageViewDeleteClick(boolean isEdit, int index);

        void deleteButtonClick();

        void confirmMemoDelete();

        void editButtonClick(String title, String text, boolean isEdit);

        void closeButtonClick(Boolean isEdit);

        void saveAlertOkClick(String title, String text);

        void saveAlertNoClick();

    }

}
