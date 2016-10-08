package com.nbm.investment.model.enums;

public enum InvestmentProductState
{
        /**
         * 预告期
         */
        NOTICE,

        /**
         * 募集期
         */
        SUBSCRIPTION,
        
        /**
         * 募集失败
         */
        FAILED,
        
        /**
         * 坏账
         */
        BADMONEY,
        
        /**
         * 履约期
         */
        COMPLIANCE,

        /**
         * 已经还款，结束
         * 
         * 处于结束状态下的产品，不能继续投资，只能还款
         */
        FINISHED;

}
