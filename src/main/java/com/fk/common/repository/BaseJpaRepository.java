package com.fk.common.repository;

import com.fk.common.query.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义Repository基类
 * (放所有Repository都需要用到的方法)
 * @param <T>   实体
 * @param <ID>  实体id
 *
 * @author gaozhiqiang
 */
@NoRepositoryBean
public interface BaseJpaRepository<T,ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

    /**
     * 根据特定条件删除实体
     * @param object
     */
    int  deleteInBatch(T object);

    /**
     * 根据特定条件集合删除实体
     * @param objects
     */
    int  deleteInBatch(List<T> objects);
    
    /**
     * 批量删除实体
     * @param ids   实体id数组
     */
    int deleteByIds(ID[] ids);

    /**
     * 删除实体
     * @param id 实体id
     */
    void deleteById(ID id);

    /**
     * 不分页查询
     * @param searchFilters
     * @return
     */

    List<T> find(List<SearchFilter> searchFilters);

    /**
     * 分页查询
     * @param searchFilters
     * @param page
     * @return
     */
    Page<T> find(List<SearchFilter> searchFilters, Pageable page);

    /**
     * 根据filterMaps不分页查询
     * @param entity
     * @return
     */
    List<T> find(T entity);

    /**
     * 根据filterMaps分页查询
     * @param entity
     * @return
     */
    Page<T> find(T entity, Pageable page);

    /**
     * 查询单一对象
     * @param entity
     * @return
     */
    T findOne(T entity);

    /**
     * 查询单一对象
     * @param id
     * @return
     */
    T findOne(ID id);
}
