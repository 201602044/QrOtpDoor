package basicapplication1.qrcodeotpdoor_app.component.item;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-18.
 */
public class DoorUserRelation_VO {
    String door_id,user_id,door_key,door_name;
    public String getDoor_name() {
        return door_name;
    }

    public void setDoor_name(String door_name) {
        this.door_name = door_name;
    }

    public String getDoor_id() {
        return door_id;
    }

    public void setDoor_id(String door_id) {
        this.door_id = door_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoor_key() {
        return door_key;
    }

    public void setDoor_key(String door_key) {
        this.door_key = door_key;
    }
}
