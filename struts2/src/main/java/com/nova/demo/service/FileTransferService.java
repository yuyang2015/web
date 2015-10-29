package com.nova.demo.service;

import javax.jws.WebService;

@WebService
public interface FileTransferService {
	public boolean uploadData(String fileName, long startPosition, byte[] buffer);

	public boolean initData(long length, String fileName);
}