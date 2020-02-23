package com.example.karorok.memoapp.Detail;

import android.net.Uri;
import android.webkit.URLUtil;

import com.example.karorok.memoapp.Base.BasePresenter;
import com.example.karorok.memoapp.Data.DBHelper;
import com.example.karorok.memoapp.Data.DummyImage;
import com.example.karorok.memoapp.Data.DummyMemo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DetailPresenter<V extends DetailContract.View> extends BasePresenter<V> implements DetailContract.Presenter<V> {

    private int memoId;

    private DummyMemo memo = new DummyMemo();

    public DetailPresenter() {
    }

    public DetailPresenter(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public void setMemoId(int id) {
        this.memoId = id;
    }

    @Override
    public void loadMemo() {
        memo = getDbHelper().getDummyMemo(memoId);
        getView().showTitle(memo.getTitle());
        getView().showText(memo.getText());
        getView().showDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(memo.getLastDate()));
        for (int i = 0; i < memo.getImages().size(); i++) {
            DummyImage di = memo.getImages().get(i);
            String imagePath = di.getImagePath();
            if(di.isUrl()){
                if(URLUtil.isValidUrl(imagePath)){
                    getView().showAddImageUrl(imagePath);
                }else{
                    getView().showAddImageNotExist();
                }
            }else{
                getView().showAddImage(imagePath);
            }
        }
    }

    @Override
    public void addImage(String imagePath) {
        memo.getImages().add(new DummyImage(imagePath,false));
        getView().showAddImage(imagePath);
    }

    @Override
    public void addImages(List<Uri> uriList) {
        for (int i = 0; i < uriList.size(); i++) {
            addImage(uriList.get(i).getPath());
        }
    }

    @Override
    public void imageViewDeleteClick(boolean isEdit, int index) {
        if(isEdit){
            memo.getImages().remove(index);
            getView().deleteImage(index + 1);
        }else{
            getView().showMessage("편집버튼을 눌러야 삭제 가능합니다.");
        }
    }

    @Override
    public void deleteButtonClick() {
        getView().showDeleteAlert();
    }

    @Override
    public void confirmMemoDelete() {
        getDbHelper().deleteMemo(memoId);
        getView().showMessage("삭제되었습니다.");
        getView().deTailViewFinish();
    }

    @Override
    public void editButtonClick(String title, String text, boolean isEdit) {
        String buttonName;
        if (!isEdit) {
            buttonName = "완료";
            getView().showBottomView();
        } else {
            buttonName = "편집";
            memo.setTitle(title);
            memo.setText(text);
            getDbHelper().saveMemoDummyToRealData(memoId,memo);
            getView().hideBottomView();
            getView().hideKeyboard();
        }
        getView().changeEditState(buttonName, !isEdit);
    }

    @Override
    public void closeButtonClick(Boolean isEdit) {
        if (isEdit) {
            getView().showEditCompleteAlert();
        } else {
            checkIsWrite();
            getView().deTailViewFinish();
        }
    }

    private void checkIsWrite(){
        if(memo.getTitle().equals("") && memo.getText().equals("") && memo.getImages().size() == 0){
            getDbHelper().deleteMemo(memoId);
        }
    }

    @Override
    public void saveAlertOkClick(String title, String text) {
        memo.setTitle(title);
        memo.setText(text);
        getDbHelper().saveMemoDummyToRealData(memoId,memo);
        getView().deTailViewFinish();
    }

    @Override
    public void saveAlertNoClick() {
        checkIsWrite();
        getView().deTailViewFinish();
    }
}
