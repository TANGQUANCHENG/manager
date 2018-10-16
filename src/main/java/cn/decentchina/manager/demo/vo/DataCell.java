package cn.decentchina.manager.demo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 唐全成
 * @date 2018-10-12
 */
@Data
public class DataCell {

    private String name;

    private String amount;

    private String budget;

    private String rate;

    public DataCell(String name, String amount, String budget, String rate) {
        this.name = name;
        this.amount = amount;
        this.budget = budget;
        this.rate = rate;
    }

    public DataCell() {
    }
}
