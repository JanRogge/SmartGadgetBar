package de.szut.SmartGadgetBar.Model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;

/**
 * Ein DropTargetListener welcher der GUI die erhaltenen Dateien übergibt
 * 
 * @author Simeon Kublenz
 *
 */
public class FileDropper implements DropTargetListener {
	AbstractWidgetPanel panel;

	/**
	 * Erzeugt ein neues Objekt des FileDropper
	 * 
	 * @param panel
	 */
	public FileDropper(AbstractWidgetPanel panel) {
		this.panel = panel;
	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
	}

	@Override
	public void dragOver(DropTargetDragEvent event) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event) {
	}

	@Override
	public void dragExit(DropTargetEvent event) {
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		Transferable transfer = event.getTransferable();
		if (transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			event.acceptDrop(DnDConstants.ACTION_COPY);
			try {
				List<?> fileList = (List<?>) transfer
						.getTransferData(DataFlavor.javaFileListFlavor);
				panel.pushFiles(fileList.toArray(new File[fileList.size()]));
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
