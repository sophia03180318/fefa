package com.jcca.fefa.mapper;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:31
 **/
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcca.fefa.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}