/*
 *  AudioTrack를 이용해 사인파를 출력시키는 코드
 */
package com.pyo.android.multimedia;

import android.app.Activity;
import android.media.AudioTrack;
import android.os.Bundle;

public class AudioTrackSineWaveActivity extends Activity {
    private AndroidAudioDevice device;
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_track_layout);
        new Thread( new Runnable( ){
           public void run( ){
             final float frequency = 440;
             //증가 할 각도의 원천값
             float increment = (float)(2*Math.PI) * frequency / 44100;
             float angle = 0;

             device = new AndroidAudioDevice( false);

             float samples[] = new float[1024];
             while( true ){
                for( int i = 0; i < samples.length; i++ ){
                	samples[i] = (float)Math.sin( angle );
                    angle += increment;
                }
                device.writeSamples( samples, 0, samples.length );
             }     
          }        
        }).start();
    }
	@Override
	public void onPause(){
		super.onPause();
		AudioTrack track = device.getAudioTrack();
		track.stop();
		track.release();
		track = null;
	}
}