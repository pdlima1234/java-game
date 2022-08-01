import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @author Pratika Dlima
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";

    private final LevelZero LEVEL_ZERO = new LevelZero();
    private final LevelOne LEVEL_ONE = new LevelOne();

    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.readCSV("res/level0.csv");
        game.readCSV("res/level1.csv");
        game.run();
    }

    /**
     * Method used to read file and create objects. Actors created stored to appropriate level object.
     * @param fileName accepts a String which is the name of a CSV file to be read
     */
    private void readCSV(String fileName) {
        Level level;

        // Store information of CSV file to `LEVEL_ZERO` or `LEVEL_ONE` depending on `fileName`
        if (fileName.equals("res/level0.csv")) {
            level = LEVEL_ZERO;
        }
        else {
            level = LEVEL_ONE;
        }

        level.setPlayer();
        level.setNonPlayerActors();
        level.setOnScreenProjectiles();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text = null;

            // Initialize game actors based on contents of CSV file
            while ((text = br.readLine()) != null) {
                String[] csvRow = text.split(",");

                if (csvRow[0].equalsIgnoreCase("Block")) {
                    if (level instanceof LevelZero) {
                        Block block = new Block();
                        block.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                                Integer.parseInt(csvRow[2])));
                        block.setInitialEntityImage();
                        block.setEntityRectangle();
                        // All non-player actors are stored in the ArrayList `nonPlayerActors`
                        level.getNonPlayerActors().add(block);
                    }

                    else {
                        Bomb bomb = new Bomb();
                        bomb.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                                Integer.parseInt(csvRow[2])));
                        bomb.setInitialEntityImage();
                        bomb.setEntityRectangle();
                        level.getNonPlayerActors().add(bomb);
                    }
                }

                else if (csvRow[0].equalsIgnoreCase("Sailor")) {
                    Sailor player = level.getPlayer();
                    player.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    player.setInitialEntityImage();
                    player.setEntityRectangle();
                    player.initializeHealthPoints();
                }

                else if (csvRow[0].equalsIgnoreCase("Pirate")) {
                    Pirate pirate = new Pirate();
                    pirate.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    pirate.initializeDirectionAndSpeed();
                    pirate.setInitialEntityImage();
                    pirate.setEntityRectangle();
                    pirate.setAttackRectangle();
                    pirate.initializeHealthPoints();
                    level.getNonPlayerActors().add(pirate);
                }

                else if (csvRow[0].equalsIgnoreCase("Treasure")) {
                    Treasure treasure = new Treasure();
                    treasure.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    treasure.setInitialEntityImage();
                    treasure.setEntityRectangle();
                    level.getNonPlayerActors().add(treasure);
                }

                else if (csvRow[0].equalsIgnoreCase("Potion")) {
                    Potion potion = new Potion();
                    potion.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    potion.setInitialEntityImage();
                    potion.setEntityRectangle();
                    level.getNonPlayerActors().add(potion);
                }

                else if (csvRow[0].equalsIgnoreCase("Elixir")) {
                    Elixir elixir = new Elixir();
                    elixir.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    elixir.setInitialEntityImage();
                    elixir.setEntityRectangle();
                    level.getNonPlayerActors().add(elixir);
                }

                else if (csvRow[0].equalsIgnoreCase("Sword")) {
                    Sword sword = new Sword();
                    sword.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    sword.setInitialEntityImage();
                    sword.setEntityRectangle();
                    level.getNonPlayerActors().add(sword);
                }

                else if (csvRow[0].equalsIgnoreCase("Blackbeard")) {
                    Blackbeard blackbeard = new Blackbeard();
                    blackbeard.setEntityTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                    blackbeard.initializeDirectionAndSpeed();
                    blackbeard.setInitialEntityImage();
                    blackbeard.setEntityRectangle();
                    blackbeard.setAttackRectangle();
                    blackbeard.initializeHealthPoints();
                    level.getNonPlayerActors().add(blackbeard);
                }

                else if (csvRow[0].equalsIgnoreCase("TopLeft")) {
                    level.setTopLeft(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                }

                else if (csvRow[0].equalsIgnoreCase("BottomRight")) {
                    level.setBottomRight(new Point(Integer.parseInt(csvRow[1]),
                            Integer.parseInt(csvRow[2])));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a state update. Allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        // Render L1 in-game play if L0 passed and L0 win message has displayed for 180 frames (3 secs)
        Level level = LEVEL_ZERO;
        if ((level == LEVEL_ZERO) && level.getStopDisplayWin() && level.getWasLevelPassed()) {
            level = LEVEL_ONE;
        }

        // Before space is pressed and level is won, we render instruction message
        if (!(level.getSpaceWasPressed() || level.getWasLevelPassed())) {
            level.renderPreLevelInstruction();
            // Check if space was pressed
            level.setSpaceWasPressed(input.wasPressed(Keys.SPACE));
        }

        // If either L0 and/or L1 is in-play and not won and player is not alive, then render loss message
        else if ((!level.getWasLevelPassed()) && level.getSpaceWasPressed() &&
                !level.getPlayer().getIsAlive()) {
            level.renderLoss();
        }

        // Render in-game play for L0 when L0 not passed and space was pressed
        else if ((level == LEVEL_ZERO) && (!level.getWasLevelPassed()) && level.getSpaceWasPressed()) {

            Sailor player = level.getPlayer();
            ArrayList<GameEntity> nonPlayerActors = level.getNonPlayerActors();
            ArrayList<StandardProjectile> onScreenProjectiles = level.getOnScreenProjectiles();

            level.getBackgroundImage().draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            player.renderPercentageHealth();

            // Render all valid L0 actors to frame - sailor, pirates and blocks
            player.renderActorToFrame();
            for (GameEntity actor : nonPlayerActors) {
                if (actor instanceof Pirate) {
                        Pirate pirate = (Pirate) actor;
                    if (pirate.getIsAlive()) {
                        pirate.inGameActions(input, level, player);
                    }
                }
                // All other level zero non-player actors are blocks. They have no actions.
                else {
                    actor.renderActorToFrame();
                }
            }

            // Renders all valid projectiles to frame
            for (int i = 0 ; i < onScreenProjectiles.size(); i++) {
                StandardProjectile projectile = onScreenProjectiles.get(i);
                if (!projectile.getInvalidProjectile()) {
                    projectile.inGameActions(input, level, player);
                    // Causes player to lose health points if it collides with projectile
                    player.receiveDamage(projectile);
                }
            }

            player.setNextCoordinate(input, level);
            // Removes player from attack state and puts player in cool down state
            player.setCurrFramesInAttack();
            // Removes player from cool down state and puts player in idle state
            player.setCurrFramesInCooldown();

            // Checks if level is passed
            LEVEL_ZERO.hasWon();
        }

        // In-game play for L1 when L1 not passed and space was pressed
        else if ((level == LEVEL_ONE) && (!level.getWasLevelPassed()) && level.getSpaceWasPressed()) {

            Sailor player = level.getPlayer();
            ArrayList<GameEntity> nonPlayerActors = level.getNonPlayerActors();
            ArrayList<StandardProjectile> onScreenProjectiles = level.getOnScreenProjectiles();

            level.getBackgroundImage().draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            player.renderPercentageHealth();

            // Render all valid L1 actors to frame
            player.renderActorToFrame();
            for (GameEntity actor : nonPlayerActors) {
                if (actor instanceof Pirate) {
                    Pirate pirate = (Pirate) actor;
                    if (pirate.getIsAlive()) {
                        pirate.inGameActions(input, level, player);
                    }
                }
                else if (actor instanceof Blackbeard) {
                    Blackbeard blackbeard = (Blackbeard) actor;
                    if (blackbeard.getIsAlive()) {
                        blackbeard.inGameActions(input, level, player);
                    }
                }
                else if (actor instanceof Bomb) {
                    Bomb bomb = (Bomb) actor;
                    // Bomb is rendered if it has not yet exploded or is currently exploding
                    if (!(bomb.getHasExploded() || bomb.getExplosionComplete())) {
                        bomb.renderActorToFrame();
                    }
                    else if (bomb.getHasExploded() && !(bomb.getExplosionComplete())) {
                        bomb.renderActorToFrame();
                        /* Sets `explosionComplete` attribute of `bomb` to true after 500 milliseconds. After
                            this the particular `bomb` becomes invalid and is not rendered. */
                        bomb.updateCurrFramesInExplosion();
                    }
                }
                else if (actor instanceof Treasure) {
                    Treasure treasure = (Treasure) actor;
                    actor.renderActorToFrame();
                    // Checks if level is passed
                    LEVEL_ONE.hasWon(treasure);
                }
                else if (actor instanceof Inventory) {
                    Inventory inventory = (Inventory) actor;
                    // Inventory item is rendered if it has not yet been picked
                    if (!inventory.getHasBeenPicked()) {
                        inventory.renderActorToFrame();
                        // Player receives benefit from inventory item if it collides with it
                        player.receiveDamage(inventory);
                    }
                    // Inventory icon is rendered if it has been picked
                    else {
                        inventory.renderIcon();
                    }
                }
                else {
                    actor.renderActorToFrame();
                }
            }

            for (int i = 0 ; i < onScreenProjectiles.size(); i++) {
                StandardProjectile projectile = onScreenProjectiles.get(i);
                if (!projectile.getInvalidProjectile()) {
                    projectile.inGameActions(input, level, player);
                    player.receiveDamage(projectile);
                }
            }

            player.setNextCoordinate(input, level);
            player.setCurrFramesInAttack();
            player.setCurrFramesInCooldown();
        }

        // Render win message if level was passed and space was pressed
        else if (level.getWasLevelPassed() && level.getSpaceWasPressed()) {
            level.renderWinMessage();
        }

        // For testing purposes - skip ahead to L1 by pressing 'W' key
        if (input.wasPressed(Keys.W)) {
            LEVEL_ZERO.setSpaceWasPressed(true);
            LEVEL_ZERO.setWasLevelPassed(true);
        }

        else if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
    }
}