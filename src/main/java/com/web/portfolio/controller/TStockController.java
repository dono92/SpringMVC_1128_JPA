package com.web.portfolio.controller;

import com.web.portfolio.entity.Classify;
import com.web.portfolio.entity.TStock;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio/tstock")
public class TStockController {

    @PersistenceContext
    protected EntityManager em;

    @PostMapping(value = {"/", "/add"})
    @Transactional
    public TStock add(@RequestBody Map<String, String> map) {
        Classify classify = em.find(Classify.class, map.get("classify_id"));
        TStock ts = new TStock();
        ts.setName(map.get("name"));
        ts.setSymbol(map.get("symbol"));
        ts.setClassify(classify);
        em.persist(ts);
        em.flush(); // 取得最新 id
        return ts;
    }
    
    @PutMapping(value = {"/", "/update"})
    @Transactional
    public TStock update(@RequestBody Map<String, String> map) {
        Classify classify = em.find(Classify.class, map.get("classify_id"));
        TStock ts = em.find(TStock.class, map.get("id"));;
        ts.setName(map.get("name"));
        ts.setSymbol(map.get("symbol"));
        ts.setClassify(classify);
        em.persist(ts);
        em.flush();
        return ts;
    }
    
    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    @Transactional
    public Boolean delete(@PathVariable("id") Long id) {
        TStock tStock = em.find(TStock.class, id);
        em.remove(tStock);
        return true;
    }
    
    @GetMapping(value = {"/{id}", "/get/{id}"})
    @Transactional
    public TStock get(@PathVariable("id") Long id) {
        TStock tStock = em.find(TStock.class, id);
        return tStock;
    }

    @GetMapping(value = {"/", "/query"})
    public List<TStock> query() {
        Query query = em.createQuery("select t from TStock t");
        List<TStock> list = query.getResultList();
        return list;
    }

}
