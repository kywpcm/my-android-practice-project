package com.pyo.android.socket.echo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class AndroidClientEchoActivity extends Activity {

	private static final String CLASSTAG = 
			AndroidClientEchoActivity.class.getSimpleName();
	private EditText ipAddress;
	private EditText port;
	private EditText socketInput;
	private EditText socketOutput;
	private Button scoketConnection;
	@Override
	public void onCreate(final Bundle saveInstanceBundle) {
		super.onCreate(saveInstanceBundle);
		setContentView(R.layout.simple_socket);

		ipAddress = (EditText) findViewById(R.id.socket_ip);
		port = (EditText) findViewById(R.id.socket_port);
		socketInput = (EditText) findViewById(R.id.socket_input);
		socketOutput = (EditText) findViewById(R.id.socket_output);
		scoketConnection = (Button) findViewById(R.id.socket_connection);

		scoketConnection.setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				socketOutput.setText("");
				String output = sendMessageServerSocket(
						ipAddress.getText().toString().trim(), 
						port.getText().toString(), 
						socketInput.getText().toString());
				socketInput.setText("");
				socketOutput.setText(output);
				socketInput.requestFocus();
			}
		});
	}
	private String sendMessageServerSocket(final String ip, final String port, 
			final String socketData){
		Socket socket = null;
		BufferedWriter toServer = null;
		BufferedReader fromServer = null;
		String  echoMessage = null;
		try {

			socket = new Socket(ip, Integer.parseInt(port));

			toServer = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			fromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			// 메세지 보내기
			String input = socketData;
			toServer.write(input + "\n");
			toServer.flush();
			//반향 문자 받기
			echoMessage = fromServer.readLine();
		} catch (IOException e) {
			Log.e(CLASSTAG, "  메세지 보내는 중 에러 발생! ", e);
		} finally {
			try {
				toServer.close();
			} catch (IOException e) {}
			try {
				fromServer.close();
			} catch (IOException e) {}
			try {
				socket.close();
			} catch (IOException e) {}           
		}     
		return echoMessage;
	};
}