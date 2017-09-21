package simpleinventory.simpleinventory.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;

import java.util.regex.Pattern;

import simpleinventory.simpleinventory.R;

/**
 * Created by gwulfwud on 8/11/2016.
 */
public class LayoutUtil {
    public static int getDisplayHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int height = size.y;
        return height;
    }

    public static int getDisplayWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int width = size.x;
        return width;
    }

    public static Snackbar displayWarningClientMessage(Context fContext, CoordinatorLayout layout, String message){
        final Snackbar fInfo;
        int color = ContextCompat.getColor(fContext, R.color.siWarningColor);
        fInfo = Snackbar.make(layout, message, Snackbar.LENGTH_LONG);
        View fInfoView = fInfo.getView();
                        /*CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        TextView txt = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                        txt.setTextColor(infoRedColor);*/
        fInfoView.setBackgroundColor(color);
        return fInfo;
    }

    public  static Drawable getEdittextWarningIcon(Context fContext){
        int errorColor = ContextCompat.getColor(fContext, R.color.siWarningColor);
        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
        Drawable warning = ContextCompat.getDrawable(fContext, R.drawable.vector_warning_outline);
        warning.setBounds(0, 0, warning.getIntrinsicWidth(), warning.getIntrinsicHeight());
        warning.setColorFilter(errorColor, mMode);

        return warning;
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean validEmail(String email){
        return LayoutUtil.EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static ProgressDialog showProgress(String message, Context context){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage(message);

        return progress;
    }
}
