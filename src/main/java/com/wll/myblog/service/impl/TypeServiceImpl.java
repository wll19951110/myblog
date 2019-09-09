package com.wll.myblog.service.impl;

import com.wll.myblog.dao.TypeRepository;
import com.wll.myblog.exception.NotFoundException;
import com.wll.myblog.po.Type;
import com.wll.myblog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        return null;
    }

    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepository.getOne(id);
        if (type1==null){
            throw new NotFoundException("找不到该类");
        }
        BeanUtils.copyProperties(type,type1);
        return typeRepository.save(type1);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
