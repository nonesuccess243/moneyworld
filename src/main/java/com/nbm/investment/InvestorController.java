package com.nbm.investment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nbm.investment.model.InvestmentInstance;

@Controller
public class InvestorController
{
        
        private final static Logger log = LoggerFactory.getLogger(InvestorController.class);
        
        @InitBinder
        public void initBinder(WebDataBinder binder)
        {
                binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                                "yyyy-MM-dd"), true, 10));
        }
        
        @RequestMapping("/investor/show.do")
        public String show(  Model model )
        {
                model.addAttribute("instance", InvestmentInstance.get());
//                log.debug(InvestmentInstance.get().getInvestmentRecordByinvestorName(model.asMap().get("investorName").toString()).toString());
//                log.debug(model.asMap().get("investorName").toString());
                return "/investor/index.jsp";
        }
        
        @RequestMapping("/investor/refreshName.do")
        public String refreshName(String investorName, String productName, Model model)
        {
        	    log.debug(investorName);
        	    log.debug( productName);
        	    
        	    //TODO 换名字的时候，按名字（investorName）显示已投资项目情况
        	    
        	    model.addAttribute("investorName", investorName);
        	    return "/investor/show.do";
        }
        
        @RequestMapping("/investor/invest.do")
        public String invest( String investorName, String productName, BigDecimal amount, Model model )
        {
                log.debug(investorName);
                log.debug( productName);
                // TODO amount大于quota-moneyNow，将quota-moneyNow设为amount返回界面
                
                if( amount == null || amount.equals(0) || productName == null || productName .equals(""))
                {
                	
                }else
                {
                	 InvestmentInstance.get().addInvestmentRecord(investorName,productName,amount);
                }
                	
                model.addAttribute("investorName", investorName);
                return "/investor/show.do";
        }

}
