package com.yz.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.item.util.IDUtils;
import com.yz.dao.model.Banner;
import com.yz.dao.model.Product;
import com.yz.dao.model.ProductExample;
import com.yz.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductController extends CoreController{
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public String list(Banner banner , Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		ProductExample example = new ProductExample();
		example.setOrderByClause("id");
		List<Product> list = productService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Product product) {
		if(StringUtils.isBlank(product.getId())){//添加
			product.setId(IDUtils.next());
			product.setCreateTime(new Date());
			productService.insert(product);
		}else{
			//update
			productService.updateByPrimaryKeySelective(product);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(String id) {
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			productService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ResponseBody
	public String findById(String id) throws Exception{
		Product product = productService.selectByPrimaryKey(id);
		return JSONUtils.serialize(product);
	}
}
