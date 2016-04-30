package sako.fabio.nasa.discovery.enums;

import sako.fabio.nasa.discovery.exceptions.BordersInvasionException;
import sako.fabio.nasa.discovery.exceptions.BusyPlaceException;
import sako.fabio.nasa.discovery.interfaces.Controllable;

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
		public void execute(Controllable controllable) throws BordersInvasionException, BusyPlaceException  {
			controllable.move();
			
		}
	};
	
	public abstract void execute(Controllable controllable)  throws BordersInvasionException, BusyPlaceException ;
	
}
