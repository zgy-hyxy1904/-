package com.bicsoft.sy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.BufferedOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp文件上传及下载
 * 
 * @Author Gaohua
 * @Version 2015/09/01
 */
public class FTPUtil {
	private static final Logger log = LoggerFactory.getLogger(FTPUtil.class);

	private FTPClient ftpClient;
	private final int port = 21;
	private final String path = "suyuanxitong/";
	private final String addr = "123.124.236.178";
	private final String username = "bocode";
	private final String password = "hyzx1234";

	public FTPUtil(){
		this.connect();
	}
	
	// 连接远程服务器
	private boolean connect() {
		boolean result = false;
		this.ftpClient = new FTPClient();
		int reply;
		try {
			this.ftpClient.connect(addr, port);
			this.ftpClient.login(username, password);
			this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				return result;
			}
			this.ftpClient.changeWorkingDirectory(path);
		} catch (Exception e) {
			log.error("connect ftp server error!");
		}
		result = true;
		return result;
	}

	// 上传文件至ftp服务器
	public boolean upload(String fileName) {
		File file = new File(fileName);
		File file2 = new File(file.getPath());
		try {
			FileInputStream input = new FileInputStream(file2);
			this.ftpClient.enterLocalPassiveMode();
			this.ftpClient.storeFile(file2.getName(), input);
			input.close();
		} catch (Exception e) {
			log.error("update file error!");
			return false;
		}
		return true;
	}

	// 压缩文件
	public boolean createZipFile(List<String> fileList, String zipFileName) {
		ZipOutputStream out = null;
		try {
			int len;
			zipFileName = zipFileName + ".zip";
			byte[] buf = new byte[1024];
			out = new ZipOutputStream(new FileOutputStream(zipFileName));

			for (String fileName : fileList) {
				File srcFile = new File(fileName);
				if(!srcFile.exists()) {
					log.error(fileName + " is not existed!!!" );
					continue;
				}
				FileInputStream inputStream = new FileInputStream(srcFile);

				out.putNextEntry(new ZipEntry(srcFile.getName()));

				while ((len = inputStream.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				out.closeEntry();
				inputStream.close();
			}
			out.close();
		} catch (Exception e) {
			log.error("create zip file error!");
			return false;
		} finally{
			try{
				if(out != null) out.close();
			}catch(IOException ie){
				ie.printStackTrace();
			}
		}

		return true;
	}

	// 取指定型ftp目录文件列表
	public List<String> getFileList(String ftpDir) {
		List<String> fileList = new ArrayList<String>();
		try {
			boolean changeStatus = this.ftpClient.changeWorkingDirectory(ftpDir);
			if(!changeStatus){
				log.debug(ftpDir+"目录不存在");
				return fileList;
			}
			this.ftpClient.enterLocalPassiveMode();
			FTPFile[] files = this.ftpClient.listFiles();
			for (FTPFile file : files) {
				if (file.isFile()) {
					fileList.add(file.getName());
				} else if (file.isDirectory()) {
					getFileList(ftpDir + file.getName() + "/");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("get file list error!");
		}
		return fileList;
	}

	//
	public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
		String strFilePath = localPath + localFileName;
		BufferedOutputStream outStream = null;
		boolean success = false;
		try {
			this.ftpClient.changeWorkingDirectory(remotePath);
			this.ftpClient.enterLocalPassiveMode();
			outStream = new BufferedOutputStream(new FileOutputStream(strFilePath));
			success = this.ftpClient.retrieveFile(remoteFileName, outStream);
			if (success == true) {
				return success;
			}
		} catch (Exception e) {
			log.error("download file error!");
			return false;
		} finally {
			if (null != outStream) {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	public List<String> downFtpFile(String reportPath, String localPath) {
		List<String> resultList = new ArrayList<String>();
		List<String> fileList = this.getFileList(reportPath);
		for (String fileName : fileList) {
			String localFileName = CommonUtil.getUUID();
			int index = fileName.lastIndexOf(".");
			if (index > -1) {
				String extName = fileName.substring(index);
				localFileName += extName;
			}
			downloadFile(reportPath, fileName, localPath, localFileName);
			resultList.add(localFileName);
		}
		return resultList;
	}
	
	public void disconnect(){
		if(this.ftpClient != null){
			try{
				this.ftpClient.disconnect();
			}catch(Exception e){
				
			}
		}
	}

	/*public static void main(String[] args) throws Exception {
		FTPUtil ftp = new FTPUtil();
		List<String> fileList= ftp.downFtpFile("/checkPicUrl/WCQSE000110201508280001", "D:\\");
		ftp.disconnect();
		//String s = StringUtils.join(fileList.toArray(), ",");
		//System.out.println(s);
	}*/
}
