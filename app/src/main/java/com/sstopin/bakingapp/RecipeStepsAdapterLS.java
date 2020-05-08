package com.sstopin.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapterLS extends RecyclerView.Adapter<RecipeStepsAdapterLS.StepsObjHolder>{

    private ArrayList<HashMap> mStepsObjList;
    final private RecyclerViewClickListener mOnClickListener;
    private int index;
    private Context mContext;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private FragmentTransaction transactionVideo;
    private FragmentManager fragmentManagerVideo;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }

    public RecipeStepsAdapterLS(Context context, ArrayList<HashMap> stepsArray,
                                RecipeAdapter.RecyclerViewClickListener listener){
        mContext = context;
        mStepsObjList =stepsArray;
        mOnClickListener= (RecyclerViewClickListener) listener;
    }

    @Override
    public StepsObjHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        fragmentManagerVideo = ((AppCompatActivity)context).getSupportFragmentManager();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.recipe_list;
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepsObjHolder objHolder = new StepsObjHolder(view);

        return objHolder;
    }

    @Override
    public void onBindViewHolder(StepsObjHolder holder, int position) {
        TextView textView = holder.textView;
        HashMap hashMap = mStepsObjList.get(position);
        textView.setText((String) hashMap.get("shortDescription"));
        String videoUrl = (String) hashMap.get("videoURL");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();

                transaction = fragmentManager.beginTransaction();
                FragmentRecipeDescription fragmentDesc = new FragmentRecipeDescription()
                         .newInstance(hashMap);
                transaction.replace(R.id.fl_recipe_step, fragmentDesc).commit();
                fragmentDesc.setRetainInstance(true);


                transactionVideo = fragmentManagerVideo.beginTransaction();
                if (!(videoUrl.equals(""))) {
                    FragmentMedia workFragment = new FragmentMedia()
                            .newInstance(videoUrl, true);
                    workFragment.setRetainInstance(true);
            //        transactionVideo.detach(workFragment).attach(workFragment);
                    transactionVideo.replace(R.id.fl_video_container, workFragment);
                } else {
                    FragmentNoVideo workFragment = new FragmentNoVideo().newInstance(true);
                    workFragment.setRetainInstance(true);
            //        transactionVideo.detach(workFragment).attach(workFragment);
                    transactionVideo.replace(R.id.fl_video_container, workFragment);
                }
                transactionVideo.commit();
            }
        });

        if (index == position) {
            textView.setBackgroundColor(Color.parseColor("#898989"));
        } else {
            textView.setBackgroundColor(Color.parseColor("#e39400"));
        }
    }

    @Override
    public int getItemCount() {
        return mStepsObjList.size();
    }

    public class StepsObjHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recipe_name) TextView textView;
        LinearLayout linearLayout;

        public StepsObjHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            linearLayout = itemView.findViewById(R.id.ll_recipe_fragment);
        }
    }
}
