package com.nbm.investment.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.nbm.investment.model.enums.InvestmentProductState;
import com.nbm.investment.model.enums.RevenueModel;

/**
 * 开始筹款日期至结束筹款日期为募集期
 * 
 * 开始计息的日期至还款日期为履约期
 * 
 * 为了简化设计，只用结束筹款日期一个名词表示结束筹款和开始计息两个概念。
 * 
 * TODO
 * 是否合适，需要询问客户。如果1月31日结束筹款，2月1日计息，则意味着款必须要在1天内打给借款人，此时间是否过于紧迫？因此我怀疑此设计不一定合适。
 * 
 * 假设1月1日开始筹款，1月31日结束筹款，3月1日为还款日，则应向投资者支付2月1日（含）至2月28日（含，非闰年）共28的利息。
 * 
 * 
 * @author 玉哲
 *
 */
public class InvestmentProduct
{
        private String name;

        /**
         * 借款金额
         */
        private BigDecimal quota;

        /**
         * 借款利率
         * 
         * 借款人应支付给网站运营者的年利率
         */
        private BigDecimal interest;

        /**
         * 年化收益率
         * 
         * 如果计息周期为一整年，则收益为本金乘以年化收益率。
         * 
         * 实际收益应该为本金乘以年化收益率除以365天乘以实际借款周期
         */
        private BigDecimal annualizedReturn;

        /**
         * 开始筹款日期
         */
        private Date startDate;

        /**
         * 结束筹款日期
         */
        private Date endDate;

        /**
         * 还款日期
         */
        private Date returnDate;

        /**
         * 创建日期
         */
        private Date createDate;

        private RevenueModel revenueModel = RevenueModel.ONCE;

        private List<InvestmentRecord> investmentRecords = new ArrayList<InvestmentRecord>();

        private BigDecimal badMoney;
        
        private InvestmentProductState state;

        private String stateName;
        
        /**
         *  筹款百分比
         */
        public String getNumberFormatData()
        {
        	NumberFormat percent = NumberFormat.getPercentInstance();
			return percent.format(getMoneyNow().divide(quota, 3, BigDecimal.ROUND_DOWN));
        	
        }
        
        /**
         * 返回计息日期
         */
        public Date getCountMoneyDay()
        {
			return new LocalDate(endDate).plusDays(1).toDateMidnight().toDate();
        }
        
        /**
         * 借款人应付的利息
         * 
         * @return
         */
        public BigDecimal getBorrowerInterest()
        {
                return quota.multiply(interest)
                                .multiply(new BigDecimal(getDeadline()))
                                .divide(new BigDecimal(InvestmentInstance.DAY_OF_YEAR), 3, BigDecimal.ROUND_DOWN);
        }

        /**
         * 起息日期。
         * 
         * 比如该字段为1，则起息方式为T+1，即投资起第二天开始计算利息
         * 
         * TODO 上面这句存疑，写代码时的意图已经记不清了，需要根据现有代码推测一下是否正确
         */
        private int valueDay;

