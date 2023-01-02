package com.web.mvc.entity.one2many;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CarSystem {
    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    static ObjectMapper obj = new ObjectMapper();
        
    public static void menu() throws Exception {
        System.out.println("-----------------");
        System.out.println("1. 新增 Driver");
        System.out.println("2. 新增 Car");
        System.out.println("3. 查詢 Drivers");
        System.out.println("4. 查詢 Cars");
        System.out.println("5. 單查 Driver");
        System.out.println("6. 單查 Car");
        System.out.println("7. 買車");
        System.out.println("8. 賣車(過戶)");
        System.out.println("9. 資產");
        System.out.println("0. 離開 Exit");
        System.out.println("-----------------");
        Scanner sc = new Scanner(System.in);
        System.out.println("請輸入選項: ");
        switch(sc.next()) {
            case "1":
                System.out.println("請輸入 Driver 名稱: ");
                addDriver(sc.next());
                break;
            case "2":
                System.out.println("請輸入 Car 名稱: ");
                String newCarName = sc.next();
                System.out.println("請輸入 Price 價格: ");
                int newCost = sc.nextInt();
                addCar(newCarName, newCost);
                break;
            case "3":
                queryDrivers();
                break;
            case "4":
                queryCars();
                break;
            case "5":
                System.out.println("請輸入要查詢的 Driver 名稱: ");
                Object driver = getDriver(sc.next());
                if (driver != null) {
                    System.out.println(obj.writeValueAsString(driver));
                }
                break;
            case "6":
                System.out.println("請輸入要查詢的 Car 名稱: ");
                Object car = getCar(sc.next());
                if (car != null) {
                    System.out.println(obj.writeValueAsString(car));
                }
                break;
            case "7":
                System.out.println("請輸入買車人(Driver): ");
                String driverName = sc.next();
                System.out.println("請輸入車名(Car): ");
                String carName = sc.next();
                buyCar(driverName, carName);
                break;    
            case "8":
                System.out.println("請輸入賣車人(Driver): ");
                String sellerName = sc.next();
                System.out.println("請輸入車名(Car): ");
                String sellerCarName = sc.next();
                System.out.println("請輸入買車人(Driver): ");
                String buyerName = sc.next();
                sellCar(sellerName, sellerCarName, buyerName);
                break; 
            case "9":
                asset();
                break;
            case "0":    
                return;
        }
        menu();
    }
    
    public static void addDriver(String name) {
        Driver driver = new Driver();
        driver.setName(name);
        em.getTransaction().begin();
        em.persist(driver);
        em.getTransaction().commit();
        System.out.println("Driver 新增成功 !");
    }
    
    public static void addCar(String name, int cost) {
        Price price = new Price();
        price.setCost(cost);
        
        Car car = new Car();
        car.setName(name);
        car.setPrice(price);
        
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        System.out.println("Car 新增成功 !");
    }
    
    public static void queryDrivers() throws Exception {
        em.clear();
        List<Driver> drivers = em.createQuery("Select d From Driver d").getResultList();
        System.out.println(obj.writeValueAsString(drivers));
    }
    
    public static void queryCars() throws Exception {
        em.clear();
        List<Car> cars = em.createQuery("Select c From Car c").getResultList();
        System.out.println(obj.writeValueAsString(cars));
    }
    
    public static Object getDriver(String name) {
        Query q = em.createQuery("Select d From Driver d Where d.name =:name");
        q.setParameter("name", name);
        int size = q.getResultList().size();
        if(size == 0) {
            System.out.println("查無此人");
            return null;
        }
        return q.getSingleResult();
    }
    
    public static Object getCar(String name) {
        Query q = em.createQuery("Select c From Car c Where c.name =:name");
        q.setParameter("name", name);
        int size = q.getResultList().size();
        if(size == 0) {
            System.out.println("查無此車");
            return null;
        }
        return q.getSingleResult();
    }
    
    public static void buyCar(String driverName, String carName) {
        Object o1 = getDriver(driverName);
        if(o1 == null) {
            System.out.println("查無此人");
            return;
        }
        Object o2 = getCar(carName);
        if(o2 == null) {
            System.out.println("查無此車");
            return;
        }
        Driver driver = (Driver)o1;
        Car car = (Car)o2;
        car.setDriver(driver);
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        System.out.println("買車成功 !");
    }
    
    public static void sellCar(String sellerName, String carName, String buyerName) {
        Object o1 = getDriver(sellerName);
        if(o1 == null) {
            System.out.println("查無此賣家");
            return;
        }
        Object o2 = getCar(carName);
        if(o2 == null) {
            System.out.println("查無此車");
            return;
        }
        Object o3 = getDriver(buyerName);
        if(o3 == null) {
            System.out.println("查無此買家");
            return;
        }
        Car car = (Car)o2;
        Driver buyer = (Driver)o3;
        car.setDriver(buyer);
        
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        System.out.println("賣車(過戶)成功 !");
    }
    
    public static void asset() {
        em.clear();
        List<Driver> drivers = em.createQuery("Select d From Driver d").getResultList();
        drivers.stream().forEach(d -> {
            int sum = d.getCars().stream().mapToInt(c -> c.getPrice().getCost()).sum();
            System.out.printf("%s $%,d\n", d.getName(), sum);
        });
    }
    
    
    public static void main(String[] args) throws Exception {
        menu();
    }
}
