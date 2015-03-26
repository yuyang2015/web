package com.nova.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileTransmission {
	private final static String path = "http://112.64.16.63:8080/a.bam";

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("start=" + System.currentTimeMillis());
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");

		int code = conn.getResponseCode();
		if (code == 200) {
			int len = conn.getContentLength();
			RandomAccessFile file = new RandomAccessFile(getFilenName(path),
					"rwd");
			// 1.设置本地文件大小跟服务器的文件大小一致
			file.setLength(len);

			// 2 .假设开启3 个线程
			int threadnumber = 6;
			int blocksize = len / threadnumber;
			/**
			 * 线程1 0*blocksize 线程2 1*bolocksize ~ 2*blocksize 线程3 2*blocksize
			 * 文件末尾
			 */
			for (int i = 0; i < threadnumber; i++) {
				int startposition = i * blocksize;
				int endpositon = (i + 1) * blocksize;
				if (i == (threadnumber - 1)) {
					// 最后一个线程
					endpositon = len;
				}

				DownLoadTask task = new DownLoadTask(i, path, startposition,
						endpositon);
				task.start();
			}
		}
	}

	public static String getFilenName(String path) {
		int start = path.lastIndexOf("/") + 1;
		return path.substring(start, path.length());
	}

}

class DownLoadTask extends Thread {
	public static final String path = "http://112.64.16.63:8080/a.bam";
	int threadid;
	String filepath;
	int startposition;
	int endpositon;

	public DownLoadTask(int threadid, String filepath, int startposition,
			int endpositon) {
		this.threadid = threadid;
		this.filepath = filepath;
		this.startposition = startposition;
		this.endpositon = endpositon;
	}

	@Override
	public void run() {
		try {
			File postionfile = new File(threadid + ".txt");
			URL url = new URL(filepath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			System.out.println("线程" + threadid + "正在下载 " + "开始位置 : "
					+ startposition + "结束位置 " + endpositon);

			if (postionfile.exists()) {
				FileInputStream fis = new FileInputStream(postionfile);
				byte[] result = StreamTool.stream2Byte(fis);
				int newstartposition = Integer.parseInt(new String(result));
				if (newstartposition > startposition) {
					startposition = newstartposition;
				}
			}
			conn.setRequestProperty("Range", "bytes=" + startposition + "-"
					+ endpositon);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(50000);

			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");

			InputStream is = conn.getInputStream();
			RandomAccessFile file = new RandomAccessFile(getFilenName(path),
					"rwd");
			// 设置数据从哪个位置开始写
			file.seek(startposition);
			byte[] buffer = new byte[1024];
			int leng = 0;
			// 代表当前读到的服务器数据的位置，同时这个值是已经存储的位置
			int currentPostition = startposition;
			// 创建一个文件对象 记录当前某个文件的下载地址
			while ((leng = is.read(buffer)) != -1) {
				file.write(buffer, 0, leng);
				currentPostition += leng;// 每次写完，当前的位置加上leng 持久化到存储设备里
				String positon = currentPostition + "";
				FileOutputStream fos = new FileOutputStream(postionfile);
				fos.write(positon.getBytes());
				fos.flush();
				fos.close();
			}
			file.close();
			System.out.println("end**=" + System.currentTimeMillis());
			System.out.println("线程" + threadid + "下载完毕");
			System.out.println("线程" + threadid + "开始位置" + startposition
					+ "结束位置" + endpositon);
			// 当线程下载完毕后，把文件删除掉
			if (postionfile.exists()) {
				postionfile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}

	public static String getFilenName(String path) {
		int start = path.lastIndexOf("/") + 1;
		return path.substring(start, path.length());
	}

}
