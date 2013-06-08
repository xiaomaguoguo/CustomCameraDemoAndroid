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

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	ImageView iview;
	SurfaceHolder sHolder;
	SurfaceView sView;
	Camera mcam;
	Button btn_capture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		iview = (ImageView) findViewById(R.id.imageView1);
		sView = (SurfaceView) findViewById(R.id.surfaceView1);
		btn_capture = (Button) findViewById(R.id.button1);
		sHolder = sView.getHolder();

		sHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0

		sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		 final PictureCallback mpic = new PictureCallback() {

			@Override
			public void onPictureTaken(byte[] arg0, Camera arg1) {

				Bitmap bit = BitmapFactory
						.decodeByteArray(arg0, 0, arg0.length);
				iview.setImageBitmap(bit);

			}

		};

		sHolder.addCallback(this);
		btn_capture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
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

		mcam.release();

	}

}
