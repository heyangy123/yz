$(function(){
		
    /*tab标签切换*/
    function tabs(tabTit,on,tabCon){
	$(tabCon).each(function(){
	  $(this).children().eq(0).show();
	 
	  });
	$(tabTit).each(function(){
	  $(this).children().eq(0).addClass(on);
	  });
     $(tabTit).children().click(function(){
        $(this).addClass(on).siblings().removeClass(on);
         var index = $(tabTit).children().index(this);
         $(tabCon).children().eq(index).show().siblings().hide();
    });
     }
  tabs(".iheader_title2","on",".investment_con");
  
  
  
  
   function tabs2(tabTit2,on,tabCon2){
	$(tabCon2).each(function(){
	  $(this).children().eq(0).show();
	 
	  });
	$(tabTit2).each(function(){
	  $(this).children().eq(0).addClass(on);
	  });
     $(tabTit2).children().click(function(){
        $(this).addClass(on).siblings().removeClass(on);
         var index = $(tabTit2).children().index(this);
         $(tabCon2).children().eq(index).show().siblings().hide();
    });
     }
  tabs2(".tabPanel_title","on",".panes");
  
  

  
 })
 




	
