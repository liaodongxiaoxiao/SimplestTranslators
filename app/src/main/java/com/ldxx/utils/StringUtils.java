package com.ldxx.utils;

public class StringUtils {
    /**
     *
     * @param str
     * @return
     */
	public static boolean isEmpty(String str) {
		if (null == str) {
			return true;
		}
		str = str.trim();
		if (str.length() > 0) {
			return false;
		}
		return true;
	}

    /**
     *
     * @param num
     * @return
     */
	public static String getFullBallNum(String num) {
		if (isEmpty(num)) {
			return "";
		} else if (num.length() > 1) {
			return num;
		} else {

			int i = Integer.parseInt(num);
			if (i < 10) {
				return "0" + i;
			} else {
				return num;
			}
		}
	}

	public static String trim(String str) {
		if (isEmpty(str) || str.length() <= 2) {
			return str;
		} else {
			return str.substring(1, str.length() - 1);
		}

	}

    public static String removeDoubleQuotation(String str){

        if(!isEmpty(str)&&(str.contains("\"")||str.contains("“")||str.contains("”"))){
            str = str.replaceAll("\"","");
            str = str.replaceAll("“","");
            str = str.replaceAll("”","");
        }
        return str;
    }
}
