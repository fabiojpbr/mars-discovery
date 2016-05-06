package sako.fabio.nasa.discovery.validation.interfaces;
/**
 * Interface de Validação
 * @author fabio
 *
 */
public interface Validator<T> {
	
	/**
	 * Realiza o check em <T>
	 * @param t
	 */
	public void check(T t);
}
