	var mainMenu;
	var mainTabs;

	function addNav(data) {
		$.each(data, function(i, sm) {
			var menulist = "";
			menulist += '<ul>';
			$.each(sm.menus, function(j, o) {
				menulist += '<li><div><a ref="' + o.menuid + '" href="javascript:void(0)" rel="'
						+ o.url + '" ><span class="icon ' + o.icon + '" >&nbsp;</span><span class="nav">' + o.menuname
						+ '</span></a></div></li> ';
			});
			menulist += '</ul>';

			$('#mainMenu').accordion('add', {
				title : sm.menuname,
				content : menulist,
				iconCls : 'icon ' + sm.icon,
				selected: i==0?true:false
			});
		});

		var pp = $('#mainMenu').accordion('panels');
		var t = pp[0].panel('options').title;
		$('#mainMenu').accordion('select', t);

	}
	
	function Clearnav() {
		var pp = $('#mainMenu').accordion('panels');

		$.each(pp, function(i, n) {
			if (n) {
				var t = n.panel('options').title;
				$('#mainMenu').accordion('remove', t);
			}
		});

		pp = $('#mainMenu').accordion('getSelected');
		if (pp) {
			var title = pp.panel('options').title;
			$('#mainMenu').accordion('remove', title);
		}
	}

	// 初始化左侧
	function InitLeftMenu() {
		hoverMenuItem();
		$(document).on('click','#mainMenu li a', function() {
			var tabTitle = $(this).children('.nav').text();
			var url = $(this).attr("rel");
			var menuid = $(this).attr("ref");
			var icon = getIcon(menuid, icon);
			addTab(tabTitle, url, icon);
			$('#mainMenu li div').removeClass("selected");
			$(this).parent().addClass("selected");
		});

	}

	/**
	 * 菜单项鼠标Hover
	 */
	function hoverMenuItem() {
		$(".easyui-accordion").find('a').hover(function() {
			$(this).parent().addClass("hover");
		}, function() {
			$(this).parent().removeClass("hover");
		});
	}

	// 获取左侧导航的图标
	function getIcon(menuid) {
		var icon = '';
		$.each(_menus, function(i, n) {
			$.each(n.menus, function(k, m){
				if (m.menuid == menuid) {
					icon += m.icon;
					return false;
				}
			});
		});
		return icon;
	}
	
	function addTab(subtitle, url, icon) {
		if (!$('#mainTabs').tabs('exists', subtitle)) {
			var opts = {
				title : subtitle,
				closable : true,
				iconCls : icon,
				content : sy.formatString('<iframe src="'+sy.basePath+'{0}"  allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', url),
				border : false,
				fit : true
			};
			
			$('#mainTabs').tabs('add', opts);
		} else {
			$('#mainTabs').tabs('select', subtitle);
			//$('#mm-tabupdate').click();
		}
		//tabClose();
	}
	
	$(function() {
		//初始化左侧
		$("#mainMenu").accordion( {
			animate : true
		});
	
		addNav(_menus);
		InitLeftMenu();
		
		//修改密码
		$('#passwordDialog').show().dialog({
			modal : true,
			closable : true,
			iconCls : 'ext-icon-lock_edit',
			buttons : [ {
				text : '修改',
				handler : function() {
					if ($('#passwordDialog form').form('validate')) {
						$.post(sy.contextPath + '/manager/changePwd', {
							'oldpwd' : $('#oldpwd').val(),
							'newpwd' : $('#newpwd').val()
						}, function(result) {
							if (result.code == 0) {
								$.messager.i('密码修改成功！');
								$('#passwordDialog').dialog('close');
							}else{
								$.messager.w('密码错误！');
							}
						}, 'json');
					}
				}
			} ],
			onOpen : function() {
				$('#passwordDialog form :input').val('');
			}
		}).dialog('close');
		
		$('#mainLayout').layout('panel', 'center').panel({
			onResize : function(width, height) {
				sy.setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);
			}
		});

		//tab页菜单
		mainTabs = $('#mainTabs').tabs({
			fit : true,
			border : false,
			tools : [ {
				iconCls : 'ext-icon-arrow_up',
				handler : function() {
					mainTabs.tabs({
						tabPosition : 'top'
					});
				}
			}, {
				iconCls : 'ext-icon-arrow_left',
				handler : function() {
					mainTabs.tabs({
						tabPosition : 'left'
					});
				}
			}, {
				iconCls : 'ext-icon-arrow_down',
				handler : function() {
					mainTabs.tabs({
						tabPosition : 'bottom'
					});
				}
			}, {
				iconCls : 'ext-icon-arrow_right',
				handler : function() {
					mainTabs.tabs({
						tabPosition : 'right'
					});
				}
			}, {
				iconCls : 'ext-icon-arrow_refresh',
				handler : function() {
					var panel = mainTabs.tabs('getSelected').panel('panel');
					var frame = panel.find('iframe');
					try {
						if (frame.length > 0) {
							for (var i = 0; i < frame.length; i++) {
								frame[i].contentWindow.document.write('');
								frame[i].contentWindow.close();
								frame[i].src = frame[i].src;
							}
							if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
								try {
									CollectGarbage();
								} catch (e) {
								}
							}
						}
					} catch (e) {
					}
				}
			}, {
				iconCls : 'ext-icon-cross',
				handler : function() {
					var index = mainTabs.tabs('getTabIndex', mainTabs.tabs('getSelected'));
					var tab = mainTabs.tabs('getTab', index);
					if (tab.panel('options').closable) {
						mainTabs.tabs('close', index);
					} else {
						//$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
					}
				}
			} ]
		});

	});