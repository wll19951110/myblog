package com.wll.myblog.web;


import com.wll.myblog.po.Type;
import com.wll.myblog.service.BlogService;
import com.wll.myblog.service.TypeService;
import com.wll.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){

        List<Type> types = typeService.listTypeTop(10000);

        if (id == -1){
            id = types.get(0).getId();
        }

        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("types",types);
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
