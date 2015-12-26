package com.example.alexander.launchcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

/**
 * Created by alexander on 21.12.15.
 */
public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ListLauncher();
    }

    @Override
    public void onCreate(Bundle savedInstanseState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanseState);
    }
}
