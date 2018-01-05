package yunfucloud.com.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jingjing on 2017/12/5.
 */

public class TestActivity extends AppCompatActivity {

    TextView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        show = (TextView) findViewById(R.id.show);
        test();
    }

    private void test() {
        //拆箱，封箱，==，equal
//        String shows = "";
//        Integer a = 100;
//        Integer b = 100;
//        Integer c = 200;
//        Integer d = 200;
//        if (a==b){
//            shows = shows+"a=b,";
//        }
//        if (c==d){
//            shows = shows+"c=d,";
//        }
//        if (c.equals(d)){
//            shows = shows+"c.qual(d),";
//        }
//        show.setText(shows);

        List a;
        List b;
        List c;
        List d;



    }

}
