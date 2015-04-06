package com.xmkj.citymanager;

import java.io.File;

import com.xmkj.citymanager.util.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class PictureDialog extends Dialog implements View.OnClickListener {

    private Activity mContext;
    protected static File sCurrentPhotoFile;
    TextView mTextCamera;
    TextView mTextAlbum;
    TextView TextCancel;
    View mButtonPanel;

    public PictureDialog(Activity context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.picture_dialog);
        mContext = context;
        mTextCamera = (TextView) findViewById(R.id.text_camera);
        mTextAlbum = (TextView) findViewById(R.id.text_album);
        TextCancel = (TextView) findViewById(R.id.text_cancel);
        mTextCamera.setOnClickListener(this);
        mTextAlbum.setOnClickListener(this);
        TextCancel.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LayoutParams p = getWindow().getAttributes();
        p.width = (int) (metrics.widthPixels * 0.85);
        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.CENTER);
        super.show();
    }

    @Override
    public void onClick(View v) {
        if (v == mTextCamera) {
            sCurrentPhotoFile = Utils.getPhotoPath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //照相
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sCurrentPhotoFile));
            mContext.startActivityForResult(intent, MainActivity.TAKE_PHOTO); //启动照相
            dismiss();
        } else if (v == mTextAlbum) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MainActivity.IMAGE_UNSPECIFIED);
            mContext.startActivityForResult(intent, MainActivity.PHOTO_ZOOM);
            dismiss();
        } else if (v == TextCancel) {
            dismiss();
        }
    }

}
