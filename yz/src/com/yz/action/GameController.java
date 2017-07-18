package com.yz.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dao.model.Ret;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.item.dao.model.User;
import com.item.service.CodeService;
import com.item.service.UserService;
import com.item.util.IDUtils;
import com.yz.dao.model.Banner;
import com.yz.dao.model.Game;
import com.yz.dao.model.GameDetail;
import com.yz.dao.model.GameExample;
import com.yz.dao.model.GamePlayer;
import com.yz.daoEx.model.GamePlayerEx;
import com.yz.service.GameDetailService;
import com.yz.service.GamePlaceService;
import com.yz.service.GamePlayerService;
import com.yz.service.GameService;
import com.yz.service.GameTypeService;


@Controller
@RequestMapping("/game")
public class GameController extends CoreController {

	@Autowired
	private GameService gameService;
	@Autowired
	private GameTypeService gameTypeService;
	@Autowired
	private GamePlaceService gamePlaceService;
	@Autowired
	private GamePlayerService gamePlayerService;
	@Autowired
	private GameDetailService gameDetailService;
	@Autowired
	CodeService codeService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public String list(Banner banner , Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		GameExample example = new GameExample();
		example.setOrderByClause("id");
		List<Game> list = gameService.selectByExample(example);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Game game) {
		List<GameDetail> gameDetailList = game.getGameDetailList();
		if(StringUtils.isBlank(game.getId())){//添加
			game.setId(IDUtils.next());
			game.setCreateTime(new Date());
			game.setState(1);
			gameService.insert(game);
			
			if(gameDetailList!=null && gameDetailList.size()>0){
				for (GameDetail gameDetail : gameDetailList) {
					if(gameDetail.getType()==0){
						//约战方
						gameDetail.setId(IDUtils.next());
						gameDetail.setGameId(game.getId());
						gameDetail.setUserId(game.getUserId());
						gameDetail.setCreateTime(new Date());
						gameDetailService.insertSelective(gameDetail);
					}
				}
			}
		}else{//update
			gameService.updateByPrimaryKeySelective(game);
			/*if(gameDetailList!=null && gameDetailList.size()>0){
				for (GameDetail gameDetail : gameDetailList) {
					if(gameDetail.getType()==0 || gameDetail.getType()==1){
						if(StringUtils.isBlank(gameDetail.getId())){
							gameDetail.setId(IDUtils.next());
							gameDetail.setGameId(game.getId());
							gameDetail.setCreateTime(new Date());
							gameDetailService.insertSelective(gameDetail);
						}else{
							gameDetailService.updateByPrimaryKeySelective(gameDetail);
						}
					}
				}
			}*/
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(String id) {
		String[] ids =id.split(",");
		for (int i = 0; i < ids.length; i++) {
			gameService.deleteByPrimaryKey(ids[i]);
		}
		return JSONUtils.serialize(new Ret(0));
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ResponseBody
	public String findById(String id) throws Exception{
		Game game = gameService.selectByPrimaryKey(id);
		if(game!=null){
			game.setGameTypeList(gameTypeService.selectByExample(null));
			game.setGamePlaceList(gamePlaceService.selectByExample(null));
			game.setGameDetailList(gameDetailService.selectByExample(null));
		}
		return JSONUtils.serialize(game);
	}
	
	@RequestMapping(value = "/findGamePlayers", method = RequestMethod.POST)
	@ResponseBody
	public String findGamePlayers(@RequestParam(value="gameId", required=true) String gameId, 
			@RequestParam(value="type", required=false) Integer type) throws Exception{
		List<GamePlayerEx> list = gamePlayerService.selectByGameId(gameId, type);
		return JSONUtils.serialize(list);
	}
	
	@RequestMapping(value = "/updateGamePlayers", method = RequestMethod.POST)
	@ResponseBody
	public String updateGamePlayers(@RequestBody Game game) throws Exception{
		Integer winner = game.getWinner();//0:约战方 1:应战方
		List<GamePlayer> list = game.getGamePlayerList();
		if(game.getId()==null || winner==null || list==null || list.size()==0){
			return JSONUtils.serialize(new Ret(1, "录入信息不完整"));
		}
		
		Game gameData = gameService.selectByPrimaryKey(game.getId());
		boolean isFirstSave = gameData.getWinner()==null;
		if(isFirstSave){
			//第一次录入
			Game tmp = new Game();
			tmp.setId(game.getId());
			tmp.setWinner(winner);
			tmp.setState(4);//已经结算
			gameService.updateByPrimaryKeySelective(tmp);
		}
			
		Integer loser_score = parseInt(codeService.getCode("game_loser_score"));
		Integer winner_score = parseInt(codeService.getCode("game_winner_score"));
		for (GamePlayer gamePlayer : list) {
			if(StringUtils.isNotBlank(gamePlayer.getId())){
				gamePlayerService.updateByPrimaryKeySelective(gamePlayer);
				
				if(isFirstSave){
					//第一次录入发放积分
					User user = new User();
					user.setId(gamePlayer.getUserId());
					user.setGoal(gamePlayer.getGoal());
					user.setAssists(gamePlayer.getAssists());
					user.setRebounds(gamePlayer.getRebounds());
					user.setSteals(gamePlayer.getSteals());
					user.setBlock(gamePlayer.getBlock());
					if(winner == gamePlayer.getType()){
						if(winner_score!=null){
							user.setTotalScore(winner_score);
							user.setScore(winner_score);
							userService.updateScorePlus(user);
						}
					}else{
						if(loser_score!=null){
							user.setTotalScore(loser_score);
							user.setScore(loser_score);
							userService.updateScorePlus(user);
						}
					}
				}
			}
		}
		
		return JSONUtils.serialize(new Ret(0));
	}
	
	public Integer parseInt(String numStr){
		try {
			return Integer.parseInt(numStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
