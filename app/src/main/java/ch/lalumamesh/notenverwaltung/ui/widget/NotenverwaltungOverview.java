package ch.lalumamesh.notenverwaltung.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.concurrent.atomic.AtomicReference;

import ch.lalumamesh.notenverwaltung.R;
import ch.lalumamesh.notenverwaltung.repository.PruefungenRepository;
import ch.lalumamesh.notenverwaltung.service.PruefungenService;

public class NotenverwaltungOverview extends AppWidgetProvider {

    public NotenverwaltungOverview() {
    }

    private static void updateAppWidget(PruefungenService pruefungenService, Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notenverwaltung_overview);
        views.setTextViewText(R.id.widget_title, context.getString(R.string.widget_title));
        pruefungenService.loadPruefungen(pruefungList -> {
            double v = PruefungenService.calculateAverageOverall(pruefungList);
            String newValue = "Schnitt: "+ v + " -> " + PruefungenService.roundToHalf(v);
            views.setTextViewText(R.id.widget_content, newValue);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }, volleyError -> {
            views.setTextViewText(R.id.widget_content, "Fehler: " + volleyError.getMessage());
            appWidgetManager.updateAppWidget(appWidgetId, views);
        });

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        PruefungenService pruefungenService = new PruefungenService(new PruefungenRepository(context));
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(pruefungenService, context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

        super.onEnabled(context);
    }
}