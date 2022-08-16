package com.car.scth.mvp.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.car.scth.BuildConfig;

/**
 * 图片相关工具类
 */
public class ImageUtils {

    /**
     * 获取圆角
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 按照倍数对图片大小进行缩放
     *
     * @param bitmap
     * @param size
     * @return
     */
    public static Bitmap scaleBitmapBySize(Bitmap bitmap, float size) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(size, size);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    /**
     * file --> uri
     * @param context
     * @param path
     *
     * @return
     */
    public static Uri getUriFromFile(Context context, String path) {
        if (context == null || TextUtils.isEmpty(path)) {
            throw new NullPointerException();
        }
        File file = new File(path);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 通过uri获取路径
     *
     * @param uri
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String handleImageOnKitKat(Uri uri, Context context) {
        String imagePath = null;

        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //Log.d(TAG, uri.toString());
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, context);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                //Log.d(TAG, uri.toString());
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null, context);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //Log.d(TAG, "content: " + uri.toString());
            imagePath = getImagePath(uri, null, context);
        }
        return imagePath;
    }

    private static String getImagePath(Uri uri, String selection, Context context) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }

    /***
     * 以文件流的方式将指定路径的图片转uri
     * @param context
     * @param path ，指定图片(或文件)的路径
     * @return
     */
    public static Uri getMediaUriFromPath(Context context, String path) {
        FileInputStream fs = null;
        Uri uri = null;
        try {
            fs = new FileInputStream(path);
            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
            uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap , null,null));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return uri;
    }

//    /***
//     * 转换成文件存储到内存中
//     * @param path ，指定图片(或文件)的路径
//     * @return
//     */
//    public static String getMediaUriFromPath_2(String path) {
//        boolean isSuccess = false;
//        String imagePath = "";
//        if (!TextUtils.isEmpty(path)){
//            Bitmap bitmap = com.blankj.utilcode.util.ImageUtils.getBitmap(path);
//            bitmap = ImageUtils.compressByQuality(bitmap, 2048L);
//            byte[] bytes = ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG);
//            imagePath = PathUtils.getExternalAppCachePath() + File.pathSeparator + System.currentTimeMillis() + ".png";
//            isSuccess = FileIOUtils.writeFileFromBytesByStream(imagePath, bytes);
//            bitmap.recycle();
//        }
//        return imagePath;
//    }



}
