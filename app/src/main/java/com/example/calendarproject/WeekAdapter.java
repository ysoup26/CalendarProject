package com.example.calendarproject;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeekAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    ArrayList<String> mItems;
    int select;
    int onlyOne=-1;//CYAN색인 뷰가 하나만 존재하도록

    public WeekAdapter(Context context, int resource, ArrayList<String> items) {
        mContext = context;
        mItems = items;
        mResource = resource; //디자인-xml파일
        select=0;
    }

    // MyAdapter 클래스가 관리하는 항목의 총 개수를 반환
    @Override
    public int getCount() {
        return mItems.size();
    }

    // MyAdapter 클래스가 관리하는 항목의 중에서 position 위치의 항목을 반환
    @Override
    public Object getItem(int position) {
        return mItems.get(position); //일수 정보 돌려줌
    }

    // 항목 id를 항목의 위치로 간주함
    @Override
    public long getItemId(int position) {
        return position;
    }

    // position 위치의 항목에 해당되는 항목뷰를 반환하는 것이 목적임
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(mResource, parent, false);
        }
        // convertView 변수로 참조되는 항목 뷰 객체내에 포함된 텍스트뷰 객체를 id를 통해 얻어옴
        TextView name = (TextView) convertView.findViewById(R.id.WEEK_itemText);
        // 어댑터가 관리하는 항목 데이터 중에서 position 위치의 항목의 문자열을 설정 텍스트뷰 객체에 설정
        name.setText(mItems.get(position));
        if(position==select) { //position과 선택이 일치하면 CYAN색으로--다
            name.setBackgroundColor(Color.CYAN);
        }else{ //선택되지 않은 뷰는 WHITE로
            name.setBackgroundColor(Color.WHITE);
        }//스크롤 재사용을 막기위한 부분 출처:https://m.blog.naver.com/heefe92/221054088347

        //회전했을때 아이템의 가로너비를 동적 변경하는 부분-
        //따로 너비 구하는 함수가 있긴 하지만 그 방법들은  프레그먼트나 액티비티에서 호출해야되서 parent를 이용함
        name.setWidth(parent.getWidth()/7);
        return convertView;

    }

}