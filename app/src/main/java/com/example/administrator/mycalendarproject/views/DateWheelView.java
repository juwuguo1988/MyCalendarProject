package com.example.administrator.mycalendarproject.views;

import android.content.Context;
import android.view.View;
import com.example.administrator.mycalendarproject.R;
import com.example.administrator.mycalendarproject.views.wheelview.NumericWheelAdapter;
import com.example.administrator.mycalendarproject.views.wheelview.OnWheelChangedListener;
import com.example.administrator.mycalendarproject.views.wheelview.WheelView;
import com.example.administrator.mycalendarproject.views.wheelview.WheelViewString;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 日历选择控件（有三种类型）
 */
public class DateWheelView {

    private Context mContext;
    private View mView;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hour;
    private WheelView wv_minute;

    public int screenHeight;
    private int wheelType;

    private static int START_YEAR = 1930, END_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static int START_HOUR = 0, END_HOUR = 23;
    private static int START_MIN = 0, END_MIN = 59;

    int thisYear = Calendar.getInstance().get(Calendar.YEAR);
    int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
    int thisDay = Calendar.getInstance().get(Calendar.DATE);
    int thisHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    int thisMinute = Calendar.getInstance().get(Calendar.MINUTE);

    public static final int TYPE_ALL = 0;   //显示年月日时分
    public static final int TYPE_DATE = 1;  //显示年月日
    public static final int TYPE_TIME = 2;  //显示时分
    public static final int TYPE_YEAR_MONRH = 3;  //显示年月

    public DateWheelView(Context context, View view, int wheelType) {
        super();
        this.mView = view;
        this.mContext = context;
        this.wheelType = wheelType;
    }

    public void initDateTimePicker(int year, int month, int day) {
        this.initDateTimePicker(year, month, day, thisHour, thisMinute);
    }

    public void initDateTimePicker(int year, int month) {
        this.initDateTimePicker(year, month, thisDay, thisHour, thisMinute);
    }

