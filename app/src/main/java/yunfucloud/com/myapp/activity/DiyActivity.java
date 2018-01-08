package yunfucloud.com.myapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import yunfucloud.com.myapp.R;
import yunfucloud.com.myapp.view.GranzortThreeView;

/**
 * Created by jingjing on 2017/11/30.
 */

public class DiyActivity extends AppCompatActivity {

    GranzortThreeView myView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);
        myView = (GranzortThreeView) findViewById(R.id.mview);
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.resetView();
            }
        });
    }

}
