package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.DoorUserRelation_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class AddDoor_Activity  extends AppCompatActivity {
//도어락을 검색해서 등록할 수 있게 해야 한다.
    EditText[] editTexts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddoor);

        editTexts=new EditText[2];
        editTexts[0]=(EditText) findViewById(R.id.adddoor_id);
        editTexts[1]=(EditText) findViewById(R.id.adddoor_name);
    }
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.adddoor_submit_button:
                addDoorUser();
                break;
                default:break;


        }
    }

    private void addDoorUser() {
        try{
            OkHttp okHttp=new OkHttp();
            Gson gson=new Gson();
            DoorUserRelation_VO doorUserRelation_vo=new DoorUserRelation_VO();
            doorUserRelation_vo.setUser_id(Login_Activity.user_id);
            doorUserRelation_vo.setDoor_id(editTexts[0].getText().toString());
            doorUserRelation_vo.setDoor_name(editTexts[1].getText().toString());
            String[] params={"addDoorUser",gson.toJson(doorUserRelation_vo)};
            String result=okHttp.execute(params).get();
            if(!result.equals("success")) throw  new Exception();
            Toast.makeText(getApplicationContext(),"도어락을 등록하였습니다.",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this, Main_Activity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"도어락을 등록하지 못했습니다.",Toast.LENGTH_LONG).show();
        }

    }
}
