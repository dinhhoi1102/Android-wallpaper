package hoi1102.com.aov_arenachibiwallpapershd;


import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;


public class DetailsActivity extends AppCompatActivity implements RewardedVideoAdListener{


    //////////////////////////////////////
//    private static final String AD_UNIT_ID = "ca-app-pub-3398251248637833/1075830378";

    ////

    public  int mCoinCount=10;
    private TextView mCoinCountText;
    private CountDownTimer mCountDownTimer;
    private boolean mGameOver;
    private boolean mGamePaused;
    private RewardedVideoAd mRewardedVideoAd;


    private long mTimeRemaining;



    /////////////////////////////////////
    private static final int ANIM_DURATION = 600;
    private ImageView imageView;
    int n = 0;
    private static int count = 1;
    public InterstitialAd mInterstitialAd;
    //    Context context;
    private AdView mAdView;

    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;
    private FrameLayout frameLayout;
    private ColorDrawable colorDrawable;
    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;
    private int arraylenght;
    private int possision;
    private static int rd;
    private static final Random RANDOM = new Random();

    String a;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);











//        checkPermissions();
        //Setting details screen layout
        setContentView(R.layout.activity_details_view);
        ////////////////////////////////////
        // Initialize the Mobile Ads SDK.
        try {
            mCoinCount = Integer.parseInt(readFromFile(DetailsActivity.this));
        }catch (Exception e){
            e.printStackTrace();
        }
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener((RewardedVideoAdListener) this);
        loadRewardedVideoAd();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_fullscreen));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startGame2();
            }
        });
        // Create the "retry" button, which tries to show an interstitial between game plays.
//        btnset= findViewById(R.id.setwallpaper);
//
//        btnset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startGame();
//            }
//        });

        // Create the "show" button, which shows a rewarded video if one is loaded.

        Button mShowVideoButton = (Button) findViewById(R.id.watch_video);

        mShowVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showRewardedVideo();
            }
        });

        // Display current coin count to user.
        mCoinCountText = (TextView) findViewById(R.id.coin_count_text);

        mCoinCountText.setText("" + mCoinCount);
        startGame();



////////////////QUANG CAO/////////////////////////////////



////////////////QUANG CAO/////////////////////////////////

