package com.ecommerce.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("messages")
	val messages: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

@Parcelize
data class ProductsItem(

	@field:SerializedName("br_name")
	val brName: String? = null,

	@field:SerializedName("discount_start_time")
	val discountStartTime: String? = null,

	@field:SerializedName("pr_price")
	val prPrice: String? = null,

	@field:SerializedName("pr_display")
	val prDisplay: String? = null,

	@field:SerializedName("total_stock")
	val totalStock: String? = null,

	@field:SerializedName("p_slug")
	val pSlug: String? = null,

	@field:SerializedName("pt_id")
	val ptId: String? = null,

	@field:SerializedName("cat_id")
	val catId: String? = null,

	@field:SerializedName("pr_name")
	val prName: String? = null,

	@field:SerializedName("discount_expire_time")
	val discountExpireTime: String? = null,

	@field:SerializedName("pr_percentOff")
	val prPercentOff: String? = null,

	@field:SerializedName("pr_code")
	val prCode: String? = null,

	@field:SerializedName("pr_weight")
	val prWeight: String? = null,

	@field:SerializedName("mer_id")
	val merId: String? = null,

	@field:SerializedName("pr_update_datetime")
	val prUpdateDatetime: String? = null,

	@field:SerializedName("pr_volume")
	val prVolume: String? = null,

	@field:SerializedName("pr_id")
	val prId: String? = null,

	@field:SerializedName("pre_order_status")
	val preOrderStatus: String? = null,

	@field:SerializedName("cat_sub_id")
	val catSubId: String? = null,

	@field:SerializedName("pr_delete_status")
	val prDeleteStatus: String? = null,

	@field:SerializedName("br_id")
	val brId: String? = null,

	@field:SerializedName("sale_for")
	val saleFor: String? = null,

	@field:SerializedName("pr_status")
	val prStatus: String? = null,

	@field:SerializedName("pr_description")
	val prDescription: String? = null,

	@field:SerializedName("pr_image")
	val prImage: List<String?>? = null
) : Parcelable

data class Data(

	@field:SerializedName("sub_categories")
	val subCategories: List<SubCategoriesItem?>? = null,

	@field:SerializedName("products")
	val products: ArrayList<ProductsItem>? = null
)

data class SubCategoriesItem(

	@field:SerializedName("sub_slug")
	var subSlug: String? = null,

	@field:SerializedName("sub_name")
	var subName: String? = null,

	@field:SerializedName("sub_id")
	var subId: String? = null,

	@field:SerializedName("fk_catid")
	var fkCatid: String? = null,

	var selected: Boolean = false
)
