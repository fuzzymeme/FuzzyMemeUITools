package com.fuzzymeme.uitools;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.animation.AnimationTimer;

public class FXPlay extends Application {
	
	private final int canvasHeight = 512;
	private final int canvasWidth = 512;
	
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
    	
        theStage.setTitle( "Java FX Play" );

        Group root = new Group();
        Scene theScene = new Scene( root, Color.BLACK );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);  
        root.getChildren().add( canvas );
        
        GridBackground background = new GridBackground(canvasWidth, canvasHeight);
        background.addBackground(root);

        GraphicsContext gc = canvas.getGraphicsContext2D();
                
        Line line1 = getGraphNodeLine(256, 256, 400, 400);
        root.getChildren().add(line1);
        
        Line line2 = getGraphNodeLine(256, 256, 400, 400);
        root.getChildren().add(line2);
        
        Circle c = getGraphNode();
        root.getChildren().add(c);
        
        Circle c2 = getGraphNode();
        root.getChildren().add(c2);
        Circle c3 = getGraphNode();
        root.getChildren().add(c3);
                
        TextNugget textNugget = new TextNugget(40, 40, 26, 3, 14);
        root.getChildren().add(textNugget.getGroup());
       
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0; 

                double x = 256 + 128 * Math.cos(t);
                double y = 256 + 128 * Math.sin(t);

                // Clear the canvas
                gc.clearRect(0, 0, 512,512);
                
                c2.setCenterX(x);c2.setCenterY(y);
                line1.setEndX(x);line1.setEndY(y);
                
                x = 256 + 128 * Math.cos(t + 1);
                y = 256 + 128 * Math.sin(t + 1);
                c3.setCenterX(x);c3.setCenterY(y);                
                
                line2.setEndX(x);line2.setEndY(y);
                textNugget.setText("x: " + x);
            }
        }.start();

        theStage.show();
    }
    
    private Line getGraphNodeLine(int fromX, int fromY, int toX, int toY) {
    	Line line = new Line(fromX, fromY, toX, toY);
        line.setStrokeWidth(2.3);
        line.setStroke(Color.GRAY);        
        return line;
    }
   
    private Circle getGraphNode() {    	
        Circle circle = new Circle();
        int circleSize = 20;
        circle.setCenterX(256.0);
        circle.setCenterY(256.0);
        circle.setRadius(circleSize);
        circle.setFill(ApplicationColors.STANDARD_BLUE);
        circle.setCache(true);
        circle.setStrokeWidth(3.3);
        circle.setStroke(Color.GRAY);
        return circle;
    }
}
