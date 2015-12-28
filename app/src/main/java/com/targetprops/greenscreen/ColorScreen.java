package com.targetprops.greenscreen;

import com.targetprops.greenscreen.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ColorScreen extends Activity /*implements ColorPicker.OnColorChangedListener*/{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 30;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    //private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;


    ImageView imageViewRT, imageViewLT, imageViewLB, imageViewRB, imageViewC;
    SingleTouchEventView fingerDraw;
    boolean fingerPathToggle=true;
    //Drawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fingerDraw = new SingleTouchEventView(this);
        fingerDraw.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(R.layout.activity_color_screen);
        addContentView(fingerDraw, fingerDraw.getLayoutParams());

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        this.findViewById(android.R.id.content).setBackgroundColor(Color.GREEN);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });
        // Set up the user interaction to manually show or hide the system UI.
        /*  **SingleTouchEventView overrides this section anyways**
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_POINTER_UP:
                        toggleActionBar(e.getPointerCount());
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });*/



        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        imageViewRT = (ImageView)findViewById(R.id.imageView);
        imageViewLT = (ImageView)findViewById(R.id.imageView2);
        imageViewLB = (ImageView)findViewById(R.id.imageView3);
        imageViewRB = (ImageView)findViewById(R.id.imageView4);
        imageViewC = (ImageView)findViewById(R.id.imageView5);

        imageViewRT.setImageResource(R.drawable.ic_tracker3);
        imageViewLT.setImageResource(R.drawable.ic_tracker3);
        imageViewLB.setImageResource(R.drawable.ic_tracker3);
        imageViewRB.setImageResource(R.drawable.ic_tracker3);
        imageViewC.setImageResource(R.drawable.ic_tracker3);

        imageViewRT.setVisibility(View.INVISIBLE);
        imageViewRB.setVisibility(View.INVISIBLE);
        imageViewLB.setVisibility(View.INVISIBLE);
        imageViewLT.setVisibility(View.INVISIBLE);
        imageViewC.setVisibility(View.INVISIBLE);

        // removes the icon from action bar
        // getActionBar().setDisplayShowHomeEnabled(true);
        // removes the title from action bar
        // getActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //FrameLayout.LayoutParams smallParams = new FrameLayout.LayoutParams(dpToPx(48), dpToPx(48));
    //FrameLayout.LayoutParams defaultParams = new FrameLayout.LayoutParams(dpToPx(64), dpToPx(64));
    //FrameLayout.LayoutParams largeParams = new FrameLayout.LayoutParams(dpToPx(96), dpToPx(96));
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_colors:
                //getColor();
                return true;
            case R.id.background_green:
                this.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.green));
                return true;
            case R.id.background_red:
                this.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.red));
                return true;
            case R.id.background_blue:
                this.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.blue));
                return true;
            case R.id.background_white:
                this.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.white));
                return true;
            case R.id.background_gray:
                this.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.gray));
                return true;
            case R.id.action_trackers:
                toggleTrackers();
                return true;
            case R.id.action_trackerSizeSmall:
                imageViewRT.getLayoutParams().height = dpToPx(48);
                imageViewRT.getLayoutParams().width  = dpToPx(48);
                //imageViewRT.setLayoutParams(smallParams);

                imageViewLT.getLayoutParams().height = dpToPx(48);
                imageViewLT.getLayoutParams().width  = dpToPx(48);
                //imageViewLT.setLayoutParams(smallParams);

                imageViewLB.getLayoutParams().height = dpToPx(48);
                imageViewLB.getLayoutParams().width  = dpToPx(48);
                //imageViewLB.setLayoutParams(smallParams);

                imageViewC.getLayoutParams().height = dpToPx(48);
                imageViewC.getLayoutParams().width  = dpToPx(48);
                //imageViewC.setLayoutParams(smallParams);

                imageViewRB.getLayoutParams().height = dpToPx(48);
                imageViewRB.getLayoutParams().width  = dpToPx(48);
                //imageViewRB.setLayoutParams(smallParams);

                return true;

            case R.id.action_trackerSizeDefault:
                imageViewRT.getLayoutParams().height = dpToPx(64);
                //imageViewRT.setLayoutParams(defaultParams);
                imageViewRT.getLayoutParams().width  = dpToPx(64);

                imageViewLT.getLayoutParams().height = dpToPx(64);
                imageViewLT.getLayoutParams().width  = dpToPx(64);
                //imageViewLT.setLayoutParams(defaultParams);

                imageViewLB.getLayoutParams().height = dpToPx(64);
                imageViewLB.getLayoutParams().width  = dpToPx(64);
                //imageViewLB.setLayoutParams(defaultParams);

                imageViewC.getLayoutParams().height = dpToPx(64);
                imageViewC.getLayoutParams().width  = dpToPx(64);
                //imageViewC.setLayoutParams(defaultParams);

                imageViewRB.getLayoutParams().height = dpToPx(64);
                imageViewRB.getLayoutParams().width  = dpToPx(64);
                //imageViewRB.setLayoutParams(defaultParams);

                return true;

            case R.id.action_trackerSizeLarge:
                imageViewRT.getLayoutParams().height = dpToPx(96);
                imageViewRT.getLayoutParams().width  = dpToPx(96);

                imageViewLT.getLayoutParams().height = dpToPx(96);
                imageViewLT.getLayoutParams().width  = dpToPx(96);

                imageViewLB.getLayoutParams().height = dpToPx(96);
                imageViewLB.getLayoutParams().width  = dpToPx(96);

                imageViewC.getLayoutParams().height = dpToPx(96);
                imageViewC.getLayoutParams().width  = dpToPx(96);

                imageViewRB.getLayoutParams().height = dpToPx(96);
                imageViewRB.getLayoutParams().width  = dpToPx(96);

                return true;

            case R.id.action_fingerPath:
                if(fingerPathToggle){
                    fingerDraw.paint.setAlpha(0);
                    item.setIcon(R.drawable.ic_action_location_found_off);
                    fingerPathToggle=false;
                }
                else{
                    fingerDraw.paint.setAlpha(75);
                    fingerDraw.setVisibility(View.VISIBLE);
                    item.setIcon(R.drawable.ic_action_location_found_on);
                    fingerPathToggle=true;
                }

            case R.id.action_settings:
                return true;
            case R.id.default_tracker:
                imageViewRT.setImageResource(R.drawable.ic_tracker3);
                imageViewLT.setImageResource(R.drawable.ic_tracker3);
                imageViewLB.setImageResource(R.drawable.ic_tracker3);
                imageViewRB.setImageResource(R.drawable.ic_tracker3);
                imageViewC.setImageResource(R.drawable.ic_tracker3);
                return true;
            case R.id.circles:
                imageViewRT.setImageResource(R.drawable.ic_filled_circle);
                imageViewLT.setImageResource(R.drawable.ic_filled_circle);
                imageViewLB.setImageResource(R.drawable.ic_filled_circle);
                imageViewRB.setImageResource(R.drawable.ic_filled_circle);
                imageViewC.setImageResource(R.drawable.ic_filled_circle);
                return true;
            case R.id.squares:
                imageViewRT.setImageResource(R.drawable.ic_filled_square);
                imageViewLT.setImageResource(R.drawable.ic_filled_square);
                imageViewLB.setImageResource(R.drawable.ic_filled_square);
                imageViewRB.setImageResource(R.drawable.ic_filled_square);
                imageViewC.setImageResource(R.drawable.ic_filled_square);
                return true;
            case R.id.right_angles:
                imageViewRT.setImageResource(R.drawable.ic_filled_angle_rt);
                imageViewLT.setImageResource(R.drawable.ic_filled_angle_lt);
                imageViewLB.setImageResource(R.drawable.ic_filled_angle_lb);
                imageViewRB.setImageResource(R.drawable.ic_filled_angle_rb);
                imageViewC.setImageResource(R.drawable.ic_filled_x);
                //imageViewC.setImageDrawable(d);
                return true;
            case R.id.x:
                imageViewRT.setImageResource(R.drawable.ic_filled_x);
                imageViewLT.setImageResource(R.drawable.ic_filled_x);
                imageViewLB.setImageResource(R.drawable.ic_filled_x);
                imageViewRB.setImageResource(R.drawable.ic_filled_x);
                imageViewC.setImageResource(R.drawable.ic_filled_x);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    /*View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };*/

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    /*public void getColor() {
        new ColorPicker(this, this, Color.WHITE).show();
    }*/
    //  The color wheel is now obsolete with the color menu
    /*public void colorChanged( int color)
    {
        this.findViewById(android.R.id.content).setBackgroundColor(color);
    }*/



    public void toggleTrackers()
    {
        if(!imageViewRT.isShown()) {
            imageViewRT.setVisibility(View.VISIBLE);
            imageViewRB.setVisibility(View.VISIBLE);
            imageViewLB.setVisibility(View.VISIBLE);
            imageViewLT.setVisibility(View.VISIBLE);
        }
        else if(!imageViewC.isShown()){
            imageViewC.setVisibility(View.VISIBLE);
        }
        else
        {
            imageViewRT.setVisibility(View.INVISIBLE);
            imageViewRB.setVisibility(View.INVISIBLE);
            imageViewLB.setVisibility(View.INVISIBLE);
            imageViewLT.setVisibility(View.INVISIBLE);
            imageViewC.setVisibility(View.INVISIBLE);
        }
    }


    public void toggleActionBar(int pointerCount)
    {
        if(pointerCount==2) {
            mSystemUiHider.toggle();
            if (getActionBar().isShowing())
                getActionBar().hide();
            else
                getActionBar().show();
        }
        else
        {
            mSystemUiHider.show();
            getActionBar().show();
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}

