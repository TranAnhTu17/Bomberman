package Bomberman;

import Bomberman.Components.Enemy.Enemy1;
import Bomberman.Components.Enemy.Enemy2;
import Bomberman.Components.Enemy.Enemy3;
import Bomberman.Components.Enemy.Enemy4;
import Bomberman.Menu.GameMenu;
import Bomberman.Menu.MainMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import Bomberman.Components.PlayerComponent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import static Bomberman.Constants.Constanst.*;
import static com.almasb.fxgl.dsl.FXGL.*;


public class GameApp extends GameApplication {
    public static boolean sound_enabled = true;
    public Map temp = new HashMap();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(SCENE_WIDTH);
        gameSettings.setHeight(SCENE_HEIGHT);
        gameSettings.setTitle(GAME_TITLE);
        gameSettings.setVersion(GAME_VERSION);

        gameSettings.setFullScreenAllowed(true);
        gameSettings.setFullScreenFromStart(true);

        gameSettings.setIntroEnabled(false);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setFontUI("game_font.ttf");
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }

            @Override
            public FXGLMenu newGameMenu() {
                return new GameMenu();
            }

        });
        gameSettings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameFactory());
        nextLevel();
        spawn("background");
    }

    private Entity getPlayer() {
        return getGameWorld().getSingleton(GameType.PLAYER);
    }

    private PlayerComponent getPlayerComponent() {
        return getPlayer().getComponent(PlayerComponent.class);
    }


    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayerComponent().up();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayerComponent().down();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayerComponent().left();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayerComponent().right();
            }

            @Override
            protected void onActionEnd() {
                getPlayerComponent().stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayerComponent().placeBomb(geti("flame"));
            }
        }, KeyCode.SPACE);

//        getInput().addAction(new UserAction("develop") {
//            @Override
//            protected void onActionBegin() {
//                getPlayerComponent().setBombInvalidation(true);
//                getGameScene().getViewport().fade(() -> {
//                    nextLevel();
//                });
//            }
//        }, KeyCode.V);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                var entityGroup = getGameWorld().getGroup(GameType.ENEMY1,
                        GameType.ENEMY2, GameType.ENEMY3, GameType.ENEMY4);
                if (entityGroup.getSize() == 0) {
                    play("next_level.wav");
                    getPlayerComponent().setBombInvalidation(true);
                    getGameScene().getViewport().fade(() -> {
                        nextLevel();
                    });

                }

            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                if (enemy.getComponent(Enemy1.class).getCurrentMoveDir() != MoveDirection.DIE
                        && getPlayerComponent().getCurrentMoveDir() != MoveDirection.DIE) {
                    play("slash.wav");
                    onPlayerDied();
                }
            }
        });
        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                if (enemy.getComponent(Enemy2.class).getCurrentMoveDir() != MoveDirection.DIE
                        && getPlayerComponent().getCurrentMoveDir() != MoveDirection.DIE) {
                    play("slash.wav");
                    onPlayerDied();
                }
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                if (enemy.getComponent(Enemy3.class).getCurrentMoveDir() != MoveDirection.DIE
                        && getPlayerComponent().getCurrentMoveDir() != MoveDirection.DIE) {
                    play("slash.wav");
                    onPlayerDied();
                }
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY4) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                if (enemy.getComponent(Enemy4.class).getCurrentMoveDir() != MoveDirection.DIE
                        && getPlayerComponent().getCurrentMoveDir() != MoveDirection.DIE) {
                    play("slash.wav");
                    onPlayerDied();
                }
            }
        });

        physics.addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FLAME) {
            @Override
            protected void onCollisionBegin(Entity player, Entity flame) {
                if (getPlayerComponent().getPlayerSkin() == PlayerSkin.NORMAL
                        && getPlayerComponent().getCurrentMoveDir() != MoveDirection.DIE) {
                    onPlayerDied();
                }
            }
        });


    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalSoundVolume(sound_enabled ? 0.3 : 0.0);
        getSettings().setGlobalMusicVolume(sound_enabled ? 0.3 : 0.0);
        loopBGM("stage_theme.mp3");

    }

    @Override
    protected void onUpdate(double tpf) {
        inc("levelTime", -tpf);

        if (getd("levelTime") <= 0.0) {
            showMessage("you lose");
            set("levelTime", TIME_LEVEL);
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("score", 0);
        vars.put("flame", 1);
        vars.put("speed", SPEED);
        vars.put("bomb", 1);
        vars.put("levelTime", TIME_LEVEL);

    }

    @Override
    protected void initUI() {
        Label level = new Label();
        level.setTextFill(Color.BLACK);
        level.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        level.textProperty().bind(getip("level").asString("Level: %d"));
        addUINode(level, 20, 20);

        Label score = new Label();
        score.setTextFill(Color.BLACK);
        score.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        score.textProperty().bind(getip("score").asString("Score: %d"));
        addUINode(score, 200, 20);


        Label flame = new Label();
        flame.setTextFill(Color.BLACK);
        flame.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        flame.textProperty().bind(getip("flame").asString("Flame: %d"));
        addUINode(flame, 460, 20);

        Label speed = new Label();
        speed.setTextFill(Color.BLACK);
        speed.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        speed.textProperty().bind(getip("speed").asString("Speed: %d"));
        addUINode(speed, 640, 20);

        Label bomb = new Label();
        bomb.setTextFill(Color.BLACK);
        bomb.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        bomb.textProperty().bind(getip("bomb").asString("Bomb: %d"));
        addUINode(bomb, 870, 20);

        Label timeLabel = new Label();
        timeLabel.setTextFill(Color.BLACK);
        timeLabel.setFont(Font.font("Showcard Gothic", UI_FONT_SIZE));
        timeLabel.textProperty().bind(FXGL.getdp("levelTime").asString("Time: %.0f"));
        FXGL.addUINode(timeLabel, 1070, 20);
    }

    public void onPlayerDied() {
        play("playerDie.wav");
        getPlayerComponent().setCurrentMoveDir(MoveDirection.DIE);
        getPlayerComponent().setBombInvalidation(true);
        getGameTimer().runOnceAfter(() -> {
            getGameScene().getViewport().fade(() -> {
                setLevel();
            });
        }, Duration.seconds(2));
    }

    private void setLevel() {
        set("score", temp.get("score"));
        set("flame", temp.get("flame"));
        set("bomb", temp.get("bomb"));
        set("levelTime", TIME_LEVEL);

        setLevelFromMap("level" + geti("level") + ".tmx");
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        viewport.bindToEntity(getPlayer(), getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("Win !!!");
            return;
        }
        inc("level", +1);

        temp.put("score", geti("score"));
        temp.put("flame", geti("flame"));
        temp.put("bomb", geti("bomb"));

        setLevel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
