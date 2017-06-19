package com.bhz.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

public class FileUtil {
	
	public static StringBuffer readLines(String fileName, int lines) throws IOException {
		StringBuffer sb = new StringBuffer("");
		RandomAccessFile raf = null;
		try {
			synchronized (FileUtil.class) {
				File file = new File(fileName);
				if (file.exists()) {
					raf = new RandomAccessFile(fileName, "r");
					String tempLine = null;
					for (int i = 0; i < lines; i++) {
						if ((tempLine = raf.readLine()) != null) {
							sb.append(tempLine + "\r\n");
						}
					}
				}
				return sb;				
			}
		} finally {
			if(raf!=null)
				raf.close();
		}
	}
	
	public static void appendLine(String fileName, String text) throws IOException {
		FileWriter fw = null;
	    BufferedWriter bw = null;
		try {
			synchronized (FileUtil.class) {
				File file=new File(fileName);
	        	if(!file.exists())
	                file.createNewFile();
				fw = new FileWriter(fileName, true);
				bw = new BufferedWriter(fw);
			    bw.write(text);
			    bw.newLine();
			    bw.flush();
			}
		} finally {
			if(fw!=null)
				fw.close();
			if(bw!=null)
				bw.close();  
		}
	}
	
	public static void deleteLines(String fileName, int lines) throws IOException {
		Boolean deleteAll = false;
		RandomAccessFile raf = null;
		RandomAccessFile raf2 = null;
		try {
			synchronized (FileUtil.class) {
				File f = new File(fileName);
				if(!f.exists())
					return;
				String dir = f.getParent();
				raf = new RandomAccessFile(fileName, "r");
				for (int i = 0; i < lines; i++) {
					if (raf.readLine() == null) {
						deleteAll = true;
						break;
					}
				}
				
				File temp = new File(dir + "temp_aaa.txt");
				if (temp.exists()) {
					temp.delete();
				}
				raf2 = new RandomAccessFile(dir + "temp_aaa.txt", "rw");
				if (!deleteAll) {
					String tempLine = null;
					while ((tempLine = raf.readLine()) != null) {
						raf2.writeBytes(tempLine);
						raf2.writeBytes("\r\n");
					}
				}
				
				raf.close();
				if (f.exists()) {
					f.delete();
				}
				if(raf2!=null)
					raf2.close();
				new File(dir + "temp_aaa.txt").renameTo(new File(fileName));
			}
		} finally {
			if(raf!=null)
				raf.close();
			if(raf2!=null)
				raf2.close();			
		}
	}
	
	public static boolean writeFile(InputStream is,String fileName)throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        FileOutputStream out = null;
        boolean flag = false;
        try {
        	synchronized (FileUtil.class) {
	        	File file=new File(fileName);
	        	if(!file.exists())
	                file.createNewFile();
	        	out = new FileOutputStream(file,false);
	            while ((line = reader.readLine()) != null) {
	            	System.out.println(line);
	                sb.append(line + "\n");
	            }
	            out.write(sb.toString().getBytes());
	            flag = true;
        	}
         } catch (Exception e) {
        	 flag = false;
        	 throw new Exception();
         } finally {
        	 out.close();
        	 is.close();
         }
         return flag;
	}
	
	public static Date getFileModifiedTime(String fileName){
        Calendar cal = Calendar.getInstance();
        try {
        	synchronized (FileUtil.class) {
	        	File f = new File(fileName);
	            if(!f.exists())
	            	return null;
	            long time = f.lastModified();
	            cal.setTimeInMillis(time);
        	}
		} catch (Exception e) {
			return null;
		}
        return cal.getTime();
    }
	
	public static StringBuffer readFile(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer("");
		RandomAccessFile raf = null;
		try {
			synchronized (FileUtil.class) {
				File file = new File(fileName);
				if (file.exists()) {
					raf = new RandomAccessFile(fileName, "r");
					String tempLine = null;
					while ((tempLine = raf.readLine()) != null) {
						tempLine = new String(tempLine.getBytes("iso8859-1"),"gbk");
						tempLine = tempLine.replace("\n", "<br/>");
						sb.append(tempLine + "\r\n");
					}
				}
				return sb;				
			}
		} finally {
			if(raf!=null)
				raf.close();
		}
	}
}
