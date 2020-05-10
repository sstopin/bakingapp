package com.sstopin.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViewsService;


public class WidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
