package com.nbm.investment.model;

import java.math.BigDecimal;
import java.util.Date;

import com.nbm.investment.model.enums.InvestmentRecordState;


/**
 * 理财产品投资记录
 * @author 玉哲
 *
 */
public class InvestmentRecord
{
        
        /**
         * 投资者
         * 
         * 由于模拟期间开发简化，没有单独的投资者实体概念，所有相关的实体类中都保存了一个名字
         */
        private String investorName;
        
        /**
         * 理财产品名字
         */
        private String productName;
        
        /**
         * 投资日期
         */
        private Date investDate;
        
        /**
         * 投资金额
         */
        private BigDecimal amount;
        
        /**
         * 状态
         */
        private InvestmentRecordState state;
        
        private InvestmentProduct investmentProduct;
        
        public InvestmentRecord( InvestmentProduct investmentProduct )
        {
                this.investmentProduct = investmentProduct;
        }

        /**
         * 
         */
        public String getInvestorName()
        {
                return investorName;
        }

        public void setInvestorName(String investorName)
        {
                this.investorName = investorName;
        }

        public String getProductName()
        {
                return productName;
        }

        public void setProductName(String productName)
        {
                this.productName = productName;
        }

        public Date getInvestDate()
        {
                return investDate;
        }

        public void setInvestDate(Date investDate)
        {
                this.investDate = investDate;
        }

        public BigDecimal getAmount()
        {
                return amount;
        }

        public void setAmount(BigDecimal amount)
        {
                this.amount = amount;
        }

        public InvestmentRecordState getState()
        {
                return state;
        }

        public void setState(InvestmentRecordState state)
        {
                this.state = state;
        }

        /**
         * 
         * @return 返还金额，只包含利息部分，不包含本金
         */
        public BigDecimal getReturnAmount()
        {
                return amount.multiply(investmentProduct.getAnnualizedReturn())
                                .multiply(new BigDecimal(investmentProduct.getDeadline()))
                                .divide(new BigDecimal(InvestmentInstance.DAY_OF_YEAR),3, BigDecimal.ROUND_DOWN);//TODO 保留三位
        }
   
        
        /**
         * 
         * @param args
         */
        public BigDecimal getReturnAmountTwo()
        {
                return amount.multiply(investmentProduct.getInterest().subtract(investmentProduct.getAnnualizedReturn()))
                                .multiply(new BigDecimal(investmentProduct.getDeadline()))
                                .divide(new BigDecimal(InvestmentInstance.DAY_OF_YEAR),3, BigDecimal.ROUND_DOWN);//TODO 保留三位
        }
//        public void setReturnAmount(BigDecimal returnAmount)
//        {
//                this.returnAmount = returnAmount;
//        }
        
        public static void main(String[] args)
        {
                new BigDecimal(10000)
//                .multiply(new BigDecimal(10000))
//                .multiply(new BigDecimal(10000))
                .divide(new BigDecimal(365),3, BigDecimal.ROUND_DOWN);
        }
}
