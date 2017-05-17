package com.fuzzymeme.uitools;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextNugget {
	
	private int x = 0; 
	private int y = 0;
	private int width = 0;
	private int xPadding = 10;
	private String title = null;
	private final Text titleText;
	private Line titleLine = null;
	private final Text messageText;
	private int numOfLinesVisible = 1;
	private int numOfCharactersWide = 12;
	private Color foregroundColor = ApplicationColors.STANDARD_BLUE;
	private TextNuggetUtils textNuggetUtils = new TextNuggetUtils();
	private List<String> visibleLines = new LinkedList<>();
	
	public TextNugget(int x, int y, String title, int fontSize, final int numOfLinesVisible, final int numOfCharactersWide) {
		this.x = x; 
		this.y = y;
		this.title = title;
		titleText = new Text(x, y, title);
		messageText = new Text(x, y, " Myq      ");
        messageText.setFont(new Font(Application.STYLESHEET_MODENA, fontSize));
		this.numOfLinesVisible = numOfLinesVisible;
		this.numOfCharactersWide = numOfCharactersWide;
	}
	
	public Group getGroup() {
		
		Group group = new Group();
		
		setUpTitleText();
				
        final double singleLineHeight = messageText.getLayoutBounds().getHeight();
        messageText.setFill(foregroundColor);

        messageText.setText(getStringToFillNugget());
        final double stringWidth = messageText.getLayoutBounds().getWidth();
        width = (int) stringWidth;
        final double stringHeight = messageText.getLayoutBounds().getHeight();
        
        int rectX = x - xPadding;
        int rectWidth = (int) stringWidth + xPadding * 2;

        int yPadding = (int) messageText.getFont().getSize() / 4;
        Rectangle r = new Rectangle();
        r.setStroke(foregroundColor);
        r.setStrokeWidth(1.8);
        r.setX(rectX);
        r.setY(y - singleLineHeight);
        r.setWidth(rectWidth);
        r.setHeight(stringHeight + yPadding * 2);
        r.setArcHeight(15);
        r.setArcWidth(15);
        r.setFill(ApplicationColors.TEXT_BOX_BACKGROUND);

        group.getChildren().add(r);
        group.getChildren().add(titleText);
        group.getChildren().add(messageText);

        if(title != null) {
			messageText.setY(messageText.getY() + singleLineHeight);
			
			int titleY = y + yPadding;
			titleLine = getTitleLine(rectX, titleY, rectX + rectWidth, titleY);
			group.getChildren().add(titleLine);
		}

        return group;
	}
	
	private Line getTitleLine(int fromX, int fromY, int toX, int toY) {
		Line line = new Line(fromX, fromY, toX, toY);
        line.setStrokeWidth(2.3);
        line.setStroke(foregroundColor);
        return line;
	}
	
	private void setUpTitleText() {
        titleText.setFill(foregroundColor);
        titleText.setFont(new Font(Application.STYLESHEET_MODENA, messageText.getFont().getSize()));
	}
	
	public void setText(String newText) {

		String maybeTruncatedString = textNuggetUtils.trunc(messageText, newText, width, xPadding);
		visibleLines.clear();
		visibleLines.add(maybeTruncatedString);
	            
		messageText.setText(maybeTruncatedString);
	}

	public void setColor(Color newColor) {
		messageText.setFill(newColor);
		titleText.setFill(newColor);
		if(titleLine != null) {
			titleLine.setFill(newColor);
		}
		foregroundColor = newColor;
	}
	
	public void appendText(String newText) {

		String maybeTruncatedString = textNuggetUtils.trunc(messageText, newText, width, xPadding);
		visibleLines.add(maybeTruncatedString);

		if(visibleLines.size() > numOfLinesVisible) {
			visibleLines.remove(0);
		}

		String visible = visibleLines.stream()
	            .map(line -> line + "\n")
	            .collect(Collectors.joining(""));
	            
		messageText.setText(visible);
	}
	
	private String getStringToFillNugget() {
	
		String initialLine = IntStream.range(0, numOfCharactersWide - 1)
        		.mapToObj(x -> "M")
        		.collect(Collectors.joining());
        
		int numOfLinesForTheBox = numOfLinesVisible;
		if(title != null) {
			numOfLinesForTheBox++;
		}
        String initialString = IntStream.rangeClosed(1, numOfLinesForTheBox)
        		.mapToObj(x -> initialLine + "\n")
        		.collect(Collectors.joining());
        initialString = initialString.substring(0, initialString.length() - 1);
        return initialString;
	}
}
