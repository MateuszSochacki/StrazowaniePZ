package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.MovieEntity;
import com.cinema.services.MovieRepository;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.ReflectionBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Dominik on 15.05.2017.
 */
@Component
public class MovieInfoController implements BootInitializable {

    //private static final double WIDTH = 1200, HEIGHT = 800;

    private PageController pageController;

    private ApplicationContext springContext;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private static StackPane mainStackPane;

    @FXML
    void btnBackClicked(MouseEvent event) {

    }

    @FXML
    void btnSubmitClicked(MouseEvent event) {
        pageController.setPage(CinemaApplication.pageChooseSeance);
    }

    @Autowired
    private MovieRepository movieRepository;

    private List<MovieEntity> movies;

    private Image[] images;

    @Override
    public void initConstruct() {

    }

    @Override
    public void setPageParrent(PageController parentPage) {
        pageController = parentPage;
    }

    private void setImages(List<MovieEntity> movies){
        try {


        for(int i = 0 ; i < movies.size() ; i++) {
            ByteArrayInputStream is = new ByteArrayInputStream(movies.get(i).getCover());
            BufferedImage read = ImageIO.read(is);
            images[i] = SwingFXUtils.toFXImage(read, null);
        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stage(Stage primaryStage) {

    }

    @Override
    public Node initView() {
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movies = movieRepository.findAll();

        // load images
        images = new Image[movies.size()];

        setImages(movies);
        // create display shelf
        DisplayShelf displayShelf = new DisplayShelf(images, movies);
        displayShelf.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        displayShelf.setStyle("-fx-background-color: whitesmoke");
        displayShelf.setPadding(new Insets(20,20,20,20));
        mainLayout.setCenter(displayShelf);
        System.out.print("asda");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springContext = applicationContext;
    }

    public double getSampleWidth() { return 495; }

    public double getSampleHeight() { return 300; }

    public static class DisplayShelf extends Region{

        @FXML
        BorderPane mainLayout;

        private static final Duration DURATION = Duration.millis(350);
        private static final Interpolator INTERPOLATOR = Interpolator.EASE_BOTH;
        private static final double SPACING = 200;
        private static final double LEFT_OFFSET = -120;
        private static final double RIGHT_OFFSET = 120;
        private static final double SCALE_SMALL = 0.9;
        private PerspectiveImage[] items;

        private Group centered = new Group();
        private Group left = new Group();
        private Group center = new Group();

        private PerspectiveImage currentItem;

        private boolean isClicked = false;

        @Override
        protected void layoutChildren() {
            // update clip to our size
            clip.setWidth(getWidth());
            clip.setHeight(getHeight());
            // keep centered centered
            centered.setLayoutY((getHeight() - PerspectiveImage.HEIGHT) / 2);
            centered.setLayoutX((getWidth() - PerspectiveImage.WIDTH) / 2);
        }

        private Group right = new Group();
        private int centerIndex = 0;
        private Timeline timeline;
        private Rectangle clip = new Rectangle();

        public DisplayShelf(Image[] images, List<MovieEntity> movies) {

            // set clip
            setClip(clip);
            // set background gradient using css


            // create items
            items = new PerspectiveImage[images.length];
            for (int i=0; i<images.length; i++) {
                MovieEntity movie = movies.get(i);
                final PerspectiveImage item = items[i] = new PerspectiveImage(images[i], movie);
                currentItem = item;

                final double index = i;
                item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if(item == currentItem){
                            //todo: Animation. Rotate and show information about film
                            if(!isClicked){
                                showMoreInformation();
                            } else {
                                showLessInformation();
                                isClicked = false;
                            }
                            System.out.println("Klik w ten sam!");
                        } else {
                            shiftToCenter(item);
                            currentItem = item;
                        }
                    }
                });
            }

            // create content
            centered.getChildren().addAll(left, right, center);
            getChildren().addAll(centered);

            update();
        }

        private void showMoreInformation(){
            isClicked = true;
            timeline = new Timeline();

            final ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();

            final PerspectiveImage centerItem = items[centerIndex];
            keyFrames.add(new KeyFrame(DURATION,
                //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                    new KeyValue(centerItem.translateXProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                    new KeyValue(centerItem.translateYProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                    new KeyValue(centerItem.scaleXProperty(), 1.3, INTERPOLATOR),
                    new KeyValue(centerItem.scaleYProperty(), 1.3, INTERPOLATOR),
                    new KeyValue(centerItem.angle, 180, INTERPOLATOR)));
                    timeline.setOnFinished(event->{
                        centerItem.backMovie.setVisible(true);
                        centerItem.frontMovie.setVisible(false);
                        Timeline timeline2 = new Timeline();
                        final ObservableList<KeyFrame> keyFrames2 = timeline2.getKeyFrames();
                        centerItem.setAngle(0);
                        keyFrames2.add(new KeyFrame(DURATION,
                                //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                                new KeyValue(centerItem.translateXProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                                new KeyValue(centerItem.translateYProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                                new KeyValue(centerItem.scaleXProperty(), 1.6, INTERPOLATOR),
                                new KeyValue(centerItem.scaleYProperty(), 1.6, INTERPOLATOR),
                                new KeyValue(centerItem.angle, 90, INTERPOLATOR)));
                        timeline2.play();
                        });
            timeline.play();

        }

        private void showLessInformation(){
            timeline = new Timeline();

            final ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();

            final PerspectiveImage centerItem = items[centerIndex];
            keyFrames.add(new KeyFrame(DURATION,
                    //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                    new KeyValue(centerItem.translateXProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                    new KeyValue(centerItem.translateYProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                    new KeyValue(centerItem.scaleXProperty(), 1.3, INTERPOLATOR),
                    new KeyValue(centerItem.scaleYProperty(), 1.3, INTERPOLATOR),
                    new KeyValue(centerItem.angle, 180, INTERPOLATOR)));
            timeline.setOnFinished(event->{
                centerItem.backMovie.setVisible(false);
                centerItem.frontMovie.setVisible(true);
                Timeline timeline2 = new Timeline();
                final ObservableList<KeyFrame> keyFrames2 = timeline2.getKeyFrames();
                centerItem.setAngle(0);
                keyFrames2.add(new KeyFrame(DURATION,
                        //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                        new KeyValue(centerItem.translateXProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                        new KeyValue(centerItem.translateYProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                        new KeyValue(centerItem.scaleXProperty(), 1.0, INTERPOLATOR),
                        new KeyValue(centerItem.scaleYProperty(), 1.0, INTERPOLATOR),
                        new KeyValue(centerItem.angle, 90, INTERPOLATOR)));
                timeline2.play();
            });
            timeline.play();

        }

        private void update() {
            // move items to new homes in groups
            left.getChildren().clear();
            center.getChildren().clear();
            right.getChildren().clear();

            for (int i = 0; i < centerIndex; i++) {
                left.getChildren().add(items[i]);
            }

            center.getChildren().add(items[centerIndex]);

            for (int i = items.length - 1; i > centerIndex; i--) {
                right.getChildren().add(items[i]);
            }

            // stop old timeline if there is one running
            if (timeline!=null) timeline.stop();
            // create timeline to animate to new positions
            timeline = new Timeline();
            // add keyframes for left items
            final ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();
            for (int i = 0; i < left.getChildren().size(); i++) {
                final PerspectiveImage it = items[i];
                double newX = -left.getChildren().size() *
                        SPACING + SPACING * i + LEFT_OFFSET;
                keyFrames.add(new KeyFrame(DURATION,
                        new KeyValue(it.translateXProperty(), newX, INTERPOLATOR),
                        new KeyValue(it.scaleXProperty(), SCALE_SMALL, INTERPOLATOR),
                        new KeyValue(it.scaleYProperty(), SCALE_SMALL, INTERPOLATOR),
                        new KeyValue(it.angle, 60.0, INTERPOLATOR)));
            }
            // add keyframe for center item
            final PerspectiveImage centerItem = items[centerIndex];
            keyFrames.add(new KeyFrame(DURATION,
                    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                    new KeyValue(centerItem.scaleXProperty(), 1.0, INTERPOLATOR),
                    new KeyValue(centerItem.scaleYProperty(), 1.0, INTERPOLATOR),
                    new KeyValue(centerItem.angle, 90.0, INTERPOLATOR)));
            // add keyframes for right items
            for (int i = 0; i < right.getChildren().size(); i++) {
                final PerspectiveImage it = items[items.length - i - 1];
                final double newX = right.getChildren().size() *
                        SPACING - SPACING * i + RIGHT_OFFSET;
                keyFrames.add(new KeyFrame(DURATION,
                        new KeyValue(it.translateXProperty(), newX, INTERPOLATOR),
                        new KeyValue(it.scaleXProperty(), SCALE_SMALL, INTERPOLATOR),
                        new KeyValue(it.scaleYProperty(), SCALE_SMALL, INTERPOLATOR),
                        new KeyValue(it.angle, 120.0, INTERPOLATOR)));
            }
            // play animation
            timeline.play();
        }

        private void shiftToCenter(PerspectiveImage item) {
            for (int i = 0; i < left.getChildren().size(); i++) {
                if (left.getChildren().get(i) == item) {
                    int shiftAmount = left.getChildren().size() - i;
                    shift(shiftAmount);
                    return;
                }
            }
            if (center.getChildren().get(0) == item) {
                return;
            }
            for (int i = 0; i < right.getChildren().size(); i++) {
                if (right.getChildren().get(i) == item) {
                    int shiftAmount = -(right.getChildren().size() - i);
                    shift(shiftAmount);
                    return;
                }
            }
        }

        public void shift(int shiftAmount) {
            if (centerIndex <= 0 && shiftAmount > 0) return;
            if (centerIndex >= items.length - 1 && shiftAmount < 0) return;
            centerIndex -= shiftAmount;
            update();
        }

    }


