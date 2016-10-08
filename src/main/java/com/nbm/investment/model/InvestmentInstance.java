package com.nbm.investment.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.nbm.investment.model.enums.InvestmentProductState;

/**
 * 表示一次投资模拟
 * 
 * 大部分的逻辑代码都在此类中
 * 
 * 由于当前没有架数据库，所有的信息都保存在内存中。因此建立此类，用来组织这些信息。
 * 
 * 本类有key字段，用于区分不同的模拟场景。但是当前为了简化开发，暂时不支持多个模拟共同进行，因此key字段基本无用。
 * 
 * 调用set方法，会将一个本类的实例保存在内存中，通过get方法获取这个实例。因此在项目启动或初始化过程中，要调用set方法
 * 
 * 模拟过程中与业务有关的所有信息，都会或直接或间接地通过本类访问。因此本类的各字段设计，应取决于运行期需要存储多少信息，以及这些信息需要用什么方式获取。
 * 
 * @author 玉哲
 *
 */
public class InvestmentInstance
{

        private final static Logger log = LoggerFactory.getLogger(InvestmentInstance.class);

        private static InvestmentInstance instance;

        public final static int DAY_OF_YEAR = 365;

        /**
         * 获取到当前已经开始的模拟信息。
         * 
         * 由于当前没有架数据库，所有的信息都保存在内存中。
         * 
         * 因此建立了InvestmentInstance这个类，用来组织这些信息。
         * 
         * 
         * @return
         */
        public static InvestmentInstance get()
        {

                if (instance == null)
                {
                        throw new RuntimeException("还没有开始模拟");
                }
                return instance;
        }

        public static void set(InvestmentInstance theIstance)
        {
                instance = theIstance;
                instance.currentDate = instance.startDate;
        }

        /**
         * 判断当前是否开始了一次模拟
         * 
         * @return
         */
        public static boolean have()
        {
                return instance != null;
        }

        public InvestmentInstance(String key, Date startDate)
        {
                super();
                this.key = key;
                this.startDate = startDate;
                this.currentDate = startDate;
                // this.totalBalance = new BigDecimal(0);
        }

        /**
         * 投资模拟的唯一标识
         * 
         * 暂时全局只有一个投资模拟实例，所以此标识无用
         */
        private String key;

        /**
         * 开始模拟的日期
         */
        private Date startDate;

        /**
         * 当前日期
         */
        private Date currentDate;

        // /**
        // * 总余额
        // */
        // private BigDecimal totalBalance;

        /**
         * 活跃中的理财产品
         * 
         * 
         */
        // private List<InvestmentProduct> activeProduct = new ArrayList<>();

        /**
         * 进行中的项目，为了获取方便，用product的name作为key
         * 
         * TODO 募集期算活跃？之前没有定义。此处这个列表，已经被用于给投资者显示有多少要可投资的项目，因此只应该显示募集期的项目，
         * 不应该显示履约期的项目
         * 
         * 该Map类型为TreeMap，可以根据插入顺序获取values
         * 
         */
        private Map<String, InvestmentProduct> activeProductMap = new TreeMap<>();

        /**
         * 已结束的项目，为了获取方便，用product的name作为key
         * 
         * 该Map类型为TreeMap，可以根据插入顺序获取values
         */
        private Map<String, InvestmentProduct> historyProductMap = new TreeMap<>();

        /**
         * 根据投资者分组的理财产品投资记录
         */
        private Multimap<String, InvestmentRecord> investmentRecordByinvestorName = ArrayListMultimap
                        .create();

        /**
         * 根据项目名字分组的理财产品投资记录
         */
        private Multimap<String, InvestmentRecord> investmentRecordByproductName = ArrayListMultimap
                        .create();

        /**
         * 根据投资者名字取所有的投资记录
         * 
         * @param investorName
         * @return
         */
        public Collection<InvestmentRecord> getInvestmentRecordByinvestorName(String investorName)
        {
                return investmentRecordByinvestorName.get(investorName);
        }

