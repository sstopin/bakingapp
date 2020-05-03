package com.sstopin.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeInfo implements Parcelable {
    private int mId;
    private String mName;
    private ArrayList<HashMap> mIngredientsArray = new ArrayList<HashMap>();
    private ArrayList<HashMap> mStepsArray = new ArrayList<HashMap>();
    private String mServings;
    private String mImage;

    public RecipeInfo(int id, String name, ArrayList<HashMap> ingredients,
                      ArrayList<HashMap> steps, String servings, String image) {
        this.mId = id;
        this.mName = name;
        this.mIngredientsArray = ingredients;
        this.mStepsArray = steps;
        this.mServings = servings;
        this.mImage = image;
    }

    protected RecipeInfo(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredientsArray = in.readArrayList(null);
        mStepsArray = in.readArrayList(null);
        mServings = in.readString();
        mImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeList(mIngredientsArray);
        dest.writeList(mStepsArray);
        dest.writeString(mServings);
        dest.writeString(mImage);
    }

    public static final Creator<RecipeInfo> CREATOR = new Creator<RecipeInfo>() {
        @Override
        public RecipeInfo createFromParcel(Parcel in) {
            return new RecipeInfo(in);
        }

        @Override
        public RecipeInfo[] newArray(int size) {
            return new RecipeInfo[size];
        }
    };

    public ArrayList<HashMap> getIngredientsArray() {
        return mIngredientsArray;
    }

    public void setIngredientsArray(ArrayList<HashMap> mIngredientsArray) {
        this.mIngredientsArray = mIngredientsArray;
    }

    public ArrayList<HashMap> getStepsArray() {
        return mStepsArray;
    }

    public void setStepsArray(ArrayList<HashMap> mStepsArray) {
        this.mStepsArray = mStepsArray;
    }

    public String getServings() {
        return mServings;
    }

    public void setServings(String mServings) {
        this.mServings = mServings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
