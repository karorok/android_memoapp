package com.example.karorok.memoapp.Data;


import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class DBHelper {

    private static DBHelper instance = new DBHelper();

    public static DBHelper getInstance() {
        return instance;
    }

    Realm realm;

    public DBHelper() {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealmInstance() {
        return realm;
    }

    public RealmResults<Memo> getMemos() {
        RealmResults<Memo> memos = realm.where(Memo.class).findAll().sort("lastDate", Sort.DESCENDING);
        return memos;
    }

    public Memo getMemo(int id) {
        RealmResults<Memo> memos = realm.where(Memo.class).equalTo("id", id).findAll();
        Memo memo = memos.first();
        return memo;
    }

    public Memo getMemoByIndex(int index) {
        RealmResults<Memo> memos = realm.where(Memo.class).findAll().sort("lastDate", Sort.DESCENDING);
        return memos.get(index);
    }

    public void insertMemoImage(int id, String imagePath, boolean isUrl){
        Memo memo = getMemo(id);
        realm.beginTransaction();
        MemoImage memoImage = realm.createObject(MemoImage.class);
        memoImage.setImageData(imagePath);
        memoImage.setUrl(isUrl);
        memo.getImages().add(memoImage);
        realm.commitTransaction();
    }

    public void saveMemoDummyToRealData(int id, DummyMemo memo){
        Memo realData = getMemo(id);

        realm.beginTransaction();
        realData.setTitle(memo.getTitle());
        realData.setText(memo.getText());
        realData.setLastDate(memo.getLastDate());
        RealmList<MemoImage> images = new RealmList<>();
        for(int i = 0; i < memo.getImages().size(); i++){
            MemoImage memoImage = realm.createObject(MemoImage.class);
            memoImage.setImageData(memo.getImages().get(i).getImagePath());
            memoImage.setUrl(memo.getImages().get(i).isUrl());
            images.add(memoImage);
        }
        realData.setImages(images);

        realm.commitTransaction();
    }

    private Memo createBasicMemo(String text){
        int key = getAutoIncrementIndex();
        realm.beginTransaction();
        Memo memo = realm.createObject(Memo.class, key);
        memo.setTitle("");
        memo.setText(text);
        memo.setLastDate(new Date());

        realm.copyToRealm(memo);
        realm.commitTransaction();
        return memo;
    }

    public DummyMemo getDummyMemo(int id){
        Memo memo = getMemo(id);
        DummyMemo dummy = new DummyMemo(memo.getTitle(),memo.getLastDate(),memo.getText());
        ArrayList<DummyImage> images = new ArrayList<>();
        for(int i = 0; i < memo.getImages().size(); i++){
            DummyImage di = new DummyImage(memo.getImages().get(i).getImageData(),memo.getImages().get(i).isUrl());
            images.add(di);
        }
        dummy.images = images;
        return dummy;
    }

    public int makeNewMemoWithText(String text) {
        Memo memo = createBasicMemo("");
        insertMemoImage(memo.getId(),text,true);
        return memo.getId();
    }

    public int makeNewMemo(){
        Memo memo = createBasicMemo("");
        return memo.getId();
    }

    public void makeNewMemoWithImage(String imagePath){
        Memo memo = createBasicMemo("");
        int memoId = memo.getId();
        insertMemoImage(memoId,imagePath,false);
    }

    public void makeNewMemoWithImages(ArrayList<String> paths){
        Memo memo = createBasicMemo("");
        int memoId = memo.getId();
        for(int i=0;i<paths.size();i++){
            insertMemoImage(memoId,paths.get(i),false);
        }
    }

    public void deleteMemo(int id){
        Memo memo = getMemo(id);
        realm.beginTransaction();
        memo.deleteFromRealm();
        realm.commitTransaction();
    }

    private int getAutoIncrementIndex() {
        int nextIndex;
        Number currentIndex = null;
        currentIndex = realm.where(Memo.class).max("id");
        if (currentIndex == null) {
            nextIndex = 0;
        } else {
            nextIndex = currentIndex.intValue() + 1;
        }
        return nextIndex;
    }
}
