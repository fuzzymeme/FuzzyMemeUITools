package com.fuzzymeme.uitools.logger;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fuzzymeme.uitools.ApplicationColors;
import com.fuzzymeme.uitools.GridBackground;
import com.fuzzymeme.uitools.TextNuggetStack;
import com.fuzzymeme.uitools.logger.LoggerKey;
import com.fuzzymeme.uitools.testrigs.LoggerTestRig;

import javafx.animation.AnimationTimer;

public class Logger extends Application {

	private final int canvasHeight = 1024;
	private final int canvasWidth = 1024;
	
	private Group root;
	
	private TextNuggetStack stack; 
	private List<Color> colors = Arrays.asList(
			ApplicationColors.STANDARD_BLUE, ApplicationColors.STANDARD_RED, ApplicationColors.STANDARD_GREEN, 
			ApplicationColors.STANDARD_YELLOW, ApplicationColors.STANDARD_CYAN, ApplicationColors.STANDARD_MAGENTA);
	private int colorIndex = 0;
	private static LoggerTestRig rig;
	
	private Map<LoggerKey, String> newTextMap = new HashMap<>();
	private Set<LoggerKey> textNuggets = new HashSet<>();	
	private Set<LoggerKey> newTextNuggets = new HashSet<>();	
	
	public Logger(){
        root = new Group();
        stack = new TextNuggetStack(root);         
	}
	
	public static void setRig(LoggerTestRig givenRig) {
		rig = givenRig;
	}
	
	public void addInitialKeys(LoggerKey... initialKeys) {
		for(LoggerKey key : initialKeys) {
			if(!newTextMap.containsKey(key)){
				addChannel(key);
			}
		}       
   }
	
	public void addChannel(LoggerKey newKey) {
		stack.addNewTextNugget(newKey);
		stack.setColor(newKey, colors.get(colorIndex++));
		colorIndex = colorIndex % colors.size();
		stack.addStack(root);
		textNuggets.add(newKey);
	}
	
	public void log(LoggerKey key, String message) {
		if(!textNuggets.contains(key)){
			newTextNuggets.add(key);
		}
		synchronized (newTextMap) {
			newTextMap.put(key, message);			
		}
	}
	
	public static void show() {
		launch(new String[0]);
    }
	
	@Override
    public void start(Stage theStage) {
		
		if(rig != null) {
			rig.callback(this);
		}
    	
        theStage.setTitle( "Logger" );

        Scene theScene = new Scene( root, Color.BLACK );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);  
        root.getChildren().add( canvas );
        
        GridBackground background = new GridBackground(canvasWidth, canvasHeight);
        background.addBackground(root);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            	for(LoggerKey key : newTextNuggets) {
            		addChannel(key);
            	}
            	newTextNuggets.clear();
            	
            	synchronized (newTextMap) {
            		for(LoggerKey key : newTextMap.keySet()) {
            			stack.setText(key, newTextMap.get(key));
            		}
            		newTextMap.clear();
            	}
            }
        }.start();

        theStage.show();
    }    

	public static void main(String[] args) {
        launch(args);
    }
}
