package com.cloud.smartcourseapp;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

/**
 * Created by xiaoxiao on 11/16/17.
 */

public class GpaFilter implements InputFilter{

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = dest.toString().substring(0, dStart) + dest.toString().substring(dEnd, dest.toString().length());
            newVal = newVal.substring(0, dStart) + source.toString() + newVal.substring(dStart, newVal.length());
            double input = Double.parseDouble(newVal);

            if (isInRange(0, 4, input)) {
                return null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }
    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
