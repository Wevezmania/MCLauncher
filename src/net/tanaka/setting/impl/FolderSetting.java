package net.tanaka.setting.impl;

import java.io.File;

import net.tanaka.setting.Setting;

public class FolderSetting extends Setting<File> {
	
	@Override
	public void setValue(File value) {
		// TODO Auto-generated method stub
		
	}
	
	public FolderSetting(final String name, final File value) {
		super(name, value);
	}

}
