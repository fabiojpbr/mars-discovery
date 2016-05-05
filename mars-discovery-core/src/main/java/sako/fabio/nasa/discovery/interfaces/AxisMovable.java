package sako.fabio.nasa.discovery.interfaces;
/**
 * Interface que tem a ação de movimento do eixo
 * @author fabio
 *
 */
public interface AxisMovable{
	/**
	 * Movimento positivo do eixo x
	 */
	void upX();
	/**
	 * Movimento negativo do eixo x
	 */
	void downX();
	/**
	 * Movimento positivo do eixo y
	 */
	void upY();
	/**
	 * Movimento negativo do eixo y
	 */
	void downY();
}
