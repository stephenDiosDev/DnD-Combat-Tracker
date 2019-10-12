package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

/**
 * This class acts as the main launcher for the program
 */
public class DndCombatTracker extends Application {

    private static ControllerManager controllerManager;

    /*  VERSION NUMBERING SYSTEM
        MAJOR.MINOR.PATCHstage
        Where MAJOR represents some major version of the program (0 is alpha and beta, > 0  is full release)
        MINOR represents some version which is not as big as major, but still a large change/addition
        PATCH represents bug fixes or additions between versions but not every addition to make it the next version
        stage is either a, b or blank
            a for alpha
            b for beta
            blank for full release of minimum viable product

    */
    private static final String stageTitle = "D&D Combat Tracker (v0.9.1b)";
    private static final String windowIconURL = "/icons/program_icon.png";


    public static final String partyFileURL = "src/partyList/party.txt";

    /* MENU COLOUR PRESETS: For darker colours, change all font colours to white
    Red: #FF3333
    Blue: #3366ff
    Green: #00b300
    Purple: #BA55D3
    Light Blue: #00CED1
    Grey: #D3D3D3
    Default: #F3F3F3
    Black: #282828                      //needs white text
     */
    public static final String BLACK = "#282828";
    public static final String BLUE = "#3366FF";
    public static final String GREEN = "#00B300";
    public static final String RED = "#FF3333";
    public static final String PURPLE = "#BA55D3";
    public static final String LIGHT_BLUE = "#00CED1";
    public static final String GREY = "#D3D3D3";
    public static final String DEFAULT = "#F3F3F3";

    public static final String SWORD = "/icons/turn_icon_SWORD.png";
    public static final String ARROW = "/icons/turn_icon_ARROW.png";
    public static final String DEFAULT_ICON = SWORD;


    //the colour of the program window as dictated by the settings
    private static String chosenColour = DEFAULT;
    private static String chosenIcon = DEFAULT_ICON;
    private static boolean needWhiteText = false;

    public static Preferences pref = Preferences.userNodeForPackage(DndCombatTracker.class);
    private static String PREF_COLOUR = "pref_colour";
    private static String PREF_TURN_ICON = "pref_icon";


    @Override
    public void start(Stage stage) throws Exception {
        //get the saved background colour from prefs. If it
        //does not exist, grab the default background colour.
        chosenColour = pref.get(PREF_COLOUR, DEFAULT);
        chosenIcon = pref.get(PREF_TURN_ICON, DEFAULT);

        System.out.println("SAVED ICON: " + pref.get(PREF_TURN_ICON, "error"));

        System.out.println("SAVED COLOUR: " + pref.get(PREF_COLOUR, "error"));
        if(chosenColour.contains("282828")) {   //if background = black, use white text
            setHighContrast(true);
        } else {
            setHighContrast(false);
        }
        controllerManager = new ControllerManager();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.SETUP));
        loader.setController(controllerManager.getSetupPageController());
        Parent root = loader.load();
        controllerManager.setRootSetupScene(root);
        controllerManager.setMainStage(stage);
        controllerManager.getMainStage().setTitle(stageTitle);
        controllerManager.getMainStage().getIcons().add(new Image(windowIconURL));

        //hard width format to ensure the program looks ok
        stage.setMinWidth(616);
        stage.setMaxWidth(616);
        stage.setMinHeight(600);
        
        controllerManager.setSceneToSetupScene();
        controllerManager.getMainStage().show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //saves the chosen background colour in prefs
    public static void setColour(String newColour) {
        chosenColour = newColour;
        pref.put(PREF_COLOUR, chosenColour);

        System.out.println("SAVING COLOUR: " + pref.get(PREF_COLOUR, "error"));
    }

    public static void setIcon(String newIcon) {
        chosenIcon = newIcon;
        pref.put(PREF_TURN_ICON, chosenIcon);

        System.out.println("SAVING ICON: " + pref.get(PREF_TURN_ICON, "error"));
    }

    public static boolean needRedContrast() {
        if(getColour().equals(RED)) {
            return true;
        } else {
            return false;
        }
    }

    public static void setHighContrast(boolean value) {
        needWhiteText = value;
    }

    public static boolean needHighContrast() {
        return needWhiteText;
    }

    public static String getColour() {
        return chosenColour;
    }

    public static String getIcon() { return chosenIcon; }
    

    public static String getStageTitle() {
        return stageTitle;
    }

    public static String getWindowIconURL() {
        return windowIconURL;
    }

    /**
     * Returns the instance of the controller manager
     * @return The instance of the controller manager
     */
    public static ControllerManager getControllerManager() {
        return controllerManager;
    }
}