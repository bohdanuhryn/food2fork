package com.bohdanuhryn.food2fork.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by BohdanUhryn on 08.02.2016.
 */
public class ViewUtils {

    public static EditText getEditText(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    View editText = getEditText(context, child);

                    if (editText instanceof EditText) {
                        return (EditText) editText;
                    }
                }
            } else if (v instanceof EditText) {
                Log.d("ViewUtils", "found edit text");
                return (EditText) v;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
