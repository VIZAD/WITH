package com.example.vizax.with.ui.Insist.decorators;

import android.content.Context;

import com.example.vizax.with.ui.Insist.decorators_util.tickAnimeSpan;
import com.example.vizax.with.ui.Insist.decorators_util.tickSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;


/**
 * Decorate several days with a dot
 */
public class EventDocDecorator  implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Context context;
    public EventDocDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5,color));
    }

}
