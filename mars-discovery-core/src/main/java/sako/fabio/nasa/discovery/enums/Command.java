package sako.fabio.nasa.discovery.enums;

import sako.fabio.nasa.discovery.interfaces.Controllable;
/**
 * Enum que representa os comandos que podem ser executados
 * @author fabio
 *
 */
public enum Command {
	R {
		@Override
		public void execute(Controllable controllable) {
			controllable.turnRight();
			
		}
	},L {
		@Override
		public void execute(Controllable controllable) {
			controllable.turnLeft();
			
		}
	},M {
		@Override
		public void execute(Controllable controllable){
			controllable.move();
			
		}
	};
	/**
	 * Executa uma ação determinado nos métodos que serão implementados pela interface {@link Controllable}
	 * @param controllable implementação da interface que realizará a ação
	 */
	public abstract void execute(Controllable controllable);
	
}
