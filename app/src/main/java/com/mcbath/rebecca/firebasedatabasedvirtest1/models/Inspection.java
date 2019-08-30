package com.mcbath.rebecca.firebasedatabasedvirtest1.models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.PropertyName;

/**
 * Created by Rebecca McBath
 * on 2019-08-27.
 */
public class Inspection {
	@PropertyName("inspection")

	private Brakes brakes;
	private Lights lights;
	private Wheels wheels;
	private Connections connections;
	private Exterior exterior;
	private Couplings couplings;
	private TieDowns tieDowns;
	private LockingPins lockingPins;
	private Interior interior;
	private Windshield windshield;
	private Engine engine;

	public Inspection(){
	}

	public Inspection( Brakes brakes, Lights lights, Wheels wheels,
	            Connections connections, Exterior exterior, Couplings couplings, TieDowns tieDowns, LockingPins lockingPins, Interior interior,
	            Windshield windshield, Engine engine) {

		this.brakes = brakes;
		this.lights = lights;
		this.wheels = wheels;
		this.connections = connections;
		this.exterior = exterior;
		this.couplings = couplings;
		this.tieDowns = tieDowns;
		this.lockingPins = lockingPins;
		this.interior = interior;
		this.windshield = windshield;
		this.engine = engine;
	}

	public Brakes getBrakes() {
		return brakes;
	}

	public void setBrakes(Brakes brakes) {
		this.brakes = brakes;
	}

	public Lights getLights() {
		return lights;
	}

	public void setLights(Lights lights) {
		this.lights = lights;
	}

	public Wheels getWheels() {
		return wheels;
	}

	public void setWheels(Wheels wheels) {
		this.wheels = wheels;
	}

	public Connections getConnections() {
		return connections;
	}

	public void setConnections(Connections connections) {
		this.connections = connections;
	}

	public Exterior getExterior() {
		return exterior;
	}

	public void setExterior(Exterior exterior) {
		this.exterior = exterior;
	}

	public Couplings getCouplings() {
		return couplings;
	}

	public void setCouplings(Couplings couplings) {
		this.couplings = couplings;
	}

	public TieDowns getTieDowns() {
		return tieDowns;
	}

	public void setTieDowns(TieDowns tieDowns) {
		this.tieDowns = tieDowns;
	}

	public LockingPins getLockingPins() {
		return lockingPins;
	}

	public void setLockingPins(LockingPins lockingPins) {
		this.lockingPins = lockingPins;
	}

	public Interior getInterior() {
		return interior;
	}

	public void setInterior(Interior interior) {
		this.interior = interior;
	}

	public Windshield getWindshield() {
		return windshield;
	}

	public void setWindshield(Windshield windshield) {
		this.windshield = windshield;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

}
