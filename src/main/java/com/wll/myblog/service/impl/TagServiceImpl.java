package com.wll.myblog.service.impl;

import com.wll.myblog.dao.TagRepository;
import com.wll.myblog.po.Tag;
import com.wll.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return null;
    }

    @Override
    public Page<Tag> listType(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return null;
    }

    @Override
    public List<Tag> listTagTop(Integer s) {
        return null;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        return null;
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
