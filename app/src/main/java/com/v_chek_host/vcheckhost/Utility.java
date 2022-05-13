package com.v_chek_host.vcheckhost;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;

//import com.aimagix.vehicleinspection.classes.MyApplication;

public class Utility {
    public static Boolean isConnectingToInternet(final Activity activity, final Context ctx) {

        long startTime = Calendar.getInstance().getTimeInMillis();
        Boolean isConnected = false;
        int check = 0;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            isConnected = info != null && info.isAvailable() && info.isConnected();

            if (isConnected == true) {
                check = 1;
            }

            switch (check) {
                case 0:
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // your stuff to update the UI
                            Toast.makeText(ctx, "No internet connection", Toast.LENGTH_SHORT).show();
                            // customToast(ctx, ctx.getString(R.string.error_no_internet));
//                            Snackbar snackbar = Snackbar.make(activity.getCurrentFocus(),
//                                    ctx.getString(R.string.error_no_internet), Snackbar.LENGTH_LONG);
//                            snackbar.show();
                        }
                    });
                    break;
                case 1:
                    //  Log.d(TAG,"Internet Connection available");
                    Log.d("isConnected: ", "" + isConnected);
                    long endtime = Calendar.getInstance().getTimeInMillis();
                    long totalTime = endtime - startTime;
                    System.out.println("Time taken for conectivity check: " + totalTime);
            }
            return isConnected;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isConnected;
    }


    public static Bitmap rotateImageIfRequired(Context context, Bitmap img) throws IOException {
        if (img.getHeight() < img.getWidth()) {
            return rotateImage(img, 90);//(img, 270);
        }
        return img;
    }

    public static Bitmap imageRotation(Context context, String path, Bitmap bitmap) {

        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);


            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    System.out.println("imagerotation " + 90);
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    System.out.println("imagerotation " + 180);
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    System.out.println("imagerotation " + 270);
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }


    public static String deviceDetail = Build.MANUFACTURER
            + " " + Build.MODEL +
            " " + Build.VERSION.RELEASE
            + " " + osName();

    static String osName() {
        String osName = "";

        Field[] fields = Build.VERSION_CODES.class.getFields();
//        String codeName = "UNKNOWN";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    osName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return osName;
    }

    static boolean isExceptionAPILoading = false;


//        InputStream input = context.getContentResolver().openInputStream(selectedImage);

//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        img.compress(Bitmap.CompressFormat.JPEG /*PNG*/, 100 /*ignored for PNG*/, bos);
//        ExifInterface ei=null;
//        if (Build.VERSION.SDK_INT > 23) {
//            byte[] bitmapdata = bos.toByteArray();
//            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
//            ei = new ExifInterface(bs);
//        } else {
//            try {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE, "Title");
//                values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
//                Uri selectedImage = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
////            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), img, "Title", null);
////            if (img != null){
////                App.bitmapValue = "bmp height: "+img.getHeight()+"bmp height: "+img.getWidth();
////            }else{
////                    App.bitmapValue = "bmp null";
////            }
////            App.uriPath=selectedImage==null?"null":selectedImage.getPath();
////            Uri selectedImage = Uri.parse(path);
//                ei = new ExifInterface(selectedImage.getPath());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        int orientation = 0;
//        if(img.getHeight()<img.getWidth()){
//            return rotateImage(img, 90);//(img, 270);
//        }

//        if (ei != null) {
//            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        }
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_UNDEFINED:
//                return rotateImage(img, 90);//(img, 270);
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                return rotateImage(img, 90);
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                return rotateImage(img, 180);
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                return rotateImage(img, 270);
//            default:
//                return img;
//        }

//        return img;
//    }


    public static Bitmap rotateImage(Bitmap imge, int degree) {
        Bitmap workingBitmap = Bitmap.createBitmap(imge);
        Bitmap img = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Canvas canvas = new Canvas(img);
//        canvas.save(); //save the position of the canvas
//        int h=img.getHeight();
//        int w=img.getWidth();
//
//        canvas.rotate(degree, w + (w/ 2), h + (h/ 2)); //rotate the canvas
//        canvas.drawBitmap(img, w, h, null); //draw the image on the rotated canvas
//        canvas.restore();  // restore the canvas position.
//        AppData.getInstance().imageRes = "Before rotate wxh:" + img.getWidth() + "x" + img.getHeight() + ", ";
//        img = ImageUtils.resize(img, 375, 1000);
//        img = ImageUtils.resize(img, 275, 275);
        //        img = ImageUtils.resize(img, 500, 1000);
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        img = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
//        img=ImageUtils.resize(img, 375, 1000);
//        AppData.getInstance().imageRes = AppData.getInstance().imageRes+ " After rotate wxh:" + img.getWidth() + "x" + img.getHeight() + ", ";
//        img.recycle();
        return /*img*/ img;
    }

    public static void clearData(Context context) {
        SharedPreferenceManager.setIslogged(context, false);
        SharedPreferenceManager.setPassword(context, null);
        SharedPreferenceManager.setUsername(context, null);
        SharedPreferenceManager.setEmployeeId(context, null);
        SharedPreferenceManager.setDisplayName(context, null);
    }


}
