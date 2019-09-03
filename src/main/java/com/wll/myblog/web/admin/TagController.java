package com.wll.myblog.web.admin;

import com.wll.myblog.po.Tag;
import com.wll.myblog.service.TagService;
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
public class  TagController {

    @Autowired
    private TagService tagService;

    /**
     * 标签列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tagList(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){

        model.addAttribute("page",tagService.listType(pageable));

        return "admin/tags";
    }


    /**
     * 标签新增页
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String tagInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 标签修改页
     * @param id
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }


    /**
     * 标签新增
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String save(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){

        //验证名称是否重复
        Tag tag_1 = tagService.findTagByName(tag.getName());

        if (tag_1!=null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }

        if (result.hasErrors()){
            return "admin/tags-input";
        }

        //保存
        Tag t = tagService.saveTag(tag);

        if (t!=null){
            attributes.addFlashAttribute("message","添加成功！");

        }else {
            attributes.addFlashAttribute("message","添加失败！");
        }

        return "redirect:/admin/tags";

    }

    /**
     * 标签修改
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String save(@Valid Tag tag, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){

        //验证名称是否重复
        Tag tag_1 = tagService.findTagByName(tag.getName());

        if (tag_1!=null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }

        if (result.hasErrors()){
            return "admin/tags-input";
        }

        //保存
        Tag t = tagService.updateTag(id,tag);

        if (t!=null){
            attributes.addFlashAttribute("message","修改成功！");

        }else {
            attributes.addFlashAttribute("message","修改失败！");
        }

        return "redirect:/admin/tags";

    }

    /**
     * 标签删除
     * @param id
     * @return
     */
    @GetMapping("/tags/{id}")
    public String delete(@PathVariable Long id){
        tagService.deleteTag(id);
        return "redirect:admin/tags";
    }

}
