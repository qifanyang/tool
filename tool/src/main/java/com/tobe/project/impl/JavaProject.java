package com.tobe.project.impl;

import java.io.File;

import com.tobe.project.IFile;
import com.tobe.project.IFolder;
import com.tobe.project.IProject;

public class JavaProject implements IProject {

	private boolean isMavenProject;
	private Folder root;

	public JavaProject(String path, boolean isMavenProject) {
		root = new Folder(path);
		this.isMavenProject = isMavenProject;
	}

	public boolean isMavenProject() {
		return isMavenProject;
	}

	public void setMavenProject(boolean isMavenProject) {
		this.isMavenProject = isMavenProject;
	}

	@Override
	public String getName() {
		return root.getName();
	}

	@Override
	public File getFile(String name) {
		File file = new File(getSourceCode().getAbsolutePath() + "/" + name);
//		return new MFile(file);
		return file;
	}

	@Override
	public IFolder getPackage(String name) {
		String replace = name.replace(".", "/");
		Folder folder = new Folder(root.getAbsolutePath() + srcPre() + "/" + replace);
		return folder;
	}

	@Override
	public IFolder getSourceCode() {
		Folder folder = new Folder(root.getAbsolutePath() + srcPre());
		return folder;
	}

	public String srcPre(){
		return isMavenProject ? "/src/main/java":"/src";
	}
}
