package com.fuzzymeme.uitools;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GridBackground {
	
	private List<Line> verticalLines = new ArrayList<>(10);
	private List<Line> horizontalLines = new ArrayList<>(10);
	private Color gridlineColor = new Color(0.30, 0.30, 0.30, 0.50);
	

	public GridBackground(final int width, final int height) {
		
		int step = 56;
		for(int i = 0; i < 10; i++) {
			Line line = new Line(i * step, 0, i * step, height);
			line.setStrokeWidth(1.8);
			line.setStroke(gridlineColor);
			verticalLines.add(line);
		}
		
		for(int i = 0; i < 10; i++) {
			Line line = new Line(0, i * step, width, i * step);
			line.setStrokeWidth(1.8);
			line.setStroke(gridlineColor);
			horizontalLines.add(line);
		}
	}
	
	public void addBackground(Group root) {
		for(Line line: verticalLines) {
			root.getChildren().add(line);
		}
		for(Line line: horizontalLines) {
			root.getChildren().add(line);
		}
	}
	
}
