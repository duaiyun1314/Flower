package com.andy.flower.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.andy.flower.R;

/**
 * Created by andy on 16-6-6.
 */
public class LoginActivity extends BaseToolBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        setTitle(R.string.login_label);
        setContentView(R.layout.activity_login);

    }

}
