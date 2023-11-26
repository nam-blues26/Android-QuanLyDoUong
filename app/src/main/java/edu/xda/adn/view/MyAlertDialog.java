package edu.xda.adn.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyAlertDialog {

    public static void showAlertDialog(Context context,
                                       String confirmTitle,
                                       String message,
                                       String namePositiveButton,
                                       String nameNegativeButton,
                                       DialogInterface.OnClickListener listenerPositive,
                                       DialogInterface.OnClickListener listenerNegative) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(confirmTitle);
            builder.setMessage(message);
            builder.setPositiveButton(namePositiveButton, listenerPositive);
            builder.setNegativeButton(nameNegativeButton, listenerNegative);
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
