package com.andy.flower.views.preference;

import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.flower.R;

/**
 * Created by andy on 16-6-21.
 */
public class BasePreference extends Preference {
    public BasePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BasePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BasePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePreference(Context context) {
        super(context);
    }

}
