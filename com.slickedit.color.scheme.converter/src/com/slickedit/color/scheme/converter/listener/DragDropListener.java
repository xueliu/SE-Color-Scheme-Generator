package com.slickedit.color.scheme.converter.listener;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import com.slickedit.color.scheme.converter.handler.ColorGenerationHandler;
import com.slickedit.color.scheme.converter.handler.SymbolColoringGenerationHandler;

public class DragDropListener implements
		DropTargetListener {

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	@Override
	public void drop(DropTargetDropEvent event) {

		// Accept copy drops
		event.acceptDrop(DnDConstants.ACTION_COPY);

		// Get the transfer which can provide the dropped item data
		Transferable transferable = event.getTransferable();

		// Get the data formats of the dropped item
		DataFlavor[] flavors = transferable.getTransferDataFlavors();

		// Loop through the flavors
		for (DataFlavor flavor : flavors) {
			try {
				// If the drop items are files
				if (flavor.isFlavorJavaFileListType()) {
					// Get all of the dropped files
					List<File> files = (List<File>) transferable
							.getTransferData(flavor);
					// Loop them through
					for (File file : files) {
						if ("XML".equalsIgnoreCase(getExtensionName(file
								.getName()))) {
							new ColorGenerationHandler(file.getAbsolutePath(), file.getParent()).execute();
							new SymbolColoringGenerationHandler(file).execute();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Inform that the drop is complete
		event.dropComplete(true);
	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
	}

	@Override
	public void dragExit(DropTargetEvent event) {
	}

	@Override
	public void dragOver(DropTargetDragEvent event) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event) {
	}
}