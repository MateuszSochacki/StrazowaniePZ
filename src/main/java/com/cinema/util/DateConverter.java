package com.cinema.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xareen on 03.06.2017.
 * A class that has been used to change the raw date into more friendly like saying "today", "tomorrow"
 * or "finished" instead of presenting numbers all the time.
 */
public class DateConverter {

    public String convertDataToString(Timestamp timestamp){

        Date currentDate = new Date(); //catching a current date
        SimpleDateFormat simpleDateTime = new SimpleDateFormat("HH:mm"); //declaring time format
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM"); //declaring date format



        Calendar currentDateCalendar = Calendar.getInstance(); //catching a current date
        Calendar seanceDateCalendar = Calendar.getInstance(); //catching a seance date

        currentDateCalendar.setTime(currentDate);
        seanceDateCalendar.setTime(timestamp);


        //show "today" if the current date is matching the seance date
        if(currentDateCalendar.get(Calendar.DAY_OF_YEAR)==seanceDateCalendar.get(Calendar.DAY_OF_YEAR)){
            return "Dzisiaj, godz: " + simpleDateTime.format(seanceDateCalendar.getTime());
        }
        //show "tomorrow" if the seance date is greater by 1 than the current date
        else if(currentDateCalendar.get(Calendar.DAY_OF_YEAR)+1 == seanceDateCalendar.get(Calendar.DAY_OF_YEAR)){
            return "Jutro, godz: " + simpleDateTime.format(seanceDateCalendar.getTime());
        }
        //show "finished" if the current date is greater than the seance date
        else if(timestamp.before(currentDate)){
            return "Zakończono- " + simpleDate.format(seanceDateCalendar.getTime());
        }
        //in every other case - show the date
        else {
            return new SimpleDateFormat("EE").format(seanceDateCalendar.getTime()) + " "
                    + simpleDate.format(seanceDateCalendar.getTime()) + ", godz: "
                    + simpleDateTime.format(seanceDateCalendar.getTime());
        }

    }
}
