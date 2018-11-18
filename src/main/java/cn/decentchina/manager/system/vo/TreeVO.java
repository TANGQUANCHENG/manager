package cn.decentchina.manager.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeVO {

    private Integer total;

    private List<NavigationVO> rows;
}
