package com.item.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.CoreConstants;
import com.base.action.CoreController;
import com.base.dao.model.BFile;
import com.base.dao.model.Ret;
import com.base.service.BFileService;
import com.base.support.FileUpRetn;
import com.base.util.GetuiPushUtil;
import com.base.util.JSONUtils;
import com.base.util.ResponseUtils;
import com.item.dao.model.SinglePage;
import com.item.service.FocusService;
import com.item.service.SinglePageService;

@Controller
public class SystemController extends CoreController {
	@Autowired
	private SinglePageService singlePageService;
	@Autowired
	private FocusService focusService;
	
	
	
	@Autowired
	private BFileService bFileService;
	

	@RequestMapping(value="/", method = RequestMethod.GET)
    public String defalut(){
    	return index();
    }
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
    public String index(){
    	return "website/index";
    }

//	/**
//	 * webview
//	 * @param id
//	 * @param type 类型
//	 */
//	@RequestMapping("/detail")
//	public String detail(String id, Integer type, ModelMap map,Integer flag) throws Exception {
//		if (StringUtils.isBlank(id) || type == null) {
//			return null;
//		}
//		Map<String,Object> ret = new HashMap<String,Object>();
//		switch (type) {
//		case 1:
//			// type==1 团购详情
//			GroupActivity a = groupActivityService.selectByPrimaryKey(id);
//			if (a != null) {
//				ret.put("content", a.getDetail());
//				map.put("obj", ret);
//			} else {
//				return "common/404";
//			}
//			break;
//		case 2:
//			// type==2 焦点图
//			Focus focus = focusService.selectByPrimaryKey(id);
//			if (focus != null) {
//				ret.put("content", focus.getContent());
//				map.put("obj", ret);
//			} else {
//				return "common/404";
//			}
//			break;
//		case 3:
//			// type==3 微信详情页
//			Article article = articleService.selectByPrimaryKey(id);
//			if (article != null) {
//				ret.put("content", article.getContent());
//				map.put("obj", ret);
//			} else {
//				return "common/404";
//			}
//			break;
//		case 4:
//			//type==4 车系详情
//			CarTypeExample cexample = new CarTypeExample();
//			cexample.createCriteria().andCodeEqualTo(id);
//			List<CarType> types = carTypeService.selectByExample(cexample);
//			if(types.size()==0){
//				return "common/404";
//			}else{
//				ret.put("content", types.get(0).getContent());
//				map.put("obj", ret);
//			}
//			break;
//		case 5:
//			// type==5 积分兑换详情页
//			CreditGoods creditGoods = creditGoodsService.selectByPrimaryKey(id);
//			if (creditGoods != null) {
//				ret.put("content", creditGoods.getContent());
//				map.put("obj", ret);
//			} else {
//				return "common/404";
//			}
//			break;
//		case 6:
//			// type==6 商家
//			Store store = storeService.selectByPrimaryKey(id);
//			if (store != null) {
//				ret.put("content", store.getRemark());
//				map.put("obj", ret);
//			} else {
//				return "common/404";
//			}
//			break;
//		case 7:
//			// type==7 新闻详情
//			News n = newsService.selectByPrimaryKey(id);
//			if (n != null) {
//				ret.put("content", n.getContent());
//				ret.put("title", n.getTitle());
//				ret.put("createTime", n.getCreateTime());
//				map.put("obj", ret);
//			} else {
//				return "common/404";
//			}
//			return "common/appDetail";
//		default:
//			break;
//		}
//		if(flag!=null){
//			switch(flag){
//			case 1:
//				map.put("title", "图文详情");break;
//			default:
//				map.put("title", "未定义");
//			}
//			return "h5/detail";
//		}
//		return "common/detail";
//	}

	@RequestMapping("/singlePage")
	public String detail(String code,ModelMap map,Integer flag) throws Exception {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		SinglePage page = singlePageService.selectByPrimaryKey(code);
		if(page == null) page = new SinglePage();
		map.put("obj", page);
		if(flag!=null){
			switch(flag){
			case 1:
				map.put("title", "积分兑换说明");break;
			case 2:
				map.put("title", page.getRemark());break;
			default:
				map.put("title", "未定义");
			}
			return "h5/detail";
		}
		return "common/detail";
	}
	
