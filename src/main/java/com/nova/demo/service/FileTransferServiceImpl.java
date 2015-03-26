package com.nova.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.jws.WebService;

@WebService(endpointInterface = "com.nova.demo.service.FileTransferService")
public class FileTransferServiceImpl implements FileTransferService {

	@Override
	public boolean uploadData(String fileName, long startPosition, byte[] buffer) {
		boolean result = false;
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.seek(startPosition);
			raf.write(buffer);
			raf.close();
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		FileTransferServiceImpl ss = new FileTransferServiceImpl();
		ss.uploadData(null, 0, null);
	}

	@Override
	public boolean initData(long length, String fileName) {
		boolean result = false;
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.setLength(length);
			raf.close();
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
