package com.jcca.fefa.controller;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/05 18:11
 **/

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jcca.fefa.controller.bean.ChallengeTaskImportDTO;
import com.jcca.fefa.entity.ChallengeTask;
import com.jcca.fefa.service.ChallengeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/challenge")
public class ChallengeTaskController {

    @Autowired
    private ChallengeTaskService service;

    @GetMapping("/list")
    public String list(
            @RequestParam(value = "contentKeyword", required = false) String contentKeyword,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Map<String, Object> map) {

        Page<ChallengeTask> p = new Page<>(page, 10);

        LambdaQueryWrapper<ChallengeTask> wrapper = new LambdaQueryWrapper<>();

        // 中文 or 英文 内容模糊搜索
        wrapper.and(
                contentKeyword != null && !contentKeyword.isEmpty(),
                w -> w.like(ChallengeTask::getContent, contentKeyword)
                        .or()
                        .like(ChallengeTask::getContentEn, contentKeyword)
        );

        // 类型筛选
        wrapper.eq(type != null && type != 0, ChallengeTask::getType, type);

        Page<ChallengeTask> pageData = service.page(p, wrapper);

        map.put("pageData", pageData);
        map.put("contentKeyword", contentKeyword);
        map.put("type", type);

        return "challenge-list";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public String save(ChallengeTask task) {
        service.saveOrUpdate(task);
        return "redirect:/challenge/list";
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.removeById(id);
        return "redirect:/challenge/list";
    }
    @PostMapping("/import")
    @ResponseBody
    public String importChallengeTask(@RequestParam("file") MultipartFile file) {
        int success = 0;
        int fail = 0;

        try {
            List<ChallengeTaskImportDTO> list =
                    EasyExcel.read(file.getInputStream())
                            .head(ChallengeTaskImportDTO.class)
                            .headRowNumber(2)
                            .sheet()
                            .doReadSync();

            for (ChallengeTaskImportDTO dto : list) {

                // ===== 基础字符串校验 =====
                if (!hasText(dto.getContent())
                        || !hasText(dto.getContentEn())
                        || !hasText(dto.getDays())
                        || !hasText(dto.getType())) {
                    fail++;
                    continue;
                }

                // ===== 数字安全转换 =====
                Integer days = safeInt(dto.getDays());
                Integer type = safeInt(dto.getType());
                Integer amountMin = safeInt(dto.getAmountMin());
                Integer amountMax = safeInt(dto.getAmountMax());

                // days 必须 > 0
                if (days == null || days < 1) {
                    fail++;
                    continue;
                }

                // type 只能是 1 / 2
                if (type == null || (type != 1 && type != 2)) {
                    fail++;
                    continue;
                }

                // 金额区间（可选）
                if (amountMin != null && amountMax != null && amountMin > amountMax) {
                    fail++;
                    continue;
                }

                // ===== 保存 =====
                ChallengeTask task = new ChallengeTask();
                task.setContent(dto.getContent().trim());
                task.setContentEn(dto.getContentEn().trim());
                task.setDays(days);
                task.setType(type);
                task.setAmountMin(amountMin);
                task.setAmountMax(amountMax);

                service.save(task);
                success++;
            }

            return "导入完成：成功 " + success + " 条，失败 " + fail + " 条";

        } catch (Exception e) {
            return "导入失败";
        }
    }

    @GetMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        try {
            String fileName = "challengeTaskTemplate.xlsx";

            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader(
                    "Content-Disposition",
                    "attachment;filename=" + fileName);
            // ===== 多行表头 =====
            List<List<String>> head = new ArrayList<>();
            head.add(Arrays.asList("中文内容", "content"));
            head.add(Arrays.asList("英文内容", "contentEn"));
            head.add(Arrays.asList("挑战天数 / 次数", "days"));
            head.add(Arrays.asList("任务类型（1累计 / 2连续）", "type"));
            head.add(Arrays.asList("金额下限", "amountMin"));
            head.add(Arrays.asList("金额上限", "amountMax"));

            EasyExcel.write(response.getOutputStream())
                    .head(head)
                    .registerWriteHandler(new SimpleColumnWidthStyleStrategy(30))
                    .sheet("模板")
                    .doWrite(Collections.emptyList());
        } catch (Exception e) {
        }
    }

    /**
     * 获取（用于编辑回显）
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public ChallengeTask get(@PathVariable("id") Long id) {
        return service.getById(id);
    }
    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private Integer safeInt(String s) {
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(s.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
