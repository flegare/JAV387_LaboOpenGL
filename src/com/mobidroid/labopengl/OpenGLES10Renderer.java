package com.mobidroid.labopengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.SystemClock;
import android.util.Log;

public class OpenGLES10Renderer implements Renderer {
		
	private static final String TAG = OpenGLES10Renderer.class.getName();
	
	private FloatBuffer triangleVB;
	
	public float mAngle;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		Log.d(TAG, "setting up the surface");
        // Set the background frame color
        gl.glClearColor(0.7f, 0.9f, 0.2f, 1.0f);
        initShapes();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        //test
        gl.glEnable(GL10.GL_DEPTH_TEST);
        
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		
        // Fait un nettoyage de notre dernier rendu
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                
        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();   // On re-initialise la matrice de position de la camera 
        
        // On specifie ou la camera sera situé dans la scène (ici -5 sur l'axe des z)
        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                   
        // Create a rotation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        
        // Draw the triangle
        gl.glColor4f(0.1f, 0.1f, 0.8f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleVB);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        
                      
        //On utilise mAngle pour faire la rotation maintenant
        gl.glRotatef(mAngle, 0.0f, 0.0f, 1.0f);              
                                      
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		 
		
		gl.glViewport(0, 0, width, height);
		 
		Log.d(TAG, "surface changed");
		 
		// make adjustments for screen ratio
	    float ratio = (float) width / height;
	    gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
	    gl.glLoadIdentity();                        // reset the matrix to its default state	      
	    gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
	      
	}
	
	
	private void initShapes() {
				
	    float triangleCoords[] = {
	            // X, Y, Z
	            -0.5f, -0.25f, 0,
	             0.5f, -0.25f, 0,
	             0.0f,  0.559016994f, 0	             
	        };
	    
	    // initialize vertex Buffer for triangle  
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float (32bits))
                triangleCoords.length * 4);
        

        
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        triangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        triangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
        triangleVB.position(0);            // set the buffer to read the first coordinate

	}
	
}
