package de.szut.SmartGadgetBar.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Der Manager welcher Layouts speichert und lädt
 * 
 * @author Simeon Kublenz
 *
 */
public class LayoutManager {

	/**
	 * Speichert ein Layout
	 * 
	 * @param layout
	 *            Das zu speichernde Layout
	 * @param filePath
	 *            Der Pfad des zu speichernden Layouts
	 */
	public void write(Layout layout, String filePath) {
		if (!new File(filePath).exists()) {
			new File("layouts").mkdir();
		}

		filePath = new File(filePath).getAbsolutePath();
		if (!filePath.endsWith(".bin")) {
			filePath += ".bin";
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(layout);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lädt ein Layout und gibt es zurück
	 * 
	 * @param file
	 *            Die Datei welche geladen werden soll
	 * @return Das geladene Layout
	 */
	public Layout read(File file) {
		if (!file.getAbsolutePath().endsWith(".bin")) {
			file = new File(file.getAbsolutePath() + ".bin");
		}
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