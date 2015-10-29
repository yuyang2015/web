package com.nova.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.io.FileUtils;

import com.nova.demo.service.FileTransferService;
import com.nova.demo.service.HelloService;

public class TestWebService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileUpload();
	}

	public static void demo() {
		try {
			URL url = new URL("http://localhost:8080/web/service/hello?wsdl");
			QName qname = new QName("http://service.demo.nova.com/",
					"HelloServiceImplService");
			Service service = Service.create(url, qname);
			HelloService hello = service.getPort(HelloService.class);
			String result = hello.say("Demo");
			System.out.println(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void FileUpload() {
		String filePath = "/Users/summer/Documents/data/1.bam";
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,
				filePath.length());
		File file = new File(filePath);
		long len = file.length();
		System.out.println("上传文件的长度：" + len);

		try {
			URL url = new URL(
					"http://114.215.159.138:8080/web/service/transfer?wsdl");
			QName qname = new QName("http://service.demo.nova.com/",
					"FileTransferServiceImplService");
			Service service = Service.create(url, qname);
			FileTransferService fts = service
					.getPort(FileTransferService.class);
			boolean result = fts.initData(len, fileName);
			if (result) {
				long threadnumber = 6;
				long blocksize = len / threadnumber;
				for (int i = 0; i < threadnumber; i++) {
					long startposition = i * blocksize;
					long endpositon = (i + 1) * blocksize;
					if (i == (threadnumber - 1)) {
						// 最后一个线程
						endpositon = len;
					}
					UploadTask ut = new UploadTask(i, fileName, filePath,
							startposition, endpositon);
					ut.start();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

}

class UploadTask extends Thread {
	int threadid;
	String fileName;
	String filePath;
	long startPosition;
	long endPosition;

	public UploadTask(int threadid, String fileName, String filePath,
			long startPosition, long endPosition) {
		this.threadid = threadid;
		this.fileName = fileName;
		this.filePath = filePath;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	@Override
	public void run() {
		System.out.println("当前线程为:" + threadid + ",线程开始位置：" + startPosition
				+ ",线程结束位置：" + endPosition);
		File postionlog = new File(threadid + ".txt");
		try {
			if (postionlog.exists()) {
				long newstartposition = Long.parseLong(FileUtils
						.readFileToString(postionlog));
				System.out.println("newstartposition:" + newstartposition);
				if (newstartposition > startPosition) {
					startPosition = newstartposition;
				}
			}
			URL url = new URL(
					"http://114.215.159.138:8080/web/service/transfer?wsdl");
			QName qname = new QName("http://service.demo.nova.com/",
					"FileTransferServiceImplService");
			Service service = Service.create(url, qname);
			FileTransferService fts = service
					.getPort(FileTransferService.class);

			RandomAccessFile file = new RandomAccessFile(filePath, "rwd");
			file.seek(startPosition);
			byte[] buffer = new byte[102400];
			// 代表当前读到的服务器数据的位置，同时这个值是已经存储的位置
			long currentPostition = startPosition;
			// 创建一个文件对象 记录当前某个文件的下载地址
			int i = 0;
			while ((endPosition - currentPostition > 102400)
					&& (i = file.read(buffer)) != -1) {
				fts.uploadData(fileName, currentPostition, buffer);

				currentPostition += i;// 每次写完，当前的位置加上i 持久化到存储设备里
				String positon = currentPostition + "";

				FileOutputStream fos = new FileOutputStream(postionlog);
				fos.write(positon.getBytes());
				fos.flush();
				fos.close();
			}
			// TODO 可以进行优化
			buffer = new byte[(int) (endPosition - currentPostition)];
			file.read(buffer);
			fts.uploadData(fileName, currentPostition, buffer);
			file.close();
			System.out.println("currentPostition" + currentPostition);
			if (postionlog.exists()) {
				postionlog.delete();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