        /**
         * 根据项目名字取所有的投资记录
         * 
         * @param investorName
         * @return
         */
        public Collection<InvestmentRecord> getInvestmentRecordByproductName(String productName)
        {
                return investmentRecordByproductName.get(productName);
        }

        /**
         * 添加一次投资理财记录。投资日期设置为当天。
         * 
         * @param investorName
         *                投资者名称
         * @param productName
         *                理财产品名称
         * @param amount
         *                投资金额
         */
        public void addInvestmentRecord(String investorName, String productName, BigDecimal amount)
        {
                InvestmentRecord record = new InvestmentRecord(activeProductMap.get(productName));

                record.setInvestorName(investorName);
                record.setProductName(productName);
                record.setAmount(amount);
                record.setInvestDate(currentDate);

                InvestmentProduct p = activeProductMap.get(record.getProductName());

                log.debug("amount=[{}], subtract=[{}] ", amount,
                                p.getQuota().subtract(p.getMoneyNow()));

                if (amount.compareTo(p.getQuota().subtract(p.getMoneyNow())) > 0)
                {
                        log.debug("超额");
                        record.setAmount(p.getQuota().subtract(p.getMoneyNow()));
                }

                investmentRecordByproductName.put(record.getProductName(), record);
                investmentRecordByinvestorName.put(record.getInvestorName(), record);

                activeProductMap.get(record.getProductName()).addInvestmentRecord(record);
        }

        /**
         * 添加一个理财产品。理财产品的创建日期会被设置为当天
         * 
         * 不论该理财产品的各日期字段是什么值，都会被加入到活跃项目中。
         * 
         * 如果该理财产品实际上已经结束，则会在添加理财产品后的第一次结算日时转为结束。
         * 
         * @param product
         */
        public void addProduct(InvestmentProduct product)
        {
                product.setCreateDate(currentDate);

                product.settle(new LocalDate(currentDate));// TODO 待整理

                activeProductMap.put(product.getName(), product);
        }

        /**
         * 时钟前进若干天，不能传入负数
         * 
         * @param day
         */
        public void go(int day)
        {
                for (int i = 0; i < day; i++)
                {
                        doOneDay();
                }
        }

        public void doOneDay()
        {
                LocalDate now = new LocalDate(currentDate);
                LocalDate after = now.plusDays(1);

                // TODO 点击前进进行每日结算，但是写到哪里，实现方法是什么
                for (InvestmentProduct p : activeProductMap.values())
                {
                        InvestmentProductState s = p.getState();

                        p.settle(now);

                        if (p.getState() != s)
                        {
                                log.debug("[{}]状态发生改变，由[{}]变为[{}]", p.getName(), s, p.getState());
                        }
                }

                // 将结束状态的项目放到历史项目中
                Set<String> keyset = new HashSet<String>();
                for (String key : activeProductMap.keySet())
                {
                        if (activeProductMap.get(key).getState() == InvestmentProductState.FINISHED
                                        || activeProductMap.get(key).getState() == InvestmentProductState.FAILED)
                        {
                                keyset.add(key);
                        }
                }
                for (String key : keyset)
                {
                        InvestmentProduct p = activeProductMap.get(key);
                        log.debug("将[{}]项目由活跃变为不活跃，该项目的借款人返还利息为[{}]，投资者盈利合计为[{}]", p.getName(),
                                        p.getBorrowerInterest(), p.getTotalInvestorInterest());
                        historyProductMap.put(key, activeProductMap.get(key));
                        activeProductMap.remove(key);
                }

                currentDate = after.toDateMidnight().toDate();
                log.debug("时间由{}前进1天，变为{}", now, after);
        }

        // TODO 在上帝界面显示已筹是否放这里，不清楚
        public void isHave()
        {
                log.debug("wowowo");
        }

        public String getKey()
        {
                return key;
        }

        public void setKey(String key)
        {
                this.key = key;
        }

        public Date getStartDate()
        {
                return startDate;
        }

        public void setStartDate(Date startDate)
        {
                this.startDate = startDate;
        }

