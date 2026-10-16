package dev.tinelix.irc.android.legacy;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Global {
    @SuppressLint("SimpleDateFormat")
    public static String formatTimestamp(Context ctx, long time, String pattern) {
        String strftime;
        Date dt = new Date(time);
        long currentTime = Calendar.getInstance().getTime().getTime();
        Date dt_midnight = new Date(currentTime + 86400);

        dt_midnight.setHours(0);
        dt_midnight.setMinutes(0);
        dt_midnight.setSeconds(0);

        if(pattern == null) {
            if ((dt_midnight.getTime() - time) < 86400) {
                strftime = String.format("%s %s", ctx.getResources().getString(R.string.today_at),
                        new SimpleDateFormat("HH:mm").format(dt));
            } else if ((dt_midnight.getTime() - time) < (86400000L)) { // one day = 86400 seconds
                strftime = String.format("%s %s", ctx.getResources().getString(R.string.yesterday_at),
                        new SimpleDateFormat("HH:mm").format(dt));
            } else if ((dt_midnight.getTime() - time) < 3153600000L) { // one year = 365 days = 3,153,600 seconds
                strftime = String.format("%s %s %s", new SimpleDateFormat("d MMMM").format(dt),
                        ctx.getResources().getString(R.string.date_at),
                        new SimpleDateFormat("HH:mm").format(dt));
            } else {
                strftime = String.format("%s %s %s", new SimpleDateFormat("d MMMM yyyy").format(dt),
                        ctx.getResources().getString(R.string.date_at),
                        new SimpleDateFormat("HH:mm").format(dt));
            }
        } else {
            strftime = new SimpleDateFormat(pattern).format(dt);
        }
        return strftime;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[value >>> 4];
            hexChars[i * 2 + 1] = hexArray[value & 0x0F];
        }

        return new String(hexChars);
    }
}
