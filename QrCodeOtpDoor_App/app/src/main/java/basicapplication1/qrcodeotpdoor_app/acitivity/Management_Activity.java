package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.DoorUserRelation_VO;
import basicapplication1.qrcodeotpdoor_app.exception.NotSavedQrCodeException;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Management_Activity  extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    String door_id,door_name;
    Intent intent;
    QRCodeWriter qrCodeWriter;
    Button button;
    ProgressBar progressBar;

    private TextView timer_text;
    private TimerTask second;
    private final Handler handler = new Handler();
    private  int timer_sec,miniute,count;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        intent=getIntent();
        door_id=intent.getStringExtra("door_id");
        door_name=intent.getStringExtra("door_name");
        imageView=(ImageView) findViewById(R.id.management_door_key_image);
        button=(Button)findViewById(R.id.management_request_key);

        Glide.with(this)
                .load(Environment.getExternalStorageDirectory().getPath() + "/QRCode/"+door_name)
                .into(imageView);

    }
    public  void  onClick(View v){
        switch (v.getId()){
            case R.id.management_request_key:
                requestKey();
                break;
            case  R.id.management_to_location:
                moveLocation();
                break;
            case  R.id.management_to_share:
                shareKey();
                break;
                default: break;
        }
    }

    private void shareKey() {
        if(bitmap==null) {
            Toast.makeText(getApplicationContext(),"공유할 QR코드가 없습니다.",Toast.LENGTH_LONG).show();
            return;
        }

        intent=new Intent(this,SharedQrCode_Activity.class);
        intent.putExtra("door_name",door_id);
        startActivity(intent);
    }

    private void moveLocation() {
        intent=new Intent(this,Location_Activity.class);
        intent.putExtra("door_id",door_id);
        startActivity(intent);

    }

    private void requestKey() {
        String result=null;
        try {
            Gson gson = new Gson();
            DoorUserRelation_VO doorUserRelation_vo = new DoorUserRelation_VO();
            doorUserRelation_vo.setDoor_id(door_id);
            doorUserRelation_vo.setUser_id(Login_Activity.user_id);
            String[] params = {"requestKey", gson.toJson(doorUserRelation_vo)};
            OkHttp okHttp = new OkHttp();
            result = okHttp.execute(params).get();
            okHttp.cancel(true);
            doorUserRelation_vo = gson.fromJson(result, DoorUserRelation_VO.class);
            //key를 받아옴
            qrCodeWriter = new QRCodeWriter();
            bitmap = toBitmap(qrCodeWriter.encode(doorUserRelation_vo.getDoor_key(), BarcodeFormat.QR_CODE, 300, 300));
            imageView.setImageBitmap(bitmap);
            timeStart();
            button.setText("재발급");
            if(!SaveBitmapToFileCache(bitmap,savePath,door_name)) throw new NotSavedQrCodeException();
        }
        catch (NotSavedQrCodeException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"QR코드를 저장하지 못했습니다.",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"QR코드를 발급받지 못했습니다.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
    public  boolean SaveBitmapToFileCache(Bitmap bitmap, String strFilePath, String filename) {

        File file = new File(strFilePath);
        if (!file.exists())

            file.mkdirs();

        File fileCacheItem = new File(strFilePath + filename);

        OutputStream out = null;

        try {

            fileCacheItem.createNewFile();

            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return  true;
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }
            return  false;
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

    public void timeStart() {
        if(timer!=null)timer.cancel();
        timer_text = (TextView) findViewById(R.id.management_timer);
        progressBar=(ProgressBar) findViewById(R.id.management_progressbar);
        progressBar.setProgress(60);
        timer_sec = 60;
        second = new TimerTask() {

            @Override
            public void run() {
                Update();
                timer_sec--;
            }
        };
        timer = new Timer();
        timer.schedule(second, 0, 1000);

    }

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                timer_text.setText(String.format("%02d",timer_sec/60)+":"+String.format("%02d",timer_sec%60)+"초"); //텍스트쓰레드
                progressBar.setProgress(timer_sec);
                if(timer_sec==0) timer.cancel();
            }
        };
        handler.post(updater);
    }
}


