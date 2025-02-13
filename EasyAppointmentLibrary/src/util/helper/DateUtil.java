/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ivanlim
 */
public class DateUtil {
    
    public DateUtil() {
    }
  
    //this method returns you a Date object, if you want to keep only the date/time in db, set the temporal type
    public Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour); //following 24-hr time format
        cal.set(Calendar.MINUTE, minute);
        
        return cal.getTime();
    }
    
    public static String getSimpleDate(Date date) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
    
    public static String getSimpleTime(Date date) {
        String format = "HH:mm";
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(format);
        String strTime = simpleTimeFormat.format(date);
        return strTime;
    }
}
