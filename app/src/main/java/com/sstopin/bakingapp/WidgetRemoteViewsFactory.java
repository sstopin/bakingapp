package com.sstopin.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context context = null;
    private int appWidgetId;

    private List<String> widgetList = new ArrayList<String>();

    public WidgetRemoteViewsFactory(Context context, Intent intent)
    {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public int getCount()
    {
        return widgetList.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.ingredients_widget_item);

        remoteView.setTextViewText(R.id.ingredients_widget_item, widgetList.get(position));

        return remoteView;
    }

    @Override
    public int getViewTypeCount()
    {
        return widgetList.size();
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public void onCreate()
    {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String currentRecipeId = pref.getString("currentRecipeId", "Not Found");

        String ingredientsList = pref.getString(currentRecipeId, "No Ingredients");

        try {
            JSONArray ingredientsArrayJSON = new JSONArray(ingredientsList);
            if (ingredientsArrayJSON.length() > 0) {
                int numOfItems = ingredientsArrayJSON.length();
                String[] ingredientsDisplayList = new String[numOfItems];
                for (int i = 0; i < numOfItems; i++) {
                    String quantity = ingredientsArrayJSON.getJSONObject(i).optString("quantity");
                    String measure = ingredientsArrayJSON.getJSONObject(i).optString("measure");
                    String ingredient = ingredientsArrayJSON.getJSONObject(i).optString("ingredient");
                    ingredientsDisplayList[i] = ingredient + "\n" + quantity + " " + measure;
                }
                widgetList = Arrays.asList(ingredientsDisplayList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
//        widgetList.clear();
    }
}
