package com.rescripter.script;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchWindow;
import org.mozilla.javascript.NativeJavaObject;

import com.rescripter.resources.FileContentsReader;
import com.rescripter.resources.WorkspaceScriptLoader;

public class RunScript {

	private ScriptStack scriptStack = new ScriptStack();
	private final FileContentsReader fileReader = new FileContentsReader();
	private ScriptRunner runner;
	
	public RunScript(IWorkbenchWindow window) throws IOException {
		this.runner = new ScriptRunner(window, scriptStack, fileReader);
	}
	
	public void withContents(String contents, IFile file) {
		try {
	    	WorkspaceScriptLoader loader = new WorkspaceScriptLoader(file, runner, scriptStack, fileReader);
			scriptStack.push(loader);
			runner.run(contents);
			scriptStack.pop();
		} finally {
			runner.done();
		}
	}
	
	public Object getProperty(String name) {
		return runner.getProperty(name);
	}
	
   public <T> T getProperty(Class<T> clazz, String name) {
      return clazz.cast(((NativeJavaObject) getProperty(name)).unwrap());
   }
	
	public Integer getIntegerProperty(String name) {
		return (Integer) getProperty(name);
	}
	
	public String getStringProperty(String name) {
		return (String) getProperty(name);
	}
	
	public void setScriptStack(ScriptStack scriptStack) {
		this.scriptStack = scriptStack;
	}

	public void setScriptRunner(ScriptRunner runner) {
		this.runner = runner;
	}
}
