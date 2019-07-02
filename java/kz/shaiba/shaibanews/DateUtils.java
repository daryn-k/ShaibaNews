package kz.shaiba.shaibanews;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    static long unixSeconds;
    static Date dateOfNews;

    static SimpleDateFormat sdf;
    static SimpleDateFormat sdf_year;
    static SimpleDateFormat sdf_dayofyear;
    static SimpleDateFormat sdf_HHmm;

    static DateFormatSymbols russSymbol;

    static Calendar calendar_today;
    static Calendar calendar_yesterday;

    static String formattedDate;
    static String year;
    static String day_of_year;
    static String year_today;
    static String day_of_year_today;
    static String year_yesterday;
    static String day_of_year_yesterday;

    public static String dateFromUNIXtoString(String unixTimestamp){

        String[] russianMonat = {
                "января",
                "февраля",
                "марта",
                "апреля",
                "мая",
                "июня",
                "июля",
                "августа",
                "сентября",
                "октября",
                "ноября",
                "декабря"
        };

        russSymbol = new DateFormatSymbols();
        russSymbol.setMonths(russianMonat);

        unixSeconds = Long.parseLong(unixTimestamp);
        dateOfNews = new Date(unixSeconds*1000L);
        sdf = new SimpleDateFormat("d MMMM, в HH:mm", russSymbol);
        sdf_year =  new SimpleDateFormat("y");
        sdf_dayofyear = new SimpleDateFormat("D");
        sdf_HHmm = new SimpleDateFormat("HH:mm");
        formattedDate = sdf.format(dateOfNews);

        calendar_today = Calendar.getInstance();
        year_today = calendar_today.get(Calendar.YEAR) + "";
        day_of_year_today = calendar_today.get(Calendar.DAY_OF_YEAR) + "";

        calendar_yesterday = Calendar.getInstance();
        calendar_yesterday.add(Calendar.DATE, -1);
        year_yesterday = calendar_yesterday.get(Calendar.YEAR) + "";
        day_of_year_yesterday = calendar_yesterday.get(Calendar.DAY_OF_YEAR) + "";

        year = sdf_year.format(dateOfNews);
        day_of_year = sdf_dayofyear.format(dateOfNews);

        if(year.equalsIgnoreCase(year_today) & day_of_year.equalsIgnoreCase(day_of_year_today)){
            formattedDate = "сегодня, в " + sdf_HHmm.format(dateOfNews);
        }
        else if(year.equalsIgnoreCase(year_yesterday) & day_of_year.equalsIgnoreCase(day_of_year_yesterday)){
            formattedDate = "вчера, в " + sdf_HHmm.format(dateOfNews);
        }

        return formattedDate;
    }


}
