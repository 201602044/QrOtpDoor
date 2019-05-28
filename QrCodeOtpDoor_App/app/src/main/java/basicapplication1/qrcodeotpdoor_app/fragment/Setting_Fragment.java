package basicapplication1.qrcodeotpdoor_app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.acitivity.Login_Activity;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.User_VO;
import basicapplication1.qrcodeotpdoor_app.component.view.KnowIndexOnClickListener;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Setting_Fragment   extends Fragment {
    private LinearLayout[] linearLayout;
    private  boolean[] change;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    EditText[] editTexts;
    Context mContext;
    Button[] buttons;
    TextView[] textViews;
    public Setting_Fragment(){

    }
    @SuppressLint("ValidFragment")
    public Setting_Fragment(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext=context;


    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = null;
        if (mPage == 3) {
            view = inflater.inflate(R.layout.fragment_setting, container, false);//fragment_page
        }
        editTexts=new EditText[4];
        editTexts[0]=(EditText) view.findViewById(R.id.setting_old_passswd);
        editTexts[1]=(EditText) view.findViewById(R.id.setting_new_passwd);
        editTexts[2]=(EditText) view.findViewById(R.id.setting_new_name);
        editTexts[3]=(EditText) view.findViewById(R.id.setting_new_phone_number);

        buttons=new Button[3];
        buttons[0]=(Button) view.findViewById(R.id.setting_change_passwd);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswd();
            }
        });
        buttons[1]=(Button) view.findViewById(R.id.setting_change_user_info);

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserInfo();
            }
        });
        buttons[2]=(Button) view.findViewById(R.id.setting_delete_user_info);
        registerForContextMenu(buttons[2]);
        /*buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserInfo();
            }
        });*/
        linearLayout=new LinearLayout[3];
        linearLayout[0]=(LinearLayout) view.findViewById(R.id.setting_list_1);
        linearLayout[1]=(LinearLayout) view.findViewById(R.id.setting_list_2);
        linearLayout[2]=(LinearLayout) view.findViewById(R.id.setting_list_3);
        change=new boolean[3];
        Arrays.fill(change,false);
        textViews=new TextView[3];
        textViews[0]=(TextView) view.findViewById(R.id.setting_click_change_passwd);
        textViews[1]=(TextView) view.findViewById(R.id.setting_click_change_info);
        textViews[2]=(TextView) view.findViewById(R.id.setting_click_delete_info);
        for(int  i=0 ; i<textViews.length;i++){
            textViews[i].setOnClickListener(new KnowIndexOnClickListener(i) {
                @Override
                public void onClick(View view) {
                    if(change[index] == true ) {
                        linearLayout[index].setVisibility(View.GONE);
                        textViews[index].setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_expand_more_black_36dp,0);
                        change[index]=false;
                        return;
                    }
                    for(int i=0;i<3;i++){
                        if(change[i]==true){
                            linearLayout[i].setVisibility(View.GONE);
                            textViews[i].setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_expand_more_black_36dp,0);
                            change[i]=false;
                        }
                    }
                    linearLayout[index].setVisibility(View.VISIBLE);
                    textViews[index].setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_expand_less_black_36dp,0);
                    change[index]=true;
                }
            });

        }
       /* FloatingActionButton button=(FloatingActionButton) view.findViewById(R.id.setting_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/
        return view;
    }

    private void deleteUserInfo() {
        try {
            OkHttp okHttp=new OkHttp();
            Gson gson=new Gson();
            String[] params={"deleteUser", Login_Activity.user_id};
            String result=okHttp.execute(params).get();
            okHttp.cancel(true);
            if(!result.equals("success")) throw new Exception();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),"삭제가 정상적으로 이루어지지 않았습니다.",Toast.LENGTH_LONG).show();
        }



    }

    private void changePasswd() {
        try {
            OkHttp okHttp=new OkHttp();
            Gson gson=new Gson();
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("user_id",Login_Activity.user_id);
            jsonObject.addProperty("user_passwd",editTexts[0].getText().toString());
            jsonObject.addProperty("user_re_passwd",editTexts[1].getText().toString());
            String[] params={"changePasswd", gson.toJson(jsonObject)};
            String result=okHttp.execute(params).get();
            okHttp.cancel(true);
            if(!result.equals("success")) throw new Exception();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),"변경이 정상적으로 이루어지지 않았습니다.",Toast.LENGTH_LONG).show();
        }
    }

    private void changeUserInfo() {
        try {
            OkHttp okHttp=new OkHttp();
            Gson gson=new Gson();
            User_VO user_vo=new User_VO();
            user_vo.setUser_id(Login_Activity.user_id);
            user_vo.setUser_name(editTexts[2].getText().toString());
            user_vo.setUser_phone_number(editTexts[3].getText().toString());
            String[] params={"deleteUser", Login_Activity.user_id};
            String result=okHttp.execute(params).get();
            okHttp.cancel(true);
            if(!result.equals("success")) throw new Exception();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),"변경이 정상적으로 이루어지지 않았습니다.",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,  ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, v,menuInfo);

        menu.setHeaderTitle("정말로 삭제하시겠습니까?");

        menu.add(0,1,0,"Yes");

        menu.add(0,2,0,"No");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
               deleteUserInfo();
                return true;
            case 2:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}