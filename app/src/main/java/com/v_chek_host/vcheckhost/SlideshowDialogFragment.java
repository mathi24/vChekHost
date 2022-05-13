package com.v_chek_host.vcheckhost;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private List<AllImagesDataModel.Message> images;
    private List<AllImagesDataModel.Message> miniMissedimages;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    private int imagesCount = 0;

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);

        images = (List<AllImagesDataModel.Message>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");
      //  MyApplication.countPageAdapter = images.size();
      //  System.out.println("AGS-count inside: "+MyApplication.countPageAdapter);

        Log.e(TAG, "AGS-count position: " + selectedPosition);
        Log.e(TAG, "AGS-count images size: " + images.size());
       // Log.e(TAG, "AGS-count countPageAdapter" + MyApplication.countPageAdapter);

        myViewPagerAdapter = new MyViewPagerAdapter();

        myViewPagerAdapter.addData(images);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);
        return v;
    }



    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }






    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + images.size());

//        String image = images.get(position).getCollectionImage();
        // lblTitle.setText(image.getName());
        // lblDate.setText(images.get(position).getCreatedOn());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //  adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        List<AllImagesDataModel.Message> images;

        public MyViewPagerAdapter() {

        }
        // Trying to add data dynamically
        public void addData(List<AllImagesDataModel.Message> images) {
            if(this.images==null){
                this.images=new ArrayList<>();
            }
            this.images.addAll(images);
            notifyDataSetChanged();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
            TextView txtUsername = (TextView) view.findViewById(R.id.txt_username);
            TextView txtPartsTag = (TextView) view.findViewById(R.id.txt_part_tag);
            TextView txtDamageTag = (TextView) view.findViewById(R.id.txt_damage_tag);
            TextView txtDate = (TextView) view.findViewById(R.id.txt_date);

            String image = images.get(position).getCollectionImage();
            //txtUsername.setText(SharedPreferenceManager.getFirstName(getContext()) + " " + SharedPreferenceManager.getLastName(getContext()));
            txtDate.setText(images.get(position).getCreatedOn());
            txtPartsTag.setText(images.get(position).getPartTag());
            txtDamageTag.setText(images.get(position).getDamageTag());


            Glide.with(getActivity()).load(image)
                    .thumbnail(0.5f)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
           // if (images.size() == 0) {
               // return MyApplication.countPageAdapter;
          //  }
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
