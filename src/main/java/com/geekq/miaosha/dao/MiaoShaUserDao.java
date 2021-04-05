package com.geekq.miaosha.dao;

import com.geekq.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MiaoShaUserDao {

    @Select("select * from miaosha_user where nickname = #{nickname}")
    MiaoshaUser getByNickname(@Param("nickname") String nickname) ;

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(@Param("id") long id) ;


    @Update("update miaosha_user set password = #{password} where id = #{id}")
    void update(MiaoshaUser toBeUpdate);


    @Insert("insert into miaosha_user (id, nickname, password, salt, head, register_date, last_login_date) value " +
            "(#{id}, #{nickname}, #{password}, #{salt}, #{head}, #{registerDate}, #{lastLoginDate}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertMiaoShaUser(MiaoshaUser miaoshaUser);

}