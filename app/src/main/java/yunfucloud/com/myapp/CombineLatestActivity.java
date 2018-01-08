package yunfucloud.com.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;
import yunfucloud.com.myapp.utils.MyUtil;

/**
 * Created by jingjing on 2017/11/30.
 */

public class CombineLatestActivity extends AppCompatActivity {

    EditText phone;
    EditText email;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combinelatest);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        login = (Button) findViewById(R.id.login);
        bindViewByRxBinding();
        initClick();
    }

    private void bindViewByRxBinding() {
        Observable<CharSequence> ObservableEmail = RxTextView.textChanges(email);
        Observable<CharSequence> ObservablePhone = RxTextView.textChanges(phone);
        Observable.combineLatest(ObservableEmail, ObservablePhone, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence email, CharSequence phone) {
                return MyUtil.isEmailValid(email.toString())&&MyUtil.isPhone(phone.toString());
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean verify) {
                if (verify) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                }
            }
        });

    }

    private void initClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CombineLatestActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
