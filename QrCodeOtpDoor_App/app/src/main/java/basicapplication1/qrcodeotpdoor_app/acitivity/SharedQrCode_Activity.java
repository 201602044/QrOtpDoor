package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.SharedUser_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class SharedQrCode_Activity  extends AppCompatActivity {
 EditText[] editTexts;
Intent intent;
String door_id;
String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedqrcode);
        editTexts=new EditText[2];
        editTexts[0]=(EditText) findViewById(R.id.shareduser_name);
        editTexts[1]=(EditText) findViewById(R.id.shareduser_phonenumeber);

        tedPermission();

        intent=getIntent();
        door_id=intent.getStringExtra("door_id");
        bitmap=(Bitmap)intent.getParcelableExtra("door_key");
    }
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.shareuser_submit:
                sharedUser();
                break;
                default:
                    break;

        }

    }

    private void sharedUser() {
        try {
            OkHttp okHttp=new OkHttp();
            Gson gson=new Gson();
            SharedUser_VO sharedUser_vo=new SharedUser_VO();
            sharedUser_vo.setUser_id(Login_Activity.user_id);
            sharedUser_vo.setShareduser_name(editTexts[0].getText().toString());
            sharedUser_vo.setShareduser_phone_number(editTexts[1].getText().toString());
            String[] params={"addSharedUser",gson.toJson(sharedUser_vo)};
            String result=okHttp.execute(params).get();
            if (!result.equals("success")) throw  new Exception();
            File imageFilleToStore=new File(savePath+door_id);
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.bignerdranch.android.test.fileprovider", imageFilleToStore);
           intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            startActivity(intent.createChooser(intent,"공유"));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"공유가 실패했습니다.",Toast.LENGTH_LONG).show();
        }
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
                .check();
    }
}
