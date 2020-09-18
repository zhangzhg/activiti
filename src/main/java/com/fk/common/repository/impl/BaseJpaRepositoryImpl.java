package com.fk.common.repository.impl;

import com.fk.common.em.StatusEnum;
import com.fk.common.listener.IDeleteListenable;
import com.fk.common.listener.IModifyListenable;
import com.fk.common.query.DynamicSpecifications;
import com.fk.common.query.SearchFilter;
import com.fk.common.repository.BaseJpaRepository;
import com.fk.common.util.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.Serializable;
import java.util.*;

/**
 * 自定义Repository实现
 * (放所有Repository都需要用到的方法)
 *
 * @param <T>  实体
 * @param <ID> id
 */
public class BaseJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {
    private static final Logger logger = LoggerFactory.getLogger(BaseJpaRepositoryImpl.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager; // jpa 实体管理器

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public BaseJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public int deleteByIds(ID[] ids) {
        //逻辑删除
        if (IDeleteListenable.class.isAssignableFrom(getDomainClass())) {
            List<ID> list = Arrays.asList(ids);
            List<T> entities = super.findAllById(list);
            for (T entity : entities) {
                ((IDeleteListenable) entity).setStatus(StatusEnum.DELETED.toString());
            }
            int result =  this.saveAll(entities).size();
            return result;
        } else {
            //物理删除
            for(ID id : ids){
                deleteById(id);
            }
            return ids.length;
        }
    }

    public void deleteById(ID id) {
        Optional<T> optional = super.findById(id);
        T entity;
        if (optional.isPresent()) {
            entity = optional.get();
        } else {
            return;
        }

        //逻辑删除
        if (entity instanceof IModifyListenable) {
            ((IModifyListenable) entity).setModifierId(SessionUtils.getCurrentUserId());
            ((IModifyListenable) entity).setModifiedTime(new Date(System.currentTimeMillis()));
        }
        if (entity instanceof IDeleteListenable) {
            ((IDeleteListenable) entity).setStatus(StatusEnum.DELETED.toString());
            this.save(entity);
        } else {
            //物理删除
            super.delete(entity);
        }
    }

    @Override
    public void delete(T entity) {
        //逻辑删除
        if (entity instanceof IModifyListenable) {
            ((IModifyListenable) entity).setModifierId(SessionUtils.getCurrentUserId());
            ((IModifyListenable) entity).setModifiedTime(new Date(System.currentTimeMillis()));
        }
        if (entity instanceof IDeleteListenable) {
            ((IDeleteListenable) entity).setStatus(StatusEnum.DELETED.toString());
            this.save(entity);
        } else {
            //物理删除
            super.delete(entity);
        }
    }

    @Override
    public List<T> find(List<SearchFilter> searchFilters) {
        Specification<T> spec = DynamicSpecifications.bySearchFilter(searchFilters);
        return this.findAll(spec);
    }

    @Override
    public Page<T> find(List<SearchFilter> searchFilters, Pageable page) {
        Specification<T> spec = DynamicSpecifications.bySearchFilter(searchFilters);
        return this.findAll(spec, page);
    }

    @Override
    public List<T> find(T entity) {
        Specification<T> spec = getSpecification(entity);
        return this.findAll(spec);
    }

    @Override
    public Page<T> find(T entity, Pageable page) {
        Specification<T> spec = getSpecification(entity);
        return this.findAll(spec, page);
    }

    @Override
    public T findOne(T entity) {
        Specification<T> spec = getSpecification(entity);
        Optional<T> optional = super.findOne(spec);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public T findOne(ID id) {
        Optional<T> optional = super.findById(id);
        T entity = null;
        if (optional.isPresent()) {
            entity = optional.get();
        }

        return entity;
    }

    private Specification<T> getSpecification(T entity) {
        List<SearchFilter> searchFilters = SearchFilter.convertBean(entity);
        return DynamicSpecifications.bySearchFilter(searchFilters);
    }

    /**
     * 根据特定条件删除实体
     *
     * @param object
     */
    public int deleteInBatch(T object) {
        String hql = QueryUtils.getQueryString("DELETE FROM %s x", this.getDomainClass().getSimpleName());
        StringBuilder sb = new StringBuilder(hql);
        sb.append(" WHERE ");
        Map map = BeanMap.create(object);
        Set<String> keys = map.keySet();
        boolean firstAttrFlag = true;
        for (String key : keys) {
            String value = (String) map.get(key);
            if (StringUtils.isNoneBlank(value)) {
            	if(!firstAttrFlag) {
            		sb.append(" AND ");
            	}
                sb.append(String.format("%s.%s = '%s'", new Object[]{"x", key, value}));
                firstAttrFlag = false;
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("deleteInBatch parameter: {}", StringUtils.join(object, ","));
        }
        return this.entityManager.createQuery(sb.toString()).executeUpdate();
    }

    /**
     * 根据特定条件批量删除实体
     */
    public int deleteInBatch(List<T> objects) {
    	if(ObjectUtils.isEmpty(objects)) {
    		return 0;
    	}
    	if(objects.size()==1) {
    		return deleteInBatch(objects.get(0));
    	}
        String hql = QueryUtils.getQueryString("DELETE FROM %s x", this.getDomainClass().getSimpleName());
        StringBuilder sb = new StringBuilder(hql);
        sb.append(" WHERE ");
        for(Iterator<T> objectIter = objects.iterator();objectIter.hasNext();) {
        	T object = objectIter.next();
        	sb.append("(");
	        Map map = BeanMap.create(object);
	        boolean firstAttrFlag = true;
	        Set<String> keys = map.keySet();
	        for (Iterator<String> iter = keys.iterator();iter.hasNext();) {
	        	String key = iter.next();
	            String value = (String) map.get(key);
	            if (StringUtils.isNoneBlank(value)) {
	            	if(!firstAttrFlag) {
	            		sb.append(" AND ");
	            	}
	                sb.append(String.format("%s.%s = '%s'", new Object[]{"x", key, value}));
	                firstAttrFlag = false;
	            }
	        }
	        sb.append(")");
	        if(objectIter.hasNext()) {
	        	sb.append(" OR ");
	        }
        }
        if (logger.isInfoEnabled()) {
            logger.info("deleteInBatch parameter: {}", StringUtils.join(objects, ","));
        }
        return this.entityManager.createQuery(sb.toString()).executeUpdate();
    }

}
