package com.example.massfilecopyexample;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.example.massfilecopyexample.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private static final int PROGRESS_DIALOG_ONE = 1;

	private Button btnList;
	private Button btnCopy;
	private TextView fileListTextView;
	private TextView resultTextView;

	private String targetDirPath = "/girlsday";
	private String targetFilePath = "/girlsday.mp4";
	private String targetFileName = "girlsday.mp4";

	private String content = "";
	private boolean flag = false;

	private ProgressDialog progressDialog;

	public String getDir(String path) {
		return getSdcard().getPath() + path;
	}

	public File getSdcard() {
		return Environment.getExternalStorageDirectory();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnList = (Button)findViewById(R.id.btnList);
		btnCopy = (Button)findViewById(R.id.btnCopy);
		fileListTextView = (TextView)findViewById(R.id.fileListTextView);
		resultTextView = (TextView)findViewById(R.id.resultTextView);

		btnList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d(TAG, getSdcard().toString());

				File[] list = getSdcard().listFiles();
				if(list != null) {
					for(int i = 0; i < list.length; i++) {
						Log.d(TAG, list[i].getPath());

						content = content.concat(list[i].getName() + "\n");
						if(list[i].getName().equals(targetFileName)) {
							flag = true;
						}
					}
				}

				Log.d(TAG, content);
				fileListTextView.setText(content);
				if(flag) {
					resultTextView.setText(R.string.exist_girlsday);
				} else {
					resultTextView.setText(R.string.not_exist_girlsday);
				}
			}
		});

		btnCopy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new AsyncTaskThreadImpl().execute(0);

			}
		});
	}

	private  class  AsyncTaskThreadImpl extends AsyncTask<Integer, Integer, String>{

		private boolean threadFlag = false;

		//��׶��� �۾��� ȣ��Ǳ����� ����
		//���� ��׶��� �۾��� ����Ǳ� ���� �����ؾ� �ϴ� �ʱ�ȭ �۾��� ����
		//Main UI Thread�������� ���� ��
		@Override
		public void onPreExecute(){
			//���α׷��� ���̾� �α׸� ���� �Ѵ�.
			showDialog(PROGRESS_DIALOG_ONE, null); 
		}

		//��׶��忡�� ���� �� ������ ���⿡ ���� 
		@Override
		public String doInBackground(Integer... startTaskObj){
			while (!threadFlag){
				try {
					Thread.sleep(500);
					fileCopy(getDir(targetFilePath), getDir(targetDirPath + targetFilePath));
					threadFlag = true;
				}catch(InterruptedException e) {
					//���ͷ�Ʈ�� �ɸ��� ���� ��
					Log.e("BACK_THREAD_TAG", "������ ���ͷ�Ʈ ���� ��!");
					threadFlag = true;
				}
			}
			return "���� ���� �Ϸ�" ;
		}

		//doInBackground(,) �޼ҵ尡 ����� �� UI Thread���� ����Ǿ�� �ϴ�
		//������ ���⼭ ����
		@Override
		public void onPostExecute(String resultMessage){
			resultTextView.setText(resultMessage);
			dismissDialog(PROGRESS_DIALOG_ONE);
		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case PROGRESS_DIALOG_ONE:
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setTitle(R.string.file_copy_text);
			progressDialog.setMessage("��ø� ��ٷ� �ּ���.");
			return progressDialog;
		default:
			break;
		}
		return super.onCreateDialog(id, args);
	}

	//������ �����ϴ� �޼ҵ�
	public void fileCopy(String inFileName, String outFileName) {
		File dir = new File(getDir(targetFilePath));
		if(!dir.exists()) {
			Log.i(TAG, "f not exist!");
			if(!dir.mkdir()) {
				Log.e(TAG, "���丮 ���� ����!");
			}

		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(inFileName));
			bos = new BufferedOutputStream(new FileOutputStream(outFileName));

			int data = 0;
			while((data=bis.read()) != -1) {
				bos.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException ioe) {}
			}
			if(bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException ioe) {}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

