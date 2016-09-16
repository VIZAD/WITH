package com.example.vizax.with.ui.Insist.decorators;

import com.example.vizax.with.ui.Insist.decorators_util.tickAnimeSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;


/**
 * Decorate a day by making the text big and bold
 */
public class ToDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    public ToDayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(final DayViewFacade view) {

                view.addSpan(new tickAnimeSpan());
        //view.addSpan(new RelativeSizeSpan(1.4f));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
    public void setDate(CalendarDay date) {
        this.date = date;
    }
}
