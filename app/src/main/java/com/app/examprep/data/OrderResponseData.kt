package com.app.examprep.data

data class OrderResponseData(
    val cf_order_id: Int,
    val customer_details: CustomerDetails,
    val entity: String,
    val order_amount: Int,
    val order_currency: String,
    val order_expiry_time: String,
    val order_id: String,
    val order_meta: OrderMeta,
    val order_note: String,
    val order_splits: List<Any>,
    val order_status: String,
    val order_tags: Any,
    val order_token: String,
    val payment_link: String,
    val payments: Payments,
    val refunds: Refunds,
    val settlements: Settlements
)