package com.avoupavou.newtonsdisc;

import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class WheelActivity  extends AppCompatActivity {

    private final String LOG_TAG="WheelActivity";
    /** The OpenGL view */
    private GLSurfaceView glSurfaceView;
    private GlRenderer mRenderer;
    private  float touch_scale_factor = 160.0f /320; //touch factor default = 180.0f / 320
    private SharedPreferences sharedPref;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initiate the Open GL view and
        // create an instance with this activity
        glSurfaceView = new GLSurfaceView(this);

        // set our renderer to be the main renderer with
        // the current activity context

        glSurfaceView.setRenderer(mRenderer= new GlRenderer(this));
        setContentView(glSurfaceView);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // Get preferences for rotation Sensitivity
        int downloadPercent = sharedPref.getInt("SEEKBAR_VALUE", 50);
        //Log.d(LOG_TAG, String.valueOf(downloadType));
        touch_scale_factor*=(float)downloadPercent/100;
        //Log.d(LOG_TAG, String.valueOf(touch_scale_factor));
    }

    /**
     * Remember to resume the glSurface
     */
    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    /**
     * Also pause the glSurface
     */
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;


                // reverse direction of rotation above the mid-line
                if (y > glSurfaceView.getHeight() / 2.0f) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < glSurfaceView.getWidth() / 2.0f) {
                    dy = dy * -1 ;
                }

                mRenderer.setSpeed(
                        mRenderer.getSpeed() -
                                ((dx + dy) * touch_scale_factor));  // = 180.0f / 320
                glSurfaceView.requestRender();
                //Log.d(LOG_TAG, String.valueOf(touch_scale_factor));
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
