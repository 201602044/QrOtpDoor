package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.DoorUserRelation_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Management_Activity  extends AppCompatActivity {
    ImageView imageView;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    String door_id;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        intent=getIntent();
        door_id=intent.getStringExtra("door_id");

    }
    public  void  onClcik(View v){
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
        intent=new Intent(this,SharedQrCode_Activity.class);
        intent.putExtra("door_id",door_id);
        intent.putExtra("door_key",bitmap);
        startActivity(intent);
    }

    private void moveLocation() {
        intent=new Intent(this,Location_Activity.class);
        intent.putExtra("door_id",door_id);
        startActivity(intent);

    }

    private void requestKey() {
        String result=null;
        try{
            Gson gson=new Gson();
            DoorUserRelation_VO doorUserRelation_vo=new DoorUserRelation_VO();
            doorUserRelation_vo.setDoor_id(door_id);
            doorUserRelation_vo.setUser_id(Login_Activity.user_id);
            String[] params={"requestKey",gson.toJson(doorUserRelation_vo)};
            OkHttp okHttp=new OkHttp();
            result=okHttp.execute(params).get();
            doorUserRelation_vo=gson.fromJson(result,DoorUserRelation_VO.class);
            //key를 받아옴
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;
            qrgEncoder = new QRGEncoder(
                    doorUserRelation_vo.getDoor_key(),
                    null,
                    QRGContents.Type.TEXT,
                    smallerDimension
            );
            //크기 resize
            bitmap = qrgEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
            //이미지 생성
           /* boolean save = QRGSaver.save(savePath, door_id, bitmap, QRGContents.ImageType.IMAGE_JPEG);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if(!save) throw  new NotSavedQrCodeException();*/
        }
/*        catch (NotSavedQrCodeException e){
            Toast.makeText(getApplicationContext(),"QR코드를 저장하지 못했습니다.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"QR코드를 발급받지 못했습니다.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }
}

