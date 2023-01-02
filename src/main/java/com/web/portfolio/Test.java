package com.web.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import com.web.portfolio.entity.Classify;
import com.web.portfolio.entity.Investor;
import com.web.portfolio.entity.Portfolio;
import com.web.portfolio.entity.TStock;
import com.web.portfolio.entity.Watch;
import javax.persistence.EntityManager;

public class Test {
    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    static ObjectMapper om = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        init();
    }
    
    public static void init() {
        Investor investor1 = new Investor("admin", "1234", "admin@java.com", 10000000);
        Investor investor2 = new Investor("john", "1111", "john@java.com", 5000000);
        
        Classify classify1 = new Classify("股票", true);
        Classify classify2 = new Classify("匯率", true);
        Classify classify3 = new Classify("指數", false);
        
        TStock ts1 = new TStock("2330.TW", "台積電", classify1);
        TStock ts2 = new TStock("2317.TW", "鴻海", classify1);
        TStock ts3 = new TStock("1101.TW", "台泥", classify1);
        
        TStock ts4 = new TStock("USDTWD=x", "美金台幣", classify2);
        TStock ts5 = new TStock("JPYTWD=x", "日幣台幣", classify2);
        TStock ts6 = new TStock("CNYTWD=x", "人民幣台幣", classify2);
        
        TStock ts7 = new TStock("^TWII", "台灣加權", classify3);
        TStock ts8 = new TStock("^IXIC", "納斯達克", classify3);
        TStock ts9 = new TStock("^DJI", "道瓊工業", classify3);
        
        Portfolio portfolio1 = new Portfolio(60.5, 2000, investor1, ts1);
        Portfolio portfolio2 = new Portfolio(35.5, 5000, investor1, ts2);
        
        Watch watch = new Watch();
        watch.addtStock(ts1);
        watch.addtStock(ts2);
        watch.addtStock(ts4);
        watch.addtStock(ts6);
        watch.addtStock(ts7);
        watch.addtStock(ts9);
        watch.setInvestor(investor1);
        watch.setName("我的觀察股");
        
        em.getTransaction().begin();
        em.persist(ts1);
        em.persist(ts2);
        em.persist(ts3);
        em.persist(ts4);
        em.persist(ts5);
        em.persist(ts6);
        em.persist(ts7);
        em.persist(ts8);
        em.persist(ts9);
        em.persist(investor1);
        em.persist(investor2);
        em.persist(portfolio1);
        em.persist(portfolio2);
        em.persist(watch);
        em.getTransaction().commit();
        
        System.out.println("OK");
    }
    
    
    
}
