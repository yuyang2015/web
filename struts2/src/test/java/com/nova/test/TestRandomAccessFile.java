package com.nova.test;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TestRandomAccessFile {
	final static String fileName = "/Users/summer/Documents/data/rtest.dat";

	public static void main(String[] args) throws Exception {
		RandomAccessFile rf = new RandomAccessFile("aa.txt", "rw");
		for (int i = 0; i < 10000; i++) {
			rf.setLength(10000l);
			rf.writeChars("a");
		}
		rf.seek(0);
		byte[] buffer = new byte[1024];
		int i = 0;
		int len = 0;
		while ((len = rf.read(buffer)) != -1) {
			System.out.println(len + "--" + i++);
		}
	}

	public static void demo1() throws Exception {
		RandomAccessFile rf = new RandomAccessFile(fileName, "rw");
		for (int i = 0; i < 10; i++) {
			// 写入基本类型double数据
			rf.writeDouble(i * 1.414);
		}
		rf.close();
		rf = new RandomAccessFile(fileName, "rw");
		// 直接将文件指针移到第5个double数据后面
		rf.seek(5 * 8);
		// 覆盖第6个double数据
		rf.writeDouble(47.0001);
		rf.close();
		rf = new RandomAccessFile(fileName, "r");
		for (int i = 0; i < 10; i++) {
			System.out.println("Value " + i + ": " + rf.readDouble());
		}
		rf.close();
	}

	public static void demo2() throws Exception {
		int length = 0x8000000;
		// 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。
		FileChannel fc = new RandomAccessFile(fileName, "rw").getChannel();
		// 注意，文件通道的可读可写要建立在文件流本身可读写的基础之上
		MappedByteBuffer out = fc
				.map(FileChannel.MapMode.READ_WRITE, 0, length);
		// 写128M的内容
		for (int i = 0; i < length; i++) {
			out.put((byte) 'x');
		}
		System.out.println("Finished writing");
		// 读取文件中间6个字节内容
		for (int i = length / 2; i < length / 2 + 6; i++) {
			System.out.print((char) out.get(i));
		}
		fc.close();
	}

	public static void demo3() throws Exception {
		RandomAccessFile file = new RandomAccessFile("file", "rw");
		// 以下向file文件中写数据
		file.writeInt(20);// 占4个字节
		file.writeDouble(8.236598);// 占8个字节
		file.writeUTF("这是一个UTF字符串");// 这个长度写在当前文件指针的前两个字节处，可用readShort()读取
		file.writeBoolean(true);// 占1个字节
		file.writeShort(395);// 占2个字节
		file.writeLong(2325451l);// 占8个字节
		file.writeUTF("又是一个UTF字符串");
		file.writeFloat(35.5f);// 占4个字节
		file.writeChar('a');// 占2个字节

		file.seek(0);// 把文件指针位置设置到文件起始处

		// 以下从file文件中读数据，要注意文件指针的位置
		System.out.println("——————从file文件指定位置读数据——————");
		System.out.println(file.readInt());
		System.out.println(file.readDouble());
		System.out.println(file.readUTF());

		file.skipBytes(3);// 将文件指针跳过3个字节，本例中即跳过了一个boolean值和short值。
		System.out.println(file.readLong());

		file.skipBytes(file.readShort()); // 跳过文件中“又是一个UTF字符串”所占字节，注意readShort()方法会移动文件指针，所以不用加2。
		System.out.println(file.readFloat());

		// 以下演示文件复制操作
		System.out.println("——————文件复制（从file到fileCopy）——————");
		file.seek(0);
		RandomAccessFile fileCopy = new RandomAccessFile("fileCopy", "rw");
		int len = (int) file.length();// 取得文件长度（字节数）
		byte[] b = new byte[len];
		file.readFully(b);
		fileCopy.write(b);
		System.out.println("复制完成！");
	}

	public static void demo4(long skip, String str, String fileName)
			throws Exception {
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			if (skip < 0 || skip > raf.length()) {
				System.out.println("跳过字节数无效");
				return;
			}
			byte[] b = str.getBytes();
			raf.setLength(raf.length() + b.length);
			for (long i = raf.length() - 1; i > b.length + skip - 1; i--) {
				raf.seek(i - b.length);
				byte temp = raf.readByte();
				raf.seek(i);
				raf.writeByte(temp);
			}
			raf.seek(skip);
			raf.write(b);
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
