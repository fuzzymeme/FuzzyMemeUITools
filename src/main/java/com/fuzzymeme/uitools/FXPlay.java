package com.fuzzymeme.uitools;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.animation.AnimationTimer;

// Animation of Earth rotating around the sun. (Hello, world!)
public class FXPlay extends Application 
{
	
	private final int canvasHeight = 512;
	private final int canvasWidth = 512;
	
	private final static Color STANDARD_BLUE = new Color(0.203, 0.5176, 0.7019, 1.0);
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle( "Java FX Play" );

        Group root = new Group();
        Scene theScene = new Scene( root, Color.BLACK );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);  
        root.getChildren().add( canvas );
        
        GridBackground background = new GridBackground(canvasWidth, canvasHeight);
        background.addBackground(root);

        GraphicsContext gc = canvas.getGraphicsContext2D();
                
        Line line1 = new Line(256, 256, 400, 400);
        line1.setStrokeWidth(2.3);
        line1.setStroke(Color.GRAY);
        root.getChildren().add(line1);
        
        Line line2 = new Line(256, 256, 400, 400);
        line2.setStrokeWidth(2.3);
        line2.setStroke(Color.GRAY);
        root.getChildren().add(line2);
        
        Circle c = getGraphNode();
        root.getChildren().add(c);
        
        Circle c2 = getGraphNode();
        root.getChildren().add(c2);
        Circle c3 = getGraphNode();
        root.getChildren().add(c3);
       
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
                
                drawBackground(gc);
                
                c2.setCenterX(x);c2.setCenterY(y);
                line1.setEndX(x);line1.setEndY(y);
                
                x = 256 + 128 * Math.cos(t + 1);
                y = 256 + 128 * Math.sin(t + 1);
                c3.setCenterX(x);c3.setCenterY(y);                
                
                line2.setEndX(x);line2.setEndY(y);
            }
        }.start();

        theStage.show();
        System.out.println("Shown");
    }
    
    private void drawBackground(GraphicsContext gc) {
    	
    }
    
    private Circle getGraphNode() {    	
        Circle c = new Circle();
        int circleSize = 20;
        c.setCenterX(256.0);
        c.setCenterY(256.0);
        c.setRadius(circleSize);
        c.setFill(STANDARD_BLUE);
        c.setCache(true);
        c.setStrokeWidth(3.3);
        c.setStroke(Color.GRAY);
        return c;
    }
}
