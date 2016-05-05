package sako.fabio.nasa.discovery.manager.interfaces;

import java.rmi.AlreadyBoundException;
import java.util.Collection;

import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.exceptions.AlreadyCreatedException;
import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.model.Coordination;
import sako.fabio.nasa.discovery.model.Identify;
import sako.fabio.nasa.discovery.model.Plateau;
import sako.fabio.nasa.discovery.model.Probe;
/**
 * 
 * @author fabio
 *
 *Interface com os comandos do gerenciador da Discovery
 */
public interface DiscoveryManagerInterface {
	/**
	 * Adicionar uma Sonda no Planalto
	 * @param name nome dado para sonda, é o seu identificador
	 * @param coordination Coordenada inicial
	 * @param direction Direção inicial
	 * @return
	 */
	public Probe addProbe(Identify<String> name, Coordination coordination, Direction direction);
	
	/**
	 * Executa comandos da Sonda
	 * @param name Nome da Sonda
	 * @param commands Lista de comandos a ser executado
	 * @return
	 */
	public Probe executeCommand(Identify<String> name, Collection<String> commands);

	/**
	 * Configura o Planalto
	 * @param plateau
	 */

	public void setPlateau(Plateau plateau);
	/**
	 * Retorna lista de Sondas do Planalto
	 * @return
	 */
	public Collection<Probe> getProbes();
	
	/**
	 * Busca a sonda pelo seu nome
	 * @param name
	 * @return a Sonda solicitada
	 */
	public Probe findProbeByName(Identify<String> name);
	/**
	 * Busca o Planalto
	 * @return  Planalto
	 */
	public Plateau getPlateau();
	
	/**
	 * Remove o Planalto atual
	 */
	public void deletePlateau();

	/**
	 * Remove do Planalto a Sonda
	 * @param identify identificador da Sonda que será removido
	 */
	public void deleteProbeByName(Identify<String> identify);
}
