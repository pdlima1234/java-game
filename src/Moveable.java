import bagel.Input;

public interface Moveable {
    /**
     * Sets the actor's next co-ordinate.
     * @param input accepts an Input which is the key that was pressed
     * @param level accepts a Level which is the game level
     */
    void setNextCoordinate(Input input, Level level);
}