	@RequestMapping("/www")
	public String www(String url,Integer type,ModelMap map) throws Exception {
		map.put("url", url);
		map.put("title", "");
		if(type!=null){
			switch(type){
			case 1:
				map.put("title", "查看物流");break;
			}
		}
		return "h5/www";
	}
	
//	@RequestMapping("/listCity")
//	@ResponseBody
//	public String listCity(String province) throws Exception {
//		JSONObject j = new JSONObject();
//		
//		if(StringUtils.isBlank(province)){
//			List<Provinces> pList = provincesService.listAll("");
//			j.put("pList", pList);
//		}
//		List<ProvinceCities> cList = provinceCitiesService.listAll(province);
//		j.put("cList", cList);
//		
//		JSONObject ret = new JSONObject();
//		ret.put("code", 0);
//		ret.put("data", j);
//		return ret.toString();
//	}
	
	@RequestMapping("/push")
	@ResponseBody
	public String push(String msg) throws Exception {
		if (StringUtils.isBlank(msg)) {
			return JSONUtils.serialize(new Ret(1, "请输入推送内容"));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", 1);
		params.put("msg_android", msg);
		String tag = "";
		GetuiPushUtil.pushMessageToApp(params, msg, tag, null);
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/goHtml/{id}")
	public void toHtml(@PathVariable("id")String id,HttpServletResponse response){
		BFile file = bFileService.selectByPrimaryKey(id);
		if(file==null){
			logger.error("文件不存在;id="+id);
			ResponseUtils.renderText(response, "页面未找到！");
			return;
		}else{
			if(file.getFileMinitype().indexOf("html")!=-1){
				File f = new File(CoreConstants.FILE_PATH+"/"+file.getFilePath());
				if(!f.exists()) {
					logger.error("文件不存在;id="+id);
					ResponseUtils.renderText(response, "页面未找到！");
					return;
				}
				String temp = file2String(f,"utf-8");
				ResponseUtils.render(response, temp,"text/html; charset=utf-8");
			}
		}
		return;
	}
	
	/** 
     * 文本文件转换为指定编码的字符串 
     * 
     * @param file         文本文件 
     * @param encoding 编码类型 
     * @return 转换后的字符串 
     * @throws IOException 
     */ 
    public static String file2String(File file, String encoding) { 
            InputStreamReader reader = null; 
            StringWriter writer = new StringWriter(); 
            try { 
                    if (encoding == null || "".equals(encoding.trim())) { 
                            reader = new InputStreamReader(new FileInputStream(file)); 
                    } else { 
                            reader = new InputStreamReader(new FileInputStream(file), encoding); 
                    } 
                    //将输入流写入输出流 
                    char[] buffer = new char[128]; 
                    int n = 0; 
                    while (-1 != (n = reader.read(buffer))) { 
                            writer.write(buffer, 0, n); 
                    } 
            } catch (Exception e) { 
                    e.printStackTrace(); 
                    return null; 
            } finally { 
                    if (reader != null) 
                            try { 
                                    reader.close(); 
                            } catch (IOException e) { 
                                    e.printStackTrace(); 
                            } 
            } 
            //返回转换结果 
            if (writer != null) 
                    return writer.toString(); 
            else return null; 
    }
    
//    @RequestMapping("/uploadHtml")
//    public void uploadHtmlEx(HttpServletResponse response,HttpServletRequest request,HttpSession session,String dir) throws Exception{
//    	String roleCode = getSessionParameter("roleCode", session);
////    	if(!roleCode.equals("administrator")||!roleCode.equals("admin")||!roleCode.equals("agent")) return;
//    	if (!ServletFileUpload.isMultipartContent(request)) {
//			ResponseUtils.renderJson(response, getError("请选择文件"));
//			return;
//		}
//    	String basePath = request.getSession().getServletContext().getRealPath("/");
//    	String htmlPath = basePath + CoreConstants.getProperty("h5.path");
//    	// 检查目录
//		File htmlDir = new File(htmlPath);
//		if (!htmlDir.isDirectory()) {
//			htmlDir.mkdirs();
//		}
//		// 检查目录
//		File jsDir = new File(htmlPath+"/js");
//		if (!jsDir.isDirectory()) {
//			jsDir.mkdirs();
//		}
//		
//		// 检查目录
//		File cssDir = new File(htmlPath+"/css");
//		if (!cssDir.isDirectory()) {
//			cssDir.mkdirs();
//		}
//		
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setHeaderEncoding("UTF-8");
//		List<?> items = upload.parseRequest(request);
//		Iterator<?> itr = items.iterator();
//		String callback = request.getParameter("callback");
//		while (itr.hasNext()) {
//			FileItem item = (FileItem) itr.next();
//			String fileName = item.getName();
//			if (!item.isFormField()) {
//				String md5 = Md5.md5(item.getInputStream());
//				BFileExample example = new BFileExample();
//				example.createCriteria().andFileMd5EqualTo(md5);
//				List<BFile> lf = bFileService.selectByExample(example);
//				if (lf.size() > 0) {
//					if(!StringUtils.isBlank(callback)){
//						ResponseUtils.render(response, "<script>parent."+callback+"('"+lf.get(0).getFileId()+"')</script>","text/html;charset=UTF-8");
//						return;
//					}
//					if ("image".equals(dir)) {
//						ResponseUtils.renderJson(response, new FileUpRetn(0, "download.do?id=" +lf.get(0).getFileId(), fileName, lf.get(0).getFileSize().longValue()));
//						return;
//					}
//					ResponseUtils.renderJson(response, new FileUpRetn(0, lf.get(0).getFileId(), fileName, lf.get(0).getFileSize().longValue()));
//					return;
//				}
//				long itemsize=item.getSize();
//				// 检查文件大小
//				if (itemsize >Long.parseLong(CoreConstants.FILE_MAXSIZE)) {
//					if(!StringUtils.isBlank(callback)){
//						ResponseUtils.renderText(response, "<script>parent."+callback+"(1)</script>");
//						return;
//					}
//					ResponseUtils.renderJson(response, getError("上传文件大小超过限制。"));
//					return;
//				}
//				// 检查扩展名
//				String fileExt = fileName.substring(
//						fileName.lastIndexOf(".") + 1).toLowerCase();
//				String newFileName = UUID.randomUUID().toString()
//						.replace("-", "");
//				String minetype = FileExportUtils.getMine(fileName);
//				
//				if(fileName.endsWith(".apk")){
//					minetype = "application/vnd.android.package-archive";
//				}else if(fileName.endsWith(".ipa")){
//					minetype = "application/iphone-package-archive";
//				}
//				
//				try {
//					
//					File f = null;
//					if(fileExt.equals("js")){
//						f=jsDir;
//					}else if(fileExt.equals("css")){
//						f=cssDir;
//					}else if(fileExt.equals("html")){
//						f=htmlDir;
//					}else{
//						ResponseUtils.renderJson(response, getError("只支持.js/.css/.html格式的文件"));
//						return;
//					}
//					if (!f.isDirectory()) {
//						f.mkdirs();
//					}
//					File uploadedFile = new File(f.getPath(), fileName);
//					if(uploadedFile.exists()) uploadedFile.delete();
//					item.write(uploadedFile);
//					
//					
//					
//					example.clear();
//					example.createCriteria().andFileNameEqualTo(fileName).andFilePathEqualTo(uploadedFile.getPath());
//					List<BFile> flist = bFileService.selectByExample(example);
//					if(flist.size()>0){
//						BFile ac = flist.get(0);
//						ac.setFileCreatetime(new Date());
//						ac.setFileSize(BigDecimal.valueOf(itemsize));
//						ac.setFileMinitype(minetype);
//						ac.setFileMd5(md5);
//						bFileService.updateByExampleSelective(ac, example);
//					}else{
//						BFile ac = new BFile();
//						ac.setFileCreatetime(new Date());
//						ac.setFileMinitype(minetype);
//						ac.setFilePath(uploadedFile.getPath());
//						ac.setFileState(1);
//						ac.setFileSize(BigDecimal.valueOf(itemsize));
//						ac.setFileMd5(md5);					
//						ac.setFileName(fileName);
//						
//						ac.setFileId(newFileName);
//						bFileService.insert(ac);
//					}
//					
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//					if(!StringUtils.isBlank(callback)){
//						ResponseUtils.render(response, "<script>parent."+callback+"(2)</script>","text/html;charset=UTF-8");
//						return;
//					}
//					ResponseUtils.renderJson(response, getError("上传文件失败。"));
//					return;
//				}
//				if(!StringUtils.isBlank(callback)){
//					ResponseUtils.render(response, "<script>parent."+callback+"('"+newFileName+"')</script>","text/html;charset=UTF-8");
//					return;
//					
//				}
//				if ("image".equals(dir)) {
//					newFileName = "download.do?id="+newFileName;
//				}
//				ResponseUtils.renderJson(response, new FileUpRetn(0, newFileName, fileName, itemsize));
//				return;
//			}
//		}
//		Map<String, Object> msg = new HashMap<String, Object>();
//		msg.put("error", 1);
//		msg.put("message", "没有发现文件域");
//		if(!StringUtils.isBlank(callback)){
//			ResponseUtils.render(response, "<script>parent."+callback+"(3)</script>","text/html;charset=UTF-8");
//			return;
//		}
//		ResponseUtils.renderJson(response, msg);
//		return;
//    }
//    
//    @RequestMapping("/fileList")
//    @ResponseBody
//    public String fileList(){
//    	
//    	return "";
//    }
    
    private FileUpRetn getError(String message) {
		return new FileUpRetn(1, message);
	}
}
