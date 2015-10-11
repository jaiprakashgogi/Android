package sample.ble.sensortag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class DisplayActivity extends Activity{
	
	private String userid;
	private int i;
	private String site_id;
	WebView myWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);
		userid = getIntent().getStringExtra("user_id");
		i = getIntent().getIntExtra("id", 0);
		site_id = getIntent().getStringExtra("site_id");
		Log.i("JAI", userid + " : "+ i + " : " + site_id);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String weblink = "http://www.mohshia.byethost12.com/handshake/update.php?product_code="+ Integer.toString(i);
		Log.i("JAI", weblink);
		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl(weblink);	
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	    if (myWebView.canGoBack()) {
	    	myWebView.goBack();
	        return;
	    }
		super.onBackPressed();
	}
	
}
