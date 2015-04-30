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

public class FileDropper implements DropTargetListener{
	@Override
	public void dragEnter(DropTargetDragEvent event) {
		if (isDroppable(event)) {
			event.acceptDrag(DnDConstants.ACTION_COPY);
		}
		else {
			event.rejectDrag();
		}
	}
	
	@Override
	public void dragOver(DropTargetDragEvent event) {}
	
	@Override
	public void dropActionChanged(DropTargetDragEvent event) {
		if (isDroppable(event)) {
			event.acceptDrag(DnDConstants.ACTION_COPY);
		}
		else {
			event.rejectDrag();
		}
	}
	
	@Override
	public void dragExit(DropTargetEvent event) {}
	
	@Override
	public void drop(DropTargetDropEvent event) {
		Transferable transfer = event.getTransferable();
		if (transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			event.acceptDrop(DnDConstants.ACTION_COPY);
			try {
				List<?> fileList = (List<?>) transfer.getTransferData(DataFlavor.javaFileListFlavor);
				File[] fileArray = fileList.toArray(new File[fileList.size()]);
				System.out.println(fileArray.length);
				event.getDropTargetContext().dropComplete(true);
			}
			catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Not finished
	 * @param event
	 * @return
	 */
	private boolean isDroppable(DropTargetDragEvent event) {
		DataFlavor[]  flavors = event.getCurrentDataFlavors();
		for (DataFlavor flavor : flavors) {
			if (flavor.isFlavorJavaFileListType()) {
				System.out.println(true);
			}
			else {
				System.out.println(false);
			}
		}
		return false;
	}
}
