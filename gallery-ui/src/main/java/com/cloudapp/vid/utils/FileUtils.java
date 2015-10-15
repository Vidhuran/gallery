package com.cloudapp.vid.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileUtils {

	private static String basePath = PropertyUtils.getProperty(PropertyKey.BaseDirectory.getKey());
	private static String imagePath = basePath + "images";
	private static String webRoot = PropertyUtils.getProperty(PropertyKey.WebRoot.getKey());
	
	public static String getWebRoot() {
		return webRoot;
	}

	public static void setWebRoot(String webRoot) {
		FileUtils.webRoot = webRoot;
	}

	private Logger LOG = Logger.getLogger(FileUtils.class.getName());
	
	public static List<String> getImagesList(String folder) throws IOException {
		return Files.walk(Paths.get(imagePath + "/" + folder))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList())
                .stream()
                .map((file) -> {
                	return webRoot + folder + "/" + file.getName();
                }).collect(Collectors.toList());
		
	}
	
	public static List<File> getFilesForDownload(String folder) throws IOException {
		return Files.walk(Paths.get(imagePath + "/" + folder))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
	}
}
