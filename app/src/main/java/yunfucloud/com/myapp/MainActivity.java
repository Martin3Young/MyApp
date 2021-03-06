package yunfucloud.com.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import yunfucloud.com.myapp.activity.CombineLatestActivity;
import yunfucloud.com.myapp.activity.DiyActivity;
import yunfucloud.com.myapp.activity.RulerActivity;
import yunfucloud.com.myapp.activity.ShoppingCarActivity;
import yunfucloud.com.myapp.activity.SlidingConflictActivity;

public class MainActivity extends AppCompatActivity {

    Button mCombineLatest;
    Button mSlidingConflict;
    Button diy;
    Button test;
    Button ruler;
    Button shoppingCar;
    long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        ButtonListener listener = new ButtonListener();
        mCombineLatest.setOnClickListener(listener);
        mSlidingConflict.setOnClickListener(listener);
        diy.setOnClickListener(listener);
        ruler.setOnClickListener(listener);
        test.setOnClickListener(listener);
        shoppingCar.setOnClickListener(listener);
    }

    private void initView() {
        mCombineLatest = (Button) findViewById(R.id.combineLatest);
        mSlidingConflict = (Button) findViewById(R.id.slidingconflict);
        diy = (Button) findViewById(R.id.diy);
        ruler = (Button) findViewById(R.id.ruler);
        test = (Button) findViewById(R.id.test);
        shoppingCar = (Button) findViewById(R.id.shopping_car);
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
                switch(v.getId()){
                case R.id.combineLatest:
                    //复杂表单验证
                    Intent combineLatest = new Intent(MainActivity.this,CombineLatestActivity.class);
                    startActivity(combineLatest);
                    break;
                case R.id.slidingconflict:
                    //滑动冲突
                    Intent slidingconflict = new Intent(MainActivity.this,SlidingConflictActivity.class);
                    startActivity(slidingconflict);
                    break;
                case R.id.diy:
                    //自定义view
                    Intent diy = new Intent(MainActivity.this,DiyActivity.class);
                    startActivity(diy);
                    break;
                case R.id.ruler:
                    Intent ruler = new Intent(MainActivity.this,RulerActivity.class);
                    startActivity(ruler);
                    break;
                case R.id.test:
                    Intent test = new Intent(MainActivity.this,TestActivity.class);
                    startActivity(test);
                    break;
                case R.id.shopping_car:
                    Intent shoppingcar = new Intent(MainActivity.this,ShoppingCarActivity.class);
                    startActivity(shoppingcar);
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - lastTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }

}
