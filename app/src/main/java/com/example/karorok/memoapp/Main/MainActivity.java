package com.example.karorok.memoapp.Main;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.karorok.memoapp.Base.BaseActivity;
import com.example.karorok.memoapp.Data.Memo;
import com.example.karorok.memoapp.Detail.DetailActivity;
import com.example.karorok.memoapp.R;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements MainContract.View{

    private FloatingActionButton floatingButton;

    private RecyclerView recyclerView;

    private TextView noMemoInfo;

    private MainPresenter<MainContract.View> presenter;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter();
        presenter.setView(this);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);

        floatingButton = findViewById(R.id.MainActivity_FloatingButton);
        floatingButton.setImageResource(R.drawable.iconfinder_write_126582);

        noMemoInfo = findViewById(R.id.MainActivity_NoMemoInfo);

        recyclerView = findViewById(R.id.MainActivity_RecyclerView);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.floatingButtonClick();
            }
        });

        checkIntent();
    }

    private void checkIntent(){
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            presenter.handleText(sharedText);
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            presenter.handleImage(getRealPathFromURI(imageUri));
        }
    }

    private void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        ArrayList<String> imagePaths = new ArrayList<>();
        if (imageUris != null) {
            for(int i=0;i<imageUris.size();i++){
                imagePaths.add(getRealPathFromURI(imageUris.get(i)));
            }
            presenter.handleImages(imagePaths);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        cursor.close();
        return path;
    }

    @Override
    public void showRecyclerView(ArrayList<Memo> memos) {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MainAdapter(memos , new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = recyclerView.getChildAdapterPosition(v);
                presenter.recyclerViewItemClick(index);
            }
        }));
    }

    @Override
    public void showWriteActivity(int id) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("id",id);
        startActivityForResult(intent,3);
    }

    @Override
    public void showNoMemoInfo() {
        noMemoInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoMemoInfo() {
        noMemoInfo.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadMemoList();
    }

    @Override
    protected void onDestroy() {
        presenter.releaseView();
        super.onDestroy();
    }

}
