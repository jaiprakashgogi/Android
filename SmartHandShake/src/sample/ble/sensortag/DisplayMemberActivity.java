package sample.ble.sensortag;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMemberActivity extends Activity implements OnClickListener {

	private String userid = "aa";
	private int id;
	Button button;
	private final String TAG = "DisplayMemberActivity";
	TextView tvName, tvPhone, tvEmail, tvOrganization, tvDescription;
	ImageView iProfile, iFacebook, iGoogle, iTwitter, iLinkedIn, iInstagram;
	String[][] details = new String[][] {
			{ "Jai Prakash", "Shruti Dutta", "chirag Nagpal", "Shantam",
					"Mohit Chawla", "Deepak Kumar" },
			{ "+91-1111122222", "+91-1234567890", "+91-1212121212",
					"+91-9119119119", "+91-9119119118", "+91-9119119117", },
			{ "jai.prakash@gmail.com", "shruti.dutta@gmail.com",
					"chirag@gmail.com", "shantam@gmail.com",
					"mohit.chwla@gmail.com", "deepak@gmail.com", },
			{ "samsung", "DTU", "AIT", "PDPU", "ISM", "CSIR", },
			{ "Description: Working as a software Engineer at Samsung",
					"Description: Student at DTU",
					"Description: Student at AIT",
					"Description: Student at PDPU",
					"Description: Student at ISM",
					"Description: Working in CSIR", } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_member);
		userid = getIntent().getStringExtra("user_id");
		button = (Button) findViewById(R.id.button1);
		tvName = (TextView) findViewById(R.id.textView1);
		tvPhone = (TextView) findViewById(R.id.textView2);
		tvEmail = (TextView) findViewById(R.id.textView3);
		tvOrganization = (TextView) findViewById(R.id.textView4);
		tvDescription = (TextView) findViewById(R.id.textView5);
		iProfile = (ImageView) findViewById(R.id.imageView1);
		iFacebook = (ImageView) findViewById(R.id.imageView2);
		iGoogle = (ImageView) findViewById(R.id.imageView3);
		iTwitter = (ImageView) findViewById(R.id.imageView4);
		iLinkedIn = (ImageView) findViewById(R.id.imageView5);
		iInstagram = (ImageView) findViewById(R.id.imageView6);

		button.setOnClickListener(this);
		iFacebook.setOnClickListener(this);
		iGoogle.setOnClickListener(this);
		iTwitter.setOnClickListener(this);
		iLinkedIn.setOnClickListener(this);
		iInstagram.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// Log.i(TAG, "userid : " + userid);
		WebView myWebView1 = (WebView) findViewById(R.id.webview1);
		WebView myWebView2 = (WebView) findViewById(R.id.webview2);
		myWebView1.getSettings().setJavaScriptEnabled(true);
		myWebView2.getSettings().setJavaScriptEnabled(true);
		WebViewClient yourWebClient = new WebViewClient() {
			// Override page so it's load on my view only
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return true;
			}
		};
		myWebView1.setWebViewClient(yourWebClient);
		myWebView2.setWebViewClient(yourWebClient);
		super.onResume();
		if (userid.contains("aa")) {
			iProfile.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.jai));
			myWebView1.loadUrl("https://www.google.co.in/search?q=jaiprakashgogi");
			myWebView2.loadUrl("https://www.google.co.in/search?q=jaiprakashgogi&tbm=isch");
			id = 0;
		} else if (userid.contains("bb")) {
			iProfile.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.shr));
			myWebView1.loadUrl("https://www.google.co.in/search?q=shrutidutta+DTU");
			myWebView2.loadUrl("https://www.google.co.in/search?q=shrutidutta+DTU&tbm=isch");
			id = 1;
		} else if (userid.contains("cc")) {
			iProfile.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.chi));
			myWebView1.loadUrl("https://www.google.co.in/search?q=chirag+nagpal");
			myWebView2.loadUrl("https://www.google.co.in/search?q=chirag+nagpal&tbm=isch");
			id = 2;
		} else if (userid.contains("dd")) {
			iProfile.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.sha));
			myWebView1.loadUrl("https://www.google.co.in/search?q=shantam+gupta+PDPU");
			myWebView2.loadUrl("https://www.google.co.in/search?q=shantam+gupta+PDPU&tbm=isch");
			id = 3;
		} else if (userid.contains("ee")) {
			iProfile.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.moh));
			myWebView1.loadUrl("https://www.google.co.in/search?q=mohitchawla+ism");
			myWebView2.loadUrl("https://www.google.co.in/search?q=mohitchawla+ism&tbm=isch");
			id = 4;
		} else {
			iProfile.setImageBitmap(BitmapFactory.decodeResource(
					getResources(), R.drawable.dee));
			myWebView1.loadUrl("https://www.google.co.in/search?q=deepak+kumar+young+indians+kec");
			myWebView2.loadUrl("https://www.google.co.in/search?q=deepak+kumar+young+indians+kec&tbm=isch");
			id = 5;
		}
		tvName.setText(details[0][id]);
		tvPhone.setText(details[1][id]);
		tvEmail.setText(details[2][id]);
		tvOrganization.setText(details[3][id]);
		tvDescription.setText(details[4][id]);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DisplayActivity.class);
		intent.putExtra("user_id", userid);
		intent.putExtra("id", id);
		switch (arg0.getId()) {
		case R.id.button1:
			intent.putExtra("site_id", "web");
			break;
		case R.id.imageView1:
			intent.putExtra("site_id", "Facebook");
			break;
		case R.id.imageView2:
			intent.putExtra("site_id", "Google");
			break;
		case R.id.imageView3:
			intent.putExtra("site_id", "Twitter");
			break;
		case R.id.imageView4:
			intent.putExtra("site_id", "Linkedin");
			break;
		case R.id.imageView5:
			intent.putExtra("site_id", "instagram");
			break;
		}
		startActivity(intent);
	}

}
