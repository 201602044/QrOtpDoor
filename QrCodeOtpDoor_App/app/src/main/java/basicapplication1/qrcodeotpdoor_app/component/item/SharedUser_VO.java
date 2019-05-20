package basicapplication1.qrcodeotpdoor_app.component.item;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-18.
 */
public class SharedUser_VO {

    String user_id,shareduser_name,shareduser_phone_number;
    //reg_date도 호응 안해줘두 돼? ㄴㄴ 필용없을듯 만들때 TimeStamp를 이용해서 서버단에서 추가해줘야함
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShareduser_name() {
        return shareduser_name;
    }

    public void setShareduser_name(String shareduser_name) {
        this.shareduser_name = shareduser_name;
    }

    public String getShareduser_phone_number() {
        return shareduser_phone_number;
    }

    public void setShareduser_phone_number(String shareduser_phone_number) {
        this.shareduser_phone_number = shareduser_phone_number;
    }


}
