package sako.fabio.nasa.discovery.manager.interfaces;

import java.util.Collection;

import sako.fabio.nasa.discovery.enums.Command;
import sako.fabio.nasa.discovery.enums.Direction;
import sako.fabio.nasa.discovery.model.CommandExecution;
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
	 * @param plateauName nome do Planalto
	 * @param probeName nome dado para sonda, é o seu identificador
	 * @param coordination Coordenada inicial
	 * @param direction Direção inicial
	 * @return
	 */
	public Probe addProbe(Identify<String> plateauName, Identify<String> probeName, Coordination coordination, Direction direction);
	
	/**
	 * Executa comandos da Sonda
	 * @param plateauName nome do Planalto
	 * @param probeName Nome da Sonda
	 * @param commands Lista de comandos a ser executado
	 * @return
	 */
	public Probe executeCommand(Identify<String> plateauName, Identify<String> probeName, Collection<Command> commands);
	/**
	 * Executa uma sequencia de commando de uma lista de sondas
	 * @param plateauName nome do Planalto
	 * @param commandExecutions
	 * @return
	 */
	public Collection<CommandExecution> executeCommand(Identify<String> plateauName, Collection<CommandExecution> commandExecutions);

	/**
	 * Configura o Planalto
	 * @param plateau
	 */

	public void setPlateau(Plateau plateau);
	/**
	 * Retorna lista de Sondas do Planalto
	 * @param plateauName nome do Planalto
	 * @return
	 */
	public Collection<Probe> getProbesByPlateauName(Identify<String> plateauName);
	
	/**
	 * Busca a sonda pelo seu nome
	 * @param plateauName nome do Planalto
	 * @param probeName
	 * @return a Sonda solicitada
	 */
	public Probe findProbeByName(Identify<String> plateauName, Identify<String> probeName);
	/**
	 * Busca o Planalto
	 * @param probeName Nome da Sonda
	 * @return  Planalto
	 */
	public Plateau findPlateauByName(Identify<String> probeName);
	
	/**
	 * Remove o Planalto atual
	 * @param plateauName nome do Planalto
	 */
	public void deletePlateau(Identify<String> plateauName);

	/**
	 * Remove do Planalto a Sonda
	 * @param plateauName nome do Planalto
	 * @param probeName identificador da Sonda que será removido
	 */
	public void deleteProbeByName(Identify<String> plateauName, Identify<String> probeName);
}
