package com.wll.myblog.web.admin;

import com.wll.myblog.po.Type;
import com.wll.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分类列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String typeList(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){

        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }


    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * 修改类型-页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 类型添加
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String save(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //验证添加类型是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);

        if (t!=null){
            attributes.addFlashAttribute("message","添加成功！");

        }else {
            attributes.addFlashAttribute("message","添加失败！");
        }

        return "redirect:/admin/types";
    }


    /**
     * 类型修改
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String edit(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //验证添加类型是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.updateType(id,type);

        if (t!=null){
            attributes.addFlashAttribute("message","修改成功！");

        }else {
            attributes.addFlashAttribute("message","修改失败！");
        }

        return "redirect:/admin/types";
    }

    /**
     * 分类删除
     * @param id
     * @return
     */
    @GetMapping("/types/{id}")
    public String delete(@PathVariable Long id){
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }

}