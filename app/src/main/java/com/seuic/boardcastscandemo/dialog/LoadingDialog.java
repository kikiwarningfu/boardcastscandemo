package com.seuic.boardcastscandemo.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.seuic.boardcastscandemo.R;


/**
 *
 */
public class LoadingDialog extends AlertDialog {

    public LoadingDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);
    }
}
