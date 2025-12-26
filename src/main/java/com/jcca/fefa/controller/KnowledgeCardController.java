package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcca.fefa.controller.bean.KnowledgeCardImportDTO;
import com.jcca.fefa.entity.KnowledgeCard;
import com.jcca.fefa.service.KnowledgeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeCardController {
    @Autowired
    private KnowledgeCardService cardService;

    @GetMapping("/list")
    public String listKnowledgeCards(
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "contentKeyword", required = false) String contentKeyword
    ) {

        Page<KnowledgeCard> pageParam = new Page<>(page, 10);
        QueryWrapper<KnowledgeCard> query = new QueryWrapper<>();

        // ===== 标题搜索（中文 title or 英文 title_en）=====
        if (title != null && !title.trim().isEmpty()) {
            query.and(q -> q
                    .like("title", title)
                    .or()
                    .like("title_en", title)
            );
            model.addAttribute("title", title);
        }

        // ===== 内容搜索（中文 content or 英文 content_en）=====
        if (contentKeyword != null && !contentKeyword.trim().isEmpty()) {
            query.and(q -> q
                    .like("content", contentKeyword)
                    .or()
                    .like("content_en", contentKeyword)
            );
            model.addAttribute("contentKeyword", contentKeyword);
        }

        IPage<KnowledgeCard> pageData = cardService.page(pageParam, query);
        model.addAttribute("pageData", pageData);
        model.addAttribute("activeMenu", "knowledge");

        return "knowledge-list";
    }

    // 显示新增知识卡表单页
    @GetMapping("/new")
    public String newKnowledgeCard(Model model) {
        model.addAttribute("knowledgeCard", new KnowledgeCard());
        return "knowledge-form";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public KnowledgeCard get(@PathVariable("id") Long id) {
        KnowledgeCard knowledgeCard = cardService.getById(id);
        return knowledgeCard;
    }

    @GetMapping("/getType")
    @ResponseBody
    public List<String> getType() {
        List<String> category = cardService.getCategory();
        return category;
    }

    @GetMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        try {
            // 文件名
            String fileName = "knowledgeCardTemplate.xlsx";

            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader(
                    "Content-Disposition",
                    "attachment;filename=" + fileName);

            // ===== 自定义多行表头 =====
            List<List<String>> head = new ArrayList<>();

            head.add(Arrays.asList("中文标题", "title"));
            head.add(Arrays.asList("中文内容", "content"));
            head.add(Arrays.asList("英文标题", "titleEn"));
            head.add(Arrays.asList("英文内容", "contentEn"));

            EasyExcel.write(response.getOutputStream())
                    .head(head)
                    .registerWriteHandler(new SimpleColumnWidthStyleStrategy(30))
                    .sheet("模板")
                    .doWrite(Collections.emptyList());


        } catch (Exception e) {
        }
    }

    @PostMapping("/import")
    @ResponseBody
    public String importKnowledgeCard(@RequestParam("file") MultipartFile file) {
        try {
            List<KnowledgeCardImportDTO> list =
                    EasyExcel.read(file.getInputStream())
                            .head(KnowledgeCardImportDTO.class)
                            .headRowNumber(2)
                            .sheet()
                            .doReadSync(); // 一次性读完

            int success = 0;
            int fail = 0;

            for (int i = 0; i < list.size(); i++) {
                KnowledgeCardImportDTO dto = list.get(i);

                // 4 个字段非空校验
                if (!hasText(dto.getTitle())
                        || !hasText(dto.getContent())
                        || !hasText(dto.getTitleEn())
                        || !hasText(dto.getContentEn())) {
                    fail++;
                    continue;
                }

                KnowledgeCard card = new KnowledgeCard();
                card.setTitle(dto.getTitle());
                card.setContent(dto.getContent());
                card.setTitleEn(dto.getTitleEn());
                card.setContentEn(dto.getContentEn());

                cardService.save(card);
                success++;
            }

            return "导入完成：成功 " + success + " 条，失败 " + fail + " 条";
        } catch (Exception e) {
            return "导入失败：" + e.getMessage();
        }
    }

    private boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
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