package ch.webvantage.nixonpi.util;

import android.os.Build;

/**
 * Created by dkummer on 15/07/15.
 */
public class EmulatorUtil {

    public static boolean isEmulator() {
        return Build.BRAND.startsWith("generic");
    }

}
