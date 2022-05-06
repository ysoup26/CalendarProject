package com.example.calendarproject;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WeekPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=3; //기본 갯수
    int ItemCenter=getItemCount()/2;
    WeekCalendar weekC;
    public WeekPagerAdapter(MonthViewActivity fa) {
        super(fa);
        weekC=new WeekCalendar(ItemCenter);
        fa.getSupportActionBar().setTitle(toString(ItemCenter)); //처음 시작화면의 메뉴바 텍스트 설정
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        weekC.makeCalendarData(position);
        WeekFragment fragment=new WeekFragment(weekC.getWeekList(),weekC.getDaysList(), weekC.getTimesList());
        return fragment;
    }
    //https://bbulog.tistory.com/34 무한 페이지를 위해 참조한 블로그
    @Override
    public int getItemCount() { //프레그먼트 갯수를 정수의 최댓값만큼-사실상 3개가 재사용되는것 //이 값에 따라 갯수가 결정된다
        return Integer.MAX_VALUE;
    }
    //public int getRealPosition(int position) { return position; } //실제아이템포지션
    public String toString(int position){
        return weekC.toString(position);
    }
}
