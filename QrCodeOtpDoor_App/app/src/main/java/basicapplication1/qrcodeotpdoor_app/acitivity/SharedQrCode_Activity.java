package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import basicapplication1.qrcodeotpdoor_app.BuildConfig;
import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.SharedUser_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class SharedQrCode_Activity  extends AppCompatActivity {
 EditText[] editTexts;
Intent intent;
String door_id,door_name;
String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedqrcode);
        editTexts=new EditText[2];
        editTexts[0]=(EditText) findViewById(R.id.shareduser_name);
        editTexts[1]=(EditText) findViewById(R.id.shareduser_phonenumeber);



        intent=getIntent();
        door_id=intent.getStringExtra("door_id");
        door_name=intent.getStringExtra("door_name");
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
            File imageFilleToStore=new File(savePath+door_name);
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID, imageFilleToStore);
           intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/*");
            startActivity(intent.createChooser(intent,"공유"));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"공유가 실패했습니다.",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_home:
                intent = new Intent(this, Main_Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_logout:
                intent = new Intent(this, Login_Activity.class);
                intent.putExtra("logout", true);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_before:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
