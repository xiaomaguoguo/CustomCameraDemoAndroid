package com.ziadgholmish.customcamera;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

//the acivity implements SurfaceHolder.Callback to call when the camera take the pic
public class MainActivity extends Activity implements SurfaceHolder.Callback {

        //image view to show the image that taken from the camera
	ImageView iview;
	//surface holder to deal with the surface view 
	SurfaceHolder sHolder;
	//surface holder that i will show the camera view in it
	SurfaceView sView;
	//declare camera variable
	Camera mcam;
	//button that capture the image 
	Button btn_capture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//get the image view from the layout
		iview = (ImageView) findViewById(R.id.imageView1);
		//get the surface view from the layout
		sView = (SurfaceView) findViewById(R.id.surfaceView1);
		//get the button
		btn_capture = (Button) findViewById(R.id.button1);
		//get the surface holder from the surface view and assign it to the sholder
		sHolder = sView.getHolder();
                //add listner to the sholder
		sHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0

               
		sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		 final PictureCallback mpic = new PictureCallback() {



                          //this method called back when the camera take pic 
			@Override
			public void onPictureTaken(byte[] arg0, Camera arg1) {

                                      //convert the byte array to bitmap
				Bitmap bit = BitmapFactory
						.decodeByteArray(arg0, 0, arg0.length);
						//set the bit mab to the image view
				iview.setImageBitmap(bit);

			}

		};

           
		sHolder.addCallback(this);
		
		
		//set the on clik actionlistner to the button
		btn_capture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//take a pic
				mcam.takePicture(null, null, mpic);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}




        
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		if (mcam != null) {

			try {
				
				mcam.setPreviewDisplay(sHolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mcam.startPreview();
			
		}else{
			
			mcam.open();
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		
		if(mcam == null){
		try {
		   mcam=Camera.open();
			mcam.setPreviewDisplay(sHolder);
			mcam.startPreview();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

                 //release camera
		mcam.release();

	}

}
