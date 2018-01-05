package yunfucloud.com.myapp.utils;

/**
 * Created by jingjing on 2017/11/30.
 */

public class MyUtil {
    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * 判断手机号
     * @param phone  手机号码
     */
    public static boolean isPhone(String phone){
        String telRegex = "[1][34578]\\d{9}";
        return phone.matches(telRegex);
    }

}
