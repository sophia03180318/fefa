package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcca.fefa.entity.KnowledgeCard;
import com.jcca.fefa.service.KnowledgeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeCardController {

    @Autowired
    private KnowledgeCardService cardService;

    // 知识卡列表（支持按标题关键字搜索和按类别过滤）
    @GetMapping("/list")
    public String listKnowledgeCards(Model model,
                                     @RequestParam(value="page", defaultValue="1") int page,
                                     @RequestParam(value="keyword", required=false) String keyword,
                                     @RequestParam(value="category", required=false) String category) {
        Page<KnowledgeCard> pageParam = new Page<>(page, 5);
        // 构建查询条件
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<KnowledgeCard> query =
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        if (category != null && !category.isEmpty()) {
            query.eq("category", category);
            model.addAttribute("currentCategory", category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            query.like("title", keyword);
            model.addAttribute("keyword", keyword);
        }
        IPage<KnowledgeCard> pageData = cardService.page(pageParam, query);
        model.addAttribute("pageData", pageData);
        return "knowledge-list";
    }

    // 显示新增知识卡表单页
    @GetMapping("/new")
    public String newKnowledgeCard(Model model) {
        model.addAttribute("knowledgeCard", new KnowledgeCard());
        return "knowledge-form";
    }

    // 显示编辑知识卡表单页
    @GetMapping("/edit")
    public String editKnowledgeCard(Model model, @RequestParam("id") Long id) {
        KnowledgeCard card = cardService.getById(id);
        model.addAttribute("knowledgeCard", card);
        return "knowledge-form";
    }

    // 提交保存知识卡（新增或编辑通用）
    @PostMapping("/save")
    public String saveKnowledgeCard(KnowledgeCard knowledgeCard) {
        // 判断是新增还是编辑（根据是否有 ID）
        if (knowledgeCard.getId() == null) {
            cardService.save(knowledgeCard);
        } else {
            cardService.updateById(knowledgeCard);
        }
        return "redirect:/knowledge/list";
    }

    // 删除知识卡
    @GetMapping("/delete")
    public String deleteKnowledgeCard(@RequestParam("id") Long id) {
        cardService.removeById(id);
        return "redirect:/knowledge/list";
    }
}