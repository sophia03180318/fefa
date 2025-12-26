package com.jcca.fefa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcca.fefa.entity.KnowledgeCard;
import com.jcca.fefa.mapper.KnowledgeCardMapper;
import com.jcca.fefa.service.KnowledgeCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:50
 **/
@Service
public class KnowledgeCardServiceImpl extends ServiceImpl<KnowledgeCardMapper, KnowledgeCard>
        implements KnowledgeCardService {

    @Resource
    private KnowledgeCardMapper knowledgeCardMapper;
    @Override
    public List<String> getCategory() {
     return  knowledgeCardMapper.getCategory();
    }
}