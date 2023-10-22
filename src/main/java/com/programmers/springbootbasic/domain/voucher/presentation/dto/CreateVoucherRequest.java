package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;

public class CreateVoucherRequest {
    private VoucherTypeEnum voucherTypeEnum;
    private Integer benefitValue;

    private CreateVoucherRequest(VoucherTypeEnum voucherTypeEnum, Integer benefitValue) {
        this.voucherTypeEnum = voucherTypeEnum;
        this.benefitValue = benefitValue;
    }

    public VoucherTypeEnum getVoucherType() {
        return voucherTypeEnum;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }

    public static CreateVoucherRequest from(String voucherType, Integer benefitValue) {
        VoucherTypeEnum voucherEnum = VoucherTypeEnum.of(voucherType);
        return new CreateVoucherRequest(voucherEnum, benefitValue);
    }

    public Voucher toEntity() {
        return new Voucher(voucherTypeEnum.getVoucherType(benefitValue), benefitValue);
    }
}