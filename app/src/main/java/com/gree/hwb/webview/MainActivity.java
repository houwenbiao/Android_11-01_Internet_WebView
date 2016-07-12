package com.gree.hwb.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity
{
	private WebView webView ;
	private Button btn_back , btn_refresh;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_refresh = (Button) findViewById(R.id.btn_reflesh);
		tv_title = (TextView) findViewById(R.id.tv_title);
		webView = (WebView) findViewById(R.id.wv_web);
		webView.loadUrl("http://www.baidu.com");//需要添加网络权限：uses-permission android:name="android.permission.INTERNET"
		//重写下面方法可以实现不使用系统浏览器打开网页
		webView.setWebChromeClient(new WebChromeClient()
		{
			@Override
			public void onReceivedTitle(WebView view, String title)
			{
				tv_title.setText(title);//获得网页标题
				super.onReceivedTitle(view, title);
			}
		});

		webView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});

		btn_back.setOnClickListener(new MyclickListener());
		btn_refresh.setOnClickListener(new MyclickListener());
		webView.setDownloadListener(new MyDownload());

	}

	class MyclickListener implements View.OnClickListener
	{

		@Override
		public void onClick(View view)
		{
			switch (view.getId())
			{
				case R.id.btn_back:

					if(webView.canGoBack())
					{
						webView.goBack();//返回上一界面
					}
					else
					{
						finish();//结束界面
					}
					break;
				case R.id.btn_reflesh:
					webView.reload();//刷新webview
					Toast.makeText(MainActivity.this,"刷新界面",Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
	class MyDownload implements DownloadListener
	{

		@Override
		public void onDownloadStart(String url, String s1, String s2, String s3, long l)
		{
			if(url.endsWith(".apk"))
			{
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				new DownLoadThread(url).start();
			}
		}
	}
}






















