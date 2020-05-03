package com.sstopin.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeObjHolder>{

    private List<RecipeInfo> mRecipeObjList;
    final private RecyclerViewClickListener mOnClickListener;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }

    public RecipeAdapter(Context mcontext, ArrayList<RecipeInfo> recipeObjIn,
                        RecyclerViewClickListener listener){
        Context mContext=mcontext;
        mRecipeObjList=recipeObjIn;
        mOnClickListener=listener;
    }

    @Override
    public RecipeObjHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForListItem = R.layout.recipe_list;
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeObjHolder objHolder = new RecipeObjHolder(view);

        return objHolder;
    }

    @Override
    public void onBindViewHolder(RecipeObjHolder holder, int position) {
        TextView textView = holder.textView;
        textView.setText(mRecipeObjList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeObjList.size();
    }

    public class RecipeObjHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView textView;

        public RecipeObjHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_recipe_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.recyclerViewListClicked(view, clickedPosition);
        }
    }
}
