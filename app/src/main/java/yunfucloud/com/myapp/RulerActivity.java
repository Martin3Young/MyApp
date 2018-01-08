package yunfucloud.com.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import yunfucloud.com.myapp.view.RulerView;

/**
 * Created by Martin on 2018/1/8.
 *
 * @新浪微博: http://weibo.com/2603687001
 * @GitHub: https://github.com/Martin3Young
 * @CSDN: http://blog.csdn.net/qq_32346021
 * @简书: http://www.jianshu.com/u/6d64225b1910
 */

public class RulerActivity extends AppCompatActivity {

    RulerView mRuler;
    TextView amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
        mRuler = (RulerView) findViewById(R.id.ruler);
        amount = (TextView) findViewById(R.id.amount);
        initView();
    }

    private void initView() {
        mRuler.setMaxValue(3000);
        mRuler.setCurrentValue(1500);
        amount.setText(mRuler.getCurrentValue()+"");
        mRuler.setScrollingListener(new RulerView.OnRulerViewScrollListener<String>() {
            @Override
            public void onChanged(RulerView rulerView, String oldValue, String newValue) {
                amount.setText(newValue);
            }

            @Override
            public void onScrollingStarted(RulerView rulerView) {

            }

            @Override
            public void onScrollingFinished(RulerView rulerView) {

            }
        });
    }

}
