package com.julyyu.gankio_kotlin.desktop;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.julyyu.gankio_kotlin.R;
import com.julyyu.gankio_kotlin.model.Girl;
import com.julyyu.gankio_kotlin.service.GirlsKissService;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link GirlsAppWidgetConfigureActivity GirlsAppWidgetConfigureActivity}
 */
public class GirlsAppWidget extends AppWidgetProvider {

    static String girlPhone;
    static RemoteViews views;
    static AppWidgetManager girlWidgetManager;
    static int girlWidgetId;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        updateWidgetView(context,appWidgetManager,appWidgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
//        for (int appWidgetId : appWidgetIds) {
//            GirlsAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        if(TextUtils.equals(intent.getAction(),"takeGirl")){
            String girl = intent.getStringExtra("girl");
            girlPhone = girl;
            Glide.with(context)
                    .load(girl)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if(resource != null && resource.getByteCount() < 12441600){
                                Log.i("byte",resource.getByteCount() + "");
                                updateWidgetView(context,resource);
                            }
                        }
                    });
        }else if(TextUtils.equals(intent.getAction(),"kissGirl")){
            Toast.makeText(context,intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        }else if(TextUtils.equals(intent.getAction(),"refusePermission")){
            Toast.makeText(context,"应用存储权限未打开,妹子送不到相册", Toast.LENGTH_SHORT).show();
        }
    }
    private static void updateWidgetView(Context context,Bitmap resource){
        views = new RemoteViews(context.getPackageName(), R.layout.girls_app_widget);
        views.setImageViewBitmap(R.id.iv_girl,resource);
//        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_save);
//        DrawableCompat.setTintList(drawable,ColorStateList.valueOf(Color.BLACK));
//        views.setImageViewBitmap(R.id.iv_save, );
        Intent intent = new Intent(context, GirlsKissService.class);
        intent.putExtra("girl",girlPhone);
        intent.setAction(GirlsKissService.Companion.getTAKE_GIRL());
        intent.setPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_save,pendingIntent);

        Intent intent1 = new Intent(context, GirlsKissService.class);
        intent1.setAction(GirlsKissService.Companion.getCALL_GIRL());
        intent1.setPackage(context.getPackageName());
        PendingIntent pendingIntent1 = PendingIntent.getService(context,0,intent1,0);
        views.setOnClickPendingIntent(R.id.iv_fresh,pendingIntent1);
//        context.startService(intent1);
        // Instruct the widget manager to update the widget
        girlWidgetManager.updateAppWidget(girlWidgetId, views);
    }
    private static void updateWidgetView(Context context, AppWidgetManager appWidgetManager,
                                         int appWidgetId){
        //        CharSequence widgetText = GirlsAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        girlWidgetManager = appWidgetManager;
        girlWidgetId = appWidgetId;
        views = new RemoteViews(context.getPackageName(), R.layout.girls_app_widget);
//        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_save);
//        DrawableCompat.setTintList(drawable,ColorStateList.valueOf(Color.BLACK));
//        views.setImageViewBitmap(R.id.iv_save, );
        Intent intent = new Intent(context, GirlsKissService.class);
        intent.putExtra("girl",girlPhone);
        intent.setAction(GirlsKissService.Companion.getTAKE_GIRL());
        intent.setPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_save,pendingIntent);

        Intent intent1 = new Intent(context, GirlsKissService.class);
        intent1.setAction(GirlsKissService.Companion.getCALL_GIRL());
        intent1.setPackage(context.getPackageName());
        PendingIntent pendingIntent1 = PendingIntent.getService(context,0,intent1,0);
        views.setOnClickPendingIntent(R.id.iv_fresh,pendingIntent1);
        context.startService(intent1);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

