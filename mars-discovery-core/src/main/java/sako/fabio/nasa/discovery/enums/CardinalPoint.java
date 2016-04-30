package sako.fabio.nasa.discovery.enums;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.interfaces.AxisMovable;

/**
 * Enum que representa os Pontos Cardiais
 * N -> Norte
 * E -> Leste
 * S -> Sul
 * W -> Oeste
 * @author fabio
 *
 */
public enum CardinalPoint {
	
	N {
		@Override
		public void move(AxisMovable movableXY) throws BordersInvasionException, BusyPlaceException {
			movableXY.upY();
			
		}
	},E {
		@Override
		public void move(AxisMovable movableXY) throws BordersInvasionException, BusyPlaceException {
			movableXY.upX();
			
		}
	},S {
		@Override
		public void move(AxisMovable movableXY) throws BordersInvasionException, BusyPlaceException {
			movableXY.downY();
			
		}
	},W {
		@Override
		public void move(AxisMovable movableXY) throws BordersInvasionException, BusyPlaceException {
			movableXY.downX();
			
		}
	};
	/**
	 * Método que obtem o próximo ponto Cardial à direita
	 * @return Retorna o Ponto Cardial à direita
	 */
	public CardinalPoint getNextRight(){
		
		int actual = this.ordinal();
		actual +=1;
		if(CardinalPoint.values().length == actual){
			actual = 0;
		}
		return CardinalPoint.values()[actual];
	}
	/**
	 * Método que obtem o próximo ponto Cardial à esquerda
	 * @return Retorna o Ponto Cardial à esquerda
	 */
	public CardinalPoint getNextLeft(){
		int actual = this.ordinal();
		actual -=1;
		if(actual < 0){
			actual = CardinalPoint.values().length - 1;
		}
		return CardinalPoint.values()[actual];
	}
	
	public abstract void move(AxisMovable movableXY)  throws BordersInvasionException, BusyPlaceException;
}
