package com.v_chek_host.vcheckhost.V3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V3.adapters.PreviewImageAdapter;
import com.v_chek_host.vcheckhost.V3.models.V3MlModelDatasetResponce;

import java.util.ArrayList;
import java.util.List;

public class PreviewModelImageActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    List<V3MlModelDatasetResponce.Message> list;
    int position;
    List<String> urlList = new ArrayList<>();
    public PreviewImageAdapter previewImageAdapter;

    public ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            list = (ArrayList<V3MlModelDatasetResponce.Message>) bundle.getSerializable("listImages");
            position = bundle.getInt("urlPosition");
        }
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDatasetType() == 0) {
                    urlList.add(list.get(i).getMlModelImageUrl());
                }
            }
        }
        recyclerView = findViewById(R.id.recycler_view);
        ivBack = findViewById(R.id.ivBack);
        previewImageAdapter = new PreviewImageAdapter(urlList, getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(previewImageAdapter);
        if (urlList.size() > 0) {
            for (int i = 0; i < urlList.size(); i++) {
                if (urlList.get(i).equalsIgnoreCase(list.get(position).getMlModelImageUrl())) {
                    recyclerView.scrollToPosition(i);
                    break;
                }
            }
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}