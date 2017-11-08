package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    Circle circle_Red, circle_Green, circle_Blue, circle_Black;
    WritableImage img = new WritableImage(800,600);
    ImageView view = new ImageView(img);
    PixelWriter pixelWriter = img.getPixelWriter();
    PixelReader pixelReader = img.getPixelReader();
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    double x,y;
    double x0 = 150,x1 = 200,x2 = 350,x3 = 400;
    double y0 = 150,y1 = 50,y2 = 50,y3 = 150;
    @Override
    public void start(Stage primaryStage) {

        createCircles();
        drawBezierCurve();
        Group root = new Group();
        root.getChildren().addAll(circle_Red, circle_Green, circle_Blue,circle_Black,view);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800,600));

        primaryStage.setTitle("Bezier Curve");
        primaryStage.getIcons().add(new Image("./Images/logo.png"));
        primaryStage.show();
    }
    void createCircles(){
        //CREATE CIRCLES

        circle_Red = new Circle(5.0f, Color.RED);
        circle_Red.setCursor(Cursor.MOVE);
        circle_Red.setCenterX(x0);
        circle_Red.setCenterY(y0);
        circle_Red.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Red.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        circle_Green = new Circle(5.0f, Color.GREEN);
        circle_Green.setCursor(Cursor.MOVE);
        circle_Green.setCenterX(x1);
        circle_Green.setCenterY(y1);
        circle_Green.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Green.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        circle_Blue = new Circle(5.0f, Color.BLUE);
        circle_Blue.setCursor(Cursor.MOVE);
        circle_Blue.setTranslateX(x2);
        circle_Blue.setTranslateY(y2);
        circle_Blue.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Blue.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        circle_Black = new Circle(5.0f,Color.BLACK);
        circle_Black.setCursor(Cursor.MOVE);
        circle_Black.setCenterX(x3);
        circle_Black.setCenterY(y3);
        circle_Black.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Black.setOnMouseDragged(circleOnMouseDraggedEventHandler);
    }
    void clearScreen(){
        for(int i=0; i<800; i++)
            for(int j=0; j<600; j++)
                if(pixelReader.getColor(i,j).equals(Color.DARKGRAY))
                    pixelWriter.setColor(i,j,Color.WHITE);
    }
    void drawBezierCurve(){
        clearScreen();
        /*x0 = circle_Red.getCenterX();
        y0 = circle_Red.getCenterY();
        x1 = circle_Green.getCenterX();
        y1 = circle_Green.getCenterY();
        x2 = circle_Blue.getCenterX();
        y2 = circle_Blue.getCenterY();
        x3 = circle_Black.getCenterX();
        y3 = circle_Black.getCenterY();*/
        plot();

    }
    void plot(){
        for(double u=0;u<=1;u += 0.0005){
            double bx = (x0 * Math.pow(1-u,3)) + (3 * x1 * u * Math.pow(1-u,2)) + (3 * x2 * u * u * (1-u)) + (x3 * Math.pow(u,3));
            double by = (y0 * Math.pow(1-u,3)) + (3 * y1 * u * Math.pow(1-u,2)) + (3 * y2 * u * u * (1-u)) + (y3 * Math.pow(u,3));
            pixelWriter.setColor((int)bx,(int)by,Color.DARKGRAY);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                    if(((Circle)t.getSource()).equals(circle_Red)){
                        x0 = ((Circle)(t.getSource())).getCenterX();
                        y0 = ((Circle)(t.getSource())).getCenterY();
                    }
                    else if(((Circle)t.getSource()).equals(circle_Green)){
                        x1 = ((Circle)(t.getSource())).getCenterX();
                        y1 = ((Circle)(t.getSource())).getCenterY();
                    }
                    else if(((Circle)t.getSource()).equals(circle_Blue)){
                        x2 = ((Circle)(t.getSource())).getCenterX();
                        y2 = ((Circle)(t.getSource())).getCenterY();
                    }
                    else if(((Circle)t.getSource()).equals(circle_Black)){
                        x3 = ((Circle)(t.getSource())).getCenterX();
                        y3 = ((Circle)(t.getSource())).getCenterY();
                    }
                    System.out.println(((Circle)(t.getSource())).getCenterX() + " " + ((Circle)(t.getSource())).getCenterY());
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                    x = ((Circle)(t.getSource())).getCenterX() + newTranslateX;
                    y = ((Circle)(t.getSource())).getCenterY() + newTranslateY;
                    if(((Circle)t.getSource()).equals(circle_Red)){
                        x0 = x;
                        y0 = y;
                    }
                    else if(((Circle)t.getSource()).equals(circle_Green)){
                        x1 = x;
                        y1 = y;
                    }
                    else if(((Circle)t.getSource()).equals(circle_Blue)){
                        x2 = x;
                        y2 = y;
                    }
                    else if(((Circle)t.getSource()).equals(circle_Black)){
                        x3 = x;
                        y3 = y;
                    }
                    drawBezierCurve();

                }
            };
}