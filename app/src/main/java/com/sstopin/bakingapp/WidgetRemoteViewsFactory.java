package com.sstopin.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context context = null;
    private int appWidgetId;

    private List<String> widgetList = new ArrayList<String>();

    public WidgetRemoteViewsFactory(Context context, Intent intent)
    {
 //       this.context = context;
 //       appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
 //               AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private void updateWidgetListView() throws JSONException {

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
//        try {
//            updateWidgetListView();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        Gson gson = new Gson();
//        String json = prefs.getString("IngredientsList", null);
//
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String[] widgetIngredientsArray = new String[jsonArray.length()];
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                widgetIngredientsArray[i] = jsonArray.getString(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        List<String> convertedToList = new ArrayList<String>(Arrays.asList(widgetIngredientsArray));
//        this.widgetList = convertedToList;
//
//
//        RemoteViews rv = new RemoteViews(context.getPackageName(),
//                R.layout.ingredients_widget_layout);
//
//        Intent intent = new Intent(context, WidgetService.class);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                appWidgetIds[i]);
//
//        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//        intent.setData(Uri.fromParts("content", String.valueOf(appWidgetIds[i]), null));
//
//        rv.setRemoteAdapter(R.id.widgetListView, intent);
//        rv.setEmptyView(R.id.widgetListView, R.id.empty_view);
//
//        appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.widgetListView);

    }

    @Override
    public void onDestroy()
    {
//        widgetList.clear();
    }
}
