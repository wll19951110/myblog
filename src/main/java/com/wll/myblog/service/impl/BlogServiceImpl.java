package com.wll.myblog.service.impl;

import com.wll.myblog.dao.BlogRepository;
import com.wll.myblog.exception.NotFoundException;
import com.wll.myblog.po.Blog;
import com.wll.myblog.po.Type;
import com.wll.myblog.service.BlogService;
import com.wll.myblog.util.MarkdownUtils;
import com.wll.myblog.util.MyBeanUtils;
import com.wll.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(b.getContent()));
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle() + "%"));
                }

                if (blog.getTypeId() != null && !"".equals(blog.getTypeId())) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }

                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.listBlog(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.listRecommendBlogTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        return null;
    }

    @Override
    public Long countBlog() {
        return null;
    }

    @Override
    public Blog saveBlog(Blog blog) {

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);

        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {

        Blog one = blogRepository.getOne(id);

        if (one == null) {
            throw new NotFoundException("该博客找不到");
        } else {
            BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
            blog.setUpdateTime(new Date());
            return blogRepository.save(one);
        }

    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
