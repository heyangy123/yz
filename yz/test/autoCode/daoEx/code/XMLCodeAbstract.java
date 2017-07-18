package autoCode.daoEx.code;

public abstract class XMLCodeAbstract {
	protected String ProjectName;
	protected String BeanName;
	protected String MapperClass;
	protected String savePath;
	
	public String getBeanName() {
		return BeanName;
	}
	public void setBeanName(String beanName) {
		BeanName = beanName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getMapperClass() {
		return MapperClass;
	}
	public void setMapperClass(String mapperClass) {
		MapperClass = mapperClass;
	}
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getClassName() {
		return BeanName;
	}
	public void setClassName(String BeanName) {
		BeanName = BeanName;
	}
	public abstract void save() throws Exception;
	public abstract String toStr();
	
}
	
