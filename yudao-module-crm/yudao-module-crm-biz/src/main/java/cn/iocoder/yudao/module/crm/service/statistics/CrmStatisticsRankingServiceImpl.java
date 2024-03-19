package cn.iocoder.yudao.module.crm.service.statistics;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.module.crm.controller.admin.statistics.vo.CrmStatisticsRanKRespVO;
import cn.iocoder.yudao.module.crm.controller.admin.statistics.vo.CrmStatisticsRankReqVO;
import cn.iocoder.yudao.module.crm.dal.mysql.statistics.CrmStatisticsRankingMapper;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * CRM 排行榜统计 Service 实现类
 *
 * @author anhaohao
 */
@Service
@Validated
public class CrmStatisticsRankingServiceImpl implements CrmStatisticsRankingService {

    @Resource
    private CrmStatisticsRankingMapper rankMapper;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;

    @Override
    public List<CrmStatisticsRanKRespVO> getContractPriceRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectContractPriceRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getReceivablePriceRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectReceivablePriceRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getContractCountRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectContractCountRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getProductSalesRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectProductSalesRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getCustomerCountRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectCustomerCountRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getContactsCountRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectContactsCountRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getFollowCountRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectFollowCountRank);
    }

    @Override
    public List<CrmStatisticsRanKRespVO> getFollowCustomerCountRank(CrmStatisticsRankReqVO rankReqVO) {
        return getRank(rankReqVO, rankMapper::selectFollowCustomerCountRank);
    }

    /**
     * 获得排行版数据
     *
     * @param rankReqVO  参数
     * @param rankFunction 排行榜方法
     * @return 排行版数据
     */
    private List<CrmStatisticsRanKRespVO> getRank(CrmStatisticsRankReqVO rankReqVO, Function<CrmStatisticsRankReqVO, List<CrmStatisticsRanKRespVO>> rankFunction) {
        // 1. 获得用户编号数组
        rankReqVO.setUserIds(getUserIds(rankReqVO.getDeptId()));
        if (CollUtil.isEmpty(rankReqVO.getUserIds())) {
            return Collections.emptyList();
        }
        // 2. 获得排行数据
        List<CrmStatisticsRanKRespVO> ranks = rankFunction.apply(rankReqVO);
        if (CollUtil.isEmpty(ranks)) {
            return Collections.emptyList();
        }
        ranks.sort(Comparator.comparing(CrmStatisticsRanKRespVO::getCount).reversed());
        // 3. 拼接用户信息
        appendUserInfo(ranks);
        return ranks;
    }

    /**
     * 拼接用户信息（昵称、部门）
     *
     * @param ranks 排行榜数据
     */
    private void appendUserInfo(List<CrmStatisticsRanKRespVO> ranks) {
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertSet(ranks, CrmStatisticsRanKRespVO::getOwnerUserId));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));
        ranks.forEach(rank -> MapUtils.findAndThen(userMap, rank.getOwnerUserId(), user -> {
            rank.setNickname(user.getNickname());
            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> rank.setDeptName(dept.getName()));
        }));
    }

    /**
     * 获得部门下的用户编号数组，包括子部门的
     *
     * @param deptId 部门编号
     * @return 用户编号数组
     */
    public List<Long> getUserIds(Long deptId) {
        // 1. 获得部门列表
        List<Long> deptIds = convertList(deptApi.getChildDeptList(deptId), DeptRespDTO::getId);
        deptIds.add(deptId);
        // 2. 获得用户编号
        return convertList(adminUserApi.getUserListByDeptIds(deptIds), AdminUserRespDTO::getId);
    }

}