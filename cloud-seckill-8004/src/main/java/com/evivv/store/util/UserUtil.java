package com.evivv.store.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成用户工具类
 * <p>
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 *
 * @author zhoubin
 * @since 1.0.0
 */
public class UserUtil {
	private static void createUser(int count) throws Exception {

		File file = new File("/Users/wuyi/jmeterTestSameUser.txt");
		if (file.exists()) {
			file.delete();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		file.createNewFile();
		raf.seek(0);
		for (int i = 0; i < count; i++) {

			String row = "user:" + 1;
			raf.seek(raf.length());
			raf.write(row.getBytes());
			raf.write("\r\n".getBytes());
		}
		raf.close();
		System.out.println("over");
	}


	public static void main(String[] args) throws Exception {
		createUser(5000);
	}
}
