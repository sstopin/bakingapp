package com.sstopin.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;



public class WidgetProvider extends AppWidgetProvider
{
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_layout);
        Intent intent = new Intent(context, WidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widgetListView, intent);
        views.setEmptyView(R.id.widgetListView, R.id.empty_view);


        Intent intentMain = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentMain, PendingIntent.FLAG_UPDATE_CURRENT);
 //       views.setOnClickPendingIntent(R.layout.ingredients_widget_item, pendingIntent);
        views.setPendingIntentTemplate(R.id.widgetListView, pendingIntent);

  //          views.setOnClickPendingIntent(R.id.empty_view, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context)
    {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context)
    {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
