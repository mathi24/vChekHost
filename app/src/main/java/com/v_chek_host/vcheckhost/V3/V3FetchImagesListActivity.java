package com.v_chek_host.vcheckhost.V3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.v_chek_host.vcheckhost.MainActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.V2.CaptureActivity;
import com.v_chek_host.vcheckhost.V3.adapters.V3FetchTakenImagesListAdapetr;
import com.v_chek_host.vcheckhost.V3.database.DatabaseClient;
import com.v_chek_host.vcheckhost.V3.database.TrainModel;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageModelResponce;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageUploadModel;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V3FetchImagesListActivity extends AppCompatActivity implements V3FetchTakenImagesListAdapetr.DeleteIconEnabled {
    private RecyclerView recyclerView;
    private V3FetchTakenImagesListAdapetr mAdapter;
    private List<V3FetchTakenImagesListAdapetr.TrainModelData> images;
    TextView tvPass, tvFail;
    TextView tvModelName;
    ImageView ivBack, ivDelete;

    List<MultipleImageUploadModel> multipleImageUploadModelList;
    Handler mHandler;
    Runnable runnable;
    public boolean isUploading = false;
    public boolean isCallingAPI = false;
    public String imagePath = "";
    public String imagePosition = "";
    public Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_images_list);
        multipleImageUploadModelList = V3PassAndFailActivity.multipleImageUploadModelList;
        tvPass = findViewById(R.id.tvPass);
        ivBack = findViewById(R.id.ivBack);
        ivDelete = findViewById(R.id.ivDelete);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvModelName = findViewById(R.id.tvModelName);
        tvModelName.setText(V3PassAndFailActivity.modelName);
        tvPass.setText(V3PassAndFailActivity.isPassAndFail ? "Pass" : "Fail");
        images = new ArrayList<>();
        mAdapter = new V3FetchTakenImagesListAdapetr(images, getApplicationContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                boolean apiCall = false;
                for (int i = 0; i < images.size(); i++
                ) {
                    if (!apiCall) {
                        if (!images.get(i).isUploaded()) {
                            apiCall = true;
                            int finalI = i;
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    //TODO your background code
                                    File file = new File(images.get(finalI).getImagePATH());
                                    if (!TextUtils.isEmpty(images.get(finalI).getImagePATH()) && file.exists()) {
                                        callMultipleImageUploadAPI(finalI, images.get(finalI).getImagePATH());
                                    }
                                }
                            });
                        }
                    }
                }

            }
        };
        loadImagesData();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmationPopUp();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modelImageIDS = "";
                for (int i = 0; i < images.size(); i++) {
                    modelImageIDS = modelImageIDS + images.get(i).getId() + ",";
                }
                if (!TextUtils.isEmpty(modelImageIDS)) {
                    if (modelImageIDS.endsWith(",")) {
                        modelImageIDS = modelImageIDS.substring(0, modelImageIDS.length() - 1);
                    }
                    callUpdateTrainingModelImagesAPI(modelImageIDS);
                }
            }
        });


    }

    public void loadImagesData() {
        /*class FetchImages extends AsyncTask<Void, Void, List<TrainModel>> {

            @Override
            protected List<TrainModel> doInBackground(Void... voids) {
                List<TrainModel> trainModelList = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase().dao().getAllCourses();
                return trainModelList;
            }

            @Override
            protected void onPostExecute(List<TrainModel> trainModels) {
                super.onPostExecute(trainModels);
                for (TrainModel trainModel : trainModels) {
                    V3FetchTakenImagesListAdapetr.TrainModelData trainModelData = new V3FetchTakenImagesListAdapetr.TrainModelData();
                    trainModelData.setId(trainModel.getId());
                    trainModelData.setImagePATH(trainModel.getImagePATH());
                    images.add(trainModelData);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
        FetchImages fetchImages = new FetchImages();
        fetchImages.execute();*/

        for (int i = 0; i < multipleImageUploadModelList.size(); i++) {
            if (!multipleImageUploadModelList.get(i).isUploaded()) {
                File file = new File(multipleImageUploadModelList.get(i).getLocalImagePath());
                if (file.exists()) {
                    V3FetchTakenImagesListAdapetr.TrainModelData trainModelData = new V3FetchTakenImagesListAdapetr.TrainModelData();
                    trainModelData.setImagePATH(multipleImageUploadModelList.get(i).getLocalImagePath());
                    trainModelData.setUploaded(multipleImageUploadModelList.get(i).isUploaded());
                    images.add(trainModelData);
                }
            }
        }
        mAdapter.notifyDataSetChanged();

        if (images.size() > 0 && !isUploading) {
            isUploading = true;
            btnAdd.setEnabled(false);
            // mHandler.postDelayed(runnable, 1000);
            callingAPI();
        }

    }

    @Override
    public void deleteIconEnabled() {
        ivDelete.setVisibility(View.VISIBLE);
    }

    public void deleteImagesFromLocal(TrainModel trainModel, int positon) {
        /*class DeleteImages extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dao()
                        .delete(trainModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                File file = new File(trainModel.getImagePATH());
                if (file.exists()) {
                    file.delete();
                }
                // images.remove(images.get(positon));
                //mAdapter.remove(positon);
//                mAdapter.notifyDataSetChanged();
            }
        }
        DeleteImages deleteImages = new DeleteImages();
        deleteImages.execute();*/
        File file = new File(trainModel.getImagePATH());
        if (file.exists()) {
            file.delete();
        }
    }

    public void deleteSelectCheckBoxes() {
        if (images.size() > 0) {
            List<V3FetchTakenImagesListAdapetr.TrainModelData> tempList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).isSetChecked && images.get(i).isDeleted) {
                    tempList.add(images.get(i));
                }
            }
            String deletedImages = "";
            if (tempList.size() > 0) {
                for (int j = 0; j < tempList.size(); j++) {
                    deletedImages = deletedImages + tempList.get(j).getId() + ",";
                }
                if (!TextUtils.isEmpty(deletedImages)) {
                    deletedImages = deletedImages.substring(0, deletedImages.length() - 1);
                    System.out.println("deleteddddd " + deletedImages);
                    callDeleteMultipleImages(deletedImages, tempList);
                }
            }

            //images.removeAll(tempList);
            //  mAdapter.notifyDataSetChanged();
            // unSelectedCheckBoxData();
        }
    }

    public void unSelectedCheckBoxData() {
        if (images.size() > 0) {
            for (V3FetchTakenImagesListAdapetr.TrainModelData trainModelData : images) {
                trainModelData.setCheckBoxSelected(false);
                trainModelData.setSetChecked(false);
                trainModelData.setDeleted(false);
            }
            ivDelete.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void callMultipleImageUploadAPI(int position, String imagePath) {
        String base64Image = base64Image(imagePath);
        Call<MultipleImageModelResponce> call = ApiClient.getInstance().getApi().getMultipleImageUpload(
                StrConstants.API_VALUE, String.valueOf(V3PassAndFailActivity.modelID), base64Image,
                SharedPreferenceManager.getUserid(getApplicationContext()));
        call.enqueue(new Callback<MultipleImageModelResponce>() {
            @Override
            public void onResponse(Call<MultipleImageModelResponce> call, Response<MultipleImageModelResponce> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        if (response.body().getMessage().getStatusCode() == 200) {
                            images.get(position).setUploaded(true);
                            images.get(position).setId(response.body().getMessage().getGetMlModelImageId());
                            File file = new File(imagePath);
                            if (file.exists()) {
                                file.delete();
                            }
                            isUploading = false;
                            isCallingAPI = false;
                            callingAPI();
                            //mHandler.postDelayed(runnable, 1000);
                        } else {
                            isCallingAPI = false;
                            callingAPI();
                        }
                    } else {
                        isCallingAPI = false;
                        callingAPI();
                    }
                } else {
                    isCallingAPI = false;
                    callingAPI();
                }
            }

            @Override
            public void onFailure(Call<MultipleImageModelResponce> call, Throwable t) {
                isUploading = false;
                //  mHandler.postDelayed(runnable, 1000);
                callingAPI();
            }
        });
    }

    @Override
    public synchronized void onDestroy() {
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
    }

    public String base64Image(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        if (bm != null) {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
            String base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
            return base64Image;
        } else {
            return null;
        }
    }

    public void callingAPI() {
        if (!isCallingAPI) {
            for (int i = 0; i < images.size(); i++) {
                if (!images.get(i).isUploaded) {
                    isCallingAPI = true;
                    imagePath = images.get(i).getImagePATH();
                    imagePosition = i + "";
                    break;
                }
            }
            if (isCallingAPI) {
                btnAdd.setEnabled(false);
                // btnAdd.setBackground();
                //btnAdd.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.activity_title_color));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                File file = new File(imagePath);
                                if (file.exists()) {
                                    callMultipleImageUploadAPI(Integer.parseInt(imagePosition), imagePath);
                                } else {
                                    callingAPI();
                                }
                            }
                        });

                    }
                });
            } else {
                btnAdd.setEnabled(true);
                //btnAdd.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorDarkBlue));
            }
        }
    }

    public void callDeleteMultipleImages(String deleteImageList, List<V3FetchTakenImagesListAdapetr.TrainModelData> tempList) {
        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        Call<MultipleImageModelResponce> call = ApiClient.getInstance().getApi().deleteTrainingImages(StrConstants.API_VALUE, deleteImageList);
        call.enqueue(new Callback<MultipleImageModelResponce>() {
            @Override
            public void onResponse(Call<MultipleImageModelResponce> call, Response<MultipleImageModelResponce> response) {
                progressDialog.dismiss();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        if (response.body().getMessage().getStatusCode() == 200) {
                            images.removeAll(tempList);
                            mAdapter.notifyDataSetChanged();
                            unSelectedCheckBoxData();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MultipleImageModelResponce> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    public void callUpdateTrainingModelImagesAPI(String modelImages) {
        System.out.println("modelImages " + modelImages);
        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        Call<MultipleImageModelResponce> call = ApiClient.getInstance().getApi().updateTrainedModel(StrConstants.API_VALUE,
                modelImages, V3PassAndFailActivity.isPassAndFail ? "1" : "0");
        call.enqueue(new Callback<MultipleImageModelResponce>() {
            @Override
            public void onResponse(Call<MultipleImageModelResponce> call, Response<MultipleImageModelResponce> response) {
                progressDialog.dismiss();
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        if (response.body().getMessage().getStatusCode() == 200) {
                            // Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MultipleImageModelResponce> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240]; //specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }


            output64.close();


            encodedFile = output.toString();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (int i = 0; i < images.size(); i++) {
            if (!TextUtils.isEmpty(images.get(i).getImagePATH())) {
                File file = new File(images.get(i).getImagePATH());
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        finish();
    }

    public void deleteConfirmationPopUp() {
        AlertDialog alertDialog = new AlertDialog.Builder(V3FetchImagesListActivity.this).create();
        alertDialog.setTitle("Delete Image ");
        alertDialog.setMessage("This image will be permanently deleted and will no longer be used to train the Model");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSelectedItems();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alertpositivebuttoncolor));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alertnativebuttoncolor));
            }
        });
        alertDialog.show();
    }

    public void deleteSelectedItems() {
        if (images.size() > 0) {
            boolean isImageUploading = true;
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).isUploaded) {
                    if (images.get(i).isSetChecked) {
                        images.get(i).setDeleted(true);
                        TrainModel trainModel = new TrainModel();
                        trainModel.setId(images.get(i).getId());
                        trainModel.setImagePATH(images.get(i).getImagePATH());
                        deleteImagesFromLocal(trainModel, i);
                    }
                } else {
                    isImageUploading = false;
                    Toast.makeText(getApplicationContext(), "Please wait images uploading", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
            if (isImageUploading) {
                deleteSelectCheckBoxes();
            } else {
                unSelectedCheckBoxData();
            }
        }
    }
}