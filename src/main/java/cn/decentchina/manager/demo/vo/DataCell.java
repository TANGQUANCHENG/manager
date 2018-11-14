package cn.decentchina.manager.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 唐全成
 * @date 2018-10-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCell {

    private String name;

    private String amount;

    private String budget;

    private String rate;
}
