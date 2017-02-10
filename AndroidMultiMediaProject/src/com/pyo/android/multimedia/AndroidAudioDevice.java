package com.pyo.android.multimedia;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AndroidAudioDevice {
	/** the audio track **/
	private final AudioTrack track;
	
	/** 음원 재생을 위하 버퍼 **/
	private short[] buffer = new short[1024];
	//채널 형태(모노, 스테레오)
	private final boolean isMono;
	
	AndroidAudioDevice( boolean isMono ){
		this.isMono = isMono;
		//오디오 출력이 가능한 최소 출력 버퍼를 설정한 후 음원을 조합
		int minSize =AudioTrack.getMinBufferSize( 44100,
				       isMono? AudioFormat.CHANNEL_IN_MONO : 
				       AudioFormat.CHANNEL_IN_STEREO,
				       AudioFormat.ENCODING_PCM_16BIT );       
		//설정
	    track = new AudioTrack( AudioManager.STREAM_MUSIC, 44100, 
	    						     isMono?AudioFormat.CHANNEL_OUT_MONO:
	    						     AudioFormat.CHANNEL_OUT_STEREO,
	    						     AudioFormat.ENCODING_PCM_16BIT,
	                                 minSize, AudioTrack.MODE_STREAM);
	    //시작(write스트림을 통해 해당 음원을 재생)
	    track.play();
	}
	public AudioTrack  getAudioTrack(){
		return track;
	}
	public void dispose(){
		track.stop();
		track.release();
	}
	public boolean isMono(){
		return isMono;
	}
	public void writeSamples(short[] samples, int offset, int numSamples){
		int writtenSamples = track.write( samples, offset, numSamples );
		while( writtenSamples != numSamples )
			track.write( samples, offset + writtenSamples, numSamples - writtenSamples );
	}
	public void writeSamples(float[] samples, int offset, int numSamples){
		if( buffer.length < samples.length )
			buffer = new short[samples.length];
		
		int bound = offset + numSamples;
		for( int i = offset, j = 0 ; i < bound; i++, j++ ){
    		float fValue = samples[i];
    		if( fValue > 1 )
    			fValue = 1;
    		if( fValue < -1 )
    			fValue = -1;
            short value = (short)( fValue * Short.MAX_VALUE);				
			buffer[j] = value;
		}
		//정확히 재생이 된 스트림의 위치가 리턴(문제 발생시 에러 코드 발생)
		int writtenSamples = track.write( buffer, 0, numSamples );
		//같지 않다면 넘어온 부분부터 다시 재생
		while( writtenSamples != numSamples )
			track.write( buffer, writtenSamples, numSamples - writtenSamples );
	}
}