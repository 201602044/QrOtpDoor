package basicapplication1.qrcodeotpdoor_app.component.item;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-19.
 */
public class Message_VO {
    String user_id,title,msg;
    //사용자한테 메세지를보내는걸 기록하는 user_id,dor_name,title,msg,regdate
    //시간별로 정렬할 수 있어야한다.  datetime기준 정렬
    //토큰이있으면 ? 보낼때매다 토큰 기록이 남는게 문제 user_id,receiveMsg가 json형태로 있다면? 추가만 해주면됨
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
