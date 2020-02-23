package com.example.karorok.memoapp.Detail;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karorok.memoapp.Base.BaseActivity;
import com.example.karorok.memoapp.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class DetailActivity extends BaseActivity implements DetailContract.View{

    private TextView closeButton;

    private TextView galleryButton;

    private LinearLayout contentParent;

    private TextView dateTextView;

    private TextView deleteButton;

    private TextView editButton;

    private TextView title;

    private EditText content;

    private RelativeLayout bottomView;

    private DetailPresenter<DetailContract.View> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        presenter = new DetailPresenter();
        presenter.setView(this);

        closeButton = findViewById(R.id.DetailActivity_closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.closeButtonClick(content.isFocusableInTouchMode());
            }
        });

        editButton = findViewById(R.id.DetailActivity_editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editButtonClick(title.getText().toString(),content.getText().toString(), content.isFocusableInTouchMode());
            }
        });

        deleteButton = findViewById(R.id.DetailActivity_deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteButtonClick();
            }
        });

        title = findViewById(R.id.DetailActivity_title);

        content = findViewById(R.id.DetailActivity_content);

        bottomView = findViewById(R.id.DetailActivity_bottomView);
        bottomView.setVisibility(View.GONE);

        galleryButton = findViewById(R.id.DetailActivity_openGallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TedBottomPicker.with(DetailActivity.this)
                        .setPeekHeight(1600)
                        .showTitle(false)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No Select")
                        .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(List<Uri> uriList) {
                                presenter.addImages(uriList);
                            }
                        });
            }
        });

        contentParent = findViewById(R.id.DetailActivity_contentParent);


        dateTextView = findViewById(R.id.DetailActivity_date);
        setPermission();
        checkIntent();
        presenter.loadMemo();

    }

    private void setPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Log.d(TAG, "check permission");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Log.d(TAG, "not check permission");
            }
        };


        TedPermission.with(DetailActivity.this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("갤러리 접근을 위한 권한입니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void checkIntent(){
        int id = getIntent().getIntExtra("id",-1);
        if (id == -1){
            return ;
        }
        presenter.setMemoId(id);
    }

    @Override
    public void showTitle(String titleText) {
        title.setText(titleText);
    }

    @Override
    public void showText(String text) {
        content.setText(text);
    }

    @Override
    public void showDate(String date) {
        dateTextView.setText(date);
    }

    @Override
    public void showAddImage(String imagePath){
        ImageView imageView = new ImageView(DetailActivity.this);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1000);
        lp.setMargins(0,10,0,10);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnLongClickListener(longClickListener);
        Glide.with(DetailActivity.this)
                .load(new File(imagePath)) // Uri of the picture
                .into(imageView);
        contentParent.addView(imageView);
    }

    @Override
    public void showAddImageUrl(String imagePath) {
        TextView textView = new TextView(DetailActivity.this);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
        lp.setMargins(0,10,0,10);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#efefef"));
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setText(imagePath);
        textView.setAutoLinkMask(Linkify.ALL);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setOnLongClickListener(longClickListener);
        contentParent.addView(textView);
    }

    @Override
    public void showAddImageNotExist() {
        TextView textView = new TextView(DetailActivity.this);
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
        lp.setMargins(0,10,0,10);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#efefef"));
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setText("url 주소가 유효하지 않습니다");
        textView.setOnLongClickListener(longClickListener);
        contentParent.addView(textView);
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int index = contentParent.indexOfChild(v) - 1;
            presenter.imageViewDeleteClick(content.isFocusableInTouchMode(),index);
            return true;
        }
    };

    @Override
    public void deleteImage(int index) {
        contentParent.removeViewAt(index);
    }

    @Override
    public void showDeleteAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
        dialog.setMessage("메모를 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.confirmMemoDelete();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void showEditCompleteAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
        dialog.setMessage("메모를 저장하시겠습니까?")
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.saveAlertOkClick(title.getText().toString(), content.getText().toString());
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.saveAlertNoClick();
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void deTailViewFinish() {
        finish();
    }

    @Override
    public void changeEditState(String buttonName, boolean isEdit) {
        editButton.setText(buttonName);

        content.setFocusable(isEdit);
        content.setFocusableInTouchMode(isEdit);

        title.setFocusable(isEdit);
        title.setFocusableInTouchMode(isEdit);
    }

    @Override
    public void showBottomView() {
        bottomView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBottomView() {
        bottomView.setVisibility(View.GONE);
    }

    public void onBackPressed(){
        presenter.closeButtonClick(content.isFocusableInTouchMode());
    }

    @Override
    protected void onDestroy() {
        presenter.releaseView();
        super.onDestroy();
    }

}
