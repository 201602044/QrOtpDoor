package basicapplication1.qrcodeotpdoor_app.component.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-17.
 */
public class OkHttp extends AsyncTask<String,Void,String> {
    private static OkHttp instance = new OkHttp();
    public static OkHttp getInstance() {
        return instance;
    }
    @Override
    protected String doInBackground(String... strings) {
        //0번째 controller선택 , 1번째 type, 2번째 sendMsg
        String url="http://thdeo706.vps.phps.kr:8080/QrOtpDoor/select";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("type", strings[0])
                .add("data",strings[1])
                //data는 json형태
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String result=response.body().string().trim();
            Log.i("Response : " ,result);

            return result;
        } catch(Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