        @Override
        public String toString()
        {
                return "InvestmentInstance [key=" + key + ", startDate=" + startDate + "]";
        }

        public Date getCurrentDate()
        {
                return currentDate;
        }

        public Date getOneDayAfter()
        {
                return new LocalDate(currentDate).plusDays(1).toDateMidnight().toDate();
        }

        public Date getThirtyDaysAfter()
        {
                return new LocalDate(currentDate).plusDays(30).toDateMidnight().toDate();
        }

        public Date getDaysAfter(int days)
        {
                return new LocalDate(currentDate).plusDays(days).toDateMidnight().toDate();
        }

        /**
         * 账户余额
         * 
         * @return
         */
        public BigDecimal getTotalBalance()
        {
                // TODO 还没有考虑到坏账的情况
                BigDecimal result = new BigDecimal(0);
                for (InvestmentProduct i : activeProductMap.values())
                {
                        if (i.getState() == InvestmentProductState.SUBSCRIPTION)
                        {
                                result = result.add(i.getMoneyNow());
                        }
                }
                for (InvestmentProduct i : historyProductMap.values())
                {
                        if (i.getState() == InvestmentProductState.FINISHED)
                        {
                                result = result.add(i.getBorrowerInterest().subtract(
                                                i.getTotalInvestorInterest()));
                        }
                        if (i.getState() == InvestmentProductState.BADMONEY)
                        {
                                result = result.add(i.getBadMoney().subtract(i.getQuota()));
                        }
                }
                return result;
        }

        public Collection<InvestmentProduct> getActiveProduct()
        {
                return activeProductMap.values();
        }

        public Collection<InvestmentProduct> getHistoryProduct()
        {
                return historyProductMap.values();
        }

        /**
         * 
         * @return 平台盈利合计
         */
        public BigDecimal getTotalProfit()
        {
                BigDecimal result = new BigDecimal(0);
                for (InvestmentProduct i : historyProductMap.values())
                {
                        if (i.getState() != InvestmentProductState.FAILED
                                        && i.getState() != InvestmentProductState.BADMONEY)
                        {
                                result = result.add(i.getBorrowerInterest().subtract(
                                                i.getTotalInvestorInterest()));
                        }
                }
                return result;
        }

        /**
         * 
         * @return 投資者盈利合計
         */
        public BigDecimal getTotalInvestorInterest()
        {
                BigDecimal result = new BigDecimal(0);
                for (InvestmentProduct i : historyProductMap.values())
                {
                        if (i.getState() != InvestmentProductState.FAILED)
                        {
                                result = result.add(i.getTotalInvestorInterest());
                        }
                }
                return result;
        }

        /**
         * 应收未收
         */
        public BigDecimal getReceivableAccountReceivable()
        {
                BigDecimal result = new BigDecimal(0);
                for (InvestmentProduct i : activeProductMap.values())
                {
                        if (i.getState() == InvestmentProductState.COMPLIANCE)
                        {
                                result = result.add(i.getBorrowerInterest().add(i.getQuota()));
                        }
                }
                return result;
        }

        /**
         * 应付未付
         * 
         * @return
         */
        public BigDecimal getPayableWithout()
        {
                BigDecimal result = new BigDecimal(0);
                for (InvestmentProduct i : activeProductMap.values())
                {
                        if (i.getState() == InvestmentProductState.COMPLIANCE)
                        {
                                result = result.add(i.getTotalInvestorInterest().add(i.getQuota()));
                        }
                }
                return result;
        }

        public Multimap<String, InvestmentRecord> getInvestmentRecordByproductName()
        {
                return investmentRecordByproductName;
        }

        public void setInvestmentRecordByproductName(Multimap<String, InvestmentRecord> investmentRecordByproductName)
        {
                this.investmentRecordByproductName = investmentRecordByproductName;
        }
        
        public static void main(String[] args)
        {
                System.out.println(InvestmentInstance.class.getClassLoader().getResource("logback.xml"));
        }
}
