package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.astuetz.PagerSlidingTabStrip;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.adapter.Main_Adapter;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class Main_Activity  extends FragmentActivity {
//토큰 발행을 여기에서 다시해야함, 어떻게? 비교해서
        ViewPager viewPager;
        Intent intent;

        private FloatingActionButton fab_main, fab_sub1, fab_sub2;

        private Animation fab_open, fab_close;

        private boolean isFabOpen = false;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                viewPager = (ViewPager) findViewById(R.id.main_viewpgaer);
                //페이지를 미리 보여주는 화면이다 .
                viewPager.setOffscreenPageLimit(4);
                //페이지화면 4개 사용한다고 선언
                viewPager.setAdapter(new Main_Adapter(getSupportFragmentManager()));
                //FragmentActivity 에서 지원하는 getSupportFragmentManager
                PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.main_taps);
                // Attach the view pager to the tab strip
                tabsStrip.setViewPager(viewPager);
                fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);

                fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);



                fab_main = (FloatingActionButton) findViewById(R.id.main_fab);

                fab_sub1 = (FloatingActionButton) findViewById(R.id.fab_sub1);
                //logout
                fab_sub2 = (FloatingActionButton) findViewById(R.id.fab_sub2);
                //adddoor
                fab_main.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                                toggleFab();
                        }
                });

                fab_sub1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                toggleFab();
                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                intent.putExtra("logout", true);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


                        }
                });

                fab_sub2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                toggleFab();
                                intent=new Intent(getApplicationContext(),AddDoor_Activity.class);
                                startActivity(intent);

                        }
                });
     }
        private void toggleFab() {

                if (isFabOpen) {

                        fab_main.setImageResource(R.drawable.ic_add_black_36dp);

                        fab_sub1.startAnimation(fab_close);

                        fab_sub2.startAnimation(fab_close);

                        fab_sub1.setClickable(false);

                        fab_sub2.setClickable(false);

                        isFabOpen = false;

                } else {

                        fab_main.setImageResource(R.drawable.ic_minus_black_36dp);

                        fab_sub1.startAnimation(fab_open);

                        fab_sub2.startAnimation(fab_open);

                        fab_sub1.setClickable(true);

                        fab_sub2.setClickable(true);

                        isFabOpen = true;

                }

        }

}
