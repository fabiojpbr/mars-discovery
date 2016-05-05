package sako.fabio.nasa.discovery.interfaces;
/**
 * Interface indica que o objeto é controlável
 * @author fabio
 *
 */
public interface Controllable {
	/**
	 * Vira para direita
	 */
	public void turnRight();

	/**
	 * Vira para esquerda
	 */
	public void turnLeft();
	/**
	 * Movimenta
	 */
	public void move();
}
