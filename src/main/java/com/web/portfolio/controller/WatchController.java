package com.web.portfolio.controller;

import com.web.portfolio.entity.TStock;
import com.web.portfolio.entity.Watch;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio/watch")
public class WatchController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping(value = {"/{id}", "/get/{id}"})
    @Transactional
    public Watch get(@PathVariable("id") Long id) {
        Watch watch = em.find(Watch.class, id);
        watch.gettStocks().size(); // 因為 @ManyToMany 預設資料載入是 Lazy, 所以加入此行可取得 tStocks 資料
        return watch;
    }
    
    @GetMapping(value = {"/", "/query"})
    public List<Watch> query() {
        Query query = em.createQuery("select w from Watch w");
        List<Watch> list = query.getResultList();
        return list;
    }
    
    @PutMapping(value = {"/{id}", "/update/{id}"})
    @Transactional
    public Boolean update(@PathVariable("id") Long id, @RequestBody Map<String, String> map) {
        Watch o_Watch = get(id);
        if (o_Watch == null) {
            return false;
        }
        o_Watch.setName(map.get("name"));
        em.persist(o_Watch);
        em.flush();
        return true;
    }
    
    @GetMapping(value = {"/{id}/add/{tstock_id}"})
    @Transactional
    public Watch add_tstock(@PathVariable("id") Long id, @PathVariable("tstock_id") Long tstock_id) {
        Watch watch = em.find(Watch.class, id);
        TStock ts = em.find(TStock.class, tstock_id);
        watch.addtStock(ts);
        em.persist(watch);
        return get(id);
    }
    
    @DeleteMapping(value = {"/{id}/remove/{tstock_id}"})
    @Transactional
    public Watch remove_tstock(@PathVariable("id") Long id, @PathVariable("tstock_id") Long tstock_id) {
        Watch watch = em.find(Watch.class, id);
        TStock ts = em.find(TStock.class, tstock_id);
        watch.removetStock(ts);
        em.persist(watch);
        return get(id);
    }
    
}
