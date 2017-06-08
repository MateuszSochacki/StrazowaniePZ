package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.AgeRatingEntity;
import com.cinema.model.CategoryEntity;
import com.cinema.model.MovieEntity;
import com.cinema.services.MovieRepository;
import com.cinema.util.CustomPopupWindow;
import com.cinema.util.ImageAnalizer;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Dominik on 15.05.2017.
 */
@Component
public class MovieInfoController implements BootInitializable {

    //private static final double WIDTH = 1200, HEIGHT = 800;

    private PageController pageController;
    private static boolean viewMode = false;
    private ApplicationContext springContext;

    private DisplayShelf displayShelf;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private StackPane mainStackPane;

    @FXML
    void btnBackClicked(MouseEvent event) {

    }

    @FXML
    void btnSubmitClicked(MouseEvent event) {
        displayShelf.showLessInformation();
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
        displayShelf = new DisplayShelf(images, movies);
        displayShelf.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        displayShelf.setStyle("-fx-background-color: whitesmoke");
        mainLayout.setCenter(displayShelf);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springContext = applicationContext;
    }

    public double getSampleWidth() { return 495; }

    public double getSampleHeight() { return 300; }

    public class DisplayShelf extends Region{

        private WebView currentWebView;
        private  final Duration DURATION = Duration.millis(350);
        private  final Interpolator INTERPOLATOR = Interpolator.EASE_BOTH;
        private static final double SPACING = 200;
        private static final double LEFT_OFFSET = -100;
        private static final double RIGHT_OFFSET = 100;
        private static final double SCALE_SMALL = 0.5;
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
                item.setMovieEntity(movie);
                currentItem = item;

                final double index = i;
                item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        if(item == currentItem){
                            if(!isClicked && !MovieInfoController.viewMode){
                                showMoreInformation();
                                viewMode = true;
                            } else {
                                showLessInformation();
                                isClicked = false;
                                viewMode = false;
                            }
                            System.out.println("Klik w ten sam!");
                        } else {
                            if(viewMode){
                                showLessInformation();
                                viewMode = false;
                                isClicked = false;
                                return;
                            }
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
            center.getParent().setDisable(true);
            isClicked = true;
            timeline = new Timeline();
            final ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();

            final PerspectiveImage centerItem = items[centerIndex];
            keyFrames.add(new KeyFrame(DURATION,
                    new KeyValue(centerItem.translateYProperty(), 40, INTERPOLATOR),
                    new KeyValue(centerItem.scaleXProperty(), 0.85, INTERPOLATOR),
                    new KeyValue(centerItem.scaleYProperty(), 0.85, INTERPOLATOR),
                    new KeyValue(centerItem.angle, 180, INTERPOLATOR)));
                    timeline.setOnFinished(event->{

                        centerItem.backMovie.setVisible(true);
                        centerItem.frontMovie.setVisible(false);
                        Timeline timeline2 = new Timeline();
                        final ObservableList<KeyFrame> keyFrames2 = timeline2.getKeyFrames();
                        centerItem.setAngle(0);
                        keyFrames2.add(new KeyFrame(DURATION,
                                //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                                new KeyValue(centerItem.translateYProperty(), 80, INTERPOLATOR),
                                new KeyValue(centerItem.scaleXProperty(), 1.0, INTERPOLATOR),
                                new KeyValue(centerItem.scaleYProperty(), 1.0, INTERPOLATOR),
                                new KeyValue(centerItem.angle, 90, INTERPOLATOR)));
                        timeline2.setOnFinished(event3->{
                            center.getParent().setDisable(false);
                            List<Node> nodeList = getAllNodes(centerItem.backMovie);
                            WebView webView = (WebView) nodeList.get(17);
                            currentWebView = webView;
                            currentWebView.getEngine().load(
                                    currentItem.getMovieEntity().getTrailer()
                            );
                        });
                        timeline2.play();
                        });
            timeline.play();

        }

