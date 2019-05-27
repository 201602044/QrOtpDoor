package basicapplication1.qrcodeotpdoor_app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.acitivity.Login_Activity;
import basicapplication1.qrcodeotpdoor_app.acitivity.Management_Activity;
import basicapplication1.qrcodeotpdoor_app.adapter.DoorList_Adapter;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.DoorUserRelation_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class DoorList_Fragment  extends Fragment {
    private  Activity activity;
    private  Context mContext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    ListView listView;
    DoorList_Adapter doorList_adapter;
    Intent intent;
    View view;
    public DoorList_Fragment(){

    }
    @SuppressLint("ValidFragment")
    public DoorList_Fragment(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);

    }
//기본 생성자, inflator를 통해 프래그먼트들을 확장하는 역할을 맡는다 .
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    private void loadDoorList() {
        try{
            Gson gson=new Gson();
            OkHttp okHttp=new OkHttp();
            String[] params ={"getDoorUserInfo", Login_Activity.user_id};
            String result=okHttp.execute(params).get();
            okHttp.cancel(true);
            DoorUserRelation_VO[] doorUserRelation_vos=gson.fromJson(result,DoorUserRelation_VO[].class);
            doorList_adapter=new DoorList_Adapter();
            listView.setAdapter(doorList_adapter);
            for(DoorUserRelation_VO doorUserRelation_vo:doorUserRelation_vos){
                doorList_adapter.addItem(doorUserRelation_vo);

            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent =new Intent(mContext,Management_Activity.class);
                    intent.putExtra("door_id", doorUserRelation_vos[i].getDoor_id());
                    intent.putExtra("door_name",doorUserRelation_vos[i].getDoor_name());
                    startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mPage == 0) {
            view = inflater.inflate(R.layout.fragment_doorlist, container, false);//fragment_page
        }

        listView = (ListView) view.findViewById(R.id.doorlist_listview);
        loadDoorList();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

            mContext=context;


    }

}