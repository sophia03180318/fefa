package com.jcca.fefa.mapper;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:32
 **/
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcca.fefa.entity.KnowledgeCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeCardMapper extends BaseMapper<KnowledgeCard> {
    @Select("select category from knowledge_card group by category")
    List<String> getCategory();

}