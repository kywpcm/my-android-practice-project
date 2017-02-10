package com.pyo.android.multimedia;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AndroidAudioDevice {
	/** the audio track **/
	private final AudioTrack track;
	
	/** ���� ����� ���� ���� **/
	private short[] buffer = new short[1024];
	//ä�� ����(���, ���׷���)
	private final boolean isMono;
	
	AndroidAudioDevice( boolean isMono ){
		this.isMono = isMono;
		//����� ����� ������ �ּ� ��� ���۸� ������ �� ������ ����
		int minSize =AudioTrack.getMinBufferSize( 44100,
				       isMono? AudioFormat.CHANNEL_IN_MONO : 
				       AudioFormat.CHANNEL_IN_STEREO,
				       AudioFormat.ENCODING_PCM_16BIT );       
		//����
	    track = new AudioTrack( AudioManager.STREAM_MUSIC, 44100, 
	    						     isMono?AudioFormat.CHANNEL_OUT_MONO:
	    						     AudioFormat.CHANNEL_OUT_STEREO,
	    						     AudioFormat.ENCODING_PCM_16BIT,
	                                 minSize, AudioTrack.MODE_STREAM);
	    //����(write��Ʈ���� ���� �ش� ������ ���)
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
		//��Ȯ�� ����� �� ��Ʈ���� ��ġ�� ����(���� �߻��� ���� �ڵ� �߻�)
		int writtenSamples = track.write( buffer, 0, numSamples );
		//���� �ʴٸ� �Ѿ�� �κк��� �ٽ� ���
		while( writtenSamples != numSamples )
			track.write( buffer, writtenSamples, numSamples - writtenSamples );
	}
}