package com.example.administrator.mycalendarproject.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.mycalendarproject.R;
import com.example.administrator.mycalendarproject.dialog.CalendarPickerDialogFragment;
import com.example.administrator.mycalendarproject.utils.TimeUtils;
import com.example.administrator.mycalendarproject.views.KCalendar;
import com.example.administrator.mycalendarproject.views.KCalendar.OnCalendarClickListener;
import com.example.administrator.mycalendarproject.views.KCalendar.OnCalendarDateChangedListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private KCalendar mKCalendar;
    public String currentDate = null;// 设置默认选中的日期  格式为 “2017:04:05” 标准DATE格式
    private Map<String, Integer> dateList = new HashMap<>();
    private RelativeLayout rl_calendar_last_month, rl_calendar_next_month, rl_calendar_title_area;
    private TextView tv_calendar_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initData();
        setListener();
    }

    private void findViewById() {
        mKCalendar = (KCalendar) findViewById(R.id.kc_window_calendar);
        tv_calendar_month = (TextView) findViewById(R.id.tv_calendar_month);
        rl_calendar_last_month = (RelativeLayout) findViewById(R.id.rl_calendar_last_month);
        rl_calendar_next_month = (RelativeLayout) findViewById(R.id.rl_calendar_next_month);
        rl_calendar_title_area = (RelativeLayout) findViewById(R.id.rl_calendar_title_area);
    }

    private void initData() {
        //初始化日历数据
        initCalendarData();
    }

    private void setListener() {
        rl_calendar_title_area.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_calendar_title_area:
                //showDialogPick(tv_calendar_month);
                //自定义
                //弹出日历选择器
                CalendarPickerDialogFragment calendarPickerDialogFragment = CalendarPickerDialogFragment
                        .newInstance(null, new CalendarPickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(String date) {
                                try {
                                    int year = TimeUtils.getYear(date);
                                    int month = TimeUtils.getMonth(date);
                                    mKCalendar.showCalendar(year, month);
                                    tv_calendar_month.setText(year + getString(R.string.year) + month + getString(R.string.month));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                calendarPickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
                break;

        }
    }

    //将两个选择时间的dialog放在该函数中
    private void showDialogPick(final TextView timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                timeText.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                mKCalendar.showCalendar(year, monthOfYear+1);
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }


    private void initCalendarData() {
        if (null != currentDate) {
            int years = Integer.parseInt(currentDate.substring(0,
                    currentDate.indexOf(":")));
            int month = Integer.parseInt(currentDate.substring(
                    currentDate.indexOf(":") + 1, currentDate.lastIndexOf(":")));
            tv_calendar_month.setText(years + getString(R.string.year) + month + getString(R.string.month));

            mKCalendar.showCalendar(years, month);
            mKCalendar.setCalendarDayBgColor(currentDate, R.drawable.calendar_date_focused);
        } else {
            try {
                tv_calendar_month.setText(
                        TimeUtils.getCurrentYear() + getString(R.string.year) + TimeUtils.getCurrentMonth() + getString(R.string.month));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        List<String> eatlist = new ArrayList<>(); //设置标记列表
        eatlist.add(TimeUtils.getTodayDate().trim());
        eatlist.add(TimeUtils.getYesdayDate().trim());
        eatlist.add("2017:07:01");
        eatlist.add("2017:07:06");
        eatlist.add("2017:07:08");
        dateList.put(TimeUtils.getTodayDate().trim(), 0);
        dateList.put(TimeUtils.getYesdayDate().trim(), 0);
        dateList.put("2017:07:01", 0);
        dateList.put("2017:07:06", 0);
        dateList.put("2017:07:08", 0);
        mKCalendar.addMarks(eatlist, R.color.primary_orange_db);

        List<String> noEatlist = new ArrayList<>(); //设置标记列表
        noEatlist.add(TimeUtils.getBeforeYesdayDate().trim());
        noEatlist.add(TimeUtils.getThreeDaysDate().trim());
        dateList.put(TimeUtils.getBeforeYesdayDate().trim(), 1);
        dateList.put(TimeUtils.getThreeDaysDate().trim(), 1);
        mKCalendar.addMarks(noEatlist, R.color.c_2bb2ba);

        //监听所选中的日期
        mKCalendar.setOnCalendarClickListener(new OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf(":") + 1,
                        dateFormat.lastIndexOf(":")));

                if (mKCalendar.getCalendarMonth() - month == 1//跨年跳转
                        || mKCalendar.getCalendarMonth() - month == -11) {
                    mKCalendar.lastMonth();

                } else if (month - mKCalendar.getCalendarMonth() == 1 //跨年跳转
                        || month - mKCalendar.getCalendarMonth() == -11) {
                    mKCalendar.nextMonth();

                } else {
                    mKCalendar.removeAllBgColor();
                    if (dateList.containsKey(dateFormat)) {
                        int status = dateList.get(dateFormat);
                        if (status == 1) {
                            //全部已吃
                            mKCalendar.setCalendarDayBgColor(dateFormat,
                                    R.drawable.calendar_date_normal);
                        } else if (status == 0) {
                            //已吃未吃
                            mKCalendar.setCalendarDayBgColor(dateFormat,
                                    R.drawable.calendar_date_abnormal);
                        } else if (status == -1) {
                            //全部未吃
                            mKCalendar.setCalendarDayBgColor(dateFormat,
                                    R.drawable.calendar_date_focused);
                        }
                    } else {
                        mKCalendar.setCalendarDayBgColor(dateFormat,
                                R.drawable.calendar_date_focused);
                    }
                    currentDate = dateFormat;//最后返回给全局 date
                }
            }

        });

        //监听当前月份
        mKCalendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                tv_calendar_month.setText(year + getString(R.string.year) + month + getString(R.string.month));
            }
        });

        //上月监听按钮
        rl_calendar_last_month.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mKCalendar.lastMonth();
            }

        });

        //下月监听按钮
        rl_calendar_next_month.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mKCalendar.nextMonth();
            }
        });

    }


}
