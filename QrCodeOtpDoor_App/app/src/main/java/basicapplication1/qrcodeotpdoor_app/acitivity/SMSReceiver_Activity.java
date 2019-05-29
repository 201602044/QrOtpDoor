package basicapplication1.qrcodeotpdoor_app.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import basicapplication1.qrcodeotpdoor_app.R;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-27.
 */
public class SMSReceiver_Activity extends Activity {
    LinearLayout[] layouts;
    EditText[] editTexts;
    TextView timer_text;
    Intent intent;
    private  int checkNumber;
    Random r;
    int timer_sec;
    Timer timer;
    private TimerTask second;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receiver);
        layouts=new LinearLayout[2];
        layouts[0]=(LinearLayout) findViewById(R.id.sms_receive_layout) ;
        layouts[1]=(LinearLayout) findViewById(R.id.sms_check_layout);
        layouts[1].setVisibility(View.GONE);
        editTexts=new EditText[2];
        editTexts[0]=(EditText) findViewById(R.id.sms_phone_number);
        editTexts[1]=(EditText) findViewById(R.id.sms_check_num);
        r=new Random();

    }
    public void  onClick(View v){
        switch (v.getId()){
            case R.id.sms_receive_button:
                receiveNum();
                break;
            case  R.id.sms_receive_re_msg:
                recevieReNum();
                break;
            case  R.id.sms_submit_num:
                checkNum();
                break;
                default:
                    break;

        }
    }

    private void receiveNum() {

        try {
            //전송
            checkNumber=r.nextInt(8999)+1000;
            //4자리수 입력문자.
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(editTexts[0].getText().toString(), null, checkNumber+"", null, null);
            Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            layouts[0].setVisibility(View.GONE);
            layouts[1].setVisibility(View.VISIBLE);
            timeStart();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    private void recevieReNum() {
        editTexts[0].setText("");
        layouts[0].setVisibility(View.VISIBLE);
        layouts[1].setVisibility(View.GONE);
    }

    private void checkNum() {
        if(checkNumber!=Integer.parseInt(editTexts[1].getText().toString())||checkNumber==Integer.MIN_VALUE){
            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다",Toast.LENGTH_LONG).show();
            return;
        }
        timer.cancel();
        intent=new Intent(this,SignUp_Activity.class);
        startActivity(intent);
        finish();
    }
    public void timeStart() {
        if(timer!=null)timer.cancel();
        timer_text = (TextView) findViewById(R.id.sms_timer);
        timer_sec = 60;
        second = new TimerTask() {

            @Override
            public void run() {
                Update();
                timer_sec--;
            }
        };
        timer = new Timer();
        timer.schedule(second, 0, 1000);

    }

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                timer_text.setText(String.format("%02d",timer_sec/60)+":"+String.format("%02d",timer_sec%60)+"초");
                //텍스트쓰레드
                if(timer_sec==0) {
                    checkNumber=Integer.MIN_VALUE;
                    timer.cancel();
                }

            }
        };
        handler.post(updater);
    }
}
