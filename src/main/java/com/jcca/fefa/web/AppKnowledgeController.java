package com.jcca.fefa.web;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jcca.fefa.common.ResultVo;
import com.jcca.fefa.common.ResultVoUtil;
import com.jcca.fefa.entity.AppUser;
import com.jcca.fefa.entity.KnowledgeCard;
import com.jcca.fefa.service.KnowledgeCardService;
import com.jcca.fefa.web.enums.ResultCodeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/appKnowledge")
public class AppKnowledgeController {

    @Resource
    private KnowledgeCardService knowledgeCardService;

    //无论哪个用户请求 每天获取同样的新的3条知识   到末尾后从第一条开始读  id自增 从1开始
     @PostMapping("/getKnowledge")
    public ResultVo getKnowledge(AppUser user) {
         LocalDate startDate = LocalDate.of(2025, 12, 25); // 系统上线日
         LocalDate today = LocalDate.now();
         long days = ChronoUnit.DAYS.between(startDate, today);

         int pageSize = 3;
         long offset = days * pageSize;

         long total = knowledgeCardService.count();
         if (total == 0) {
             return ResultVoUtil.success(Collections.emptyList());
         }
         offset = offset % total;

         List<KnowledgeCard> list = knowledgeCardService.list(
                 new LambdaQueryWrapper<KnowledgeCard>()
                         .orderByAsc(KnowledgeCard::getId)
                         .last("LIMIT " + offset + "," + pageSize)
         );

         if (list.size() < pageSize) {
             int remain = pageSize - list.size();
             List<KnowledgeCard> extra = knowledgeCardService.list(
                     new LambdaQueryWrapper<KnowledgeCard>()
                             .orderByAsc(KnowledgeCard::getId)
                             .last("LIMIT 0," + remain)
             );
             list.addAll(extra);
         }

         return ResultVoUtil.success(ResultCodeEnum.SUCCESS.getMessage(user.getLanguage()),list);
    }


}