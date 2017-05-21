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

/**
 * 
 * TODO 
 * Need to adjust x and rectX - the actual position will be left of x due to padding
 * Use a scratch pad Text object for getting height etc rather than monkeying around with real text objects
 *
 */
public class TextNugget {
	
	private double x = 0; 
	private double y = 0;
	private double rectWidth = 0;
	private double rectHeight = 0;
	private int xPadding = 10;
	private double singleLineHeight;
	private double stringHeight;
	private double rectX;
	private double yPadding;
	private boolean initialised = false;
	
	private String title = null;
	private final Text titleText;
	private Line titleLine = null;
	
	private final Text messageText;
	private int numOfLinesVisible = 1;
	private int numOfCharactersWide = 12;
	private Color foregroundColor = ApplicationColors.STANDARD_BLUE;
	
	private List<String> visibleLines = new LinkedList<>();
	private TextNuggetUtils textNuggetUtils = new TextNuggetUtils();

	
	public TextNugget(double x, double y, String title, int fontSize, final int numOfLinesVisible, final int numOfCharactersWide) {
		this.x = x; 
		this.y = y;
		this.title = title;
		titleText = new Text(x, y, title);
		messageText = new Text(x, y, " ");
        messageText.setFont(new Font(Application.STYLESHEET_MODENA, fontSize));
        messageText.setFill(foregroundColor);
		this.numOfLinesVisible = numOfLinesVisible;
		this.numOfCharactersWide = numOfCharactersWide;
	}
	
	public void init() {
		if(initialised) {
			return;
		}
        singleLineHeight = getLineHeightFor(messageText, "Text to calc line height Myqj");
        
        double stringWidth = getLineWidthFor(messageText, getStringToFillNugget());
        stringHeight = getLineHeightFor(messageText, getStringToFillNugget());
        
        yPadding = messageText.getFont().getSize() / 4;
        rectX = x - xPadding;
        
        rectWidth = stringWidth + xPadding * 2;
        rectHeight = stringHeight + yPadding * 2;
        initialised = true;
	}
	
	public Group getGroup() {
		
		Group group = new Group();
		
		init();
		
		setUpTitleText();
						
        Rectangle r = getRectangle(singleLineHeight, stringHeight, rectX, yPadding);

        group.getChildren().add(r);
        group.getChildren().add(titleText);
        group.getChildren().add(messageText);

        addTitleIfPresent(group, singleLineHeight, yPadding, rectX);
       
        return group;
	}
	
	public double getHeight() {
		return rectHeight;
	}
	
	public String getTitle() {
		return title;
	}

	private double getLineWidthFor(Text text, String givenString) {
		text.setText(givenString);
		return messageText.getLayoutBounds().getWidth();
	}
	
	private double getLineHeightFor(Text text, String givenString) {
		text.setText(givenString);
        double boundsHeight = messageText.getLayoutBounds().getHeight();
		text.setText("");
		return boundsHeight;
	}
	
	private void addTitleIfPresent(Group group, double singleLineHeight, double yPadding, double rectX) {
		if(title != null) {
//			messageText.setY(messageText.getY() + singleLineHeight);
			messageText.setY(y + singleLineHeight);
			
			double titleY = y + yPadding;
			titleLine = getTitleLine(rectX, titleY, rectX + rectWidth, titleY);
			group.getChildren().add(titleLine);
		}
	}

	private Rectangle getRectangle(final double singleLineHeight, final double stringHeight, double rectX,  double yPadding) {
		
		Rectangle r = new Rectangle();
		r.setStroke(foregroundColor);
        r.setStrokeWidth(1.8);
        r.setX(rectX);
        r.setY(y - singleLineHeight);
        r.setWidth(rectWidth);
        r.setHeight(rectHeight);
        r.setArcHeight(15);
        r.setArcWidth(15);
        r.setFill(ApplicationColors.TEXT_BOX_BACKGROUND);
        
        return r;
	}
	
	private Line getTitleLine(double fromX, double fromY, double toX, double toY) {
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
		String maybeTruncatedString = textNuggetUtils.trunc(messageText, newText, (int)rectWidth, xPadding);
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
		String maybeTruncatedString = textNuggetUtils.trunc(messageText, newText, (int)rectWidth, xPadding);
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
