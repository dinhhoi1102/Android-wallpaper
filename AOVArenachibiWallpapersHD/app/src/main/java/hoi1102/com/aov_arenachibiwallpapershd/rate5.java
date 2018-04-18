package hoi1102.com.aov_arenachibiwallpapershd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class rate5 {
    public static void goToApp(Context context){
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    public static void goToStore(Context context){
        final String nameDevIdGP = "American+love";
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + nameDevIdGP)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=" + nameDevIdGP)));
        }
    }
    public static void shareLinkGP(Context ct) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + ct.getPackageName());
        ct.startActivity(intent);
    }
}