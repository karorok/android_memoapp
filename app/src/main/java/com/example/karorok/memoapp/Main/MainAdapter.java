package com.example.karorok.memoapp.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karorok.memoapp.Base.BaseViewHolder;
import com.example.karorok.memoapp.Data.DBHelper;
import com.example.karorok.memoapp.Data.Memo;
import com.example.karorok.memoapp.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class MainAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<Memo> mList;

    private View.OnClickListener clickListener;

    public MainAdapter(ArrayList<Memo> list, View.OnClickListener clickListener) {
        this.mList = list;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends BaseViewHolder {

        public TextView title;

        public TextView date;

        public TextView text;

        public RelativeLayout imageParent;

        public ImageView image;

        public TextView imageNum;

        public TextView imageNotFound;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Memo_title);
            date = itemView.findViewById(R.id.Memo_date);
            text = itemView.findViewById(R.id.Memo_text);
            imageParent = itemView.findViewById(R.id.Memo_imageParent);
            image = itemView.findViewById(R.id.Memo_image);
            imageNum = itemView.findViewById(R.id.Memo_imageNum);
            imageNotFound = itemView.findViewById(R.id.Memo_imageNotFound);
        }

        @Override
        protected void clear() {
            image.setImageDrawable(null);
            date.setText("");
            text.setText("");
            imageParent.setVisibility(View.GONE);
            imageNum.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Memo memo = DBHelper.getInstance().getMemoByIndex(position);
            title.setText(memo.getTitle());
            date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(memo.getLastDate()));
            String memoText = memo.getText().split("\n")[0];
            text.setText(memoText);
            int imagesNum = memo.getImages().size();
            if(imagesNum > 0){
                imageParent.setVisibility(View.VISIBLE);
                if(imagesNum > 1) {
                    imageNum.setText("+" + (imagesNum - 1));
                }
                String firstImagePath = memo.getImages().get(0).getImageData();

                if(memo.getImages().get(0).isUrl()){
                    if(URLUtil.isValidUrl(firstImagePath)){
                        imageNotFound.setText(firstImagePath);
                    }else{
                        imageNotFound.setText("");
                    }
                }else{
                    Glide.with(itemView.getContext())
                            .load(new File(firstImagePath))
                            .into(image);
                }
            }
        }

    }
}