    public static class PerspectiveImage extends Parent {

        private int idMovie;
        private static final double REFLECTION_SIZE = 0.25;
        private static final double WIDTH = 300;
        private static final double HEIGHT = WIDTH + (WIDTH*REFLECTION_SIZE);
        private static final double RADIUS_H = WIDTH/ 3 ;
        private static final double BACK = WIDTH / 10;
        private PerspectiveTransform transform = new PerspectiveTransform();

        private VBox backMovie = null;

        private VBox frontMovie = null;

        public ImageView getImageView() {
            return imageView;
        }

        public void setIdMovie(int idMovie) {
            this.idMovie = idMovie;
        }

        private ImageView imageView = null;

        /** Angle Property */

        private final DoubleProperty angle = new SimpleDoubleProperty(45.0) {
            @Override protected void invalidated() {
                // when angle changes calculate new transform
                double lx = (RADIUS_H - Math.sin(Math.toRadians(angle.get())) * RADIUS_H - 1);
                double rx = (RADIUS_H + Math.sin(Math.toRadians(angle.get())) * RADIUS_H + 1);
                double uly = (-Math.cos(Math.toRadians(angle.get())) * BACK);
                double ury = -uly;
                transform.setUlx(lx);
                transform.setUly(uly);
                transform.setUrx(rx);
                transform.setUry(ury);
                transform.setLrx(rx);
                transform.setLry(HEIGHT + uly);
                transform.setLlx(lx);
                transform.setLly(HEIGHT + ury);
            }
        };
        public final double getAngle() { return angle.getValue(); }
        public final void setAngle(double value) { angle.setValue(value); }
        public final DoubleProperty angleModel() { return angle; }

