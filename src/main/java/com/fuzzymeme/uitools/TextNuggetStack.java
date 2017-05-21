package com.fuzzymeme.uitools;

import java.util.HashMap;
import java.util.Map;

import com.fuzzymeme.uitools.logger.LoggerKey;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/*
 * TODO Switch to enums for the keys
 */
public class TextNuggetStack {
	
	private Map<LoggerKey, TextNugget> textNuggets = new HashMap<>();
	private double interNuggetPadding = 12;
	private double x = 32;
	private double y = 40;
	private double height = y;
	
	private Group root = null;
	
	public TextNuggetStack(Group root) {
		this.root = root;
	}
	
	public void initStack(LoggerKey ... startingKeys) {		
		for(LoggerKey key: startingKeys) {
			TextNugget nugget = new TextNugget(x, y, key.toString(), 24, 2, 18);
			nugget.init();
			textNuggets.put(key, nugget);
			height += nugget.getHeight() + interNuggetPadding; 
			y += height;
		}
	}
	
	public void addStack(Group root) {			
		for(TextNugget nugget: textNuggets.values()) {
			root.getChildren().add(nugget.getGroup());
		}
	}
	
	public void addNewTextNugget(LoggerKey newKey) {
		TextNugget nugget = new TextNugget(x, height, newKey.toString(), 24, 2, 18);
		nugget.init();
		textNuggets.put(newKey, nugget);
		height += nugget.getHeight() + interNuggetPadding;
		root.getChildren().add(nugget.getGroup());
	}
	
	public void setColor(LoggerKey key, Color newColor) {
		if(textNuggets.containsKey(key)) {
			textNuggets.get(key).setColor(newColor);
		}
	}
	
	public void appendText(LoggerKey key, String newText) {
		if(textNuggets.containsKey(key)) {
			textNuggets.get(key).appendText(newText);
		} 
	}

	public void setText(LoggerKey key, String newText) {
		if(textNuggets.containsKey(key)) {
			textNuggets.get(key).setText(newText);
		}
	}

}
