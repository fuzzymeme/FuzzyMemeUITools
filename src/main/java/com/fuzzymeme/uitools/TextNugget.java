package com.fuzzymeme.uitools;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextNugget {
	
	private int x = 0; 
	private int y = 0;
	private int width = 0;
	private int xPadding = 10;
	private final Text text;
	private int numOfLinesVisible = 1;
	private int numOfCharactersWide = 12;
	private TextNuggetUtils textNuggetUtils = new TextNuggetUtils();
	private List<String> visibleLines = new LinkedList<>();
	
	public TextNugget(int x, int y, int fontSize, final int numOfLinesVisible, final int numOfCharactersWide) {
		this.x = x; 
		this.y = y;
		text = new Text(x, y, " Myq      ");
        text.setFont(new Font(Application.STYLESHEET_MODENA, fontSize));
		this.numOfLinesVisible = numOfLinesVisible;
		this.numOfCharactersWide = numOfCharactersWide;
	}
	
	public Group getGroup() {
		
		Group group = new Group();
		
        final double singleLineHeight = text.getLayoutBounds().getHeight();
        text.setFill(ApplicationColors.STANDARD_BLUE);

        text.setText(getStringToFillNugget());
        final double stringWidth = text.getLayoutBounds().getWidth();
        width = (int) stringWidth;
        final double stringHeight = text.getLayoutBounds().getHeight();
        
        int yPadding = (int) text.getFont().getSize() / 4;
        Rectangle r = new Rectangle();
        r.setStroke(ApplicationColors.STANDARD_BLUE);
        r.setStrokeWidth(1.8);
        r.setX(x - xPadding);
        r.setY(y - singleLineHeight);
        r.setWidth(stringWidth + xPadding * 2);
        r.setHeight(stringHeight + yPadding * 2);
        r.setArcHeight(15);
        r.setArcWidth(15);
        r.setFill(ApplicationColors.TEXT_BOX_BACKGROUND);

        group.getChildren().add(r);
        group.getChildren().add(text);
        return group;
	}
	
	public void setText(String newText) {

		String maybeTruncatedString = textNuggetUtils.trunc(text, newText, width, xPadding);
		visibleLines.add(maybeTruncatedString);

		if(visibleLines.size() > numOfLinesVisible) {
			visibleLines.remove(0);
		}

		String visible = visibleLines.stream()
	            .map(line -> line + "\n")
	            .collect(Collectors.joining(""));
	            
		text.setText(visible);
	}
	
	private String getStringToFillNugget() {
		String initialLine = IntStream.range(0, numOfCharactersWide - 1)
        		.mapToObj(x -> "M")
        		.collect(Collectors.joining());
        
        String initialString = IntStream.rangeClosed(1, numOfLinesVisible)
        		.mapToObj(x -> initialLine + "\n")
        		.collect(Collectors.joining());
        initialString = initialString.substring(0, initialString.length() - 1);
        return initialString;
	}
}
