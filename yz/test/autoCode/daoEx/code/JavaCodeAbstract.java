package autoCode.daoEx.code;

public abstract class JavaCodeAbstract {
	protected String projectName;
	protected String ClassName;
	protected boolean isMoedl;
	
	public boolean isMoedl() {
		return isMoedl;
	}

	public void setMoedl(boolean isMoedl) {
		this.isMoedl = isMoedl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	

	public String getClassName() {
		return ClassName;
	}

	public void setClassName(String className) {
		ClassName = className;
	}

	public abstract void save() throws Exception;
	public abstract String toStr();
}
