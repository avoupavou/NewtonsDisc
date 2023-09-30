package com.avoupavou.newtonsdisc;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.preference.PreferenceManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class GlRenderer implements GLSurfaceView.Renderer {

    private static final float MAX_SPEED = 144;
    private Square square;        // the square
    private Context context;
    private float ultimateSpeed;
    private float angle;
    private float speed;

    /**
     * Constructor to set the handed over context
     */
    public GlRenderer(Context context) {
        this.context = context;
        speed = 0;
        ultimateSpeed = MAX_SPEED;
        angle = 0;
        // initialise the square
        this.square = new Square();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // clear Screen and Depth Buffer
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String downloadBgColor = sharedPref.getString("bgColor", "black");

        // Set the background frame color
        if (downloadBgColor.equals("white")) {
            gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            //black
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }


        // Reset the Modelview Matrix
        gl.glLoadIdentity();

        // Drawing
        gl.glTranslatef(0.0f, 0.0f, -5.0f);        // move 5 units INTO the screen

        // Optimal speed for auto rotation - default is off
        angle += ultimateSpeed;


        angle += speed;

        speed = Math.min(speed, MAX_SPEED);
        speed = Math.max(speed, -MAX_SPEED);


        gl.glRotatef(angle, 0, 0, 1);
        square.draw(gl);                        // Draw the triangle

        // slow down the disc1
        speed = speed - 0.01f * speed;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) {                        //Prevent A Divide By Zero By
            height = 1;                        //Making Height Equal One
        }

        gl.glViewport(0, 0, width, height);    //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION);    //Select The Projection Matrix
        gl.glLoadIdentity();                    //Reset The Projection Matrix

        //Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);    //Select The Modelview Matrix
        gl.glLoadIdentity();                    //Reset The Modelview Matrix

        // Get preferences for disc1 speed pantazis.25.01.15
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
        boolean speedOn = sharedPref.getBoolean("speed", false);
        if (!speedOn) ultimateSpeed = 0;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        // Load the texture for the square
        square.loadGLTexture(gl, this.context);

        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping ( NEW )
        gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
        //gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
        gl.glClearDepthf(1.0f);                    //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);            //The Type Of Depth Testing To Do

        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float angle) {
        speed = angle;
    }
}