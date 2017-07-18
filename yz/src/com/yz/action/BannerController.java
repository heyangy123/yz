package com.yz.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.base.CoreConstants;
import com.base.action.CoreController;
import com.base.dao.model.BFile;
import com.base.dao.model.BFileExample;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.mobile.base.Md5;
import com.base.service.BFileService;
import com.base.util.FileExportUtils;
import com.base.util.JSONUtils;
import com.base.web.annotation.LoginFilter;
import com.item.util.IDUtils;
import com.yz.dao.model.Banner;
import com.yz.dao.model.BannerExample;
import com.yz.service.BannerService;


@Controller
@RequestMapping("/banner")
@LoginFilter
public class BannerController extends CoreController {
	
	@Autowired
	private BannerService bannerService;
	@Autowired
	private BFileService fileService;
	
	
	@RequestMapping("list")
	@ResponseBody
	public String list(Banner banner , Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		BannerExample example = new BannerExample();
		example.setOrderByClause("id");
		List<Banner> list = bannerService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}

	@ResponseBody
	@RequestMapping("save")
	public String save(Banner banner) {
		if(StringUtils.isBlank(banner.getId())){//添加
			banner.setId(IDUtils.next());
			banner.setCreateTime(new Date());
			banner.setState(1);
			bannerService.insert(banner);
		}else{//update
			bannerService.updateByPrimaryKeySelective(banner);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String id) {
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			bannerService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public String findById(String id) throws Exception{
		Banner banner = bannerService.selectByPrimaryKey(id);
		return JSONUtils.serialize(banner);
	}
	
	/***  
     * 保存文件  
     *  
     * @param file  
     * @return  
     */  
    private String saveFile(HttpServletRequest request, MultipartFile file) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {
            	String md5 = Md5.md5(file.getInputStream());
                BFileExample example = new BFileExample();
				example.createCriteria().andFileMd5EqualTo(md5);
				List<BFile> lf = fileService.selectByExample(example);
				if (lf!=null && lf.size() > 0) {
					return lf.get(0).getFileId();
				}
            	
				
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )  
                //String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/" + file.getOriginalFilename();
            	String fileId = IDUtils.next();
            	
            	// 检查扩展名
            	String originalName = file.getOriginalFilename();
				String fileExt = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
            	String fileName = "banner_" + fileId + "." + fileExt;
            	//String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/banner/" + fileName;
            	// 文件保存目录路径
            	String filePath = CoreConstants.FILE_PATH + "upload/banner/" + fileName;
                System.err.println(filePath);
                File saveFile = new File(filePath);
                if (!saveFile.getParentFile().exists())
                    saveFile.getParentFile().mkdirs();
                 System.err.println(filePath);
                // 转存文件
                file.transferTo(saveFile);
                
                //save file
                BFile bfile = new BFile();
                bfile.setFileId(fileId);
                bfile.setFilePath("upload/banner/" + fileName);
                String minetype = FileExportUtils.getMine(fileName);
                bfile.setFileMinitype(minetype);
                bfile.setFileName(file.getOriginalFilename());
                bfile.setFileState(1);
                bfile.setFileSize(new BigDecimal(file.getSize()));
				BufferedImage bufferedImage = ImageIO.read(saveFile);
				int width = bufferedImage.getWidth();
				int height = bufferedImage.getHeight();
				bfile.setFileCreater(width+"x"+height);
				bfile.setFileCreatetime(new Date());
				bfile.setFileMd5(md5);
				fileService.insert(bfile);
                
                return fileId;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;  
    }  
   
    /**  
     * 上传图片  
     *  
     * @param files  
     * @param request  
     * @return  
     */  
    @ResponseBody  
    @RequestMapping("filesUpload")  
    public Ret filesUpload(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {  
        if (files != null && files.length > 0) {  
            for (int i = 0; i < files.length; i++) {  
                MultipartFile file = files[i];  
                // 保存文件  
                String fileId = saveFile(request, file);  
                if(fileId!=null){
                	Ret ret = new Ret(1);
                	ret.setData(fileId);
                	return ret;
                }
            }
        }
        return new Ret(0);
    } 
    
}
