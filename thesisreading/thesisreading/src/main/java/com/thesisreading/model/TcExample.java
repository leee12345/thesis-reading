package com.thesisreading.model;

import java.util.ArrayList;
import java.util.List;

public class TcExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TcExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andTcIdIsNull() {
            addCriterion("tc_id is null");
            return (Criteria) this;
        }

        public Criteria andTcIdIsNotNull() {
            addCriterion("tc_id is not null");
            return (Criteria) this;
        }

        public Criteria andTcIdEqualTo(String value) {
            addCriterion("tc_id =", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdNotEqualTo(String value) {
            addCriterion("tc_id <>", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdGreaterThan(String value) {
            addCriterion("tc_id >", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdGreaterThanOrEqualTo(String value) {
            addCriterion("tc_id >=", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdLessThan(String value) {
            addCriterion("tc_id <", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdLessThanOrEqualTo(String value) {
            addCriterion("tc_id <=", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdLike(String value) {
            addCriterion("tc_id like", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdNotLike(String value) {
            addCriterion("tc_id not like", value, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdIn(List<String> values) {
            addCriterion("tc_id in", values, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdNotIn(List<String> values) {
            addCriterion("tc_id not in", values, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdBetween(String value1, String value2) {
            addCriterion("tc_id between", value1, value2, "tcId");
            return (Criteria) this;
        }

        public Criteria andTcIdNotBetween(String value1, String value2) {
            addCriterion("tc_id not between", value1, value2, "tcId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(String value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(String value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(String value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(String value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(String value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(String value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLike(String value) {
            addCriterion("course_id like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotLike(String value) {
            addCriterion("course_id not like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<String> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<String> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(String value1, String value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(String value1, String value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIsNull() {
            addCriterion("teacher_id is null");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIsNotNull() {
            addCriterion("teacher_id is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherIdEqualTo(String value) {
            addCriterion("teacher_id =", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotEqualTo(String value) {
            addCriterion("teacher_id <>", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdGreaterThan(String value) {
            addCriterion("teacher_id >", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdGreaterThanOrEqualTo(String value) {
            addCriterion("teacher_id >=", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLessThan(String value) {
            addCriterion("teacher_id <", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLessThanOrEqualTo(String value) {
            addCriterion("teacher_id <=", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLike(String value) {
            addCriterion("teacher_id like", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotLike(String value) {
            addCriterion("teacher_id not like", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIn(List<String> values) {
            addCriterion("teacher_id in", values, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotIn(List<String> values) {
            addCriterion("teacher_id not in", values, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdBetween(String value1, String value2) {
            addCriterion("teacher_id between", value1, value2, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotBetween(String value1, String value2) {
            addCriterion("teacher_id not between", value1, value2, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityIsNull() {
            addCriterion("tea_identity is null");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityIsNotNull() {
            addCriterion("tea_identity is not null");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityEqualTo(String value) {
            addCriterion("tea_identity =", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityNotEqualTo(String value) {
            addCriterion("tea_identity <>", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityGreaterThan(String value) {
            addCriterion("tea_identity >", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityGreaterThanOrEqualTo(String value) {
            addCriterion("tea_identity >=", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityLessThan(String value) {
            addCriterion("tea_identity <", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityLessThanOrEqualTo(String value) {
            addCriterion("tea_identity <=", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityLike(String value) {
            addCriterion("tea_identity like", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityNotLike(String value) {
            addCriterion("tea_identity not like", value, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityIn(List<String> values) {
            addCriterion("tea_identity in", values, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityNotIn(List<String> values) {
            addCriterion("tea_identity not in", values, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityBetween(String value1, String value2) {
            addCriterion("tea_identity between", value1, value2, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeaIdentityNotBetween(String value1, String value2) {
            addCriterion("tea_identity not between", value1, value2, "teaIdentity");
            return (Criteria) this;
        }

        public Criteria andTeachHourIsNull() {
            addCriterion("teach_hour is null");
            return (Criteria) this;
        }

        public Criteria andTeachHourIsNotNull() {
            addCriterion("teach_hour is not null");
            return (Criteria) this;
        }

        public Criteria andTeachHourEqualTo(Integer value) {
            addCriterion("teach_hour =", value, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourNotEqualTo(Integer value) {
            addCriterion("teach_hour <>", value, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourGreaterThan(Integer value) {
            addCriterion("teach_hour >", value, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("teach_hour >=", value, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourLessThan(Integer value) {
            addCriterion("teach_hour <", value, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourLessThanOrEqualTo(Integer value) {
            addCriterion("teach_hour <=", value, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourIn(List<Integer> values) {
            addCriterion("teach_hour in", values, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourNotIn(List<Integer> values) {
            addCriterion("teach_hour not in", values, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourBetween(Integer value1, Integer value2) {
            addCriterion("teach_hour between", value1, value2, "teachHour");
            return (Criteria) this;
        }

        public Criteria andTeachHourNotBetween(Integer value1, Integer value2) {
            addCriterion("teach_hour not between", value1, value2, "teachHour");
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
