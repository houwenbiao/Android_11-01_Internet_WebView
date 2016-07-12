package com.gree.hwb.webview;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/7/11.
 */
public class DownLoadThread extends Thread
{
	private String urlpath;

		public DownLoadThread(String url)
		{

			this.urlpath = url;
		}

		@Override
		public void run()
		{
			try
			{
				URL url = new URL(urlpath);
				URLConnection conn = url.openConnection();
				InputStream is = conn.getInputStream();
				Log.i("hwb","检测SD卡是否挂载");//因为genymotion没有SD卡，所以不能使用SD卡
				//检测sdcard是否挂载
	/*			if(Environment.getExternalStorageState() == (Environment.MEDIA_MOUNTED))
				{*/
					Log.i("hwb","SD卡存在");
					File file = new File("data//data//com.gree.hwb.webview", "yingyong.apk");
					if(!file.exists())
					{
						file.createNewFile();
					}
					FileOutputStream os = new FileOutputStream(file);
					byte[] array = new byte[1024];
					int index = is.read(array);
					while (index != -1)
					{
						os.write(array, 0, index);
						index = is.read(array);
					}
					if(os != null)
					{
						os.flush();
						os.close();
					}
					if(is != null)
					{
						is.close();
					}
				/*}*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
}
