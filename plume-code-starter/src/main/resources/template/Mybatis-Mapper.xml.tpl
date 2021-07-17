<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackageName}.mapper.${ClassName}Mapper">
  <resultMap id="BaseResultMap" type="${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix}">
#foreach(${fieldModel} in ${fieldModelList})
#if($fieldModel.pk)
    <id column="${fieldModel.columnName}" property="${fieldModel.name}" jdbcType="${fieldModel.jdbcType}"/>
#else
    <result column="${fieldModel.columnName}" property="${fieldModel.name}" jdbcType="${fieldModel.jdbcType}"/>
#end
#end
  </resultMap>
  <sql id="Base_Column_List">
#foreach(${fieldModel} in ${fieldModelList})
    ${fieldModel.columnName}#if($foreach.hasNext),#end
#end

  </sql>
  <select id="page" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List"/>
    from ${tableName}
  </select>
  <select id="selectByPrimaryKey" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List"/>
    from ${tableName}
    where
#foreach(${primaryKey} in ${primaryKeyList})
    ${primaryKey.columnName} = #{${primaryKey.name}, jdbcType=${primaryKey.jdbcType}}
    #if($foreach.hasNext) and #end
#end
  </select>
  <delete id="deleteByPrimaryKey" >
    delete from ${tableName}
    where
#foreach(${primaryKey} in ${primaryKeyList})
    ${primaryKey.columnName} = #{${primaryKey.name}, jdbcType=${primaryKey.jdbcType}}
    #if($foreach.hasNext) and #end
#end
    </delete>
    <insert id="insert" parameterType="${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix}">
    insert into ${tableName} (
#foreach(${fieldModel} in ${fieldModelList})
    ${fieldModel.columnName}#if($foreach.hasNext),#end
#end

    )
    values (
#foreach(${fieldModel} in ${fieldModelList})
    #{${fieldModel.columnName}, jdbcType=${fieldModel.jdbcType}}#if($foreach.hasNext),#end

#end
  )
  </insert>
  <insert id="insertSelective" parameterType="${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix}">
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
#foreach(${fieldModel} in ${fieldModelList})
    <if test="${fieldModel.name} != null">
         ${fieldModel.columnName}#if($foreach.hasNext),#end

    </if>
#end
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
#foreach(${fieldModel} in ${fieldModelList})
      <if test="${fieldModel.name} != null">
        #{${fieldModel.name},jdbcType=${fieldModel.jdbcType}}#if($foreach.hasNext),#end

      </if>
#end
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix}">
    update ${tableName}
    <set>
#foreach(${fieldModel} in ${fieldModelList})
    <if test="${fieldModel.name} != null">
        ${fieldModel.columnName} = #{${fieldModel.name},jdbcType=${fieldModel.jdbcType}}#if($foreach.hasNext),#end

    </if>
#end
    </set>
    where
#foreach(${primaryKey} in ${primaryKeyList})
    ${primaryKey.columnName} = #{${primaryKey.name}, jdbcType=${primaryKey.jdbcType}}
    #if($foreach.hasNext) and #end
#end
  </update>
  <update id="updateByPrimaryKey" parameterType="${basePackageName}.mapper.entity.${ClassName}${setting.entPostfix}">
    update ${tableName}
    set
#foreach(${fieldModel} in ${fieldModelList})
    ${fieldModel.columnName} = #{${fieldModel.name},jdbcType=${fieldModel.jdbcType}}#if($foreach.hasNext),#end

#end
    where
#foreach(${primaryKey} in ${primaryKeyList})
    ${primaryKey.columnName} = #{${primaryKey.name}, jdbcType=${primaryKey.jdbcType}}#if($foreach.hasNext) and #end
#end
  </update>
</mapper>
