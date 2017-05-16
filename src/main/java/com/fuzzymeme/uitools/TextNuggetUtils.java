package com.fuzzymeme.uitools;

import javafx.scene.text.Text;

public class TextNuggetUtils {
	
	private Text workingText = new Text("");		
	
	/** Truncate the given text to the given width **/
	public String trunc(final Text givenText, final String message, final int width, final int paddingWidth){

		workingText.setFont(givenText.getFont());
		workingText.setText(message);
		
		String truncMessage = message;
		int messageWidth = (int)workingText.getLayoutBounds().getWidth();
		if(messageWidth < (width - (paddingWidth * 2))){
			return message;
		}
		// Truncate until it fits inside the box. Could do a binary search if I felt this was slow, 
		// or count up instead of down for cases of really long strings
		while(messageWidth >= (width - (paddingWidth * 2)) && truncMessage.length() > 0){
			truncMessage = truncMessage.substring(0, truncMessage.length() - 1);
			workingText.setText(truncMessage + "... "); 
			messageWidth = (int)workingText.getLayoutBounds().getWidth();
		}		
		return truncMessage + "...";
	}
}