//        Log.e("where :","Cropngoai----------------------------------------------------------------");
        ActionBar actionBar = getSupportActionBar();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        thumbnailTop = bundle.getInt("top");
        thumbnailLeft = bundle.getInt("left");
        thumbnailWidth = bundle.getInt("width");
        possision = bundle.getInt("position");
        thumbnailHeight = bundle.getInt("height");
        arraylenght =bundle.getInt("arraylength");

        imageView = (ImageView) findViewById(R.id.grid_item_image);
        Picasso.with(DetailsActivity.this).load(MainActivity.url.get(possision)).placeholder(R.drawable.loading2).into(imageView);
        final Bitmap bm = imageView.getDrawingCache();
        Button btset = (Button) findViewById(R.id.setwallpaper);
        btset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCoinCount<1){
                    Xyz();
//                    Toast.makeText(getApplicationContext(),"Not enought coins to set wallpaper,please watch video", Toast.LENGTH_LONG).show();
                }else {
                    addCoins(-1);
                    imageView.setDrawingCacheEnabled(true);
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bm = drawable.getBitmap();
                    imageView.buildDrawingCache();
//                Bitmap bm= imageView.getDrawingCache();

                    WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext());
                    wpm.setWallpaperOffsetSteps(1, 1);
                    try {
                        wpm.setBitmap(bm);
                        Toast.makeText(getApplicationContext(), "Set Wallpaprer Successfully", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        Button btnSave = (Button)findViewById(R.id.bntSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCoinCount<1){
                    Xyz();
//                  Toast.makeText(getApplicationContext(),"Not enought coins to save wallpaper,please watch video", Toast.LENGTH_LONG).show();
                }else {
                    addCoins(-1);
                    downloadFile(MainActivity.url.get(possision),DetailsActivity.this);
                    Toast.makeText(getApplicationContext(), "Save Wallpaper Successes", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btnMore =(Button)findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionMenu.goToApp(DetailsActivity.this);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            String startX;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                float eventX = event.getX();
                boolean re = false;

//                if (count ==rd){
//                    showInterstitial();
//                    random();
//                    Toast.makeText(DetailsActivity.this,"???" , Toast.LENGTH_SHORT).show();
//                }


                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = String.valueOf(eventX);
                        re = true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        float x1 = Float.parseFloat(startX);
                        if(x1<eventX){
                            if(possision==0){
                                possision=(arraylenght-1);



                            }else {

                                possision-=1;


                            }
                            Picasso.with(DetailsActivity.this).load(MainActivity.url.get(possision)).placeholder(R.drawable.loading2).into(imageView);
                            imageView.buildDrawingCache();
                            count++;
                            startGame2();



                        }
                        else {
                            if(possision==(arraylenght-1)){
                                possision=0;
                            }
                            else {
                                possision++;

                            }

                            Picasso.with(DetailsActivity.this).load(MainActivity.url.get(possision)).placeholder(R.drawable.loading2).into(imageView);
                            imageView.buildDrawingCache();
                            count++;



                            break;
                        }
                    default:
                        re = false;

                }
                return re;

            }

        });





        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = imageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    imageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / imageView.getWidth();
                    mHeightScale = (float) thumbnailHeight / imageView.getHeight();

                    enterAnimation();

                    return true;
                }
            });
        }
    }
    ///////////////////////////////adward//////////////////////////////////////////////////////////////
    public void Xyz() {
        loadRewardedVideoAd();
        new AlertDialog.Builder(DetailsActivity.this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Not enough Coins!")
                .setMessage("You can watch video Ads to earn Coins . Thanks you very much!!!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        n=1;

                        showRewardedVideo();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        n = 2;
                    }
                }).show();
    }

    @Override
    public void onPause() {
        super.onPause();

        mRewardedVideoAd.pause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        startGame2();
        mRewardedVideoAd.resume(this);
    }

    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(getString(R.string.AD_UNIT_ID), new AdRequest.Builder().build());
        }
    }

    private void addCoins(int coins) {
        mCoinCount = mCoinCount + coins;
        mCoinCountText.setText("" + mCoinCount);
    }

    private void startGame() {
        // Hide the retry button, load the ad, and start the timer.

        loadRewardedVideoAd();
        mGamePaused = false;
        mGameOver = false;
    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.


    private void showRewardedVideo() {

        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    public void onRewardedVideoAdLeftApplication() {

    }


    public void onRewardedVideoAdClosed() {

        // Preload the next video ad.
        loadRewardedVideoAd();
    }


    public void onRewardedVideoAdFailedToLoad(int errorCode) {

    }


    public void onRewardedVideoAdLoaded() {

    }


    public void onRewardedVideoAdOpened() {
//        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }


    public void onRewarded(RewardItem reward) {
        Toast.makeText(this,
                String.format(" onRewarded! currency: %s amount: %d", reward.getType(),
                        reward.getAmount()),
                Toast.LENGTH_SHORT).show();
        addCoins(reward.getAmount());
        loadRewardedVideoAd();
    }


    public void onRewardedVideoStarted() {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }



    public void downloadFile(String uRl, Activity activity) {
//        checkPermissions();
        a = Integer.toString(possision);
        DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);
        File direct = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+getString(R.string.app_name));
        File direct2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+getString(R.string.app_name) + Integer.toString(possision) + ".png");

        if (direct.exists() == false) {
            if (direct2.exists() == true) {
            } else {
                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false).setTitle("Wallpaper")
                        .setDescription("Design by HoiDung")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/"+getString(R.string.app_name), Integer.toString(possision) + ".png");
                mgr.enqueue(request);
                Toast.makeText(getApplicationContext(),"Save Wallpaprer Successfully", Toast.LENGTH_LONG).show();
            }
        } else {
            if (direct2.exists() == true) {

            } else {
                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false).setTitle("Wallpaper")
                        .setDescription("Design by Hoi")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/"+getString(R.string.app_name), Integer.toString(possision) + ".png");
                mgr.enqueue(request);
                Toast.makeText(getApplicationContext(),"Save Wallpaprer Successfully", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void storedata(String mcoincount, Activity activity) {
//        checkPermissions();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput("config.txt", activity.MODE_PRIVATE));
            outputStreamWriter.write(mcoincount);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Activity activity) {

        String ret = "";

        try {
            InputStream inputStream = activity.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    public void enterAnimation() {

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        imageView.setPivotX(0);
        imageView.setPivotY(0);
        imageView.setScaleX(mWidthScale);
        imageView.setScaleY(mHeightScale);
        imageView.setTranslationX(mLeftDelta);
        imageView.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        imageView.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();

    }

    public void exitAnimation(final Runnable endAction) {

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                    translationX(mLeftDelta).translationY(mTopDelta)
                    .setInterpolator(sInterpolator).withEndAction(endAction);
        }

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }


    public void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();


        } else {
            startGame2();
        }
    }
    public void startGame2() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);


        }
    }



    @Override
    public void onBackPressed() {
        startGame2();
        showInterstitial();
        storedata(String.valueOf(mCoinCount),DetailsActivity.this);
        super.onBackPressed();


    }



}
