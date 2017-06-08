package com.cinema.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xareen on 03.06.2017.
 */
public class DateConverter {

    public String convertDataToString(Timestamp timestamp){

        Date currentDate = new Date();
        SimpleDateFormat simpleDateTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM");



        Calendar currentDateCalendar = Calendar.getInstance();
        Calendar seanceDateCalendar = Calendar.getInstance();

        currentDateCalendar.setTime(currentDate);
        seanceDateCalendar.setTime(timestamp);


        if(currentDateCalendar.get(Calendar.DAY_OF_YEAR)==seanceDateCalendar.get(Calendar.DAY_OF_YEAR)){
            return "Dzisiaj, godz: " + simpleDateTime.format(seanceDateCalendar.getTime());
        }
        else if(currentDateCalendar.get(Calendar.DAY_OF_YEAR)+1 == seanceDateCalendar.get(Calendar.DAY_OF_YEAR)){
            return "Jutro, godz: " + simpleDateTime.format(seanceDateCalendar.getTime());
        }
        else if(timestamp.before(currentDate)){
            return "Zakończono- " + simpleDate.format(seanceDateCalendar.getTime());
        }
        else {
            return new SimpleDateFormat("EE").format(seanceDateCalendar.getTime()) + " "
                    + simpleDate.format(seanceDateCalendar.getTime()) + ", godz: "
                    + simpleDateTime.format(seanceDateCalendar.getTime());
        }

    }
}
