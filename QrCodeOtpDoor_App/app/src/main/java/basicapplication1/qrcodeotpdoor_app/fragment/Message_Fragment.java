package basicapplication1.qrcodeotpdoor_app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basicapplication1.qrcodeotpdoor_app.R;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Message_Fragment  extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
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