        private void showLessInformation(){
            center.getParent().setDisable(true);
            timeline = new Timeline();
            final ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();

            final PerspectiveImage centerItem = items[centerIndex];
            keyFrames.add(new KeyFrame(DURATION,
                    //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
//                    new KeyValue(centerItem.translateXProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                    new KeyValue(centerItem.translateYProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                    new KeyValue(centerItem.scaleXProperty(), 0.85, INTERPOLATOR),
                    new KeyValue(centerItem.scaleYProperty(), 0.85, INTERPOLATOR),
                    new KeyValue(centerItem.angle, 180, INTERPOLATOR)));
            timeline.setOnFinished(event->{
                centerItem.backMovie.setVisible(false);
                centerItem.frontMovie.setVisible(true);
                Timeline timeline2 = new Timeline();
                final ObservableList<KeyFrame> keyFrames2 = timeline2.getKeyFrames();
                centerItem.setAngle(0);
                keyFrames2.add(new KeyFrame(DURATION,
                        //    new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
//                        new KeyValue(centerItem.translateXProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                        new KeyValue(centerItem.translateYProperty(), USE_COMPUTED_SIZE, INTERPOLATOR),
                        new KeyValue(centerItem.scaleXProperty(), 0.7, INTERPOLATOR),
                        new KeyValue(centerItem.scaleYProperty(), 0.7, INTERPOLATOR),
                        new KeyValue(centerItem.angle, 90, INTERPOLATOR)));
                timeline2.setOnFinished(event3->{
                    center.getParent().setDisable(false);
                    List<Node> nodeList = getAllNodes(centerItem.backMovie);
                    WebView webView = (WebView) nodeList.get(17);
                    currentWebView = webView;
                    currentWebView.getEngine().load(
                            ""
                    );
                });
                timeline2.play();
            });
            timeline.play();

        }

        public ArrayList<Node> getAllNodes(Parent root) {
            ArrayList<Node> nodes = new ArrayList<Node>();
            addAllDescendents(root, nodes);
            return nodes;
        }

        private void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
            for (Node node : parent.getChildrenUnmodifiable()) {
                nodes.add(node);
                if (node instanceof Parent)
                    addAllDescendents((Parent)node, nodes);
            }
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
                    new KeyValue(centerItem.scaleXProperty(), 0.7, INTERPOLATOR),
                    new KeyValue(centerItem.scaleYProperty(), 0.7, INTERPOLATOR),
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

        public MovieEntity getMovieEntity() {
            return movieEntity;
        }

        public void setMovieEntity(MovieEntity movieEntity) {
            this.movieEntity = movieEntity;
        }

        private MovieEntity movieEntity;

        private int idMovie;
        private static final double REFLECTION_SIZE = 0.25;
        private static final double WIDTH = 400;
        private static final double HEIGHT = 800;
        private static final double RADIUS_H = HEIGHT /4;
        private static final double BACK = HEIGHT / 10;
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
            imageView.fitWidthProperty().bind(frontMovie.widthProperty());
            imageView.fitHeightProperty().bind(frontMovie.heightProperty());

            Reflection reflection = new Reflection();
            reflection.setFraction(REFLECTION_SIZE);
            frontMovie.setEffect(reflection);
            //frontMovie.setPadding(new Insets(12,12,12,12));
            frontMovie.getChildren().addAll(imageView);
            frontMovie.setMinSize(WIDTH,HEIGHT);
            frontMovie.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

            backMovie = createBackMovie(movie,image);
            backMovie.setEffect(reflection);
            setEffect(transform);
            getChildren().addAll(frontMovie, backMovie );
        }


