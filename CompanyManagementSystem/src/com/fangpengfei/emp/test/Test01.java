package com.fangpengfei.emp.test;

import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;

public class Test01 {
	public static final void initMouseInputListener(final JComponent component, final JPopupMenu popupMenu,
			boolean enableRightKey) {
		final MouseInputListener mouseInputListener = getMouseInputListener(component, popupMenu, enableRightKey);
		component.addMouseListener(mouseInputListener);
		component.addMouseMotionListener(mouseInputListener);
	}

	/**
	 * 添加鼠标右键单击选择监听，并显示右键菜单.<br/>
	 *
	 * @param table
	 * @param popupMenu
	 * @param enableRightKey
	 * @return MouseInputListener
	 */
	public static final MouseInputListener getMouseInputListener(final JComponent component, final JPopupMenu popupMenu,
			final boolean enableRightKey) {
		return new MouseInputListener() {
			public void mouseClicked(MouseEvent e) {
				processEvent(e);
			}

			public void mousePressed(MouseEvent e) {
				processEvent(e);
			}

			public void mouseReleased(MouseEvent e) {
				if (e.getSource() instanceof JScrollPane) {
					JScrollPane scrollPane = (JScrollPane) e.getSource();
					if (scrollPane.getViewport().getComponent(0) instanceof JTable) {
						JTable jtb = (JTable) scrollPane.getViewport().getComponent(0);
						DefaultCellEditor dce = (DefaultCellEditor) jtb.getCellEditor();
						if (dce != null) {
							dce.stopCellEditing();
						}

						jtb.getSelectionModel().clearSelection();
					}
				}

				processEvent(e);

				if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0 && !e.isControlDown() && !e.isShiftDown()) {
					// 右键菜单显示
					popupMenu.show(component, e.getX(), e.getY());
				}
			}

			public void mouseEntered(MouseEvent e) {
				processEvent(e);
			}

			public void mouseExited(MouseEvent e) {
				processEvent(e);
			}

			public void mouseDragged(MouseEvent e) {
				processEvent(e);
			}

			public void mouseMoved(MouseEvent e) {
				processEvent(e);
			}

			private void processEvent(MouseEvent e) {
				if (enableRightKey) {
					int modifiers = e.getModifiers();
					boolean isRow = false;
					if ((modifiers & MouseEvent.BUTTON3_MASK) != 0) {
						modifiers -= MouseEvent.BUTTON3_MASK;
						// 将右键改成左键
						modifiers |= MouseEvent.BUTTON1_MASK;

						if (e.getSource() instanceof JTable) {
							JTable table = (JTable) e.getSource();
							int selIndex = table.rowAtPoint(e.getPoint());
							int[] indexs = table.getSelectedRows();
							if (selIndex >= 0 && indexs.length > 1) {
								for (int in : indexs) {
									if (selIndex == in) {
										isRow = true;
									}
								}
							} else {
								isRow = false;
							}
						}
						if (!isRow) {
							MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), modifiers,
									e.getX(), e.getY(), e.getClickCount(), false);

							component.dispatchEvent(ne);
						}
						return;
					}
				}
			}
		};
	}
}
