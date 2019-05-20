package basicapplication1.qrcodeotpdoor_app.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.acitivity.Login_Activity;
import basicapplication1.qrcodeotpdoor_app.adapter.MessageList_Adapter;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.Message_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Message_Fragment  extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    ListView listView;
    MessageList_Adapter messageList_adapter;
    Intent intent;
    public Message_Fragment(){

    }
    @SuppressLint("ValidFragment")
    public Message_Fragment (int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        listView= (ListView) getView().findViewById(R.id.message_listview);
        loadMsgList();
    }

    private void loadMsgList() {
        try{
            Gson gson=new Gson();
            OkHttp okHttp=new OkHttp();
            String[] params ={"getDoorUserInfo", Login_Activity.user_id};
            String result=okHttp.execute(params).get();
            Message_VO[] message_vos=gson.fromJson(result,Message_VO[].class);

            listView.setAdapter(messageList_adapter);
            for(Message_VO  message_vo:message_vos){
                messageList_adapter.addItem(message_vo);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = null;
        if (mPage == 1) {
            view = inflater.inflate(R.layout.fragment_message, container, false);//fragment_page
        }
        return view;
    }
}