package com.xuexue.lib.sdk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

public class OpenAppDialog extends DialogFragment {

    private OpenAppDialogDelegate mDelegate;


    public OpenAppDialog() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(this.getTag());
        builder.setMessage("已完成下载，请点击确认键打开课程");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (mDelegate != null) {
                    mDelegate.doConfirm();
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (mDelegate != null) {
                    mDelegate.doCancel();
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(-1).setTextSize(18.0F);
        dialog.getButton(-2).setTextSize(18.0F);
        dialog.getButton(-1).setTextColor(Color.parseColor("#FF2262aa"));
        dialog.getButton(-2).setTextColor(-7829368);
        return dialog;
    }

    public void show(FragmentManager manager, String tag, OpenAppDialogDelegate delegate) {
        super.show(manager, tag);
        this.mDelegate = delegate;
    }


    public interface OpenAppDialogDelegate {
        void doCancel();

        void doConfirm();
    }

}

