package com.pyo.android.widget.expension;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class ThreadProcessDialogActivity extends Activity{
	
   private static final String DEBUG = "ThreadProcessDialogActivity";
   private ProgressBar mProgress1;
   private ProgressBar mProgress2;
   private ProgressBar mProgress3;
   private int mProgressStatus = 0;
   private int mProgress2Status = 0;
   private int mProgress3Status = 0;
   private Activity currentActivity = this;
   
   private Handler mHandler = new Handler();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
		
     requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
     requestWindowFeature(Window.FEATURE_PROGRESS);
     setProgress(10000);
		
     setContentView(R.layout.progress_layout);
		
     final Chronometer timer = (Chronometer)findViewById(R.id.Chronometer01);
         
     mProgress1 = (ProgressBar)findViewById(R.id.progressBar1);
       new Thread(new Runnable() {
	    public void run() {
	      while (mProgressStatus < 100){
	       try {
		    synchronized (this) {
		       wait(50);
		    }
	      }catch(InterruptedException e) {
		    Log.e(DEBUG, "wait failed1", e);
	      }
	      mProgressStatus++;

	      //진행바 갱신
	      mHandler.post(new Runnable() {
	       public void run() {
		      mProgress1.setProgress(mProgressStatus);
	        }
	      });
	     } //while문 끝
	  }
	}).start();	
     mProgress2 = (ProgressBar) findViewById(R.id.progressBar2);
       new Thread(new Runnable() {
	    public void run() {
	      timer.start(); //크로노미터 시작
	      while (mProgress2Status < 100) {
	      try {
	        synchronized (this) {
		     wait(100);
	        }
	      }catch(InterruptedException e) {
		    Log.e(DEBUG, "wait failed2", e);
	      }
	     mProgress2Status++;
	     mHandler.post(new Runnable() {
	       public void run(){
		    mProgress2.setProgress(mProgress2Status/4);
		    mProgress2.setSecondaryProgress(mProgress2Status);
		    currentActivity.setProgress(mProgress2Status*100);
	       }
	     });
	   } //while문 끝 
	   mHandler.post(new Runnable(){
	    public void run() {
	      ProgressBar progresstop = (ProgressBar)findViewById(R.id.progressBar3);
	       progresstop.setVisibility(ProgressBar.GONE);			
	     }
	    });
	   timer.stop();
	  }
     }).start();
		
      mProgress3 = (ProgressBar) findViewById(R.id.progressBar3);
	   new Thread(new Runnable() {
	    public void run() {
	    while (mProgress3Status < 100){
	      try {
	       synchronized (this) {
	         wait(100);
	       }
	      }catch(InterruptedException e){
	      Log.e(DEBUG, "wait failed", e);
	     }
	     mProgress3Status++;
	    // 업데이트하기
	     mHandler.post(new Runnable(){
	     public void run() {
		  mProgress3.setProgress(mProgress3Status/4);
		  mProgress3.setSecondaryProgress(mProgress3Status);
	     }
	    });
	   }//while문 끝
	  }
        }).start();	

       SeekBar seek = (SeekBar) findViewById(R.id.seekbar1);
       seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
	     public void onProgressChanged(SeekBar seekBar, int progress,
					    boolean fromTouch) {		
	         Log.d(DEBUG, "progress = " + progress + " fromTouch = " + fromTouch);
	         ((TextView)findViewById(R.id.seek_text)).setText("값: "+progress);
	         seekBar.setSecondaryProgress((progress+seekBar.getMax())/2);			
         }
	    public void onStartTrackingTouch(SeekBar seekBar){}
        public void onStopTrackingTouch(SeekBar seekBar){} 	
       });
       
       RatingBar rate = (RatingBar) findViewById(R.id.ratebar1);
       rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
	    public void onRatingChanged(RatingBar ratingBar, float rating,
				   boolean fromTouch) {
	       ((TextView)findViewById(R.id.rating_text)).setText("Rating 값 : "+ rating);
	    }
       });   
       registerForContextMenu(timer);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      super.onContextItemSelected(item);  
      Chronometer timer = (Chronometer)findViewById(R.id.Chronometer01);
      switch (item.getItemId()){
         case R.id.stop_timer:
             timer.stop();
             break;
         case R.id.start_timer:
             timer.start();
             break;
         case R.id.reset_timer:
             timer.setBase(SystemClock.elapsedRealtime());
             break;
      }
      return true;
    }
    @Override
    public void onCreateContextMenu(
        ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);   
        if (v.getId() == R.id.Chronometer01){
            getMenuInflater().inflate(R.menu.timer_context, menu);
            menu.setHeaderIcon(android.R.drawable.ic_media_play)
                             .setHeaderTitle("타이머 제어");
        }
    }
}