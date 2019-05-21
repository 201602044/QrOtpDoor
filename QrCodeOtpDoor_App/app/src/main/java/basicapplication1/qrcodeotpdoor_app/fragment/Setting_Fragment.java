package basicapplication1.qrcodeotpdoor_app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.acitivity.Login_Activity;
import basicapplication1.qrcodeotpdoor_app.component.asynctask.OkHttp;
import basicapplication1.qrcodeotpdoor_app.component.item.User_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Setting_Fragment   extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    EditText[] editTexts;
    Context mContext;
    Button[] buttons;
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
        if (mPage == 2) {
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
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserInfo();
            }
        });


        return view;
    }
    public  void  onClick(View v){
        switch (v.getId()){
            case R.id.setting_change_passwd:
                changePasswd();
                break;
            case  R.id.setting_change_user_info:
                changeUserInfo();
                break;
            case  R.id.setting_delete_user_info:
                deleteUserInfo();
                    break;
                    default: break;
        }

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
       //미구현

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
}