package com.example.androidcustomintentsample;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class InternetViewResAction {

	public InternetViewResAction() {}
	
	public void excuteAction(InternetViewReqActivity reqActivity, HashMap<String, String> contentMaps) throws IOException {
		Set<String> keys = contentMaps.keySet();
		Iterator<String> schemes = keys.iterator();
		while(schemes.hasNext()) {
			String scheme = schemes.next();
			String domain = contentMaps.get(scheme);
//			File path = new File("C:\\Program Files\\Google\\Chrome\\Application\\chrome ");
			Process proc = Runtime.getRuntime().exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome " + scheme.concat(domain));
			reqActivity.onActivityResult("정상 실행 됐네요!");
		}
	}
	
}
