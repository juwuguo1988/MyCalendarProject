package com.example.administrator.mycalendarproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.administrator.mycalendarproject.R;


/**
 * Created by DysaniazzZ on 23/12/2016.
 */

public abstract class BaseBottomDialogFragment extends DialogFragment {

    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //可以通过Style设置对话框样式
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        builder.setView(generateDialogView());
        AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        //让对话框居于底部
        window.setGravity(Gravity.BOTTOM);
        //让对话框填充屏幕
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return alertDialog;
    }

    //生成自定义DialogView
    public abstract View generateDialogView();
}
