package Bomberman.Constants;

public class Constanst {
    public static final int SCENE_WIDTH = 920;
    public static final int SCENE_HEIGHT = 704;
    public static final int GAME_WORLD_WIDTH = 1344;
    public static final int GAME_WORLD_HEIGHT = 704;
    public static final String GAME_TITLE = "BOMBERMAN";
    public static final String GAME_VERSION = "1.0";
    public static final int MAX_LEVEL = 3;
    public static final int STARTING_LEVEL = 0;

    public static final int SPEED = 150;
    public static final int ENEMY_SPEED = 100;
    public static final int FIRE_SIZE = 60;

    public enum MoveDirection {
        UP, RIGHT, DOWN, LEFT, STOP,DIE
    }

}