        public PerspectiveImage(Image image, MovieEntity movie) {
            frontMovie = new VBox();
            backMovie = new VBox();
            setImageView(image, movie);
        }

        public void setImageView(Image image, MovieEntity movie){
            //getChildren().clear();
            imageView = new ImageView(image);

            Text text = new Text(movie.getTitle());
            text.setStyle("-fx-text-fill: black;-fx-font-size: 12px");
            text.setFill(Color.BLACK);

            Reflection reflection = new Reflection();
            reflection.setFraction(REFLECTION_SIZE);
            frontMovie.setEffect(reflection);
            //frontMovie.setPadding(new Insets(12,12,12,12));
            frontMovie.setStyle("-fx-background-color: transparent;" +
                   "-fx-border-color: lightslategray");
            frontMovie.getChildren().addAll(imageView);
            frontMovie.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

            backMovie.setEffect(reflection);
            backMovie.setVisible(false);
            backMovie.setMinSize(200, 300);
            backMovie.getChildren().add(text);
            backMovie.setPadding(new Insets(20,20,20,20));
            backMovie.setStyle("-fx-background-color: white;" +
                                "-fx-border-color: #757575");

            setEffect(transform);
            getChildren().addAll(frontMovie, backMovie );
        }


        public int getIdMovie() {
            return idMovie;
        }

        public VBox getBackMovie() {
            return backMovie;
        }

        public void setBackMovie(VBox backMovie) {
            this.backMovie = backMovie;
        }

        public VBox getFrontMovie() {
            return frontMovie;
        }

        public void setFrontMovie(VBox frontMovie) {
            this.frontMovie = frontMovie;
        }


    }
}
