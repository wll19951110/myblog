package com.wll.myblog.web.admin;

import com.wll.myblog.po.Type;
import com.wll.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String typeList(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){

        model.addAttribute("page",typeService.listType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String save(Type type,Model model){

        Type t = typeService.saveType(type);

        if (t!=null){
            model.addAttribute("type",t);

        }else {
            model.addAttribute("message","添加失败");
        }

        return "redirect:/admin/types";
    }

}
