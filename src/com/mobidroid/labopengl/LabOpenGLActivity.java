package com.mobidroid.labopengl;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class LabOpenGLActivity extends Activity {
			
	private GLSurfaceView mGLView;
    private OpenGLES10Renderer mRenderer;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        mGLView = new OpenGLSurfaceView(this);
        setContentView(mGLView);        
    }
        
    @Override
    protected void onPause() {
        super.onPause();
        // Ici on fait simplement une pause du rendu
        // cependant si notre application utilise beaucoup
        // de memoire pour faire le rendu il serait preferable
        // d'ajouter une petit ménage de la mémoire 
        mGLView.onPause();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // L'action devrait reprendre ici, si jamais 
        // on avait fait un ménage de la mémoire ici
        // on rechargerait les trucs en mémoire
        mGLView.onResume();
    }
    
         
    class OpenGLSurfaceView extends GLSurfaceView {
    	
        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private float mPreviousX;
        private float mPreviousY;

        public OpenGLSurfaceView(Context context){
            super(context);

            // Set the Renderer for drawing on the GLSurfaceView
            mRenderer = new OpenGLES10Renderer();
            setRenderer(mRenderer);

            // Render the view only when there is a change
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            
        }
                       
        @Override 
        public boolean onTouchEvent(MotionEvent e) {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position changed.

            float x = e.getX();
            float y = e.getY();
            
            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:
        
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;
        
                    // reverse direction of rotation above the mid-line
                    if (y > getHeight() / 2) {
                      dx = dx * -1 ;
                    }
        
                    // reverse direction of rotation to left of the mid-line
                    if (x < getWidth() / 2) {
                      dy = dy * -1 ;
                    }
                  
                    mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;
                    requestRender();
            }

            mPreviousX = x;
            mPreviousY = y;
            return true;
        } 
    }
}