        public VBox createBackMovie(MovieEntity movie,Image image)
        {
            VBox root = new VBox();

            //get colors
            ImageAnalizer imageAnalizer = new ImageAnalizer();
            List<String> colors = new ArrayList<>();
            colors = imageAnalizer.getColors(movie);


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scene/MovieInfoCard.fxml"));
            MovieInfoCardController controller = new MovieInfoCardController(movie, image, colors);

            fxmlLoader.setController(controller);
            try {
                root = fxmlLoader.load();
                root.setStyle("-fx-border-width: 0 2 2 2; -fx-background-color: white; -fx-border-color: "+colors.get(1)+";");
                root.setVisible(false);
                WebView web = controller.getCardMovieTrailer();
                web.setId(root.toString());

                return root;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return root;
        }


//
//            Text title = new Text(movie.getTitle());
//            title.setStyle("-fx-text-fill: "+colors.get(0)+";"+
//                    "-fx-font-size: 24px;");
//
//            TextFlow textFlow = new TextFlow();
//            textFlow.getChildren().add(title);
//
//            HBox hbox = new HBox();
//            hbox.setPadding(new Insets(6,6,6,6));
//            hbox.setAlignment(Pos.CENTER);
//
//            ImageView imageView = new ImageView(image);
//            imageView.setFitWidth(100);
//            imageView.preserveRatioProperty().setValue(true);
//            hbox.getChildren().add(imageView);
//
//            GridPane gridPane = new GridPane();
//            ColumnConstraints columm = new ColumnConstraints();
//            columm.setPercentWidth(50);
//
//            gridPane.setHgap(12);
//            gridPane.setVgap(6);
//            gridPane.setPadding(new Insets(6, 6,6,6));
//
//            RowConstraints row = new RowConstraints();
//
//            gridPane.getColumnConstraints().addAll(columm, columm);
//            gridPane.getRowConstraints().addAll(row,row,row,row,row);
//
//            List <String> movieDetailsList = new ArrayList<>();
//
//            movieDetailsList.add("Reżyser:");
//            movieDetailsList.add(movie.getDirector());
//
//            movieDetailsList.add("Czas Trwania:");
//            movieDetailsList.add(movie.getDuration().toString()+"min");
//
//            List <CategoryEntity> list = movie.getCategoryEntities();
//            String categoriesString ="";
//            for (CategoryEntity category: list) {
//                categoriesString += category.getName()+", ";
//            }
//            categoriesString.substring(0, categoriesString.length()-1);
//
//            movieDetailsList.add("Kategorie:");
//            movieDetailsList.add(categoriesString);
//
//            movieDetailsList.add("Data Premiery:");
//            movieDetailsList.add(movie.getReleaseDate().toString());
//
//            movieDetailsList.add("Ograniczenie wiekowe:");
//            movieDetailsList.add(movie.getAgeRatingEntities().getRequiredAge().toString() +"+");
//
//            int licznik =0;
//            for (int rows=0; rows<((movieDetailsList.size())/2); rows++)
//            {
//                for (int col=0; col<2; col++) {
//                    TextFlow movieDetailsListTextFlow = new TextFlow();
//                    Text movieDetailsListText = new Text(movieDetailsList.get(licznik));
//                    movieDetailsListText.setStyle("-fx-text-fill: "+colors.get(0)+";"+
//                            "-fx-font-size: 14px;");
//                    licznik++;
//                    movieDetailsListTextFlow.getChildren().add(movieDetailsListText);
//                    gridPane.add(movieDetailsListTextFlow, col, rows);
//                }
//            }
//            hbox.getChildren().add(gridPane);
//
//            Text description = new Text(movie.getDescription());
//            TextFlow descriptonTextFlow = new TextFlow();
//            description.setStyle("-fx-text-fill: "+colors.get(0)+";"+
//                    "-fx-font-size: 14px;");
//
//            descriptonTextFlow.getChildren().add(description);
//
//
//
//            root.setVisible(false);
//            root.setMinSize(400, 600);
//            root.setMaxSize(400, 600);
//
//            root.getChildren().addAll(textFlow, hbox , descriptonTextFlow);
//            root.setPadding(new Insets(8,8,8,8));
//            root.setStyle("-fx-background-color: white;"+"-fx-border-color: "+colors.get(1)+";");
//

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