        /**
         * 结算
         * 
         * @param currentDate
         *                结算日期。如传入1月1日，意味着此函数运行于1月1日晚24:00时，对1月1日的行为进行结算
         */
        public void settle(LocalDate currentDate)
        {
                // TODO 重新设定product的状态
                
                // 如果当前日期早于开始筹款日期，晚于发布日期，则处于预告期
                if (currentDate.isBefore(new LocalDate(startDate)))
                {
                        if (state != InvestmentProductState.NOTICE)
                        {
                                state = InvestmentProductState.NOTICE;
                                stateName = "active";
                        }
                } else if (currentDate.equals(new LocalDate(startDate))) // 如果当前日期等于开始筹款日期，则将项目状态设置为募集期
                {
                        if (state != InvestmentProductState.NOTICE && state != null && state != InvestmentProductState.SUBSCRIPTION)
                        {
                                throw new RuntimeException("当前日期等于开始筹款日期，但当前项目状态不等于预告期[state=" + state + ", currentDate=" + currentDate + "]");
                        }
                        state = InvestmentProductState.SUBSCRIPTION;
                        stateName = "success";
                } else if (currentDate.equals(new LocalDate(endDate).plusDays(1)))// 如果当前日期等于结束筹款日期的下一天，则将项目状态改为履约期
                {
                        if (state != InvestmentProductState.SUBSCRIPTION)
                        {
                                throw new RuntimeException("当前日期等于計算利息日期，但当前项目状态不等于履約期");
                        }

                        // 如果款已经筹足，则进入履约期
                        if ( quota.equals(getMoneyNow()))
                        {
                        state = InvestmentProductState.COMPLIANCE;
                        stateName = "info";
                        }
                        else{
                        state = InvestmentProductState.FAILED;
                        stateName = "danger";
                        }
                        // 如果款没有筹足，则退款
                } else if (currentDate.equals(new LocalDate(returnDate).plusDays(1)))// 如果当前日期等于还款日期的下一天，则将项目状态改为结束，并进行结算
                {
                        if (state != InvestmentProductState.COMPLIANCE)
                        {
                                throw new RuntimeException("当前日期等于还款日期的下一天日期，但当前项目状态不等于結束期");
                        }

                        // TODO 结算。由于所有金额信息都是实时计算，此处怀疑不需要结算

                        state = InvestmentProductState.FINISHED;
                        stateName = "warning";
                }  
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public BigDecimal getQuota()
        {
                return quota;
        }

        public void setQuota(BigDecimal quota)
        {
                this.quota = quota;
        }

        public BigDecimal getAnnualizedReturn()
        {
                return annualizedReturn;
        }

        public void setAnnualizedReturn(BigDecimal annualizedReturn)
        {
                this.annualizedReturn = annualizedReturn;
        }

        public Date getStartDate()
        {
                return startDate;
        }

        public void setStartDate(Date startDate)
        {
                this.startDate = startDate;
        }

        public Date getCreateDate()
        {
                return createDate;
        }

        public void setCreateDate(Date createDate)
        {
                this.createDate = createDate;
        }

        public RevenueModel getRevenueModel()
        {
                return revenueModel;
        }

        public void setRevenueModel(RevenueModel revenueModel)
        {
                this.revenueModel = revenueModel;
        }

        public int getValueDay()
        {
                return valueDay;
        }

        public void setValueDay(int valueDay)
        {
                this.valueDay = valueDay;
        }

        public Date getEndDate()
        {
                return endDate;
        }

        public void setEndDate(Date endDate)
        {
                this.endDate = endDate;
        }

        public Date getReturnDate()
        {
                return returnDate;
        }

        public void setReturnDate(Date returnDate)
        {
                this.returnDate = returnDate;
        }

        /**
         * 借款期限
         * 
         * 如果开始借款时间是1日，结束时间是2日，则借款期限应为2-1+1=2天
         * 
         * @return
         */
        public int getDeadline()
        {
                return Days.daysBetween(new LocalDate(startDate), new LocalDate(endDate)).getDays() + 1;
        }

        /**
         * 添加一条理财产品投资记录
         * 
         * @param record
         */
        public void addInvestmentRecord(InvestmentRecord record)
        {
                investmentRecords.add(record);
        }

        public BigDecimal getInterest()
        {
                return interest;
        }

        public void setInterest(BigDecimal interest)
        {
                this.interest = interest;
        }

        public InvestmentProductState getState()
        {
                return state;
        }

        public void setState(InvestmentProductState state)
        {
                this.state = state;
        }

        
        /**
         * 
         * @return 已筹金额
         */
        public BigDecimal getMoneyNow()
        {
                BigDecimal result = new BigDecimal(0);
                for( InvestmentRecord i : investmentRecords)
                {
                        result = result.add(i.getAmount());
                }
                
                return result;
        }
        
        /**
         * 
         * @return 应付给投资者的利息综合
         */
        public BigDecimal getTotalInvestorInterest()
        {
                BigDecimal result = new BigDecimal(0);
                for( InvestmentRecord i : investmentRecords)
                {
                        result = result.add(i.getReturnAmount());
                }
                
                return result;
        }

        public static void main(String[] args)
        {
                int i = Days.daysBetween(new LocalDate("2015-01-01"), new LocalDate("2015-01-02"))
                                .getDays();
                System.out.println(i);
        }

		public BigDecimal getBadMoney() {
			return badMoney;
		}

		public void setBadMoney(BigDecimal badMoney) {
			this.badMoney = badMoney;
		}

		public String setBadState()
		{
			this.state = InvestmentProductState.BADMONEY;
			return this.state.toString();
		}

		public String getStateName() {
			return stateName;
		}

		public void setStateName(String stateName) {
			this.stateName = stateName;
		}
}
