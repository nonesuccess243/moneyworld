package com.nbm.investment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nbm.investment.model.InvestmentInstance;
import com.nbm.investment.model.InvestmentProduct;

@Controller
public class GodController
{
        private final static Logger log = LoggerFactory.getLogger(GodController.class);
		private Object currentDate;

        
        /**
         * 
         * @param binder
         */
        @InitBinder
        public void initBinder(WebDataBinder binder)
        {
                //设置日期类型的字段，前端按照yyyy-MM-dd显示 or 解析？忘了
                binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                                "yyyy-MM-dd"), true, 10));
        }

        
        /**
         * 管理员界面统一入口
         * 
         * 如果已经开始了一次模拟，则进入该模拟的概要页面
         * 
         * 如果没有开始模拟，则进入开始模拟的页面
         * @param model
         * @return
         */
        @RequestMapping("/god/show.do")
        public String show(Model model)
        {
                if (InvestmentInstance.have())
                {
                        model.addAttribute("instance", InvestmentInstance.get());

                        return "/god/index.jsp";
                } else
                {
                        return "/god/prepare.jsp";
                }
        }
        @RequestMapping("/god/refreshPage.do")
        public String refreshPage()
        {
        	return "/god/index.jsp";
        }
        
        /**
         * 开始一次模拟
         * @param key
         * @param startDate
         * @param model
         * @return
         */
        @RequestMapping("/god/start.do")
        public String start(String key, Date startDate, Model model)
        {
                InvestmentInstance instance = new InvestmentInstance(key, startDate);
                log.debug(instance.toString());
                InvestmentInstance.set(instance);
                return "/god/show.do";
        }

        @RequestMapping("/god/jump.do")
        public String jump(String name,Model model)
        {
        	model.addAttribute("instance", InvestmentInstance.get());
        	return "/god/viewDetails.jsp";
        }
        
        /**
         * 前进一天
         * @param day
         * @return
         */
        @RequestMapping("/god/go.do")
        public String go(int day)
        {
                InvestmentInstance.get().go(day);
                return "/god/show.do";
        }

        @RequestMapping("/god/end.do")
        public String end(InvestmentProduct product)
        {
        	if (product.getReturnDate().equals(this.currentDate))
            {
            	product.getQuota().multiply(product.getInterest());
            }
			return "/god/show.do";
        }
        
        //TODO 改BADMONEY狀態
        /**
         * 選中的項目的狀態改為壞賬，邏輯不對
         * @param product
         * @param state
         * @param model
         * @return
         */
        @RequestMapping("god/bad.do")
        public String bad(InvestmentProduct product,String state,Model model)
        {
        	product.setBadState();
        	log.debug(product.getState().toString());
        	return "/god/show.do";
        }
        
        /**
         * 添加一项理财产品
         * @param product
         * @return
         */
        @RequestMapping("/god/addProduct.do")
        public String addProduct(InvestmentProduct product)
        {
                if (StringUtils.isBlank(product.getName()))//如果前台没有命名，则随机分配一个名字
                {
                        Date d = new Date();
                        product.setName(new SimpleDateFormat("yyyyMMdd").format(d) + "_"
                                        + new Random(d.getTime()).nextInt(100) + d.getTime() % 1000);
                }
                
                product.setAnnualizedReturn(product.getAnnualizedReturn().divide(new BigDecimal(100)));
                product.setInterest(product.getInterest().divide(new BigDecimal(100)));
                
                InvestmentInstance.get().addProduct(product);
                return "/god/show.do";
        }
}
