package com.example.command_pipeliner.presentation.product;

import com.example.command_pipeliner.presentation.common.IdempotentKeyBody;
import lombok.Data;

@Data
public class UpdateProductStockBody extends IdempotentKeyBody {

    private Integer stock;
}