    public void initDateTimePicker(int year, int month, int day, int hour, int minute) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        //年
        wv_year = (WheelView) mView.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(mContext, START_YEAR, END_YEAR));
        wv_year.setLabel("年");
        wv_year.setCurrentItem(year - START_YEAR);

        // 月
        wv_month = (WheelView) mView.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(mContext, 1, 12));
        wv_month.setCyclic(true);
        wv_month.setLabel("月");
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) mView.findViewById(R.id.day);
        wv_day.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 29));
            } else {
                wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 28));
            }
        }
        wv_day.setLabel("日");
        wv_day.setCurrentItem(day - 1);

        // 时
        wv_hour = (WheelView) mView.findViewById(R.id.hour);
        wv_hour.setAdapter(new NumericWheelAdapter(mContext, START_HOUR, END_HOUR));
        wv_hour.setCyclic(true);
        wv_hour.setLabel("时");
        wv_hour.setCurrentItem(hour - START_HOUR);

        // 分
        wv_minute = (WheelView) mView.findViewById(R.id.minute);
        wv_minute.setAdapter(new NumericWheelAdapter(mContext, START_MIN, END_MIN));
        wv_minute.setCyclic(true);
        wv_minute.setLabel("分");
        wv_minute.setCurrentItem(minute - START_MIN);

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 31));
                } else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 29));
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 28));
                    }
                }

                if (wv_year.getCurrentItem() == END_YEAR - START_YEAR && wv_month.getCurrentItem() >= thisMonth) {
                    wv_month.setCurrentItem(thisMonth);
                    if (wv_day.getCurrentItem() >= thisDay - 1) {
                        wv_day.setCurrentItem(thisDay - 1);
                        if (wv_hour.getCurrentItem() >= thisHour) {
                            wv_hour.setCurrentItem(thisHour);
                            if (wv_minute.getCurrentItem() >= thisMinute) {
                                wv_minute.setCurrentItem(thisMinute);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChanged(WheelViewString wheel, int oldValue, int newValue) {
            }
        };

        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 31));
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 30));
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 29));
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(mContext, 1, 28));
                    }
                }

                if (wv_year.getCurrentItem() == END_YEAR - START_YEAR && newValue >= thisMonth) {
                    wv_month.setCurrentItem(thisMonth);
                    if (wv_day.getCurrentItem() >= thisDay - 1) {
                        wv_day.setCurrentItem(thisDay - 1);
                        if (wv_hour.getCurrentItem() >= thisHour) {
                            wv_hour.setCurrentItem(thisHour);
                            if (wv_minute.getCurrentItem() >= thisMinute) {
                                wv_minute.setCurrentItem(thisMinute);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChanged(WheelViewString wheel, int oldValue, int newValue) {
            }
        };

        // 添加"日"监听
        OnWheelChangedListener wheelListener_day = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wv_year.getCurrentItem() == END_YEAR - START_YEAR && wv_month.getCurrentItem() >= thisMonth
                        && newValue >= thisDay - 1) {
                    wv_day.setCurrentItem(thisDay - 1);
                    if (wv_hour.getCurrentItem() >= thisHour) {
                        wv_hour.setCurrentItem(thisHour);
                        if (wv_minute.getCurrentItem() >= thisMinute) {
                            wv_minute.setCurrentItem(thisMinute);
                        }
                    }
                }
            }

            @Override
            public void onChanged(WheelViewString wheel, int oldValue, int newValue) {
            }
        };

        // 添加"时"监听
        OnWheelChangedListener wheelListener_hour = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wv_year.getCurrentItem() == END_YEAR - START_YEAR && wv_month.getCurrentItem() >= thisMonth
                        && wv_day.getCurrentItem() >= thisDay - 1 && newValue >= thisHour) {
                    wv_hour.setCurrentItem(thisHour);
                    if (wv_minute.getCurrentItem() >= thisMinute) {
                        wv_minute.setCurrentItem(thisMinute);
                    }
                }
            }

            @Override
            public void onChanged(WheelViewString wheel, int oldValue, int newValue) {
            }
        };

        // 添加"分"监听
        OnWheelChangedListener wheelListener_minute = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wv_year.getCurrentItem() == END_YEAR - START_YEAR && wv_month.getCurrentItem() >= thisMonth
                        && wv_day.getCurrentItem() >= thisDay - 1 && wv_hour.getCurrentItem() >= thisHour && newValue >= thisMinute) {
                    wv_minute.setCurrentItem(thisMinute);
                }
            }

            @Override
            public void onChanged(WheelViewString wheel, int oldValue, int newValue) {
            }
        };

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);
        wv_day.addChangingListener(wheelListener_day);
        wv_hour.addChangingListener(wheelListener_hour);
        wv_minute.addChangingListener(wheelListener_minute);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = (screenHeight / 100) * 3;
        wv_year.TEXT_SIZE = textSize;
        wv_month.TEXT_SIZE = textSize;
        wv_day.TEXT_SIZE = textSize;
        wv_hour.TEXT_SIZE = textSize;
        wv_minute.TEXT_SIZE = textSize;

        switch (wheelType) {
            case 3:
                wv_year.setVisibility(View.VISIBLE);
                wv_month.setVisibility(View.VISIBLE);
                wv_day.setVisibility(View.GONE);
                wv_hour.setVisibility(View.GONE);
                wv_minute.setVisibility(View.GONE);
                break;
            case 2:
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hour.setVisibility(View.VISIBLE);
                wv_minute.setVisibility(View.VISIBLE);
                break;
            case 1:
                wv_year.setVisibility(View.VISIBLE);
                wv_month.setVisibility(View.VISIBLE);
                wv_day.setVisibility(View.VISIBLE);
                wv_hour.setVisibility(View.GONE);
                wv_minute.setVisibility(View.GONE);
                break;
            case 0:
            default:
                wv_year.setVisibility(View.VISIBLE);
                wv_month.setVisibility(View.VISIBLE);
                wv_day.setVisibility(View.VISIBLE);
                wv_hour.setVisibility(View.VISIBLE);
                wv_minute.setVisibility(View.VISIBLE);
                break;
        }
    }

    public String getTime() {
        StringBuilder sb = new StringBuilder();
        String year = String.valueOf(wv_year.getCurrentItem() + START_YEAR);
        String month = wv_month.getCurrentItem() < 9 ? "0" + (wv_month.getCurrentItem() + 1) : String.valueOf(wv_month.getCurrentItem() + 1);
        String day = wv_day.getCurrentItem() < 9 ? "0" + (wv_day.getCurrentItem() + 1) : String.valueOf(wv_day.getCurrentItem() + 1);
        String hour = wv_hour.getCurrentItem() < 10 ? "0" + wv_hour.getCurrentItem() : String.valueOf(wv_hour.getCurrentItem());
        String minute = wv_minute.getCurrentItem() < 10 ? "0" + wv_minute.getCurrentItem() : String.valueOf(wv_minute.getCurrentItem());
        switch (wheelType) {
            case 2:
                sb.append(hour).append(":").append(minute);
                break;
            case 1:
                sb.append(year).append("-").append(month).append("-").append(day);
                break;
            case 0:
            default:
                sb.append(year).append("-").append(month).append("-").append(day).append(" ").append(hour).append(":").append(minute);
                break;
        }
        return sb.toString();
    }

    public String getCurrentTime() {
        StringBuilder sb = new StringBuilder();
        String year = String.valueOf(wv_year.getCurrentItem() + START_YEAR);
        String month = wv_month.getCurrentItem() < 9 ? "0" + (wv_month.getCurrentItem() + 1) : String.valueOf(wv_month.getCurrentItem() + 1);
        String day = wv_day.getCurrentItem() < 9 ? "0" + (wv_day.getCurrentItem() + 1) : String.valueOf(wv_day.getCurrentItem() + 1);
        String hour = wv_hour.getCurrentItem() < 10 ? "0" + wv_hour.getCurrentItem() : String.valueOf(wv_hour.getCurrentItem());
        String minute = wv_minute.getCurrentItem() < 10 ? "0" + wv_minute.getCurrentItem() : String.valueOf(wv_minute.getCurrentItem());
        switch (wheelType) {
            case 2:
                sb.append(hour).append(":").append(minute);
                break;
            case 1:
                sb.append(year).append(":").append(month).append(":").append(day);
                break;
            case 0:
            default:
                sb.append(year).append(":").append(month).append(":").append(day).append(" ").append(hour).append(":").append(minute);
                break;
        }
        return sb.toString();
    }
}
