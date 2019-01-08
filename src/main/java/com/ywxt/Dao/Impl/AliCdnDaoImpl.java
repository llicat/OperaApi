package com.ywxt.Dao.Impl;

import com.ywxt.Dao.AliCdnDao;
import com.ywxt.Domain.AliCdn;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliCdnDaoImpl implements AliCdnDao {

    private SessionFactory sessionFactory;

    public AliCdnDaoImpl() {
        Configuration configuration = new Configuration();
        this.sessionFactory = configuration.configure().buildSessionFactory();
    }

    // 分页查找
    public List<AliCdn> getCdnList(HashMap<String, Object> params, int pageNumber, int pageSize) {
        Session session = this.sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AliCdn.class);
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                if (e.getKey().equals("orderAsc")) {
                    criteria.addOrder(Order.asc((String) e.getValue()));
                } else if (e.getKey().equals("orderDesc")) {
                    criteria.addOrder(Order.desc((String) e.getValue()));
                } else {
                    criteria.add(Restrictions.eq(e.getKey(), e.getValue()));
                }
            }
        }
        criteria.addOrder(Order.asc("id"));
        criteria.setFirstResult((pageNumber - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        List<AliCdn> list = criteria.list();
        session.close();
        return list;
    }

    // 保存
    public void saveAliCdns(List<AliCdn> list) {
        Session session = this.sessionFactory.openSession();
        for (AliCdn aliCdn : list) {
            session.save(aliCdn);
        }
        session.close();
    }


    // 删除
    public void deleteAliCdnByAccessId(String accessId) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String hqlDelete = "delete AliCdn where accessKeyId = :accessKeyId";
        session.createQuery(hqlDelete)
                .setParameter("accessKeyId", accessId)
                .executeUpdate();
        tx.commit();
        session.close();
    }
}
