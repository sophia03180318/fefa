package com.jcca.fefa.web;

/**
 * @description:
 * @author: sophia
 * @create: 2025/12/03 15:52
 **/

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.jcca.fefa.common.ResultVo;
import com.jcca.fefa.common.ResultVoUtil;
import com.jcca.fefa.entity.AppUser;
import com.jcca.fefa.entity.ChallengeTask;
import com.jcca.fefa.service.ChallengeTaskService;
import com.jcca.fefa.web.enums.ResultCodeEnum;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/appChallenge")
public class AppChallengeController {

    @Resource
    private ChallengeTaskService challengeTaskService;


    /**
     * 根据用户存款(balance)获取符合金额区间的挑战任务
     */
    @PostMapping("/getNewChallenge")
    public ResultVo getNewChallenge(AppUser user) {

        if (ObjectUtils.isNull(user.getBalance())) {
            return ResultVoUtil.error(ResultCodeEnum.PARAM_EMPTY.getMessage(user.getLanguage()));
        }
        double balance = user.getBalance();
        List<ChallengeTask> list = challengeTaskService.list(
                new LambdaQueryWrapper<ChallengeTask>()
                        .le(ChallengeTask::getAmountMin, balance)  // amount_min <= balance
                        .ge(ChallengeTask::getAmountMax, balance)  // amount_max >= balance
                        //.orderByAsc(ChallengeTask::getId)          // 可选：保证顺序稳定
        );

        if (list == null || list.isEmpty()) {
            return ResultVoUtil.success(Collections.emptyList());
        }

        return ResultVoUtil.success(ResultCodeEnum.SUCCESS.getMessage(user.getLanguage()),list);
    }


}