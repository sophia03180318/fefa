package com.jcca.fefa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/05 18:08
 **/

@Data
@TableName("challenge_task")
public class ChallengeTask {

    @TableId
    private Long id; //递增

    private String content;//中文内容

    private String contentEn;//英文内容

    private Integer days;  //挑战天数 /挑战次数

    /** 1：累计型任务(一次性任务算一次累计)；2：连续性任务 必须连续几天  只能是1或者2*/
    private Integer type;

    private Integer amountMax;//金额上限


    private Integer amountMin;//金额下限

}