package net.tanaka.setting;

public abstract class Setting <V> {
	
	private V value;
	
	private final String NAME;
	
	protected Setting(final String name, final V value) {
		this.NAME = name;
		this.value = value;
	}
	
	public abstract void setValue(final V value);
	
	public final V getValue() { return this.value; }
	public final String getName() { return this.NAME; }

}
