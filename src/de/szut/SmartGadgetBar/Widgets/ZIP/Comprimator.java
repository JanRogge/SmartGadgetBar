package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.*;
import java.util.zip.*;

public class Comprimator {
	
	public void comprimate(String[] fileNames, String outName){
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(new File(outName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File[] files = new File[fileNames.length];
		for(int i=0; i<fileNames.length; i++){
			files[i] = new File(fileNames[i]);
		}
		String src = files[0].getParentFile().getAbsolutePath();
		comprimate(files, outName, out, src);
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done");
	}
	
	private  void comprimate(File[] files, String outName, ZipOutputStream zout, String src){
		try{
			for(int i=0; i< files.length;i++){
				File child = files[i];
				if(child.isFile()){
					addToZip(child, zout, src);
				}else{
					addDirToZip(child, zout, src);
					comprimate(child.listFiles(), outName, zout, src);
				}
			}
		
		}catch(Exception e){
			
		}
	}
	
	private void addToZip(File f, ZipOutputStream out, String src) throws IOException{
		System.out.println(f.getAbsolutePath());
		System.out.println(src);
		System.out.println(f.getAbsolutePath().substring(src.length()+1, f.getAbsolutePath().length()));
		out.putNextEntry(new ZipEntry(f.getAbsolutePath().substring(src.length()+1, f.getAbsolutePath().length())));
	}
	private void addDirToZip(File dir, ZipOutputStream out, String src) throws IOException{
		System.out.println(dir.getAbsolutePath().substring(src.length()+1, dir.getAbsolutePath().length()));
		out.putNextEntry(new ZipEntry(dir.getAbsolutePath().substring(src.length()+1, dir.getAbsolutePath().length())+"/"));
	}
	 

}