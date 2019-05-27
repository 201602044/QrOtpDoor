package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.User_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Login_Activity extends AppCompatActivity {
    EditText[] editTexts;
    CheckBox checkBox;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Intent intent;
    public static Activity login_Activity;
    public static String user_id ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tedPermission();
        editTexts=new EditText[2];
        editTexts[0]=(EditText)findViewById(R.id.login_user_id);
        editTexts[1]=(EditText)findViewById(R.id.login_user_passwd);
        checkBox=(CheckBox) findViewById(R.id.login_check);
        //값 초기화

        pref=getSharedPreferences("pref",MODE_PRIVATE);
        editor = pref.edit();
        intent = getIntent();

        if(intent.getBooleanExtra("logout",false)){
            editor.clear();
            editor.commit();
            return;
        }
        //로그아웃으로 들어온 거라면 저장된 사용자 정보를 삭제한다.

        loadAutoLogin();


    }
    public  void onClick(View v){
        switch (v.getId()){
            case  R.id.login_to_main:
                login();
                break;
            case  R.id.login_to_signup:
                moveSignUp();
                break;
                default:
                    break;
        }
    }
    public  void  login(){
        try {
            String result=null;
            OkHttp http=new OkHttp();
            User_VO user_vo=new User_VO();
            user_vo.setUser_id(editTexts[0].getText().toString());
            user_vo.setUser_passwd(editTexts[1].getText().toString());
            Gson gson=new Gson();
            String data=gson.toJson(user_vo);
            String[] params={"login",data};
            result =http.execute(params).get();
            http.cancel(true);
            //통신을 통해  아이디/비밀번호를 전달하고 받아온다
            if(result.equals("success")) {
                //로그인성공시
                user_id = user_vo.getUser_id();
                //로그인된 아이디 기입
                if (checkBox.isChecked()) {
                    //체크박스 키고 로그인 성공
                    if(!pref.getString("user_id","").equals(user_id) || !pref.getString("user_id","").equals("")) FirebaseInstanceId.getInstance().getToken();
                    //로그인시 파이어베이스에 새 토큰 전달(토큰=사용지아이디)
                    //저장된 id값이 없거나 새로운 값으로 로그인하지 않으면 로그인시 토큰전달을 안함.
                    //즉 자동로그인을 한 상태여야 메세지 전달을 받을 수 있다.!


                    editor.putString("user_id",user_vo.getUser_id());
                    editor.putString("user_passwd",user_vo.getUser_passwd());
                    editor.putBoolean("login_checkbox",true);
                    editor.commit();
                    //공유변수에 값 저장
                }
                else {
                    editor.clear();
                    editor.commit();
                }
                //체크박스 끄고 로그인
                intent = new Intent(this, Main_Activity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "로그인이 실패하였습니다" , Toast.LENGTH_LONG).show();
                editTexts[0].setText("");
                editTexts[1].setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //로그인을 하되,  체크박스의 값을 확인해서 그에따라 저장을 시켜둬야함

    public  void loadAutoLogin(){
        checkBox.setChecked(pref.getBoolean("login_checkbox",false));
        if(checkBox.isChecked()) {
            editTexts[0].setText(pref.getString("user_id",""));
            editTexts[1].setText(pref.getString("user_passwd",""));
            login(); //주석해제시 자동 로그인
        }
    }
    //시작시 pref에서 체큽ㄱ스에 값이 저장되있다면 불러와서 만들어 주야한다.
    public  void moveSignUp(){
        intent = new Intent(this, SMSReceiver_Activity.class);
        startActivity(intent);
    }
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissions(Manifest.permission.CAMERA)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .setPermissions(Manifest.permission.SEND_SMS)
                .setPermissions(Manifest.permission.VIBRATE)
                .check();
    }
}
