package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.Door_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Location_Activity   extends AppCompatActivity implements OnMapReadyCallback {
    String door_id;
    String door_langtitude,door_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Intent intent=getIntent();

        door_id=intent.getStringExtra("door_id");
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng location= new LatLng(37.56, 126.97);
        //37.56, 126.97 이게 default 서울값
        try{
            OkHttp okHttp=new OkHttp();
            Gson gson=new Gson();
            Door_VO door_vo=new Door_VO();
            door_vo.setDoor_id(door_id);
            String json=gson.toJson(door_vo);
            String[] params={"getDoorLocation",json};
            String result=okHttp.execute(params).get();
            door_vo=gson.fromJson(result,Door_VO.class);
            door_longitude=door_vo.getDoor_longitude();
            door_langtitude=door_vo.getDoor_latitude();
            location= new LatLng( Integer.parseInt(door_longitude),Integer.parseInt(door_langtitude));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"값을 불러오지 못했습니다.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("도어락");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
