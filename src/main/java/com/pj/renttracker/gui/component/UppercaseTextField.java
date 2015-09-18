package com.pj.renttracker.gui.component;

import javafx.scene.control.TextField;

/**
 * 
 * @author PJ Miranda
 *
 */
public class UppercaseTextField extends TextField {

	@Override
	public void replaceText(int start, int end, String text) {
		super.replaceText(start, end, text.toUpperCase());
	}

	@Override
	public void replaceSelection(String text) {
		super.replaceSelection(text.toUpperCase());
	}

}