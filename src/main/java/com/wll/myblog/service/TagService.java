package com.wll.myblog.service;

import com.wll.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag findTagByName(String name);

    Page<Tag> listType(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer s);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

}
