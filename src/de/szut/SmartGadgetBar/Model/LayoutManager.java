package de.szut.SmartGadgetBar.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LayoutManager {

	public void write(Layout layout, String filePath) {
		if (!new File(filePath).exists()) {
			try {
				FileWriter writer = new FileWriter(new File(filePath), false);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!filePath.endsWith(".bin")) {
			filePath += ".bin";
		}
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(layout);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Layout read(File file) {
		Layout layout = null;
		ObjectInputStream in;
		if (file.exists()) {
			try {
				in = new ObjectInputStream(new FileInputStream(file));
				Object object = in.readObject();
				if (object instanceof Layout) {
					layout = (Layout) object;
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return layout;
	}
}