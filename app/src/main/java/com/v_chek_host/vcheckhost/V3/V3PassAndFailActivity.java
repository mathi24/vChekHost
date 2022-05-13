package com.v_chek_host.vcheckhost.V3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.CaptureActivity;
import com.v_chek_host.vcheckhost.V2.V2ModelTrainingDataSetActivity;
import com.v_chek_host.vcheckhost.V2.adapters.ModelTrainingDataAdapetr;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V3.Utils.RealPathUtils;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageUploadModel;
import com.v_chek_host.vcheckhost.V3.models.V3MlModelDatasetResponce;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V3PassAndFailActivity extends BaseActivity {
    TextView tvPass, tvFail;
    public static boolean isPassAndFail = true;
    Context context;
    ImageView ivBack;
    private RecyclerView recyclerView;
    private ModelTrainingDataAdapetr mAdapter;
    //private List<ModelTrainingSetDataModel.Message> images;
    private List<V3MlModelDatasetResponce.Message> images;
    //public ModelTrainingSetDataModel modelTrainingSetDataModel;
    public V3MlModelDatasetResponce modelTrainingSetDataModel;
    public Button btnAdd;
    public int vImageResult = 1;

    public static int modelID = 0;
    TextView tvModelName;
    public static String modelName;
    public static List<MultipleImageUploadModel> multipleImageUploadModelList = new ArrayList<>();
    boolean isOpenCamera = false;
    BottomSheetDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v3_pass_and_fail);
        context = V3PassAndFailActivity.this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            modelID = bundle.getInt("model_id");
            modelName = bundle.getString("model_name");
        }
        tvPass = findViewById(R.id.tvPass);
        tvFail = findViewById(R.id.tvFail);
        ivBack = findViewById(R.id.ivBack);
        btnAdd = findViewById(R.id.btnAdd);
        tvModelName = findViewById(R.id.tvModelName);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvModelName.setText(modelName);
        images = new ArrayList<>();
        mAdapter = new ModelTrainingDataAdapetr(images, context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (isPassAndFail) {
            tvPass.setTextColor(getResources().getColor(R.color.black));
            tvFail.setTextColor(getResources().getColor(R.color.activity_title_color));
        }
        tvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPassAndFail = true;
                vImageResult = 1;
                tvFail.setTextColor(getResources().getColor(R.color.activity_title_color));
                tvPass.setTextColor(getResources().getColor(R.color.black));
                addPassList();


            }
        });
        tvFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPassAndFail = false;
                vImageResult = 0;
                tvPass.setTextColor(getResources().getColor(R.color.activity_title_color));
                tvFail.setTextColor(getResources().getColor(R.color.black));
                addFailList();

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomDialogSheet();
            }
        });
        // callModelTrainedDataSet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callModelTrainedDataSet();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void callModelTrainedDataSet() {
        if (Utility.isConnectingToInternet(V3PassAndFailActivity.this, context)) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Call<V3MlModelDatasetResponce> call = ApiClient.getInstance().getApi().
                    getMlModelDatasetByModelIdForMobileV2(StrConstants.API_VALUE, modelID);
            call.enqueue(new Callback<V3MlModelDatasetResponce>() {
                @Override
                public void onResponse(Call<V3MlModelDatasetResponce> call, Response<V3MlModelDatasetResponce> response) {
                    progressDialog.dismiss();
                    int statusCode = response.code();
                    if (statusCode == 200) {

                        assert response.body() != null;
                        if (response.body().getMessage() != null) {
                            modelTrainingSetDataModel = response.body();
                            List<V3MlModelDatasetResponce.Message> trainDataModels = response.body().getMessage();
                            images.clear();
                            /*for (int i = 0; i < trainDataModels.size(); i++) {
                                images.add(trainDataModels.get(i));
                            }*/

                            for (int i = 0; i < trainDataModels.size(); i++) {
                                if (vImageResult == 1) {
                                    if (trainDataModels.get(i).getImageResult() != 0) {
                                        images.add(trainDataModels.get(i));
                                    }
                                } else {
                                    if (trainDataModels.get(i).getImageResult() == 0) {
                                        images.add(trainDataModels.get(i));
                                    }
                                }

                            }
                            // images = trainDataModels;
                            // txtCount.setText("( " + images.size() + " )");
                            mAdapter = new ModelTrainingDataAdapetr(images, context);
                            recyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(V3PassAndFailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<V3MlModelDatasetResponce> call, Throwable t) {
                    progressDialog.dismiss();
                    //Toast.makeText(V3PassAndFailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void addPassList() {
        if (modelTrainingSetDataModel.getMessage() != null) {
            images.clear();
            for (int i = 0; i < modelTrainingSetDataModel.getMessage().size(); i++) {
                if (modelTrainingSetDataModel.getMessage().get(i).getImageResult() != 0) {
                    images.add(modelTrainingSetDataModel.getMessage().get(i));
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addFailList() {
        if (modelTrainingSetDataModel != null && modelTrainingSetDataModel.getMessage() != null) {
            images.clear();
            for (int i = 0; i < modelTrainingSetDataModel.getMessage().size(); i++) {
                if (modelTrainingSetDataModel.getMessage().get(i).getImageResult() == 0) {
                    images.add(modelTrainingSetDataModel.getMessage().get(i));
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public void openBottomDialogSheet() {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.upload_image_bottm_sheet_pop_up, null);
        TextView tvOpenCamera = (TextView) view.findViewById(R.id.tvOpenCamera);
        TextView tvGallary = (TextView) view.findViewById(R.id.tvGallery);
        dialog = new BottomSheetDialog(context, R.style.DialogStyle);
        dialog.setContentView(view);
        dialog.show();
        tvOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open camera
                isOpenCamera = true;
                cameraPermission();
            }
        });
        tvGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallary
                isOpenCamera = false;
                cameraPermission();
                //openGallary();
            }
        });
    }

    private void cameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            //Navigation.findNavController(view).navigate(R.id.photoBasedDataCameraFragment);
                            // Intent intent = new Intent(MainActivity.this, CamaraActivity.class);
                            // startActivity(intent);
                            //  openCamera();
                            //createPhotoTaker();
                            //  photoTaker.captureImage();
                            if (isOpenCamera) {
                                startActivity(new Intent(V3PassAndFailActivity.this, CaptureActivity.class));
                            } else {
                                openGallary();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.txt_need_permissions));
        builder.setMessage(getString(R.string.txt_app_settings_permissions));
        builder.setPositiveButton(getString(R.string.txt_goto_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(R.string.txt_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void openGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            multipleImageUploadModelList.clear();
            if (data.getClipData() != null) {

                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    try {
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        if (selectedImage!=null) {
                            getImageFilePath(imageUri);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //DocumentFile sourceFile = DocumentFile.fromSingleUri(context, imageUri);
                    ////if (sourceFile!=null && sourceFile.exists()) {
                    //}
                }
            } else if (data.getData() != null) {
                Uri imgUri = data.getData();
                // DocumentFile sourceFile = DocumentFile.fromSingleUri(context, imgUri);
                // if (sourceFile!=null && sourceFile.exists()) {
                try {
                    InputStream imageStream = getContentResolver().openInputStream(imgUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if (selectedImage!=null) {
                        getImageFilePath(imgUri);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
               // getImageFilePath(imgUri);
                //}
            }
            if (multipleImageUploadModelList.size()>0) {
                Intent intent = new Intent(getApplicationContext(), V3FetchImagesListActivity.class);
                startActivity(intent);
            }
        }
    }

    public void getImageFilePath(Uri uri) {

      /*  File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];

        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            MultipleImageUploadModel multipleImageUploadModel = new MultipleImageUploadModel(imagePath, "", false, "");
            multipleImageUploadModelList.add(multipleImageUploadModel);
            cursor.close();
        }*/
        if (!TextUtils.isEmpty(RealPathUtils.getRealPath(getApplicationContext(), uri))) {
            MultipleImageUploadModel multipleImageUploadModel = new MultipleImageUploadModel(RealPathUtils.getRealPath(getApplicationContext(), uri), "", false, "");
            multipleImageUploadModelList.add(multipleImageUploadModel);
        }
       /* try {
            String id = DocumentsContract.getDocumentId(uri);
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getCacheDir().getAbsolutePath() + "/" + id);
            writeFile(inputStream, file);
            String filePath = file.getAbsolutePath();
            if (!TextUtils.isEmpty(filePath)) {
                MultipleImageUploadModel multipleImageUploadModel = new MultipleImageUploadModel(filePath, "", false, "");
                multipleImageUploadModelList.add(multipleImageUploadModel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            if (uri.getAuthority() != null) {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}