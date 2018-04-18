package hoi1102.com.aov_arenachibiwallpapershd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ActionMenu {
    public ActionMenu(){}

    public static void goToApp(Context context){
        final String appPackageName = context.getPackageName();
        try {
            //Link to AppStore Amazon
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("amzn://apps/android?p=" + appPackageName)));
            //Link to CH_Play
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            //Link to AppStore Amazon
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + appPackageName)));
            //Link to CH_Play
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    public static void goToStore(Context context){
        final String nameDevIdGP = "Store+Entertainment";
        final String appPackageName = context.getPackageName();
        try {
            //Link to AppStore Amazon
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("amzn://apps/android?p=" + appPackageName + "&showAll=1")));
            //Link to CH_Play
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + nameDevIdGP)));
        } catch (android.content.ActivityNotFoundException anfe) {
            //Link to AppStore Amazon
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + appPackageName + "&showAll=1")));
            //Link to CH_Play
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=" + nameDevIdGP)));
        }
    }
    public static void shareLinkGP(Context ct) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //For Amazon
//        intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + ct.getPackageName());
        //For CH_Play
//        intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + ct.getPackageName());
        ct.startActivity(intent);
    }
}