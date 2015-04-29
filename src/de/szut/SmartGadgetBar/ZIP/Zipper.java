package de.szut.SmartGadgetBar.ZIP;

import java.util.zip.*;

public class Zipper /* implements WidgetInterface+ */{

	Deflater deflet;

	public Zipper() {

		deflet = new Deflater();
	}

	public void comprimate(String[] files, String outName) {
		 Comprimator.comprimate(files, outName);
	}

	public void decomprimate(String zipFile) {
		Decomprimator.decomprimate(zipFile);
	}

}
