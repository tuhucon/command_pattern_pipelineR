package com.example.command_pipeliner.presentation.product;

import com.example.command_pipeliner.presentation.common.IdempotentKeyBody;
import lombok.Data;

@Data
public class UpdateProductPriceBody extends IdempotentKeyBody {

    private Integer price;
}
