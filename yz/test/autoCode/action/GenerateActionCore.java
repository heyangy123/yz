package autoCode.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

public class GenerateActionCore {

	/* 配置项 配置参数include:包含;exclude:不包含。二者配置一项即可 */
	private static String include = "WebFootprint";
	private static String exclude = "";
	
	//action 所在包
	private static String package_name = "com.website";

	
	
	
	
	
	
	
	
	
	private static String class_name = "";

	private static String base_name = "";

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//String packageName = GenerateActionCore.class.getPackage().getName();
		GenerateActionCore.generateAll(package_name);
		System.out.println("执行结束!");
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	private static String createFile(String path, String fileName) {
		File dirFile = new File(path);
		if (!dirFile.exists() && !dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		if (!path.substring(path.length() - 1, path.length()).equals("/")) {
			path = (new StringBuilder(String.valueOf(path))).append("/")
					.toString();
		}
		File file = new File((new StringBuilder(String.valueOf(path))).append(
				fileName).toString());
		if (file != null && !file.isFile()) {

		}

		if (file == null || !file.isFile()) {
			try {
				file.createNewFile();
				return (new StringBuilder(String.valueOf(path))).append(
						fileName).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 根据模版创建文件内容
	 * 
	 * @param templateFile
	 * @param sourceFile
	 */
	private static void createFileContent(String templateFile, String sourceFile) {
		try {
			StringBuffer packageName = new StringBuffer(package_name);
			BufferedReader buffRader = new BufferedReader(new FileReader(
					templateFile));
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(
					sourceFile));
			String line = buffRader.readLine();
			while (line != null) {
				line = line.replaceAll("&className&", class_name);
				// 这是其中一个bug
				if (class_name.length() > 1) {
					line = line.replaceAll("&className.toFirstLowerCase&",
							class_name.substring(0, 1).toLowerCase()
									+ class_name.substring(1));
				}
				line = line.replaceAll("&className.toLowerCase&",
						class_name.toLowerCase());
				line = line.replaceAll("&basePackageName&",
						package_name.substring(0, package_name.indexOf(".")));
				line = line.replaceAll("&packageName&", packageName.toString()
						.substring(packageName.lastIndexOf(".") + 1));
				buffWriter.write(line);
				buffWriter.newLine();
				line = buffRader.readLine();
			}
			buffWriter.flush();
			buffWriter.close();
			buffRader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化参数
	 * 
	 * @param packName
	 * @param codeclass
	 * @param baseName
	 */
	@SuppressWarnings("rawtypes")
	private static void initName(String packName, Class codeclass,
			String baseName) {
		package_name = packName;
		String className = codeclass.getName();
		class_name = className.substring(className.lastIndexOf(".") + 1);
		base_name = baseName;
	}

	/**
	 * 执行
	 * 
	 * @param codeclass
	 * @param packageName
	 * @param basePath
	 */
	@SuppressWarnings("rawtypes")
	public static void generateSource(Class codeclass, String packageName,
			String basePath) {
		initName(packageName, codeclass, basePath);
		String tempName = packageName.replace(".", "\\");
		StringBuffer filePath = new StringBuffer();
		filePath.append(basePath).append("\\src").append("\\").append(tempName)
				.append("\\").append("action");
		String file = createFile(filePath.toString(), (new StringBuilder(
				class_name)).append("Controller").append(".java").toString());
		StringBuffer templatePath = new StringBuffer();
		templatePath.append(base_name).append("\\")
				.append("test\\autoCode\\action").append("\\")
				.append("templateController.template");
		if (file != null)
			createFileContent(templatePath.toString(), file);
	}

	/**
	 * 生成Controller
	 * 
	 * @param packageName
	 */
	public static void generateAll(String packageName) {
		String projectPath = System.getProperty("user.dir");
		
		Set<Class<?>> set = getClasses(packageName + ".dao.model");

		Iterator<Class<?>> it = set.iterator();

		while (it.hasNext()) {
			GenerateActionCore.generateSource(it.next(), packageName,
					projectPath);
		}
	}

	/**
	 * 根据包名获取dao.model下的所有实体类
	 * 
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClasses(String packageName) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();

						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											if (!className.contains("Example")&& addToSet(className)) {
												// 添加到classes
												classes.add(Class
														.forName(packageName
																+ '.'
																+ className));
											}
										} catch (ClassNotFoundException e) {
											// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					if (!className.contains("Example") && addToSet(className)) {
						// 添加到集合中去
						classes.add(Class
								.forName(packageName + '.' + className));
					}

				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
	
	private static Set<String> addToIncludeSet(){
		
		Set<String> set = null;
		
		if (StringUtils.isNotBlank(include)){
			set = new HashSet<String>();
			for (String str : include.split(",")){
				set.add(str.trim());
			}
		}
		return set;
	}
	
	private static Set<String> addToExcludeSet(){
		Set<String> set = null;
		
		if (StringUtils.isNotBlank(exclude)){
			set = new HashSet<String>();
			for (String str : exclude.split(",")){
				set.add(str.trim());
			}
		}
		return set;
	}
	
	private static boolean addToSet(String className){
		if (addToIncludeSet() != null){
			Iterator<String> it = addToIncludeSet().iterator();
			while (it.hasNext()){
				if (it.next().equals(className)){
					return true;
				}
			}
			return false;
		}else if (addToExcludeSet() != null){
			Iterator<String> it = addToExcludeSet().iterator();
			while (it.hasNext()){
				if (it.next().equals(className)){
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
	}
}
