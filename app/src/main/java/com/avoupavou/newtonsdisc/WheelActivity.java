package com.avoupavou.newtonsdisc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public class WheelActivity extends Activity {

    /**
     * The OpenGL view
     */
    private GLSurfaceView glSurfaceView;
    private GlRenderer mRenderer;
    private float touchScaleFactor;
    private float mPreviousX;
    private float mPreviousY;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        touchScaleFactor = 160.0f / 320; //touch factor default = 180.0f / 320
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

        mRenderer = new GlRenderer(this);
        glSurfaceView.setRenderer(mRenderer);
        setContentView(glSurfaceView);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // Get preferences for rotation Sensitivity
        int downloadPercent = sharedPref.getInt("SEEKBAR_VALUE", 50);
        touchScaleFactor *= downloadPercent / 100.0f;
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

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = x - mPreviousX;
            float dy = y - mPreviousY;


            // reverse direction of rotation above the mid-line
            if (y > glSurfaceView.getHeight() / 2.0f) {
                dx = dx * -1;
            }

            // reverse direction of rotation to left of the mid-line
            if (x < glSurfaceView.getWidth() / 2.0f) {
                dy = dy * -1;
            }

            mRenderer.setSpeed(
                    mRenderer.getSpeed() -
                            ((dx + dy) * touchScaleFactor));  // = 180.0f / 320
            glSurfaceView.requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
