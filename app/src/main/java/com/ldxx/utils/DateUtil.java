package com.ldxx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.net.ParseException;

/**
 * <p>Title: [鏃堕棿宸ュ叿绫籡 </p>
 * <p>Description: [璇ョ被姒傝鍔熻兘浠嬬粛鎻忚堪]</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * @author <a href="mailto: [847353020@qq.com]">杈戒笢灏忓皬</a>
 * @update [淇敼浜篯 [淇敼鏃堕棿]
 * @version $Revision$
 */
public class DateUtil {
    public static final int DATE = 1;

    public static final int DATE_TIME = 2;

    /**
     * <p>Discription:閺冦儲婀℃潪顒�閹存劕鐡х粭锔胯</p>
     * @return
     * @author 鐠佹悂绠�2012-12-13
     * @update [娣囶喗鏁兼禍绡�鐠佹悂绠�[娣囶喗鏁奸弮鍫曟？]2012-12-13 [閸欐ɑ娲块幓蹇氬牚]
     */
    public static String dateToString(Date date) {

        String fmt = "yyyy-MM-dd";
        return dateToString(date, fmt);
    }

    /**
     * <p>Discription:閺冦儲婀℃潪顒佸床閹存劕鐡х粭锔胯</p>
     * @param date    鐟曚浇娴嗛幑銏㈡畱date
     * @param pattern 閺嶇厧绱�
     * @return
     * @author 閻滃宕�2013-2-28
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * Discription:鐎涙顑佹稉鑼舵祮閸栨牗鍨氶弮銉︽埂缁鐎�鐎涙顑佹稉鎻掕埌瀵繗顪厃yyy-MM-dd鐞涳拷
     * 
     * @param dateStr
     * @return
     * @author 鐠佹悂绠�2012-10-9
     * @update [娣囶喗鏁兼禍绡�鐠佹悂绠�[娣囶喗鏁奸弮鍫曟？]2012-10-9 [閸欐ɑ娲块幓蹇氬牚]
     */
    public static Date stringToDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return stringToDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * <p>Discription:鐎涙顑佹稉鑼舵祮閸栨牗鍨氶弮銉︽埂缁鐎�/p>
     * @param dateStr 鐟曚浇娴嗛惃鍕摟缁楋拷
     * @param pattern 閺冦儲婀￠弽鐓庣础
     * @return
     * @author 閻滃宕�2013-2-28
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static Date stringToDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * <p>Discription:閸掋倖鏌囨稉銈勯嚋閺冦儲婀￠弰顖氭儊閸︺劌鎮撴稉锟姐亯</p>
     * @param date1
     * @param date2
     * @return
     * @author 閻滃宕�2013-3-4
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static boolean dateInSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return true;
        }
        else {
            long i = 0l;
            if (date1.before(date2)) {
                i = date2.getTime() - date1.getTime();
            }
            else {
                i = date1.getTime() - date2.getTime();
            }
            if (i > 1000 * 60 * 60 * 24) {
                return false;
            }
            else {
                return true;
            }
        }
    }

    /**
     * <p>Discription:long缁鐎烽惃鍕）閺堢喕娴嗛幑銏″灇Date鐎电钖�/p>
     * @param l
     * @return
     * @author 閻滃宕�2013-2-26
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static Date longToDate(long l) {
        if (l == 0l) {
            return null;
        }
        else {
            return new Date(l);
        }
    }

    

    /**
     * <p>Discription:鏉╂柨娲栬ぐ鎾冲閻ㄥ嫭妞傞梻杈剧礄閸栧懎鎯堥弮璺哄瀻缁夋帪绱氱�妤冾儊娑擄拷/p>
     * @return
     * @author 閻滃宕�2013-4-11
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static String getCurrentDateTime() {
        Date d = Calendar.getInstance().getTime();
        return DateToString(d, DATE_TIME);
    }

    /**
     * <p>Discription:鏉╂柨娲栬ぐ鎾冲閺冨爼妫跨�妤冾儊娑撹绱濈�妤冾儊娑撹尙娈戦弽鐓庣础閼奉亜鐣炬稊锟�p>
     * @param pattern 鐎涙顑佹稉鍙夌壐瀵拷
     * @return
     * @author 閻滃宕�2013-8-29
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static String getCurrentDateTime(String pattern) {
        Date d = Calendar.getInstance().getTime();
        return dateToString(d, pattern);
    }

    /**
     * <p>Discription:閺傝纭堕崝鐔诲厴娑擃厽鏋冮幓蹇氬牚</p>
     * @param date
     * @param type
     * @return
     * @author 閻滃宕�2013-4-11
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static String DateToString(Date date, int type) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * <p>Discription:閼惧嘲褰囪ぐ鎾冲閺冨爼妫�/p>
     * @return
     * @author 閻滃宕�2013-4-11
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * <p>Discription:缂佹瑥鐣鹃弮鍫曟？閺勵垰鎯侀崷銊ョ秼閸撳秵妞傞梻瀵告畱N婢垛晛鍞�/p>
     * @param date  閹稿洤鐣鹃惃鍕闂傦拷
     * @param n     n婢垛晛鍞�
     * @return
     * @author 閻滃宕�2013-7-29
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static boolean dateWithInNDay(Date date, int n) {

        Calendar now = Calendar.getInstance();
        if (date.after(now.getTime())) {
            return false;
        }
        else {
            Calendar d = Calendar.getInstance();
            d.setTime(date);
            d.set(Calendar.DAY_OF_MONTH, d.get(Calendar.DAY_OF_MONTH) + n);
            if (dateInSameDay(now.getTime(), d.getTime())) {
                return true;
            }
            else {
                return d.after(now);
            }
        }
    }

    /**
     * Discription:鐎涙顑佹稉鑼舵祮閸栨牗鍨氶弮銉︽埂缁鐎�鐎涙顑佹稉鎻掕埌瀵繗顪厃yyy-MM-dd鐞涳拷
     * 
     * @param dateStr
     * @return
     * @author 鐠佹悂绠�2012-10-9
     * @update [娣囶喗鏁兼禍绡�鐠佹悂绠�[娣囶喗鏁奸弮鍫曟？]2012-10-9 [閸欐ɑ娲块幓蹇氬牚]
     */
    public static Date StringToDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        return StringToDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * <p>Discription:鐎涙顑佹稉鑼舵祮閸栨牗鍨氶弮銉︽埂缁鐎�/p>
     * @param dateStr 鐟曚浇娴嗛惃鍕摟缁楋拷
     * @param pattern 閺冦儲婀￠弽鐓庣础
     * @return
     * @author 閻滃宕�2013-2-28
     * @update [娣囶喗鏁兼禍绡�[娣囶喗鏁奸弮鍫曟？] [閸欐ɑ娲块幓蹇氬牚]
     */
    public static Date StringToDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * <p>Discription:鑾峰彇鍘诲勾鐨勫勾浠藉�</p>
     * @return
     * @author 杈戒笢灏忓皬 2013-11-28
     * @update [淇敼浜篯 [淇敼鏃堕棿] [鍙樻洿鎻忚堪]
     */
    public static String getPastYear(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return String.valueOf((year-1));
    }
    
    /**
     * <p>Discription:鑾峰彇褰撳墠骞翠唤</p>
     * @return
     * @author 杈戒笢灏忓皬 2013骞�1鏈�8鏃�
     * @update [淇敼浜篯 [淇敼鏃堕棿] [鍙樻洿鎻忚堪]
     */
    public static String getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR) + "";
    }
}
