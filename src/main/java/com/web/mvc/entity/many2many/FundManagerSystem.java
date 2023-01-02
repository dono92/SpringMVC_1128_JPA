package com.web.mvc.entity.many2many;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

public class FundManagerSystem {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    static ObjectMapper obj = new ObjectMapper();

    public static void menu() throws Exception {
        System.out.println("---------------------");
        System.out.println("1. 新增 股票 Stock");
        System.out.println("2. 新增 基金 Fund");
        System.out.println("3. 加入 基金成分股");
        System.out.println("4. 查詢 所有股票");
        System.out.println("5. 查詢 所有基金");
        System.out.println("0. Exit");
        System.out.println("---------------------");
        Scanner sc = new Scanner(System.in);
        System.out.println("請輸入選項: ");
        switch (sc.next()) {
            case "1":
                System.out.println("請輸入股票代號: ");
                addStock(sc.next());
                break;
            case "2":
                System.out.println("請輸入基金名稱: ");
                addFund(sc.next());
                break;
            case "3":
                System.out.println("請輸入基金名稱: ");
                addFundStock(sc.next());
                break;
            case "4":
                queryStocks();
                break;
            case "5":
                queryFunds();
                break;
            case "0":
                return;
        }
        menu();
    }

    public static void main(String[] args) throws Exception {
        menu();
    }

    private static void addStock(String symbol) throws Exception {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        em.getTransaction().begin();
        em.persist(stock);
        em.getTransaction().commit();
        System.out.println("Stock 新增成功 !");
    }

    private static void addFund(String name) throws Exception {
        Fund fund = new Fund();
        fund.setName(name);
        em.getTransaction().begin();
        em.persist(fund);
        em.getTransaction().commit();
        System.out.println("Fund 新增成功 !");
    }

    private static void addFundStock(String name) throws Exception {
        Fund fund = null;
        try {
            fund = em.createQuery("Select f From Fund f Where f.name=:name", Fund.class)
                    .setParameter("name", name)
                    .getSingleResult();
            System.out.println("基金基本資料:" + obj.writeValueAsString(fund));
            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("請輸入成分股股票代號(或按下 N 離開): ");
                String symbol = sc.next();
                if (symbol.equalsIgnoreCase("n")) {
                    break;
                }
                Stock stock = null;
                try {
                    stock = em.createQuery("Select s From Stock s Where s.symbol=:symbol", Stock.class)
                            .setParameter("symbol", symbol)
                            .getSingleResult();
                    fund.getStocks().add(stock);
                    em.getTransaction().begin();
                    em.persist(fund);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println("無此股票代號~");
                }
            }
        } catch (Exception e) {
            System.out.println("無此基金~");
            return;
        }

    }

    private static void queryStocks() throws Exception {
        em.clear();
        List<Stock> stocks = em.createQuery("Select s From Stock s").getResultList();
        
        for (Stock s : stocks) {
            s.getFunds().size();
        }
        //System.out.println(obj.writeValueAsString(stocks));
        prettyPrintingStock(stocks);
    }

    private static void queryFunds() throws Exception {
        em.clear();
        List<Fund> funds = em.createQuery("Select f From Fund f").getResultList();
        for (Fund f : funds) {
            f.getStocks().size();
        }
        //System.out.println(new Gson().toJson(funds));
        //System.out.println(obj.writeValueAsString(funds));
        prettyPrintingFund(funds);
    }

    private static void prettyPrintingFund(List<Fund> funds) {
        String leftAlignFormat = "| %-4d | %-4s | %-20s |%n";
        System.out.format("+------+------+----------------------+%n");
        System.out.format("| ID   | Name | Stocks               |%n");
        System.out.format("+------+------+----------------------+%n");
        for (Fund f : funds) {
            String stocksString = f.getStocks().stream().map(s -> s.getSymbol()).collect(Collectors.joining(", ")); 
            System.out.format(leftAlignFormat, f.getId(), f.getName(), stocksString);
        }
        System.out.format("+------+------+----------------------+%n");
        System.out.println("按任意鍵繼續...");
        new Scanner(System.in).nextLine();
    }
    
    private static void prettyPrintingStock(List<Stock> stocks) {
        String leftAlignFormat = "| %-4d | %-8s | %-20s |%n";
        System.out.format("+------+----------+----------------------+%n");
        System.out.format("| ID   | Name     | Funds                |%n");
        System.out.format("+------+----------+----------------------+%n");
        for (Stock s : stocks) {
            String fundsString = s.getFunds().stream().map(f -> f.getName()).collect(Collectors.joining(", ")); 
            System.out.format(leftAlignFormat, s.getId(), s.getSymbol(), fundsString);
        }
        System.out.format("+------+----------+----------------------+%n");
        System.out.println("按任意鍵繼續...");
        new Scanner(System.in).nextLine();
    }
}
