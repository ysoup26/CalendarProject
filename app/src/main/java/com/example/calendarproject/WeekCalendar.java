package com.example.calendarproject;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekCalendar {
    private Calendar now;
    private int center;
    private ArrayList<String> weekList=new ArrayList<>();
    private ArrayList<String> daysList=new ArrayList<>();
    private ArrayList<String> timesList=new ArrayList<>();
    //주간 달력에 대한 데이터 리스트들을 반환하는 클래스
    public WeekCalendar(int c) {
        center=c;
        //공통 데이터 리스트를 만듬-시간과 달력부분은 모두 동일함
        for (int i = 0; i < 7 * 24; i++) {
            if (i < 24) //0부터 23까지
                timesList.add(Integer.toString(i));
            daysList.add(Integer.toString(i));
        }
    }
    //프래그먼트 만들어서 반환하는 함수
    public void makeCalendarData(int position){
        weekList=new ArrayList<>(); //week리스트는 포지션에 따라 달라지니 매번 초기화
        /*주(week)의 시작일 구하기*/
        int start=startDay(); //현재 날짜의 주 시작일 구함
        now.set(DATE,start+7*(position-center)); //현재포지션-중심 포지션의 차이만큼 7을 곱하면 각 포지션에 대한 주를 구할 수 있음
        /*달력을 위한 데이터 만들기*/
        for(int i=0; i<7; i++) {
            int day=now.get(DATE);
            weekList.add(Integer.toString(day));
            day++;
            now.set(DATE,day); //ex) day가 32일땐 그대로 출력하면 안되니 set해서 올바른 날짜가 되도록
        }
    }
    //해당 프레그먼트의 날짜 반환 ex)2022년 5월
    public String toString(int position){
        int start=startDay();
        now.set(DATE,start+7*(position-center)); //중심 포지션를 기준으로 곱함
        return now.get(YEAR)+"년 "+(now.get(MONTH)+1)+"월 "+now.get(DATE)+"일";
    }
    public int startDay(){ //현재 날짜 기준으로 일요일(시작일) 반환
        now=getInstance(); //현재 날짜로 초기화 후
        int curDay= now.get(DATE);  //now는 매번 현재값을 가리켜야함
        int startDay = now.get(Calendar.DAY_OF_WEEK); //그 주의 요일 반환 (일:1 ~ 토:7)
        return curDay-startDay+1; //현재 일수-요일 값+1하면 그 주의 시작일이 된다
    }
    public ArrayList<String> getWeekList() {
        return weekList;
    }

    public ArrayList<String> getDaysList() {
        return daysList;
    }

    public ArrayList<String> getTimesList() {
        return timesList;
    }
}
