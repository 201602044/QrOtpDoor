package basicapplication1.qrcodeotpdoor_app.component.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // 설치할때 여기서 토큰을 자동으로 만들어 준다
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // 생성한 토큰을 서버로 날려서 저장하기 위해서 만든거
        //토큰이 primary key 가 되고 이에따라 아이디를 변경해서 해야 하나 ?
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
     //내가 구축할 push서버로 보내주는거


    }
}