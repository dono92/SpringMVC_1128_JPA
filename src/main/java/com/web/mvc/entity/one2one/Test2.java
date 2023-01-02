package com.web.mvc.entity.one2one;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class Test2 {
    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    public static void main(String[] args) throws Exception {
//        add("John", "Mary");
//        add("Tom", "Jane");
        queryHusband();
        queryWife();
//        get(Husband.class, 1051L);
//        get(Wife.class, 1301L);
//        update(1251L, "Vincent", "Anita");
//        delete(1151L);
    }
    
    public static void add(String name1, String name2) {
        Husband husband = new Husband();
        husband.setName(name1);
        
        Wife wife = new Wife();
        wife.setName(name2);
        
        husband.setWife(wife);
        
        em.getTransaction().begin();
        em.persist(husband);
        em.getTransaction().commit();
        System.out.println("Add OK !");
    }

    public static void queryHusband() throws Exception {
        List<Husband> list = em.createQuery("Select h From Husband h", Husband.class).getResultList();
        print(list);
    }
    
    public static void queryWife() throws Exception {
        List<Wife> list = em.createQuery("Select h From Wife h", Wife.class).getResultList();
        print(list);
    }
    
    public static void get(Class cls, Long id) throws Exception {
        Object object = em.find(cls, id);
        print(object);
    }
    
    public static void update(Long id, String name1, String name2) {
        Husband husband = em.find(Husband.class, id);
        if(husband == null) return;
        Wife wife = husband.getWife();
        if(name1 != null) {
            husband.setName(name1);
        }
        if(name2 != null) {
            wife.setName(name2);
        }
        em.getTransaction().begin();
        em.persist(husband);
        em.getTransaction().commit();
        System.out.println("Update OK !");
    }
    
    public static void delete(Long id) {
        Husband husband = em.find(Husband.class, id);
        if(husband == null) return;
        em.getTransaction().begin();
        em.remove(husband);
        em.getTransaction().commit();
        System.out.println("Delete OK !");
    }
    
    public static void print(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(object);
        System.out.println(json);
    }
}
