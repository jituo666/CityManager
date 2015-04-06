package com.xmkj.citymanager.util;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Environment;

public class Utils {

    public static File getPhotoPath() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        File photoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (!photoDir.exists()) {
            photoDir.mkdirs();
        }
        File photoPath = new File(photoDir.getPath() + File.separator + format.format(date) + ".jpg");
        return photoPath;
    }
}
