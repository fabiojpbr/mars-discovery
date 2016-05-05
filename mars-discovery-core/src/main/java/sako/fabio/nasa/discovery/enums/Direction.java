package sako.fabio.nasa.discovery.enums;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.interfaces.AxisMovable;

/**
 * Enum que representa as Direções dos pontos Cardinais
 * N -> Norte
 * E -> Leste
 * S -> Sul
 * W -> Oeste
 * @author fabio
 *
 */
public enum Direction {
	
	N {
		@Override
		public void move(AxisMovable movableXY) {
			movableXY.upY();
			
		}
	},E {
		@Override
		public void move(AxisMovable movableXY) {
			movableXY.upX();
			
		}
	},S {
		@Override
		public void move(AxisMovable movableXY) {
			movableXY.downY();
			
		}
	},W {
		@Override
		public void move(AxisMovable movableXY) throws BordersInvasionException, BusyPlaceException {
			movableXY.downX();
			
		}
	};
	/**
	 * Método que obtem o próximo ponto da Direção à direita
	 * @return ponto da Direção à direita
	 */
	public Direction getNextRight(){
		
		int actual = this.ordinal();
		actual +=1;
		if(Direction.values().length == actual){
			actual = 0;
		}
		return Direction.values()[actual];
	}
	/**
	 * Método que obtem o próximo ponto da Direção à esquerda
	 * @return ponto da Direção à esquerda
	 */
	public Direction getNextLeft(){
		int actual = this.ordinal();
		actual -=1;
		if(actual < 0){
			actual = Direction.values().length - 1;
		}
		return Direction.values()[actual];
	}
	/**
	 * Método abstrato utilizado no Enum {@link Direction} para realizar a movimentação
	 * @param movableXY
	 */
	public abstract void move(AxisMovable movableXY);
}
