package autoCode.daoEx.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class JavaCodeImpl extends JavaCodeAbstract {
	public static final String BLANK_1 = " ";
	public static final String SEPERATOR_1 = "\t";
	public static final String SEPERATOR_2 = "\n";
	public static final String CODE_PUBLIC = "public ";
	public static final String CODE_IMPORT = "import ";
	public static final String RT_1 = "\r\n";
	public static final String ANNOTATION_AUTHOR_PARAMTER = "@author ";
	public static final String ANNOTATION_AUTHOR_NAME = "------";
	public static final String ANNOTATION_AUTHOR = "@author ------";
	public static final String ANNOTATION_DATE = "@date ";
	public static final String ANNOTATION = "/**\r\n * @author ------\r\n * @date " + new Date().toString() + "\r\n" + " " + "*/" + "\r\n";
	private String Package;
	private String importStr;
	private String savePath;
	private String publicStr;

	public JavaCodeImpl(String projectName, String ClassName, boolean isModel) {
		this.projectName = projectName;
		this.ClassName = ClassName;
		this.isMoedl = isModel;
	}

	public void init() {
		if (this.isMoedl) {
			this.savePath = ("./src/com/" + this.projectName + "/daoEx/model");
			this.Package = ("package com." + this.projectName + ".daoEx.model;\n\n");
			this.importStr = ("import com." + this.projectName + ".dao.model." + this.ClassName + ";\n\n");
			this.publicStr = ("public class " + this.ClassName + "Ex extends " + this.ClassName + "{\n\n}");
		} else {
			this.savePath = ("./src/com/" + this.projectName + "/daoEx");
			this.importStr = "\n";
			this.Package = ("package com." + this.projectName + ".daoEx;\n\n");
			this.publicStr = ("public interface " + this.ClassName + "Ex{\n\n}");
		}
	}

	public String toStr() {
		return this.Package + this.importStr + this.publicStr;
	}

	public void save() throws Exception {
		init();
		File dir = new File(this.savePath);
		if (!dir.exists())
			dir.mkdirs();
		File java = new File(this.savePath + "/" + this.ClassName + "Ex.java");
		if (java.exists()) {
			return;
		}
		java.createNewFile();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(java);
			fos.write(toStr().getBytes("UTF-8"));
			fos.flush();
		} catch (FileNotFoundException e) {
			System.out.println("文件创建失败");
			return;
		} finally {
			if (fos != null) {
				fos.close();
			}
			fos = null;
		}
	}
}
