package com.wll.myblog.service.impl;

import com.wll.myblog.dao.TagRepository;
import com.wll.myblog.exception.NotFoundException;
import com.wll.myblog.po.Tag;
import com.wll.myblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Tag findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listType(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids)){
            String[] idArray = ids.split(",");
            for (int i = 0;i<idArray.length;i++) {
                list.add(new Long(idArray[i]));
            }
        }

        return tagRepository.findAllById(list);
    }

    @Override
    public List<Tag> listTagTop(Integer s) {
        return null;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {

        Tag t = tagRepository.getOne(id);

        if (t==null){
            throw new NotFoundException("该标签找不到");
        }else {
            BeanUtils.copyProperties(tag,t);
            return tagRepository.save(t);
        }

    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
