package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.User_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class SignUp_Activity   extends AppCompatActivity {
    EditText[] editTexts;
    LinearLayout button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTexts=new EditText[5];
        editTexts[0]=(EditText) findViewById(R.id.signup_user_id);
        editTexts[1]=(EditText) findViewById(R.id.signup_user_passwd);
        editTexts[2]=(EditText) findViewById(R.id.signup_user_re_passwd);
        editTexts[3]=(EditText) findViewById(R.id.signup_user_name);
        editTexts[4]=(EditText) findViewById(R.id.signup_user_phonenumber);
        //값 초기화
      /*  button=(LinearLayout) findViewById(R.id.signup_recaptchabutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SafetyNet.getClient(getApplicationContext()).verifyWithRecaptcha("6LeF06QUAAAAAJB61nLmFE2gs7YVkjMXa-3OqsX9")
                        .addOnSuccessListener((Executor) getApplicationContext(),
                                new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                        // Indicates communication with reCAPTCHA service was
                                        // successful.
                                        String userResponseToken = response.getTokenResult();
                                        if (!userResponseToken.isEmpty()) {
                                            // Validate the user response token using the
                                            // reCAPTCHA siteverify API.
                                        }
                                    }
                                })
                        .addOnFailureListener((Executor) getApplicationContext(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    // An error occurred when communicating with the
                                    // reCAPTCHA service. Refer to the status code to
                                    // handle the error appropriately.
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.d("", "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else {
                                    // A different, unknown type of error occurred.
                                    Log.d("", "Error: " + e.getMessage());
                                }
                            }
                        });
            }
        });*/
//      리캡챠 쓸꺼면 추가해서 써야한다.
    }
    public  void  onClck(View v){
        switch (v.getId()){
            case  R.id.signup_addUser:
                addUser();
                break;
                default:
                    break;
        }
    }
    public  void  addUser(){
        try {
            if(isRightForm()==false) return;
            //폼 형식 검사
            String result = null;
            OkHttp http = new OkHttp();
            User_VO user_vo = new User_VO();
            user_vo.setUser_id(editTexts[0].getText().toString());
            user_vo.setUser_passwd(editTexts[1].getText().toString());
            user_vo.setUser_name(editTexts[3].getText().toString());
            user_vo.setUser_phone_number(editTexts[4].getText().toString());
            Gson gson = new Gson();
            String data = gson.toJson(user_vo);
            String[] params = {"addUser", data};
            result = http.execute(params).get();
            http.cancel(true);
            //통신을 통해  아이디/비밀번호를 전달하고 받아온다
            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(),"회원가입이 성공하였습니다! 로그인하세요",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this, Login_Activity.class);
                startActivity(intent);
                finish();
                //파베 토큰때문에 로그인창으로 보내서 로그인하게해야한다.

            }
        }catch (Exception e){
                e.printStackTrace();
        }

    }

    private boolean isRightForm() {
        for(int i=0 ;i<editTexts.length;i++){
            //sms 인증번호 제외 , 나중에는 확인
            if(editTexts[i].getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"공백이 있습니다.",Toast.LENGTH_LONG).show();
                return false;
            }
        }//공백제외
        if(editTexts[1].getText().toString().equals(editTexts[2].getText().toString())) {
            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
            return false;
        }
        //비밀번호 확인
        return true;
    }
}
