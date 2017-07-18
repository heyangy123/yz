function AutoComplete(v_item, v_options) {
	// 对外参数
	this.options = {
		url : null,
		textField:null,
		formatItem:null,
		callback : null,
		param : {}
	};

	$.extend(this.options, v_options);

	$(v_item).autocomplete(this.options.url, {
		dataType:"json",
		selectFirst : false,
		extraParams : this.options.param,
		matchCase : true,
		matchSubset : false,
		//mustMatch : true,
		parse:parse,
		formatItem:formatItem,
		width : $(v_item).width() + 9
	});
	
	function parse(data){
		if(data == '无匹配结果')return null;
		return $.map(data, function(row) {
			return {
				data: row,
				result: row[this.options.textField]
			}
		});
	}
	
	if(this.options.formatItem){
		formatItem = this.options.formatItem;
	}else{
		formatItem = function(data, i, max) {//格式化列表中的条目 row:条目对象,i:当前条目数,max:总条目数
			return data[this.options.textField];
	    }
	}
	

	if (this.options.callback) {
		var loader = this;
		$(v_item).result(function(event, row, formatted) {
			loader.options.callback(row);
		});
	}

}
