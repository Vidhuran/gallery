package com.cloudapp.vid.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DownloadHelper {

	public static ByteArrayOutputStream getFileStream(String folder) {
		
		System.out.println("Helper folder : " + folder);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos);

		try {
			FileUtils.getFilesForDownload(folder).forEach((file) -> {
				try {
					FileInputStream fis = new FileInputStream(file);
					ZipEntry zipEntry = new ZipEntry(file.getName());
					zos.putNextEntry(zipEntry);
					byte[] bytes = new byte[1024];
					int length;
					while ((length = fis.read(bytes)) >= 0) {
						zos.write(bytes, 0, length);
					}
					zos.closeEntry();
					fis.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		try {
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bos;
	}
}
