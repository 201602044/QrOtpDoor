package basicapplication1.qrcodeotpdoor_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import basicapplication1.qrcodeotpdoor_app.R;
import basicapplication1.qrcodeotpdoor_app.component.item.Message_VO;

/**
 * Created by LeeSeungJae_201602044  on 2019-05-14.
 */
public class MessageList_Adapter extends BaseAdapter {
    private ArrayList<Message_VO> list= new ArrayList<Message_VO>() ;

    // ListViewAdapter의 생성자
    public MessageList_Adapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return list.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_message, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView[] textViews=new TextView[2];
        textViews[0]=(TextView) convertView.findViewById(R.id.messge_title);
        textViews[1]=(TextView) convertView.findViewById(R.id.message_msg);

        //만들어야할게 전체 제목 날짜  닉네임 좋아요 싫어요 // + linearlayout의 버튼 ,
        // Data Set(listViewItemPageList)에서 position에 위치한 데이터 참조 획득
        final Message_VO message_vo =list.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        textViews[0].setText(message_vo.getTitle());
        textViews[1].setText(message_vo.getMsg());


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return list.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem( Message_VO item) {
        list.add(item);
    }


}
