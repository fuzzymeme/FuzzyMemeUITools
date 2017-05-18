package com.fuzzymeme.uitools;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;

public class TextNuggetStack {
	
	private Map<String, TextNugget> textNuggets = new HashMap<>();
	private double interNuggetPadding = 12;
	
	public void initStack(String ... startingKeys) {		
		int x = 80;
		int y = 80;
		
		for(String key: startingKeys) {
			TextNugget nugget = new TextNugget(x, y, key, 24, 2, 18);
			nugget.init();
			textNuggets.put(key, nugget);
			y += nugget.getHeight() + interNuggetPadding;
		}
	}
	
	public void addStack(Group root) {	
		for(TextNugget nugget: textNuggets.values()) {
			root.getChildren().add(nugget.getGroup());
		}
	}
	
	public void appendText(String key, String newText) {
		if(textNuggets.containsKey(key)) {
			textNuggets.get(key).appendText(newText);
		}
	}

	public void setText(String key, String newText) {
		if(textNuggets.containsKey(key)) {
			textNuggets.get(key).setText(newText);
		}
	}

}
