package com.item.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.util.JSONUtils;
import com.item.dao.model.Cities;
import com.item.dao.model.CitiesExample;
import com.item.dao.model.Provinces;
import com.item.dao.model.ProvincesExample;
import com.item.service.CitiesService;
import com.item.service.ProvincesService;


@Controller
@RequestMapping("/area")
public class AreaController {
	
	@Autowired
	private CitiesService citiesService;
	@Autowired
	private ProvincesService provincesService;
	
	
	@RequestMapping("/findProvinces")
	@ResponseBody
	public String findProvinces() throws Exception{
		ProvincesExample pex = new ProvincesExample();
		pex.setOrderByClause("id asc");
		List<Provinces> list = provincesService.selectByExample(pex);
		return JSONUtils.serialize(list);
	}
	
	@RequestMapping("/findCities")
	@ResponseBody
	public String findProvinces(String provinceCode) throws Exception{
		CitiesExample exa = new CitiesExample();
		exa.createCriteria().andProvinceCodeEqualTo(provinceCode);
		List<Cities> list = citiesService.selectByExample(exa);
		return JSONUtils.serialize(list);
	}
	
}
