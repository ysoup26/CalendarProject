package com.example.calendarproject;

import static java.util.Calendar.getInstance;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeekFragment extends Fragment {
    private ArrayList<String> weekList;
    private ArrayList<String> daysList;
    private ArrayList<String> timesList;
    private View NULL;

    public WeekFragment() {
        // Required empty public constructor
    }
    //생성시 달력에 대한 데이터 3개를 받음
    public WeekFragment(ArrayList<String> w, ArrayList<String> d, ArrayList<String> t) {
        // Required empty public constructor
        Log.d("kuku", "WeekFragment: ");
        weekList=w;
        daysList=d;
        timesList=t;
    }
    public static WeekFragment newInstance(String param1, String param2) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        //Log.d("kuku", "onCreateView: ");
        //커스텀 어댑터를 weekAdater 사용하여 주를 표시하는 그리드뷰와 연결
        WeekAdapter weekAdapt = new WeekAdapter(getActivity(), R.layout.week_item, weekList);
        GridView weekGrid = (GridView) rootView.findViewById(R.id.WEEK_weekgrid);
        weekGrid.setAdapter(weekAdapt);

        //커스텀 어댑터를 weekAdater 사용하여 주간 달력을 표시하는 그리드뷰와 연결
        WeekAdapter dayAdapt=new WeekAdapter(getActivity(), R.layout.week_item, daysList);
        GridView dayGrid = (GridView) rootView.findViewById(R.id.WEEK_daygrid);
        dayGrid.setAdapter(dayAdapt);

        //주간 달력 0~23 시간을 표시하는 리스트뷰
        ListView list = (ListView) rootView.findViewById(R.id.WEEK_timelist);
        list.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.week_item, timesList));

        //주와 달력에 대한 아이템 클릭 리스너 설정
        weekGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                for(int i=0;i<weekAdapt.getCount();i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.WHITE);
                }
                v.setBackgroundColor(Color.CYAN);//Color.parseColor("#008B8B")); //dark cyan
            }
        });
        dayGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                for(int i=0;i<dayAdapt.getCount();i++){
                    View child=parent.getChildAt(i);
                    if(child!=NULL) //그냥 하면 NULL pointer오류가 발생해서 해당부분으로 막아줌
                        child.setBackgroundColor(Color.WHITE);
                    if(i<weekAdapt.getCount()){ //달력을 클릭했을때 주간에도 영향을 주어서
                        weekGrid.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                }
                Toast.makeText(getActivity(),"position = "+position,Toast.LENGTH_SHORT).show();
                weekGrid.getChildAt(position%7).setBackgroundColor(Color.CYAN);//position%7을 하면 0~6의 값이 나온다
                dayAdapt.select=position;
                v.setBackgroundColor(Color.CYAN);
            }// https://128june.tistory.com/m/51 전체 흰색으로 바꾸고 실행
        });
        //달력 스크롤 이벤트-스크롤 될때 시간 리스트도 함께 움직이도록
        dayGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) { }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //setScrollY함수의 매개변수는 픽셀값이라 dp값을 읽어와 계산해줌
                //아이템의 높이 dp가 50
                list.setScrollY(i/7* dpToPx(50));//list와 grid의 스크롤 값은 7배 차이 + px값만큼 곱함
            }
            //https://ddolcat.tistory.com/95 스크롤 함수에 대한 정보
        });
        return rootView;
    }
    //dp를 px로 변환하는 함수
    public int dpToPx(int sizeDP){
        int pxVal=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,sizeDP,getResources().getDisplayMetrics());
        return pxVal; //https://chebaum.tistory.com/13 dp를 px로 바꾸는 함수
    }
}