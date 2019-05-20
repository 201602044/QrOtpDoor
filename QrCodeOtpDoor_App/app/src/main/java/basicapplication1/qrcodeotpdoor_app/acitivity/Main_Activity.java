package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.astuetz.PagerSlidingTabStrip;
import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.adapter.Main_Adapter;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Main_Activity  extends FragmentActivity {
//토큰 발행을 여기에서 다시해야함, 어떻게? 비교해서
        ViewPager viewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                viewPager = (ViewPager) findViewById(R.id.main_viewpgaer);
                //페이지를 미리 보여주는 화면이다 .
                viewPager.setOffscreenPageLimit(3);
                //페이지화면 3개 사용한다고 선언
                viewPager.setAdapter(new Main_Adapter(getSupportFragmentManager()));
                //FragmentActivity 에서 지원하는 getSupportFragmentManager
                PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.main_taps);
                // Attach the view pager to the tab strip
                tabsStrip.setViewPager(viewPager);
        }
}
