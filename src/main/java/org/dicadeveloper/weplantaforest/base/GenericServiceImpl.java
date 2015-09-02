package org.dicadeveloper.weplantaforest.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.collect.Lists;

public abstract class GenericServiceImpl<T extends Base, D extends BaseDto, ID extends Serializable> implements GenericService<T, D, ID> {

    protected Class<T> _entityClass;

    protected Class<D> _dtoClass;

    protected DozerBeanMapper _mapper;

    protected JpaRepository<T, ID> _repository;

    @SuppressWarnings("unchecked")
    public GenericServiceImpl(DozerBeanMapper mapper, JpaRepository<T, ID> repository) {
        _mapper = mapper;
        _repository = repository;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        _entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        _dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public D findOne(ID id) {
        return _mapper.map(_repository.findOne(id), _dtoClass);
    }

    @Override
    public List<D> findAll() {
        List<D> result = Lists.newArrayList();
        for (T t : _repository.findAll()) {
            result.add(_mapper.map(t, _dtoClass));
        }
        return result;
    }

    @Override
    public Page<D> findAll(Pageable page) {
        List<D> result = new ArrayList<D>(page.getPageSize());
        Page<T> allEntitiesInPage = _repository.findAll(page);
        for (T t : allEntitiesInPage) {
            result.add(_mapper.map(t, _dtoClass));
        }
        Page<D> results = new PageImpl<D>(result, page, allEntitiesInPage.getTotalElements());
        return results;
    }

    @Override
    public void save(D dto) {
        T entity = _repository.saveAndFlush(_mapper.map(dto, _entityClass));
        dto.setId(entity.getId());
    }

    /**
     * Returns true in case at least one entity exists otherwise false.
     */
    @Override
    public boolean existsAtAll() {
        return _repository.count() > 0;
    }

    @Override
    public long count() {
        return _repository.count();
    }

    public void delete(ID entityId) {
        _repository.delete(entityId);
    }

}