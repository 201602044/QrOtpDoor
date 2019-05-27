package basicapplication1.qrcodeotpdoor_app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.fragment.DoorList_Fragment;
import basicapplication1.qrcodeotpdoor_app.fragment.History_Fragment;
import basicapplication1.qrcodeotpdoor_app.fragment.Message_Fragment;
import basicapplication1.qrcodeotpdoor_app.fragment.Setting_Fragment;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Main_Adapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider  {
    //탭에 들어갈 화면들을 통합해서 각 화면에 맞는 fragment를 반환하는 Adaptor 이다 .
    final int PAGE_COUNT = 4;
    //private String tabTitles[] = new String[] { "첫번째탭", "두번째탭", "세번째탭","네번째탭" };
    //글자일떄
    private int[] imgs = {R.drawable.ic_lock_black_36dp,R.drawable.ic_notifications_36pt, R.drawable.ic_history_black_36dp,R.drawable.ic_settings_black_36dp,};
    //이미지를 상속하고  싶을때

    //탭은 이미지로 처리를해야함
    //ㅡㄹ어갈 이미지? 문, 세팅,
    public Main_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("position",String.valueOf(position));
        if (position == 0) {
            return new DoorList_Fragment(position);
        } else if (position == 1) {
            return new Message_Fragment(position);
        }
        else  if (position==2){
            return  new History_Fragment(position);
        }
        else {
            return new Setting_Fragment(position);
        }
    }

    //이미지 쓸때
    @Override
    public int getPageIconResId(int position) {
        return imgs[position];
    }
    }