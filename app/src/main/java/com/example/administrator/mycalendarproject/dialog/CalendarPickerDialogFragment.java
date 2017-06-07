package com.example.administrator.mycalendarproject.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.example.administrator.mycalendarproject.R;
import com.example.administrator.mycalendarproject.utils.TimeUtils;
import com.example.administrator.mycalendarproject.views.DateWheelView;
import com.example.administrator.mycalendarproject.views.wheelview.ScreenInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DysaniazzZ on 23/12/2016.
 */
public class CalendarPickerDialogFragment extends BaseBottomDialogFragment {

    private String mOriginDate;
    private String mNewSetDate;
    private DateWheelView mDateWheelView;
    private static OnDateSetListener sOnDateSetListener;
    private static final String ORIGIN_DATE = "origin_date";
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy:MM:dd");

    public static CalendarPickerDialogFragment newInstance(String date, OnDateSetListener onDateSetListener) {
        CalendarPickerDialogFragment datePickerDialogFragment = new CalendarPickerDialogFragment();
        Bundle args = new Bundle();
        args.putString(ORIGIN_DATE, date);
        datePickerDialogFragment.setArguments(args);
        sOnDateSetListener = onDateSetListener;
        return datePickerDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mOriginDate = args.getString(ORIGIN_DATE, null);
        }
    }

    @Override
    public View generateDialogView() {
        View datePickerView = LayoutInflater.from(mContext).inflate(R.layout.dialog_date_picker, null);
        ScreenInfo screenInfo = new ScreenInfo((Activity) mContext);
        mDateWheelView = new DateWheelView(mContext, datePickerView, DateWheelView.TYPE_YEAR_MONRH);
        mDateWheelView.screenHeight = screenInfo.getHeight();
        initData();
        //取消按钮
        datePickerView.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        datePickerView.findViewById(R.id.dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewSetDate = mDateWheelView.getCurrentTime();
                getDialog().dismiss();
                if (sOnDateSetListener != null && !mNewSetDate.equals(mOriginDate)) {
                    sOnDateSetListener.onDateSet(mNewSetDate);
                }
            }
        });
        return datePickerView;
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(mOriginDate) && TimeUtils.isDate(mOriginDate, "yyyy:MM:dd")) {
            try {
                calendar.setTime(mDateFormat.parse(mOriginDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        mDateWheelView.initDateTimePicker(year, month);
    }

    public interface OnDateSetListener {

        void onDateSet(String date);
    }
}
