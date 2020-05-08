package com.sstopin.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepsObjHolder>{

    private List<HashMap> mStepsObjList;
    final private RecyclerViewClickListener mOnClickListener;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }

    public RecipeStepsAdapter(Context mcontext, ArrayList<HashMap> recipeObjIn,
                              RecyclerViewClickListener listener){
        Context mContext=mcontext;
        mStepsObjList =recipeObjIn;
        mOnClickListener= (RecyclerViewClickListener) listener;
    }

    @Override
    public StepsObjHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.recipe_steps;
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
    }

    @Override
    public int getItemCount() {
        return mStepsObjList.size();
    }

    public class StepsObjHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tv_recipe_steps) TextView textView;

        public StepsObjHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.recyclerViewListClicked(view, clickedPosition);
        }
    }
}
