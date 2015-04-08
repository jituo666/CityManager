package com.xmkj.citymanager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xmkj.citymanager.util.PictureListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsActivity extends Activity {

    private TextView mTitle;
    private TextView mAuthor;
    private TextView mContent;
    private ImageView mImage;
    private View mExit;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mTitle = (TextView) this.findViewById(R.id.newTitle);
        mAuthor= (TextView) this.findViewById(R.id.newAuthor);
        mContent = (TextView) this.findViewById(R.id.newContent);
        mImage = (ImageView) this.findViewById(R.id.newImage);
        mTitle.setText(getIntent().getStringExtra("title"));
        mAuthor.setText(getIntent().getStringExtra("author"));
        mContent.setText(getIntent().getStringExtra("content"));
        //
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_bg)
                .showImageForEmptyUri(R.drawable.default_bg)
                .showImageOnFail(R.drawable.default_bg)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("url"), mImage, options, new PictureListAdapter.AnimateFirstDisplayListener());
//
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
