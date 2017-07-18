package com.yz.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GamePlayerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GamePlayerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNull() {
            addCriterion("game_id is null");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNotNull() {
            addCriterion("game_id is not null");
            return (Criteria) this;
        }

        public Criteria andGameIdEqualTo(String value) {
            addCriterion("game_id =", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotEqualTo(String value) {
            addCriterion("game_id <>", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThan(String value) {
            addCriterion("game_id >", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThanOrEqualTo(String value) {
            addCriterion("game_id >=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThan(String value) {
            addCriterion("game_id <", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThanOrEqualTo(String value) {
            addCriterion("game_id <=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLike(String value) {
            addCriterion("game_id like", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotLike(String value) {
            addCriterion("game_id not like", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdIn(List<String> values) {
            addCriterion("game_id in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotIn(List<String> values) {
            addCriterion("game_id not in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdBetween(String value1, String value2) {
            addCriterion("game_id between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotBetween(String value1, String value2) {
            addCriterion("game_id not between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andIsCaptainIsNull() {
            addCriterion("is_captain is null");
            return (Criteria) this;
        }

        public Criteria andIsCaptainIsNotNull() {
            addCriterion("is_captain is not null");
            return (Criteria) this;
        }

        public Criteria andIsCaptainEqualTo(Integer value) {
            addCriterion("is_captain =", value, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainNotEqualTo(Integer value) {
            addCriterion("is_captain <>", value, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainGreaterThan(Integer value) {
            addCriterion("is_captain >", value, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_captain >=", value, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainLessThan(Integer value) {
            addCriterion("is_captain <", value, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainLessThanOrEqualTo(Integer value) {
            addCriterion("is_captain <=", value, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainIn(List<Integer> values) {
            addCriterion("is_captain in", values, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainNotIn(List<Integer> values) {
            addCriterion("is_captain not in", values, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainBetween(Integer value1, Integer value2) {
            addCriterion("is_captain between", value1, value2, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andIsCaptainNotBetween(Integer value1, Integer value2) {
            addCriterion("is_captain not between", value1, value2, "isCaptain");
            return (Criteria) this;
        }

        public Criteria andGoalIsNull() {
            addCriterion("goal is null");
            return (Criteria) this;
        }

        public Criteria andGoalIsNotNull() {
            addCriterion("goal is not null");
            return (Criteria) this;
        }

        public Criteria andGoalEqualTo(Integer value) {
            addCriterion("goal =", value, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalNotEqualTo(Integer value) {
            addCriterion("goal <>", value, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalGreaterThan(Integer value) {
            addCriterion("goal >", value, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalGreaterThanOrEqualTo(Integer value) {
            addCriterion("goal >=", value, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalLessThan(Integer value) {
            addCriterion("goal <", value, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalLessThanOrEqualTo(Integer value) {
            addCriterion("goal <=", value, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalIn(List<Integer> values) {
            addCriterion("goal in", values, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalNotIn(List<Integer> values) {
            addCriterion("goal not in", values, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalBetween(Integer value1, Integer value2) {
            addCriterion("goal between", value1, value2, "goal");
            return (Criteria) this;
        }

        public Criteria andGoalNotBetween(Integer value1, Integer value2) {
            addCriterion("goal not between", value1, value2, "goal");
            return (Criteria) this;
        }

        public Criteria andAssistsIsNull() {
            addCriterion("assists is null");
            return (Criteria) this;
        }

        public Criteria andAssistsIsNotNull() {
            addCriterion("assists is not null");
            return (Criteria) this;
        }

        public Criteria andAssistsEqualTo(Integer value) {
            addCriterion("assists =", value, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsNotEqualTo(Integer value) {
            addCriterion("assists <>", value, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsGreaterThan(Integer value) {
            addCriterion("assists >", value, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsGreaterThanOrEqualTo(Integer value) {
            addCriterion("assists >=", value, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsLessThan(Integer value) {
            addCriterion("assists <", value, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsLessThanOrEqualTo(Integer value) {
            addCriterion("assists <=", value, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsIn(List<Integer> values) {
            addCriterion("assists in", values, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsNotIn(List<Integer> values) {
            addCriterion("assists not in", values, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsBetween(Integer value1, Integer value2) {
            addCriterion("assists between", value1, value2, "assists");
            return (Criteria) this;
        }

        public Criteria andAssistsNotBetween(Integer value1, Integer value2) {
            addCriterion("assists not between", value1, value2, "assists");
            return (Criteria) this;
        }

        public Criteria andReboundsIsNull() {
            addCriterion("rebounds is null");
            return (Criteria) this;
        }

        public Criteria andReboundsIsNotNull() {
            addCriterion("rebounds is not null");
            return (Criteria) this;
        }

        public Criteria andReboundsEqualTo(Integer value) {
            addCriterion("rebounds =", value, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsNotEqualTo(Integer value) {
            addCriterion("rebounds <>", value, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsGreaterThan(Integer value) {
            addCriterion("rebounds >", value, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsGreaterThanOrEqualTo(Integer value) {
            addCriterion("rebounds >=", value, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsLessThan(Integer value) {
            addCriterion("rebounds <", value, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsLessThanOrEqualTo(Integer value) {
            addCriterion("rebounds <=", value, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsIn(List<Integer> values) {
            addCriterion("rebounds in", values, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsNotIn(List<Integer> values) {
            addCriterion("rebounds not in", values, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsBetween(Integer value1, Integer value2) {
            addCriterion("rebounds between", value1, value2, "rebounds");
            return (Criteria) this;
        }

        public Criteria andReboundsNotBetween(Integer value1, Integer value2) {
            addCriterion("rebounds not between", value1, value2, "rebounds");
            return (Criteria) this;
        }

        public Criteria andStealsIsNull() {
            addCriterion("steals is null");
            return (Criteria) this;
        }

        public Criteria andStealsIsNotNull() {
            addCriterion("steals is not null");
            return (Criteria) this;
        }

        public Criteria andStealsEqualTo(Integer value) {
            addCriterion("steals =", value, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsNotEqualTo(Integer value) {
            addCriterion("steals <>", value, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsGreaterThan(Integer value) {
            addCriterion("steals >", value, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsGreaterThanOrEqualTo(Integer value) {
            addCriterion("steals >=", value, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsLessThan(Integer value) {
            addCriterion("steals <", value, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsLessThanOrEqualTo(Integer value) {
            addCriterion("steals <=", value, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsIn(List<Integer> values) {
            addCriterion("steals in", values, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsNotIn(List<Integer> values) {
            addCriterion("steals not in", values, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsBetween(Integer value1, Integer value2) {
            addCriterion("steals between", value1, value2, "steals");
            return (Criteria) this;
        }

        public Criteria andStealsNotBetween(Integer value1, Integer value2) {
            addCriterion("steals not between", value1, value2, "steals");
            return (Criteria) this;
        }

        public Criteria andBlockIsNull() {
            addCriterion("block is null");
            return (Criteria) this;
        }

        public Criteria andBlockIsNotNull() {
            addCriterion("block is not null");
            return (Criteria) this;
        }

        public Criteria andBlockEqualTo(Integer value) {
            addCriterion("block =", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotEqualTo(Integer value) {
            addCriterion("block <>", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockGreaterThan(Integer value) {
            addCriterion("block >", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockGreaterThanOrEqualTo(Integer value) {
            addCriterion("block >=", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockLessThan(Integer value) {
            addCriterion("block <", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockLessThanOrEqualTo(Integer value) {
            addCriterion("block <=", value, "block");
            return (Criteria) this;
        }

        public Criteria andBlockIn(List<Integer> values) {
            addCriterion("block in", values, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotIn(List<Integer> values) {
            addCriterion("block not in", values, "block");
            return (Criteria) this;
        }

        public Criteria andBlockBetween(Integer value1, Integer value2) {
            addCriterion("block between", value1, value2, "block");
            return (Criteria) this;
        }

        public Criteria andBlockNotBetween(Integer value1, Integer value2) {
            addCriterion("block not between", value1, value2, "block");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}