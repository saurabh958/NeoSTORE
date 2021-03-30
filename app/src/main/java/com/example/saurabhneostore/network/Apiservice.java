package com.example.saurabhneostore.network;

import com.example.saurabhneostore.model.FetchAccount.FetchDetailModel;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.model.MyCart.MyCartModel;
import com.example.saurabhneostore.model.Order.OrderModel;
import com.example.saurabhneostore.model.ProductModel;
import com.example.saurabhneostore.model.QuantityModel;
import com.example.saurabhneostore.model.RateModel;
import com.example.saurabhneostore.model.TableModel;
import com.example.saurabhneostore.model.myordermodel.DetailOrder;
import com.example.saurabhneostore.model.myordermodel.MyOrderModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiservice
{
    @FormUrlEncoded
    @POST("login")
    Call<LoginmModelz>checkdata(@Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("forgot")
    Call<LoginmModelz>forgetdata(@Field("email") String email);

    @FormUrlEncoded
    @POST("register")
    Call<LoginmModelz>createdata(@Field("first_name") String first_name,
                               @Field("last_name") String last_name,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("confirm_password") String confirm_password,
                               @Field("gender") String gender,
                               @Field("phone_no") String phone_no);

    @GET("getList")
    Call<TableModel>getProduct(@Query("product_category_id")String categoryid,
                               @Query("limit")Integer limt,
                               @Query("page")Integer pagez);
    @FormUrlEncoded
    @POST("update")
    Call<LoginmModelz>editDetail(@Header("access_token") String token,
                                 @Field("first_name")String fname,
                                 @Field("last_name")String lname,
                                 @Field("email")String email,
                                 @Field("dob")String dob,
                                 @Field("profile_pic")String pic,
                                 @Field("phone_no")String phone);

    @FormUrlEncoded
    @POST("change")
    Call<LoginmModelz>editPass(@Header("access_token")String token,
                               @Field("old_password")String old,
                               @Field("password")String pass,
                               @Field("confirm_password")String confpass);

    @GET("getDetail")
    Call<ProductModel>getProductDetail(@Query("product_id")String product_id);

    @FormUrlEncoded
    @POST("setRating")
    Call<RateModel> ratePost(@Field("product_id") String product_id,
                             @Field("rating") String rating);

    @FormUrlEncoded
    @POST("addToCart")
    Call<QuantityModel> quantityAdd(@Header("access_token") String access,
                                    @Field("product_id") String product_id,
                                    @Field("quantity") String quantity);

    @GET("cart")
    Call<MyCartModel>getCartDetail(@Header("access_token")String access);


    @FormUrlEncoded
    @POST("deleteCart")
    Call<QuantityModel> deleteItem(@Header("access_token") String access,
                                    @Field("product_id") String product_id);


    @FormUrlEncoded
    @POST("editCart")
    Call<QuantityModel> editItem(@Header("access_token") String access,
                                 @Field("product_id") String product_id,
                                 @Field("quantity")String Quantity);


    @FormUrlEncoded
    @POST("order")
    Call<OrderModel> orderItem(@Header("access_token") String access,
                                @Field("address") String Address);

    @GET("orderList")
    Call<MyOrderModel> getOrderList(@Header("access_token")String access);


    @GET("orderDetail")
    Call<DetailOrder> getOrderDetail(@Header("access_token")String access,
                                     @Query("order_id")String orderid);

    @GET("getUserData")
    Call<FetchDetailModel> fetchAccountDetail(@Header("access_token")String access);









}
